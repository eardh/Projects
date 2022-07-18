package com.eardh.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eardh.model.Entity;
import com.eardh.model.Project;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 项目服务层
 * @author eardh
 * @date 2022/6/11 22:31
 */
@Transactional(propagation= Propagation.REQUIRED)
public interface IProjectService extends IService<Project>{

    /**
     * 查询用户下的项目列表
     * @param userId 当前用户ID
     * @param currentPage 当前页码
     * @param pageSize 页面大小 0 - 表示查询全部
     * @return 项目列表
     */
    List<Project> getProjectsByUserId(String userId, Integer currentPage, Integer pageSize);

    /**
     * 删除项目
     * @param projectId 项目ID
     * @return 是否删除成功
     */
    boolean removeProject(String projectId);
}
