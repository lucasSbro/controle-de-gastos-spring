package com.controle.pagamento.dto;

import jakarta.persistence.Id;
import lombok.Data;

@Data
public class SPlan {
    public 	String interval;
    @Id
    public String prodId;
    public String nickname;
    public int amt;
    public int days;
    public String curr;
}
