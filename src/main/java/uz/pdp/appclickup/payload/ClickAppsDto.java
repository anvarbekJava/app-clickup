package uz.pdp.appclickup.payload;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ClickAppsDto {

    private Long id;

    @NotNull
    private String name;

    private Long iconId;
}
