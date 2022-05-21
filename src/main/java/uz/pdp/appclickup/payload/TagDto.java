package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
public class TagDto {

    private UUID id;

    @NotNull
    private String name;

    private String color;

    private Long workspaceId;


}
