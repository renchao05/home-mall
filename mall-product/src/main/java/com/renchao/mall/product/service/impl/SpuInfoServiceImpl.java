package com.renchao.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.renchao.common.constant.ProductConstant;
import com.renchao.common.to.*;
import com.renchao.common.to.es.SkuEsModel;
import com.renchao.common.utils.R;
import com.renchao.mall.product.entity.*;
import com.renchao.mall.product.feign.CouponFeignService;
import com.renchao.mall.product.feign.SearchFeignService;
import com.renchao.mall.product.feign.WareFeignService;
import com.renchao.mall.product.service.*;
import com.renchao.mall.product.vo.spuinfo.*;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.renchao.common.utils.PageUtils;
import com.renchao.common.utils.Query;

import com.renchao.mall.product.dao.SpuInfoDao;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


@Service("spuInfoService")
public class SpuInfoServiceImpl extends ServiceImpl<SpuInfoDao, SpuInfoEntity> implements SpuInfoService {

    @Autowired
    private SpuInfoDescService descService;

    @Autowired
    private SpuImagesService spuImagesService;

    @Autowired
    private ProductAttrValueService attrValueService;

    @Autowired
    private CouponFeignService couponFeignService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private WareFeignService wareFeignService;

    @Autowired
    SearchFeignService searchFeignService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SpuInfoEntity> page = this.page(
                new Query<SpuInfoEntity>().getPage(params),
                new QueryWrapper<SpuInfoEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPageWrapper(Map<String, Object> params) {
        Object key = params.get("key");
        Object catelogId = params.get("catelogId");
        Object brandId = params.get("brandId");
        Object status = params.get("status");
        LambdaQueryWrapper<SpuInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!StringUtils.isEmpty(catelogId) && !catelogId.equals("0"), SpuInfoEntity::getCatalogId, catelogId);
        wrapper.eq(!StringUtils.isEmpty(brandId) && !brandId.equals("0"), SpuInfoEntity::getBrandId, brandId);
        if (!StringUtils.isEmpty(key)) {
            wrapper.and(w -> w.eq(SpuInfoEntity::getId, key).or().like(SpuInfoEntity::getSpuName, key));
        }
        wrapper.eq(!StringUtils.isEmpty(status), SpuInfoEntity::getPublishStatus, status);
        IPage<SpuInfoEntity> page = this.page(new Query<SpuInfoEntity>().getPage(params), wrapper);
        return new PageUtils(page);
    }

