package com.eardh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 实体持久化层接口
 * @author eardh
 * @date 2022/6/10 21:18
 */
@Mapper
public interface EntityMapper extends BaseMapper<Entity> {

    /**
     * 查询实体列表，分页或全部
     * @param page {@link Page}
     * @param projectId 项目ID
     * @return 实体列表
     */
    List<Entity> queryAllOrPageByProjectId(@Param("page") Page<Entity> page, @Param("projectId") String projectId);
}
