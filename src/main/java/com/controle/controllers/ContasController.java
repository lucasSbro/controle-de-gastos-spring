package com.controle.controllers;

import com.controle.dto.Conta;
import com.controle.service.ContaService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/conta")
public class ContasController {

    @Autowired
    ContaService contaService;

    @GetMapping
    public List<Conta> getContas(){
         return contaService.getContas();
    }

    @PostMapping
    public ResponseEntity salvar(Conta conta) {
        contaService.salvar(conta);
        return ResponseEntity.ok(conta);
    }
}
