package uz.pdp.appclickup.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WorkspaceDTO {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String color;

    private UUID avatarId;

    private String intialLetter;

}
