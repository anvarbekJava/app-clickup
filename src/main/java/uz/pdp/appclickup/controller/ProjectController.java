package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import uz.pdp.appclickup.entity.Users;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ProjectDto;
import uz.pdp.appclickup.payload.ProjectMemberDto;
import uz.pdp.appclickup.security.CurrentUser;
import uz.pdp.appclickup.service.ProjectService;

import javax.validation.Valid;
import java.sql.Statement;

@RestController
@RequestMapping("/api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;

    @PostMapping("/add")
    public HttpEntity<?> addProject(@Valid @RequestBody ProjectDto dto, @CurrentUser Users users){
        ApiResponse apiResponse = projectService.addProject(dto, users);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/addAndRemoveMember/{id}")
    public HttpEntity<?> addAndRemoveInviteMember(@Valid @RequestBody ProjectMemberDto dto, @PathVariable Long id){
        ApiResponse apiResponse = projectService.addAndRemoveInviteMember(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/deletProject/{id}")
    public HttpEntity<?> deletProject(@PathVariable Long id){
        ApiResponse apiResponse = projectService.deletProject(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
}
