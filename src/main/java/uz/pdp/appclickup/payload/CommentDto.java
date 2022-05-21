package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CommentDto {
    @NotNull
    private String name;
}
