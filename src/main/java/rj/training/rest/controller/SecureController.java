package rj.training.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/myrestapp/secure")
public class SecureController {

    @GetMapping("/data")
    public String getSecureData() {
        return "This is secure data";
    }
}
