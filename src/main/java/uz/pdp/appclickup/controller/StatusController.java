package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.StatusDto;
import uz.pdp.appclickup.service.StatusService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    StatusService statusService;

    @PostMapping("/add")
    public HttpEntity<?> addStatus(@Valid @RequestBody StatusDto dto){
        ApiResponse apiResponse  = statusService.addStatus(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/edet/{id}")
    public HttpEntity<?> edetStatus(@Valid @RequestBody StatusDto dto, @PathVariable Long id){
       ApiResponse apiResponse =  statusService.edetStatus(dto, id);
       return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

}
