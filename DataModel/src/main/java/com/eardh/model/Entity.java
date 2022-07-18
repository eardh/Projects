package com.eardh.model;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.convert.ValueConverter;

import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.List;

/**
 * 实体
 * @author eardh
 * @date 2022/6/10 20:19
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Entity {

    private String id;

    @ID(groups = {Crud.Create.class})
    private String projectId;

    @TableField("name")
    @NotNull(groups = {Crud.Create.class})
    @Length(min = 1, max = 50, message = "实体名格式错误")
    private String entityName;

    @NotNull(groups = {Crud.Create.class})
    @Length(min = 1, max = 50, message = "物理表名格式错误")
    private String tableName;

    @Length(max = 500, message = "备注太长")
    private String remark;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Null
    private Date createTime;

    @TableField(value = "last_modify_time", fill = FieldFill.UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Null
    private Date lastModTime;

    public Entity(String id, String projectId, String entityName, String tableName, String remark, Date createTime, Date lastModTime) {
        this.id = id;
        this.projectId = projectId;
        this.entityName = entityName;
        this.tableName = tableName;
        this.remark = remark;
        this.createTime = createTime;
        this.lastModTime = lastModTime;
    }

    @TableField(exist = false)
    @Null
    private List<Field> fields;

}
