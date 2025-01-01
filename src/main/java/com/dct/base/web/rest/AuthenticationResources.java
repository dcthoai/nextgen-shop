package com.dct.base.web.rest;

import com.dct.base.dto.request.BaseRequestDTO;
import com.dct.base.dto.response.BaseResponseDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthenticationResources {

    @PostMapping("/login")
    public BaseResponseDTO login(@RequestBody BaseRequestDTO loginRequest) {

        return null;
    }
}
