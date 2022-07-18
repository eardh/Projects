package com.eardh.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.eardh.model.Answer;
import com.eardh.model.Entity;
import com.eardh.model.Field;
import com.eardh.model.Project;
import com.eardh.service.IEntityService;
import com.eardh.service.IProjectService;
import com.eardh.utils.Constant;
import com.eardh.validate.Crud;
import com.eardh.validate.ID;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;
import java.awt.*;
import java.util.List;

/**
 * 项目操作接口
 * @author eardh
 * @date 2022/6/12 19:21
 */
@RestController
@RequestMapping("/project")
@Validated
@Slf4j
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    /**
     * 获取当前用户项目列表
     * @param currentPage 当前页面
     * @param pageSize 页面大小
     * @return 响应体
     */
    @GetMapping
    public Answer getProjects(@RequestParam(value = "currentPage", required = false) Integer currentPage,
                              @RequestParam(value = "pageSize", defaultValue = "0", required = false) Integer pageSize) {
        log.info("获取项目列表");
        List<Project> projects = projectService.getProjectsByUserId(Constant.userId, currentPage, pageSize);
        return Answer.success(projects);
    }

    /**
     * 创建项目
     * @param project {@link Project}
     * @return 响应体
     */
    @PostMapping
    public Answer createProject(@Validated(value = {Crud.Create.class, Default.class}) @RequestBody Project project) {
        log.info("创建项目");
        project.setUserId(Constant.userId);
        boolean save = projectService.save(project);
        if (!save) {
            Answer.fail("操作失败");
        }
        return Answer.success(MapUtil.builder().put("projectId", project.getId()).build());
    }

    /**
     * 更新项目信息
     * @param projectId 项目ID
     * @param project {@link Project}
     * @return 响应体
     */
    @PutMapping("/{projectId}")
    public Answer updateProject(@ID @PathVariable String projectId,
                                @Validated @RequestBody Project project) {
        log.info("更新项目信息，项目ID：{}", projectId);
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Project::getId, projectId);
        boolean update = projectService.update(project, wrapper);
        if (!update) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }

    /**
     * 删除项目
     * @param projectId 项目ID
     * @return 响应体
     */
    @DeleteMapping("/{projectId}")
    public Answer removeProject(@ID @PathVariable String projectId) {
        log.info("删除项目，项目ID：{}", projectId);
        boolean b = projectService.removeProject(projectId);
        if (!b) {
            Answer.fail("操作失败");
        }
        return Answer.success();
    }

    /**
     * 获取项目详情
     * @param projectId 项目ID
     * @return 响应体
     */
    @GetMapping("/{projectId}")
    public Answer getProjectDetail(@ID @PathVariable String projectId) {
        log.info("获取项目详情，项目ID：{}", projectId);
        LambdaUpdateWrapper<Project> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Project::getId, projectId);
        Project project = projectService.getOne(wrapper);
        if (StringUtils.checkValNull(project)) {
            return Answer.fail("项目不存在");
        }
        return Answer.success(project);
    }
}
