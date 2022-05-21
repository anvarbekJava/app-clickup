package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.WorkspaceUser;
import uz.pdp.appclickup.entity.enums.AccessType;

import java.util.List;
import java.util.UUID;

@Data
public class SpaceUserDto {

    private List<UUID> userId;


    private AccessType accessType;


}
