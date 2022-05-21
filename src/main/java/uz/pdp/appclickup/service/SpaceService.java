package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.payload.*;

public interface SpaceService {
    ApiResponse addSpace(SpaceDto dto, Users users);

    ApiResponse edetSpace(Long id, SpaceDto dto);

    ApiResponse deleteSpace(Long id);

    ApiResponse toSpaceAddView(Long id, SpaceViewDto dto);

    ApiResponse toSpaceAddCLickApp(Long id, SpaceClickAppDto dto);

    ApiResponse toSpaceAddSpaceUser(SpaceUserDto dto, Long id);

    ApiResponse toSpaceDeletView(SpaceViewDto dto, Long id);

    ApiResponse deletClickApp(SpaceClickAppDto dto, Long id);

    ApiResponse toSpaceDeletSpaceUser(SpaceUserDto dto, Long id);

    ApiResponse getAllSpace(Long id, Users users);
}
