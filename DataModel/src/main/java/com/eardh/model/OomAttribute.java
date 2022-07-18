package com.eardh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 面向对象属性
 * @author eardh
 * @date 2022/6/10 20:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OomAttribute implements BaseAttribute{

    @TableId(value = "field_id", type = IdType.INPUT)
    @ID(groups = {Crud.Create.class})
    private String fieldId;

    @NotNull(groups = {Crud.Create.class})
    private Boolean addReq;

    @NotNull(groups = {Crud.Create.class})
    private Boolean updateReq;

    @NotNull(groups = {Crud.Create.class})
    private Boolean deleteReq;

    @NotNull(groups = {Crud.Create.class})
    private Boolean queryReq;

    @NotNull(groups = {Crud.Create.class})
    private Boolean isEnum;

    private Integer assoProgress;

    private Boolean progressReq;
}
