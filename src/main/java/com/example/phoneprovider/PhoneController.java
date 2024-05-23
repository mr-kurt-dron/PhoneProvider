package com.example.phoneprovider;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PhoneController {

    private final PhoneDriver phoneDriver;

    public PhoneController(PhoneDriver phoneDriver) {
        this.phoneDriver = phoneDriver;
    }


    @GetMapping("/provider")
    public ResponseEntity<String> getProvider(@RequestParam(value = "phone") String phone)  {
        var provider = phoneDriver.getProviderFromPhone(phone);
        return ResponseEntity
                .ok()
                .body(provider);
    }
}