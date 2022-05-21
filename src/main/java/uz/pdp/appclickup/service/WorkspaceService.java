package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.entity.Workspace;
import uz.pdp.appclickup.entity.WorkspaceUser;
import uz.pdp.appclickup.payload.*;

import java.util.List;
import java.util.UUID;


public interface WorkspaceService {

    ApiResponse addWorkspace(WorkspaceDTO workspaceDTO, Users user);

    ApiResponse deleteWorkspace(Long id);

    ApiResponse addOrEditOrRemoveWorkspace(Long id, MemberDTO memberDTO, Users users);

    ApiResponse joinToWorkspace(Long id, Users user);

    ApiResponse editWorkspace(Long id, WorkspaceDTO workspaceDTO);

    ApiResponse changeOwnerWorkspace(ChangeOwnerDto dto);

    List<MemberDTO> getMemberGuest(Long id);

    List<WorkspaceDTO> getMyWorkspace(Users users);
}
