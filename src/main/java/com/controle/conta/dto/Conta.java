package com.controle.conta.dto;

import com.controle.auth.user.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    @DBRef
    Usuario usuario;
}
