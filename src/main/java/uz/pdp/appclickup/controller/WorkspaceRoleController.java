package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.PermissionRoleDto;
import uz.pdp.appclickup.payload.WorkspaceRoleDto;
import uz.pdp.appclickup.service.WorkspaceRoleService;

import javax.validation.Valid;
/*
Workspace edit qilish, ownerini o'zgartish,
member va mehmonlarini ko'rish, Workspacelari ro'yxatini olish,
Workspace ga role qo'shish va
Workspace rolelarini permisison berish yoki olib tashlash kabi amallarni bajaruvchi method larni yozing.
Video darslikda ishlangan proyektni quyidagi link orqali yuklab oling: https://github.com/sirojiddinEcma/app-clickup
 */

@RestController
@RequestMapping(value = "/api/role")
public class WorkspaceRoleController {
    @Autowired
    WorkspaceRoleService workspaceRoleService;

    /*
    Workspace add role
     */
    @PostMapping("/addRole")
    public HttpEntity<?> addWorkspaceRole(@RequestBody WorkspaceRoleDto dto){
        ApiResponse apiResponse = workspaceRoleService.addWorkspaceRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/addPermissionToWorkspaceRole")
    public HttpEntity<?> addPermissionToWorkspaceRole(@Valid @RequestBody PermissionRoleDto dto){
        ApiResponse apiResponse = workspaceRoleService.addPermissionWorkspaceRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/removePermissionToWorkspaceRole")
    public HttpEntity<?> removePermissionToWorspaceRole(@Valid @RequestBody PermissionRoleDto dto){
        ApiResponse apiResponse = workspaceRoleService.removePermissionWorkspaceRole(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }
}
