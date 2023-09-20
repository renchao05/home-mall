package com.renchao.mall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import com.renchao.common.validator.ListValue;
import com.renchao.common.validator.group.AddGroup;
import com.renchao.common.validator.group.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 *
 * @author renchao
 * @email renchao05@gmail.com
 * @date 2022-07-05 11:37:42
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 品牌id
     */
    @TableId
    @NotNull(message = "品牌ID不能为空！", groups = UpdateGroup.class)
    @Null(message = "品牌ID必须为空！", groups = AddGroup.class)
    private Long brandId;
    /**
     * 品牌名
     */
    @NotBlank(message = "品牌名不能为空！", groups = AddGroup.class)
    private String name;
    /**
     * 品牌logo地址
     */
    @NotNull(message = "URL地址为空！", groups = {AddGroup.class})
    @URL(message = "必须是合法的URL地址！", groups = {AddGroup.class,UpdateGroup.class})
    private String logo;
    /**
     * 介绍
     */
    @NotNull(message = "介绍不能为空！", groups = {AddGroup.class})
    private String descript;
    /**
     * 显示状态[0-不显示；1-显示]
     */
	@ListValue(values = {0,1},message = "必须是0或者1",groups = {AddGroup.class,UpdateGroup.class})
    private Integer showStatus;
    /**
     * 检索首字母
     */
    @Pattern(regexp = "^[a-zA-Z]$", message = "必须是一个字母！",groups = {AddGroup.class})
    private String firstLetter;
    /**
     * 排序
     */
    @NotNull(message = "必须是大于0的整数！",groups = {AddGroup.class})
    @Min(value = 0, message = "必须是大于0的整数！",groups = {AddGroup.class})
    private Integer sort;

}
