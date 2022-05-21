package uz.pdp.appclickup.service;

import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CheckListDto;
import uz.pdp.appclickup.payload.CheckListItemDto;

import java.util.UUID;

public interface CheckListService {
    ApiResponse addCheckList(CheckListDto dto);

    ApiResponse addCheckListItem(CheckListItemDto dto);

    ApiResponse addAssignCheckList(Long id, UUID userId);

    ApiResponse edetCheckList(CheckListDto dto, Long id);

    ApiResponse deleteCheckList(Long id);


    ApiResponse edetItemCheckList(CheckListItemDto dto, Long id);
}
