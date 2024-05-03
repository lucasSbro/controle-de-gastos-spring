package com.controle.controllers;

import com.controle.pagamento.dto.Key;
import com.controle.pagamento.service.KeyService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stripe/key")
@SecurityRequirement(name = "bearerAuth")
public class KeyController {

    @Autowired
    KeyService keyService;

    @Hidden
    @PostMapping("/{key}")
    public ResponseEntity salvarSecretKey(@PathVariable("key") String key) {
        keyService.salvarSecretKey(key);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public String getKey() {
        return keyService.getKey();
    }
}