    /**
     * 商品上架
     */
    @Override
    public R spuUp(Long spuId) {
        // 获取skuInfo
        List<SkuInfoEntity> skuInfos = skuInfoService.listBySpuId(spuId);
        if (CollectionUtils.isEmpty(skuInfos)) {
            throw new RuntimeException("没有需要上架是商品");
            // TODO 没有需要上架是sku商品 异常需要统一处理
        }

        // TODO 查询热度信息 hotScore 暂时默认0
        Long hotScore = 0L;


        SpuInfoEntity spuInfo = this.getById(spuId);
        // 获取品牌
        BrandEntity brand = brandService.getById(spuInfo.getBrandId());
        // 获取分类
        CategoryEntity category = categoryService.getById(spuInfo.getCatalogId());
        // 获取属性attrs,而且是可检索的
        List<ProductAttrValueEntity> attrList = attrValueService.getBySpuIdAndSearch(spuId);
        List<SkuEsModel.Attrs> attrsList = null;
        if (!CollectionUtils.isEmpty(attrList)) {
            attrsList = attrList.stream().map(attr -> {
                SkuEsModel.Attrs attrs = new SkuEsModel.Attrs();
                attrs.setAttrId(attr.getAttrId());
                attrs.setAttrName(attr.getAttrName());
                attrs.setAttrValue(attr.getAttrValue());
                return attrs;
            }).collect(Collectors.toList());
        }


        List<SkuEsModel.Attrs> finalAttrsList = attrsList;
        List<SkuEsModel> skuEsModels = skuInfos.stream().map(skuInfo -> {
            // 基本信息
            SkuEsModel skuEsModel = new SkuEsModel();
            BeanUtils.copyProperties(skuInfo, skuEsModel);
            skuEsModel.setSkuPrice(skuInfo.getPrice());

            // 图片信息skuImg
            SkuImagesEntity images = skuImagesService.getBySkuId(skuInfo.getSkuId());
            skuEsModel.setSkuImg(images.getImgUrl());

            // 热度信息hotScore
            skuEsModel.setHotScore(hotScore);

            //        private Boolean hasStock;   // 查询库存，然后判断
            Integer stock = (Integer) wareFeignService.getStock(skuInfo.getSkuId()).get("stock");
            skuEsModel.setHasStock(stock != null && stock >= 0);

            // 品牌信息 brandName brandImg
            skuEsModel.setBrandName(brand.getName());
            skuEsModel.setBrandImg(brand.getLogo());

            // 三级分类 catalogName
            skuEsModel.setCatalogName(category.getName());

            // 属性信息
            skuEsModel.setAttrs(finalAttrsList);

            return skuEsModel;
        }).collect(Collectors.toList());
        // TODO 事务，可以采用队列 柔性事务-可靠消息+最终一致性方案（异步确保型）
        R r = searchFeignService.saveProduct(skuEsModels);
        if (r.get("code").equals(0)) {
            SpuInfoEntity infoEntity = new SpuInfoEntity();
            infoEntity.setId(spuId);
            infoEntity.setPublishStatus(ProductConstant.PublishStatus.ONLINE.getCode());
            this.updateById(infoEntity);
        }

        return r;
    }

    @Override
    public Map<Long, SpuInfoTo> getSpuMapBySkuIds(List<Long> skuIds) {
        List<SkuInfoEntity> list = skuInfoService.listByIds(skuIds);
        Set<Long> spuIds = list.stream().map(SkuInfoEntity::getSpuId).collect(Collectors.toSet());
        List<SpuInfoEntity> spuInfos = this.listByIds(spuIds);
        Map<Long, SpuInfoTo> spuMap = spuInfos.stream().collect(Collectors.toMap(SpuInfoEntity::getId, spuInfo -> {
            SpuInfoTo spuInfoTo = new SpuInfoTo();
            BeanUtils.copyProperties(spuInfo, spuInfoTo);
            return spuInfoTo;
        }));
        return list.stream().collect(Collectors.toMap(SkuInfoEntity::getSkuId, info -> spuMap.get(info.getSpuId())));
    }

    @GlobalTransactional
    @Override
    public void saveSpuInfo(SpuInfoVo spuInfo) {
        // 1、保存SPU基本信息：pms_spu_info
        SpuInfoEntity spuInfoEntity = new SpuInfoEntity();
        BeanUtils.copyProperties(spuInfo, spuInfoEntity);
        spuInfoEntity.setCreateTime(new Date());
        spuInfoEntity.setUpdateTime(new Date());
        this.save(spuInfoEntity);
        Long spuId = spuInfoEntity.getId();
        Long catalogId = spuInfo.getCatalogId();
        Long brandId = spuInfo.getBrandId();

        // 2、保存spu的描述图片：pms_spu_info_desc
        descService.saveSpuDecript(spuInfo.getDecript(), spuId);

        // 3、保存spu图片集：pms_spu_images
        spuImagesService.saveBatchSpuImages(spuInfo.getImages(), spuId);

        // 4、保存spu规格参数：pms_product_attr_value
        attrValueService.saveBatchBaseAttrs(spuInfo.getBaseAttrs(), spuId);

        // 5、保存spu的积分信息：sms_spu_bounds
        saveSpuBounds(spuInfo.getBounds(), spuId);

        // 6、保存所有sku信息：
        // 6.1、sku基本信息：pms_sku_info
        // 6.2、sku图片信息：pms_sku_images
        // 6.3、sku销售属性信息：pms_sku_sale_attr_value
        // 6.4、sku优惠、满减等信息：sms_sku_ladder，sms_sku_full_reduction，sms_member_price
        saveSkuInfo(spuInfo.getSkus(), spuId, catalogId, brandId);

    }

