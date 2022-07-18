package com.eardh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eardh.model.enums.DataType;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

/**
 * 概念属性
 * @author eardh
 * @date 2022/6/10 20:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CdmAttribute implements BaseAttribute{

    @TableId(value = "field_id", type = IdType.INPUT)
    @ID(groups = {Crud.Create.class})
    private String fieldId;

    @NotNull(groups = {Crud.Create.class})
    private DataType dataType;

    @NotNull(groups = {Crud.Create.class})
    @Range(min = 1, max = 65535, message = "长度超限")
    private Integer dataLength;
}
