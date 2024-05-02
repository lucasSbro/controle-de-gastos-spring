package com.controle.pagamento.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeResponse<T> {
    private String status;
    private String message;
    private Integer httpStatus;
    private T data;
}
