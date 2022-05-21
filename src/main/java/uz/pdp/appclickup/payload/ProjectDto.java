package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.AccessType;



import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ProjectDto {
    @NotNull
    private String name;

    @NotNull
    private String color;

    @NotNull
    private Long spaceId;


    private AccessType accessType;

    private List<String> lists;
}
