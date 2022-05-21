package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class TaskAttachmentDto {
    @NotNull
    private UUID taskId;

    private boolean pinCoverImaga;

}
