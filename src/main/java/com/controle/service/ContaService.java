package com.controle.service;

import com.controle.dto.Conta;
import com.controle.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    public List<Conta> getContas() {
        return contaRepository.findAll();
    }
}
