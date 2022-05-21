package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class CheckListItemDto {

    @NotNull
    private String name;

    private Long checkId;

    private boolean resolved;

    private UUID userId;
}
