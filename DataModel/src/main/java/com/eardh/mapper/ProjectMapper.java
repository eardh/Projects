package com.eardh.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目持久化层接口
 * @author eardh
 * @date 2022/6/10 21:18
 */
@Mapper
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 查询项目列表，分页或全部
     * @param page {@link Page}
     * @param userId 用户ID
     * @return 项目列表
     */
    List<Project> queryAllOrPageByUserId(@Param("page") Page<Entity> page, @Param("userId") String userId);
}
