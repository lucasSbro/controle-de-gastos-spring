package com.controle.controllers;

import com.controle.dto.Conta;
import com.controle.repository.ContaRepository;
import com.controle.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
