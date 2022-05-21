package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;
@Data
public class PermissionRoleDto {

    @NotNull
    private Long workspaceId;

    @NotNull
    private UUID workspaceRoleId;

    private List<String> permissions;
}
