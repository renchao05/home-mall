package com.renchao.gulimall.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.gulimall.product.dao.CategoryBrandRelationDao;
import com.renchao.gulimall.product.vo.Catalog2Vo;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.gulimall.product.dao.CategoryDao;
import com.renchao.gulimall.product.entity.CategoryEntity;
import com.renchao.gulimall.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Transactional
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private CategoryBrandRelationDao categoryBrandRelationDao;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查询数据
        List<CategoryEntity> category = baseMapper.selectList(null);

        // 数据存入map,方便后面使用
        Map<Long, CategoryEntity> map = new HashMap<>();
        category.forEach(c -> map.put(c.getCatId(), c));

        List<CategoryEntity> parents = new ArrayList<>();
        // 按父节点进行分类，然后再把分类后的数据存入对应的父节点
        Map<Long, List<CategoryEntity>> collect = category.stream().collect(Collectors.groupingBy(CategoryEntity::getParentCid));
        collect.forEach((p, c) -> {
            if (p != 0) {
                CategoryEntity categoryParent = map.get(p);
                // 排序后存入父节点
                categoryParent.addChildren(sort(c));
                if (categoryParent.getParentCid() == 0) {
                    parents.add(categoryParent);
                }
            }
        });
        // 排序后返回
        return sort(parents);
    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        //TODO 1、检查当前删除的菜单是否被引用

        baseMapper.deleteBatchIds(asList);
    }

    @Override
    public Long[] findCatelogPath(Long catelogId) {
        Long id = catelogId;
        ArrayList<Long> path = new ArrayList<>();
        path.add(id);
        while (id != null && id != 0) {
            id = baseMapper.selectById(id).getParentCid();
            if (id != 0) {
                path.add(id);
            }
        }
        Collections.reverse(path);
        return path.toArray(new Long[0]);
    }

    //    @CacheEvict(value = "category", key = "'getLevel1CategoryList'")
//    @Caching(evict = {
//            @CacheEvict(value = "category", key = "'getLevel1CategoryList'"),
//            @CacheEvict(value = "category", key = "'getCatalogJson'")
//    })
    @CacheEvict(value = "category", allEntries = true)
    @Override
    public void updateDetails(CategoryEntity category) {
        this.updateById(category);
        String name = category.getName();
        if (!StringUtils.isEmpty(name)) {
            categoryBrandRelationDao.updateCategory(category.getCatId(), name);
        }
    }

    /**
     * 获取一级分类
     */
    // category 是缓存分区，一般按照业务类型分
    // 表示该方法的结果需要缓存，如果缓存中有，方法不会调用
    @Cacheable(value = {"category"}, key = "#root.methodName", sync = true)
    @Override
    public List<CategoryEntity> getLevel1CategoryList() {
//        System.out.println("方法被调用。。。");
        LambdaQueryWrapper<CategoryEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryEntity::getParentCid, 0);
        return this.list(wrapper);
    }

    /**
     * SpringCache
     */
    @Cacheable(value = "category", key = "#root.methodName", sync = true)
    public Map<Long, List<Catalog2Vo>> getCatalogJson() {
//        System.out.println("方法被调用。。。。。。");
        return getCatalogJsonNoCache();
    }

    /**
     * Redisson 分布式锁
     * 废弃
     */
    public Map<Long, List<Catalog2Vo>> getCatalogJsonDistributedLock() {
        // 获取锁
        RLock lock = redissonClient.getLock("catalogJsonLock");
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (catalogJson == null) {
            try {
                // 上锁
                lock.lock();
                catalogJson = ops.get("catalogJson");
                if (catalogJson == null) {
                    System.out.println("查询数据库 。。。。。。。。。。。。。");
                    Map<Long, List<Catalog2Vo>> catalogJsonNoCache = getCatalogJsonNoCache();
                    ops.set("catalogJson", JSON.toJSONString(catalogJsonNoCache));
                    return catalogJsonNoCache;
                }
            } finally {
                // 解锁
                lock.unlock();
            }

        }
        return JSON.parseObject(catalogJson, new TypeReference<Map<Long, List<Catalog2Vo>>>() {
        });
    }

    /**
     * 获取三级分类，Redis缓存 + 本地锁
     * 分布式锁 - 参考Redis6笔记
     * 废弃
     */
    public Map<Long, List<Catalog2Vo>> getCatalogJsonLocalLock() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (catalogJson == null) {
            synchronized (this) {
                catalogJson = ops.get("catalogJson");
                if (catalogJson == null) {
                    System.out.println("查询数据库。。。。。。。。。。。。。");
                    Map<Long, List<Catalog2Vo>> catalogJsonNoCache = getCatalogJsonNoCache();
                    ops.set("catalogJson", JSON.toJSONString(catalogJsonNoCache));
                    return catalogJsonNoCache;
                }
            }
        }
        return JSON.parseObject(catalogJson, new TypeReference<Map<Long, List<Catalog2Vo>>>() {
        });
    }

    /**
     * 获取三级分类，简单Redis缓存
     * 废弃
     */
    public Map<Long, List<Catalog2Vo>> getCatalogJsonSimpleLock() {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String catalogJson = ops.get("catalogJson");
        if (catalogJson == null) {
            Map<Long, List<Catalog2Vo>> catalogJsonNoCache = getCatalogJsonNoCache();
            ops.set("catalogJson", JSON.toJSONString(catalogJsonNoCache));
            return catalogJsonNoCache;
        }
        return JSON.parseObject(catalogJson, new TypeReference<Map<Long, List<Catalog2Vo>>>() {
        });
    }

    /**
     * 获取三级分类，没有缓存
     */
    public Map<Long, List<Catalog2Vo>> getCatalogJsonNoCache() {
        // 一级分类
        HashMap<Long, List<Catalog2Vo>> map = new HashMap<>();
        List<CategoryEntity> categoryList = listWithTree();
        categoryList.forEach(category -> {
            Long cat1Id = category.getCatId();
            List<CategoryEntity> childrenList = category.getChildren();
            List<Catalog2Vo> catalog2Vos = new ArrayList<>();
            // 二级分类
            childrenList.forEach(children -> {
                Long cat2Id = children.getCatId();
                Catalog2Vo catalog2Vo = new Catalog2Vo();
                catalog2Vo.setCatalog1Id(cat1Id);
                catalog2Vo.setId(cat2Id);
                catalog2Vo.setName(children.getName());
                List<CategoryEntity> ccs = children.getChildren();
                ArrayList<Catalog2Vo.Catalog3Vo> catalog3Vos = new ArrayList<>();
                // 三级分类
                ccs.forEach(cc -> {
                    Catalog2Vo.Catalog3Vo catalog3Vo = new Catalog2Vo.Catalog3Vo();
                    catalog3Vo.setCatalog2Id(cat2Id);
                    catalog3Vo.setId(cc.getCatId());
                    catalog3Vo.setName(cc.getName());
                    catalog3Vos.add(catalog3Vo);
                });
                catalog2Vo.setCatalog3List(catalog3Vos);
                catalog2Vos.add(catalog2Vo);
            });
            map.put(cat1Id, catalog2Vos);
        });
        return map;
    }

    /**
     * 排序
     */
    private List<CategoryEntity> sort(List<CategoryEntity> cs) {
        return cs.stream().sorted((c1, c2) -> {
            Integer s1 = c1.getSort();
            Integer s2 = c2.getSort();
            if (s1 == null || s2 == null) {
                return 1;
            }
            return s1 - s2;
        }).collect(Collectors.toList());
    }

}