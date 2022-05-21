package uz.pdp.appclickup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.appclickup.entity.Workspace;
import uz.pdp.appclickup.entity.WorkspacePermission;
import uz.pdp.appclickup.entity.WorkspaceRole;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;
import uz.pdp.appclickup.entity.enums.WorkspaceRoleName;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.PermissionRoleDto;
import uz.pdp.appclickup.payload.WorkspaceRoleDto;
import uz.pdp.appclickup.repository.WorkspacePermissionRepository;
import uz.pdp.appclickup.repository.WorkspaceRepository;
import uz.pdp.appclickup.repository.WorkspaceRoleRepository;
import uz.pdp.appclickup.service.WorkspaceRoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WorkspaceRoleServiceImpl implements WorkspaceRoleService {
    @Autowired
    WorkspaceRoleRepository workspaceRoleRepository;

    @Autowired
    WorkspaceRepository workspaceRepository;

    @Autowired
    WorkspacePermissionRepository workspacePermissionRepository;

    @Override
    public ApiResponse addWorkspaceRole(WorkspaceRoleDto dto) {
        if (workspaceRoleRepository.existsByWorkspaceIdAndName(dto.getWorkspaceId(), dto.getName()))
            return new ApiResponse("Bu workspace da bunday role mavjud!", false);
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (!optionalWorkspace.isPresent())
            return new ApiResponse("Bunday workspace mavjud emas", false);
        Workspace workspace = optionalWorkspace.get();
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getExtendsRole());
        if (!optionalWorkspaceRole.isPresent())
            return new ApiResponse("Bunday role mavjud emas", false);
        WorkspaceRole extendsRole = optionalWorkspaceRole.get();

        WorkspaceRole workspaceRole = new WorkspaceRole(
                workspace,
                dto.getName(),
                WorkspaceRoleName.valueOf(extendsRole.getName())
        );
        workspaceRoleRepository.save(workspaceRole);

        List<WorkspacePermission> permissionList = new ArrayList<>();
        List<WorkspacePermission> permissions =
                workspacePermissionRepository.findAllByWorkspaceRole_NameAndWorkspaceRole_WorkspaceId(extendsRole.getName(), dto.getWorkspaceId());
        for (WorkspacePermission permission : permissions) {
            WorkspacePermission workspacePermission = new WorkspacePermission(
                    workspaceRole,
                    permission.getPermission()
            );
            permissionList.add(workspacePermission);
        }
        workspacePermissionRepository.saveAll(permissionList);
        return new ApiResponse("Saqlandi", true);
    }

    @Override
    public ApiResponse addPermissionWorkspaceRole(PermissionRoleDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId()))
            return new ApiResponse("Workspace mavjud emas", false);
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getWorkspaceRoleId());
        if (!optionalWorkspaceRole.isPresent())
            return new ApiResponse("WorkspaceRole topilmadi", false);
        WorkspaceRole workspaceRole = optionalWorkspaceRole.get();
        List<WorkspacePermission> workspacePermissionList = workspacePermissionRepository.findAllByWorkspaceRole(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> permissionList = new ArrayList<>(workspacePermissionList);
        for (String permission : dto.getPermissions()) {
            boolean has = false;
            for (WorkspacePermission workspacePermission : workspacePermissionList) {
                if (workspacePermission.getPermission().getName().equals(WorkspacePermissionName.valueOf(permission))){
                    has = true;
                    break;
                }
            }
            if (!has){
                WorkspacePermission workspacePermission = new WorkspacePermission(
                        workspaceRole,
                        WorkspacePermissionName.valueOf(permission)
                );
               permissionList.add(workspacePermission);
            }
        }
        workspacePermissionRepository.saveAll(permissionList);
        return new ApiResponse("Edet Workspace permission", true);
    }

    @Override
    public ApiResponse removePermissionWorkspaceRole(PermissionRoleDto dto) {
        if (!workspaceRepository.existsById(dto.getWorkspaceId()))
            return new ApiResponse("Workspace mavjud emas", false);
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getWorkspaceRoleId());
        if (!optionalWorkspaceRole.isPresent())
            return new ApiResponse("WorkspaceRole topilmadi", false);
        WorkspaceRole workspaceRole = optionalWorkspaceRole.get();
        List<WorkspacePermission> workspacePermissionList = workspacePermissionRepository.findAllByWorkspaceRole(dto.getWorkspaceId(), workspaceRole.getName());
        List<WorkspacePermission> permissionList = new ArrayList<>();
        for (String permission : dto.getPermissions()) {
            for (WorkspacePermission workspacePermission : workspacePermissionList) {
                if (workspacePermission.getPermission().equals(WorkspacePermissionName.valueOf(permission))){
                    permissionList.add(workspacePermission);
                }
            }
        }
        workspacePermissionRepository.deleteAll(permissionList);
        return new ApiResponse("Edet Workspace permission", true);
    }
}
