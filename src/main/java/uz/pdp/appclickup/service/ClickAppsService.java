package uz.pdp.appclickup.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ClickAppsDto;

public interface ClickAppsService {
    ApiResponse addClickApps(ClickAppsDto dto);

    ApiResponse edetClickApps(Long id, ClickAppsDto dto);
}
