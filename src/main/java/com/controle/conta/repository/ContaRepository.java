package com.controle.conta.repository;

import com.controle.auth.user.Usuario;
import com.controle.conta.dto.Conta;
import com.controle.conta.dto.Mes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContaRepository extends MongoRepository<Conta, String> {

    List<Conta> findByMes(Mes mes);
    List<Conta> findByUsuario(Usuario usuario);
    List<Conta> findByUsuarioAndMes(Usuario usuario, Mes mes);
}
