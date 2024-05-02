package com.controle.pagamento.dto;

import lombok.Data;

@Data
public class DadosCartao {
    private String numero;
    private int mesExpiracao;
    private int anoExpiracao;
    private String cvc;
}
