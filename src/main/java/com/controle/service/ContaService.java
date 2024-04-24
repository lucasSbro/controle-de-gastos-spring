package com.controle.service;

import com.controle.dto.Conta;
import com.controle.dto.Mes;
import com.controle.repository.ContaRepository;
import org.apache.catalina.connector.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    public List<Conta> getContas() {
        System.out.println("Consultando contas...");
        return contaRepository.findAll();
    }

    public List<Conta> getContasMes(Mes mes) {
        System.out.println("Consultando contas...");
        return contaRepository.findByMes(mes);
    }

    public Conta salvar(Conta conta) {
        return contaRepository.save(conta);
    }

    public void atualizar(Conta conta) {
        contaRepository.save(conta);
    }

    public void delete(String id) {
        contaRepository.deleteById(id);
    }
}
