package com.controle.pagamento.service;

import com.controle.pagamento.dto.Key;
import com.controle.pagamento.repository.KeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyService {

    @Autowired
    KeyRepository keyRepository;

    public void salvarSecretKey(String key) {
        keyRepository.save(new Key(key));
    }

    public String getKey() {
        return keyRepository.findAll().stream().findFirst().get().getSecretKey();
    }
}
