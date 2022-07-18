package com.eardh.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eardh.model.enums.SearchType;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * 逻辑属性
 * @author eardh
 * @date 2022/6/10 20:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LdmAttribute implements BaseAttribute {

    @TableId(value = "field_id", type = IdType.INPUT)
    @ID(groups = {Crud.Create.class})
    private String fieldId;

    @TableField(value = "foreign_key")
    @NotNull(groups = {Crud.Create.class})
    private Boolean isForeignKey;

    @NotNull(groups = {Crud.Create.class})
    private Boolean isSearch;

    @NotNull(groups = {Crud.Create.class})
    private Boolean isSorted;

    @NotNull(groups = {Crud.Create.class})
    private Boolean isUnique;

    @NotNull(groups = {Crud.Create.class})
    private SearchType searchType;

}
