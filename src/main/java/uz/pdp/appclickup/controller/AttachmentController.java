package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.service.AttachmentService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = "/attachment")
public class AttachmentController {
    @Autowired
    AttachmentService attachmentService;

    @PostMapping("/upload")
    public HttpEntity<?> uploadFileSystem(MultipartHttpServletRequest request){
       ApiResponse apiResponse =  attachmentService.uploadFileSystem(request);
       return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }
    @GetMapping("/getSystem/{id}")
    public void getSystemFile(HttpServletResponse response, @PathVariable UUID id) throws IOException {
        attachmentService.getSystemFile(response, id);
    }
}
