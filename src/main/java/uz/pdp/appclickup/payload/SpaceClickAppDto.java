package uz.pdp.appclickup.payload;

import lombok.Data;
import uz.pdp.appclickup.entity.ClickApps;

import java.util.List;

@Data
public class SpaceClickAppDto {
    private List<Long> clickApps;
}
