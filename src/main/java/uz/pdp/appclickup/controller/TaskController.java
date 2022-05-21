package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appclickup.payload.*;
import uz.pdp.appclickup.service.AttachmentService;
import uz.pdp.appclickup.service.TaskService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.UUID;
/*
Status va tasklarni qo'shish, Task statusini o'zgartirish, Task ga file biriktirish, biriktirilgan file
 ni o'chirish, Task ga comment yoki tag qo'shish uni edit qilish va o'chirish,
  task ga user assign qilish va  assign qilingan user ni olib tashlash kabi amallarni
  bajaruvchi method larni yozing.

Checklist qo'shish, edit qilish, delete qilish, checklist ga item qo'shish, qo'shilgan item ni delete qilish,
 checklistga assign qilish va space bo'yicha viewlarn olish kabi amallarni bajaruvchi method larni yozing.

 */

@RestController
@RequestMapping("/api/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/add")
    public HttpEntity<?> addTask(@Valid @RequestBody TaskDto dto){
        ApiResponse apiResponse = taskService.addTask(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/edet/{id}")
    public HttpEntity<?> edetTask(@Valid @RequestBody TaskDto dto, @PathVariable UUID id){
        ApiResponse apiResponse = taskService.edetTask(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/changeStatus/{id}")
    public HttpEntity<?> changeStatus(@PathVariable UUID id, @RequestParam Long statusId){
        ApiResponse apiResponse = taskService.changeStatus(id, statusId);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/create/subTask/{id}")
    public HttpEntity<?> createSubTask(@Valid @RequestBody TaskDto dto, @PathVariable UUID id){
        ApiResponse apiResponse = taskService.createSubTask(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/add/comment")
    public HttpEntity<?> addCommentTask(@Valid @RequestBody CommentDto dto, @RequestParam UUID id){
        ApiResponse apiResponse = taskService.addCommentTask(dto,id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/delete/comment/{id}")
    public HttpEntity<?> deleteComment(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.deleteComment(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PostMapping("/add/tag/{id}")
    public HttpEntity<?> addTag(@Valid @RequestBody TagDto dto, @PathVariable UUID id){
        ApiResponse apiResponse = taskService.addTag(dto, id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/remove/tag/{id}")
    public HttpEntity<?> removeTag(@PathVariable UUID id){
        ApiResponse apiResponse = taskService.removeTag(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PostMapping("/assign/user")
    public HttpEntity<?> assignUser(@RequestBody TaskUserDto dto){
        ApiResponse apiResponse = taskService.assignAddUser(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/assign/remove")
    public HttpEntity<?> removeAssign(@RequestBody TaskUserDto dto){
        ApiResponse apiResponse = taskService.removeAssign(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @PutMapping("/add/attachment")
    public HttpEntity<?> addAttachment(@RequestBody TaskAttachmentDto dto, MultipartHttpServletRequest request){
        ApiResponse apiResponse = taskService.addAttachmentTask(dto, request);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/get/attachment/{id}")
    public void getTaskAttachment(HttpServletResponse response, @PathVariable UUID id) throws IOException {
       attachmentService.getSystemFile(response, id);
    }
}
