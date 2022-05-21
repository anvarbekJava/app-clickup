package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.AccessType;
import uz.pdp.appclickup.entity.enums.WorkspacePermissionName;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class CategoryDto {
    @NotNull
    private String name;

    private String color;

    private Long projectId;


    private AccessType accessType;

    private List<UUID> members;

    private WorkspacePermissionName workspacePermissionName;
}
