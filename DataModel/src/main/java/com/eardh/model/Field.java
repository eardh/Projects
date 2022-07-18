package com.eardh.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.apache.ibatis.annotations.One;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 字段
 * @author eardh
 * @date 2022/6/10 20:21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Field {

    private String id;

    @ID(groups = {Crud.Create.class})
    private String entityId;

    @NotNull(groups = {Crud.Create.class})
    @Length(min = 1, max = 50, message = "字段名格式错误")
    private String fieldName;

    @Length(max = 200, message = "描述太长")
    private String description;

    @Length(max = 500, message = "备注太长")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Null(message = "创建时间不能修改")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    public Field(String id, String entityId, String fieldName, String description, String remark, Date createTime) {
        this.id = id;
        this.entityId = entityId;
        this.fieldName = fieldName;
        this.description = description;
        this.remark = remark;
        this.createTime = createTime;
    }

    @TableField(exist = false)
    @Null
    private CdmAttribute cdmAttribute;

    @TableField(exist = false)
    @Null
    private LdmAttribute ldmAttribute;

    @TableField(exist = false)
    @Null
    private PdmAttribute pdmAttribute;

    @TableField(exist = false)
    @Null
    private OomAttribute oomAttribute;
}
