package com.eardh.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eardh.config.exception.DataOprException;
import com.eardh.mapper.EntityMapper;
import com.eardh.mapper.ProjectMapper;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import com.eardh.service.IEntityService;
import com.eardh.service.IProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author eardh
 * @date 2022/6/12 18:53
 */
@Service
@Slf4j
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EntityMapper entityMapper;

    @Autowired
    private IEntityService entityService;

    @Override
    public List<Project> getProjectsByUserId(String userId, Integer currentPage, Integer pageSize) {
        Page<Entity> page = null;
        if (pageSize != 0) {
            page = new Page<>(currentPage, pageSize);
        }
        return projectMapper.queryAllOrPageByUserId(page, userId);
    }

    @Override
    public boolean removeProject(String projectId) {
        LambdaQueryWrapper<Project> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Project::getId, projectId);
        int d = projectMapper.delete(wrapper);
        if (d == 0) {
            throw new DataOprException("项目删除失败");
        }
        LambdaQueryWrapper<Entity> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Entity::getProjectId, projectId)
                .select(Entity::getId);
        List<Entity> list = entityMapper.selectList(wrapper1);
        for (Entity entity : list) {
            entityService.removeEntity(entity.getId());
        }
        return true;
    }

}
