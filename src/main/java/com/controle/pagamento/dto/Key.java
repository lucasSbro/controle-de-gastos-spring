package com.controle.pagamento.dto;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "key")
public class Key {
    String secretKey;

    public Key(String secretKey) {
        this.secretKey = secretKey;
    }
}
