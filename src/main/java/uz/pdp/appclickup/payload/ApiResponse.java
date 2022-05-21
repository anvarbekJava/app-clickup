package uz.pdp.appclickup.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.pdp.appclickup.entity.Attachment;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;

    private boolean success;

    private Object list;

    private Attachment attachment;

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ApiResponse(String message, boolean success, Attachment attachment) {
        this.message = message;
        this.success = success;
        this.attachment = attachment;
    }

    public ApiResponse(String message, boolean success, Object list) {
        this.message = message;
        this.success = success;
        this.list = list;
    }
}
