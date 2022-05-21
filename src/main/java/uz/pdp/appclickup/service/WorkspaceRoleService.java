package uz.pdp.appclickup.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.PermissionRoleDto;
import uz.pdp.appclickup.payload.WorkspaceRoleDto;

public interface WorkspaceRoleService {
    ApiResponse addWorkspaceRole(WorkspaceRoleDto dto);

    ApiResponse addPermissionWorkspaceRole(PermissionRoleDto dto);

    ApiResponse removePermissionWorkspaceRole(PermissionRoleDto dto);
}
