package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.entity.enums.AddType;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class ProjectMemberDto {

    @NotNull
    private Long projectId;

    @NotBlank
    private WorkspacePermissionName permissions;

    private List<UUID> users;

}
