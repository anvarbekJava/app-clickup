package uz.pdp.appclickup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pdp.appclickup.entity.WorkspacePermission;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;

import java.util.List;
import java.util.UUID;

public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, UUID> {

    @Query("select w from WorkspacePermission w where w.workspaceRole.workspace.id = ?1 and w.workspaceRole.name = ?2")
    List<WorkspacePermission> findAllByWorkspaceRole(Long workspace_id, String name);


    List<WorkspacePermission> findAllByWorkspaceRole_NameAndWorkspaceRole_WorkspaceId(String workspaceRole_name, Long workspaceRole_workspace_id);
}
