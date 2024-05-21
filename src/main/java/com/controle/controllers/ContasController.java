package com.controle.controllers;

import com.controle.conta.dto.Conta;
import com.controle.conta.dto.Mes;
import com.controle.conta.service.ContaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/conta")
@SecurityRequirement(name = "bearerAuth")
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

    @GetMapping("/imprimir/{mes}")
    public ResponseEntity<byte[]> imprimir(@PathVariable("mes") Mes mes) throws IOException {

        ByteArrayOutputStream xlsx = contaService.imprimir(mes);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", "relatorio_" + mes + ".xlsx");
        byte[] bytes = xlsx.toByteArray();

        return ResponseEntity.ok()
                .headers(headers)
                .body(bytes);
    }
}
