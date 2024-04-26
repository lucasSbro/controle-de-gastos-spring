package com.controle.service;

import com.controle.dto.Conta;
import com.controle.dto.Mes;
import com.controle.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    ImprimirService imprimirService;

    public List<Conta> getContas() {
        log.info("Consultando todas contas...");
        return contaRepository.findAll();
    }

    public List<Conta> getContasMes(Mes mes) {
        log.info("Consultando contas por mês...");
        return contaRepository.findByMes(mes);
    }

    public Conta salvar(Conta conta) {
        log.info("Salvando: "+ conta.getGasto());
        return contaRepository.save(conta);
    }

    public void atualizar(Conta conta) {
        log.info("Atualizando: "+ conta.getGasto());
        contaRepository.save(conta);
    }

    public void delete(String id) {
        log.info("Deletando id: "+ id);
        contaRepository.deleteById(id);
    }

    public ByteArrayOutputStream imprimir(Mes mes) throws IOException {
        log.info("Imprimindo relatório mês: "+ mes);
        List<Conta> contas = contaRepository.findByMes(mes);
        ImprimirService imprimirService = new ImprimirService(contas);
        ByteArrayOutputStream response = imprimirService.generateExcelFile();
        return response;
    }
}
