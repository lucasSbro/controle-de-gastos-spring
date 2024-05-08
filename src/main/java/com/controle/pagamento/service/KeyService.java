package com.controle.pagamento.service;

import com.controle.pagamento.dto.Key;
import com.controle.pagamento.repository.KeyRepository;
import com.controle.pagamento.util.CryptoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyService {

    @Autowired
    KeyRepository keyRepository;

    public void salvarSecretKey(String key) throws Exception {
        key = CryptoUtil.encrypt(key);
        keyRepository.save(new Key(key));
    }

    public String getKey() throws Exception {
        String secretKey = keyRepository.findAll().stream().findFirst().get().getSecretKey();
        secretKey = CryptoUtil.decrypt(secretKey);
        return secretKey;
    }
}
