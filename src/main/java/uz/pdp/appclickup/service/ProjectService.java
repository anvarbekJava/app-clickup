package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDto;
import uz.pdp.appclickup.payload.ProjectMemberDto;

public interface ProjectService {
    ApiResponse addProject(ProjectDto dto, Users users);


    ApiResponse addAndRemoveInviteMember(ProjectMemberDto dto, Long id);

    ApiResponse deletProject(Long id);
}
