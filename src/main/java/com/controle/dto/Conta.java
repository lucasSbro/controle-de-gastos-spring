package com.controle.dto;

import com.controle.auth.user.Usuario;
import jakarta.persistence.Id;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "conta")
public class Conta {
    @Id
    String id;
    String gasto;
    Double valor;
    Mes mes;
    Boolean positivo;
    Usuario usuario;
}
