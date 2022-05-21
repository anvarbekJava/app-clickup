package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.*;
import uz.pdp.appclickup.entity.enums.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDto;
import uz.pdp.appclickup.payload.ProjectMemberDto;
import uz.pdp.appclickup.repository.*;
import uz.pdp.appclickup.service.ProjectService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    SpaceRepository spaceRepository;
    @Autowired
    SpaceUserRepository spaceUserRepository;
    @Autowired
    ProjectUserRepository projectUserRepository;
    @Autowired
    StatusRepository statusRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public ApiResponse addProject(ProjectDto dto, Users users) {
        if (projectRepository.existsByNameAndSpaceId(dto.getName(), dto.getSpaceId()))
            return new ApiResponse("Bunday project bor", false);
        Space space = spaceRepository.findById(dto.getSpaceId()).orElseThrow(() -> new ResourceNotFoundException("Space"));
        Project project = new Project(
                dto.getName(),
                dto.getColor(),
                space,
                dto.getAccessType()
        );
        List<Category> categories = new ArrayList<>();
        for (String list : dto.getLists()) {
            categories.add(new Category(
                    list,
                    project
            ));
        }

        Project saveProject = projectRepository.save(project);
        List<Category> categoryList = categoryRepository.saveAll(categories);

        for (Category category : categoryList) {
            statusRepository.save(new Status(
               "TODO",
               "gray",
               space,
               project,
               category,
               StatusType.OPEN
            ));
            statusRepository.save(new Status(
               "COMPLETED",
               "green",
               space,
               project,
               category,
               StatusType.CLOSED
            ));
        }
        List<ProjectUser> userList = new ArrayList<>();
        if(dto.getAccessType().equals(AccessType.PUBLIC)){
            List<SpaceUser> spaceUsers = spaceUserRepository.findAllBySpaceId(dto.getSpaceId());
            for (SpaceUser spaceUser : spaceUsers) {
                for (WorkspacePermissionName value : WorkspacePermissionName.values()) {
                    userList.add(new ProjectUser(project, spaceUser.getMember(), value));
                }
            }
        }else {
            for (WorkspacePermissionName value : WorkspacePermissionName.values()) {
                userList.add(new ProjectUser(project, users, value));
            }
        }
        projectUserRepository.saveAll(userList);

        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse addAndRemoveInviteMember(ProjectMemberDto dto, Long id) {
        Project project = projectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("project"));
        List<SpaceUser> spaceUsers = spaceUserRepository.findAllBySpaceId(project.getSpace().getId());
        List<ProjectUser> userList = new ArrayList<>();
        if (project.getAccessType().equals(AccessType.PRIVATE)){
            for (UUID user : dto.getUsers()) {
                for (SpaceUser spaceUser : spaceUsers) {
                    if (spaceUser.getMember().getId().equals(user)) {
                        for (WorkspacePermissionName value : dto.getPermissions().values()) {
                            userList.add(new ProjectUser(project, spaceUser.getMember(), value));
                        }
                    }
                }
            }
        }else {
            for (SpaceUser spaceUser : spaceUsers) {
                for (WorkspacePermissionName value : WorkspacePermissionName.values()) {
                    userList.add(new ProjectUser(project, spaceUser.getMember(), value));
                }
            }
        }
        projectUserRepository.saveAll(userList);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse deletProject(Long id) {
        if (!projectRepository.existsById(id))
            return new ApiResponse("No delet project", false);
        projectRepository.deleteById(id);
        return new ApiResponse("Deleleted project", true);
    }
}
