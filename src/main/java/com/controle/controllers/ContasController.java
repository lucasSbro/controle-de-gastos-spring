package com.controle.controllers;

import com.controle.dto.Conta;
import com.controle.dto.Mes;
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

    @GetMapping("/{mes}")
    public List<Conta> getContasMes(@PathVariable("mes") Mes mes){
         return contaService.getContasMes(mes);
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody Conta conta) {
        contaService.salvar(conta);
        return ResponseEntity.ok(conta);
    }

    @PutMapping
    public ResponseEntity atualizar(@RequestBody Conta conta) {
        contaService.atualizar(conta);
        return ResponseEntity.ok(conta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        contaService.delete(id);
        return ResponseEntity.ok().build();
    }
}
