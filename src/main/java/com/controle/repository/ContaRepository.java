package com.controle.repository;

import com.controle.dto.Conta;
import com.controle.dto.Mes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends MongoRepository<Conta, String> {

    List<Conta> findByMes(Mes mes);
}
