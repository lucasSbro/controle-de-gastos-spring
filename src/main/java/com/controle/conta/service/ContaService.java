package com.controle.conta.service;

import com.controle.auth.user.Usuario;
import com.controle.auth.user.UsuarioRepository;
import com.controle.conta.dto.Conta;
import com.controle.conta.dto.Mes;
import com.controle.conta.repository.ContaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ContaService {

    @Autowired
    ContaRepository contaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<Conta> getContas() {
        log.info("Consultando todas contas...");
        return contaRepository.findByUsuario(getUsuario());
    }

    public List<Conta> getContasMes(Mes mes) {
        log.info("Consultando contas pelo mês: "+ mes);
        return contaRepository.findByUsuarioAndMes(getUsuario(), mes);
    }

    public Conta salvar(Conta conta) {
        log.info("Salvando: "+ conta.getGasto());
        conta.setUsuario(getUsuario());
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
        List<Conta> contas = contaRepository.findByUsuarioAndMes(getUsuario(), mes);
        ImprimirService imprimirService = new ImprimirService(contas);
        ByteArrayOutputStream response = imprimirService.generateExcelFile();
        return response;
    }

    public Usuario getUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = (String) authentication.getPrincipal();
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.orElse(null);
    }
}
