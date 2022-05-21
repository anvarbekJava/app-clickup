package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.entity.Tag;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.TagDto;
import uz.pdp.appclickup.service.TagService;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    TagService tagService;

    @PostMapping("/create")
    public HttpEntity<?> createTag(@Valid @RequestBody TagDto dto){
        ApiResponse apiResponse = tagService.createTag(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @DeleteMapping("/delete/{id}")
    public HttpEntity<?> deleteTag(@PathVariable UUID id){
        ApiResponse apiResponse = tagService.deleteTag(id);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/getTag/{workId}")
    public HttpEntity<?> getTagWorksapace(@PathVariable Long workspaceId){
        List<Tag> tagAll = tagService.getTagAll(workspaceId);
        return ResponseEntity.ok(tagAll);
    }
}
