package uz.pdp.appclickup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appclickup.payload.ApiResponse;
import uz.pdp.appclickup.payload.ClickAppsDto;
import uz.pdp.appclickup.service.ClickAppsService;

@RestController
@RequestMapping(value = "/api/click")
public class ClickAppsController {
    @Autowired
    ClickAppsService clickAppsService;

    @PostMapping("/add")
    public HttpEntity<?> addClickApps(@RequestBody ClickAppsDto dto){
        ApiResponse apiResponse = clickAppsService.addClickApps(dto);
        return ResponseEntity.status(apiResponse.isSuccess()?200:409).body(apiResponse);
    }

    @PutMapping("/edet/{id}")
    public HttpEntity<?> edetClickApps(@PathVariable Long id, @RequestBody ClickAppsDto dto){
        ApiResponse apiResponse  = clickAppsService.edetClickApps(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess()?201:409).body(apiResponse);
    }


}
