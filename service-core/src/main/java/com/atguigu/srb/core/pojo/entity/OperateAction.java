package com.atguigu.srb.core.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 操作行为记录表
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="OperateAction对象", description="操作行为记录表")
public class OperateAction implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
      @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "操作主键id")
    private Long operateId;

    @ApiModelProperty(value = "操作类型")
    private String operateType;

    @ApiModelProperty(value = "操作内容")
    private String content;

    @ApiModelProperty(value = "操作用户id")
    @TableField("actUser_id")
    private Long actuserId;

    @ApiModelProperty(value = "操作用户")
    @TableField("actUser")
    private String actuser;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "逻辑删除(1:可用，0:不可用)")
    @TableField("is_deleted")
    @TableLogic
    private Boolean deleted;


}
