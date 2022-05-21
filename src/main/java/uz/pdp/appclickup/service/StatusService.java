package uz.pdp.appclickup.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.StatusDto;

public interface StatusService {
    ApiResponse addStatus(StatusDto dto);

    ApiResponse edetStatus(StatusDto dto, Long id);
}