    /**
     * 保存spu的积分信息：sms_spu_bounds
     */
    public void saveSpuBounds(Bounds bounds, Long spuId) {
        if (bounds == null) {
            return;
        }
        SpuBoundsTo boundsTo = new SpuBoundsTo();
        BeanUtils.copyProperties(bounds, boundsTo);
        boundsTo.setSpuId(spuId);
        boundsTo.setWork((byte) 15);// TODO 前端未传数据，默认15
        couponFeignService.saveSpuBounds(boundsTo);
    }

    /**
     * 保存所有sku信息
     */
    private void saveSkuInfo(List<Skus> skus, Long spuId, Long catalogId, Long brandId) {
        if (CollectionUtils.isEmpty(skus)) {
            return;
        }
        skus.forEach(sku -> {
            // 6.1、sku基本信息：pms_sku_info
            SkuInfoEntity skuInfo = new SkuInfoEntity();
            BeanUtils.copyProperties(sku, skuInfo);
            sku.getImages().forEach(img -> {
                if (img.getDefaultImg() == 1) {
                    skuInfo.setSkuDefaultImg(img.getImgUrl());
                }
            });
            skuInfo.setSpuId(spuId);
            skuInfo.setCatalogId(catalogId);
            skuInfo.setBrandId(brandId);
            skuInfoService.save(skuInfo);

            // 6.2、sku图片信息：pms_sku_images
            Long skuId = skuInfo.getSkuId();
            skuImagesService.saveBatchImages(sku.getImages(), skuId);

            // 6.3、sku销售属性信息：pms_sku_sale_attr_value
            skuSaleAttrService.saveBatchAttr(sku.getAttr(), skuId);

            // 6.4、sku打折信息：sms_sku_ladder
            BigDecimal b0 = new BigDecimal(0);
            Integer fullCount = sku.getFullCount();
            BigDecimal discount = sku.getDiscount();
            if (fullCount != 0 && discount.compareTo(b0) > 0) {
                SkuLadderTo skuLadderTo = new SkuLadderTo();
                skuLadderTo.setSkuId(skuId);
                skuLadderTo.setFullCount(fullCount);
                skuLadderTo.setDiscount(discount);
                skuLadderTo.setAddOther(sku.getCountStatus());
                couponFeignService.saveSkuLadder(skuLadderTo);
            }


            // 6.5、sku满减信息：sms_sku_full_reduction
            BigDecimal fullPrice = sku.getFullPrice();
            BigDecimal reducePrice = sku.getReducePrice();
            if (fullPrice.compareTo(b0) > 0 && reducePrice.compareTo(b0) > 0) {
                SkuFullReductionTo fullReductionTo = new SkuFullReductionTo();
                fullReductionTo.setSkuId(skuId);
                fullReductionTo.setFullPrice(sku.getFullPrice());
                fullReductionTo.setReducePrice(sku.getReducePrice());
                fullReductionTo.setAddOther(sku.getPriceStatus());
                couponFeignService.saveSkuFullReduction(fullReductionTo);
            }

            // 6.6、会员价信息：sms_member_price
            List<MemberPrice> memberPrices = sku.getMemberPrice();
            if (!CollectionUtils.isEmpty(memberPrices)) {
                List<MemberPriceTo> memberPriceTos = memberPrices.stream()
                        .filter(memberPrice -> memberPrice.getPrice().compareTo(b0) > 0)
                        .map(memberPrice -> {
                            MemberPriceTo memberPriceTo = new MemberPriceTo();
                            memberPriceTo.setSkuId(skuId);
                            memberPriceTo.setMemberLevelId(memberPrice.getId());
                            memberPriceTo.setMemberLevelName(memberPrice.getName());
                            memberPriceTo.setMemberPrice(memberPrice.getPrice());
                            return memberPriceTo;
                        }).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(memberPriceTos)) {
                    couponFeignService.saveMemberPrices(memberPriceTos);
                }
            }
        });
    }
}