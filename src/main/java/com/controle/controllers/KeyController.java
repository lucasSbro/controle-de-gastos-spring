package com.controle.controllers;

import com.controle.pagamento.service.KeyService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequestMapping("/api/stripe/key")
@SecurityRequirement(name = "bearerAuth")
public class KeyController {

    @Autowired
    KeyService keyService;

    @PostMapping("/{key}")
    public ResponseEntity salvarSecretKey(@PathVariable("key") String key) throws Exception {
        keyService.salvarSecretKey(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public String getKey() throws Exception {
        return keyService.getKey();
    }
}
