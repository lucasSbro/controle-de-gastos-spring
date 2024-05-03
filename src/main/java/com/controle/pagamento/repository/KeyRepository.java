package com.controle.pagamento.repository;

import com.controle.pagamento.dto.Key;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface KeyRepository extends MongoRepository<Key, String> {
}
