package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.enums.StatusType;

import javax.validation.constraints.NotNull;

@Data
public class StatusDto {

    @NotNull
    private String name;

    private String color;

    private Long spaceId;

    private Long projectId;

    private Long categoryId;

    private StatusType statusType;
}
