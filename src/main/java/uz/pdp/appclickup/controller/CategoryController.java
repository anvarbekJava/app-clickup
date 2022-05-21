package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.CategoryDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public HttpEntity<?> addCategory(@Valid @RequestBody CategoryDto dto, @CurrentUser Users users){
        ApiResponse apiResponse = categoryService.addCategory(dto, users);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/edet/{id}")
    public HttpEntity<?> edetCategory(@Valid @RequestBody CategoryDto dto, @PathVariable Long id, @CurrentUser Users users){
        ApiResponse apiResponse = categoryService.edetCategory(dto, id, users);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/archived/{id}")
    public HttpEntity<?> archivedCategory(@PathVariable Long id){
        ApiResponse apiResponse = categoryService.archivedCategory(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
