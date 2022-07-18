package com.eardh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eardh.model.enums.DataType;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 物理属性
 * @author eardh
 * @date 2022/6/10 20:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PdmAttribute implements BaseAttribute{

    @TableId(value = "field_id", type = IdType.INPUT)
    @ID(groups = {Crud.Create.class})
    private String fieldId;

    @TableField("code")
    @NotNull(groups = {Crud.Create.class})
    private String codeName;

    @NotNull(groups = {Crud.Create.class})
    private DataType dataType;

    @NotNull(groups = {Crud.Create.class})
    @Range(min = 1, max = 65535, message = "长度超限")
    private Integer dataLength;

    @NotNull(groups = {Crud.Create.class})
    private Integer dataPrecision;

    @NotNull(groups = {Crud.Create.class})
    private Boolean primaryKey;

    @TableField("foreign_key")
    @NotNull(groups = {Crud.Create.class})
    private Boolean isForeignKey;

    @NotNull(groups = {Crud.Create.class})
    private Boolean isUnique;

    @NotNull(groups = {Crud.Create.class})
    private Boolean notNull;

    @Length(max = 200, message = "默认值太长")
    private String defaultValue;

}
