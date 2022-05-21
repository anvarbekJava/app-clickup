package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CheckListDto {

    @NotNull
    private String name;

    private UUID taskId;

}
