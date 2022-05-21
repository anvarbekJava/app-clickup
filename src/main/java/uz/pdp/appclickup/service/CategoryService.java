package uz.pdp.appclickup.service;

import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryDto;

public interface CategoryService {
    ApiResponse addCategory(CategoryDto dto, Users users);

    ApiResponse edetCategory(CategoryDto dto, Long id, Users users);

    ApiResponse archivedCategory(Long id);
}
