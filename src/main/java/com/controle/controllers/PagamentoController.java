package com.controle.controllers;

import com.controle.pagamento.dto.CreatePaymentRequest;
import com.controle.pagamento.dto.DadosCartao;
import com.controle.pagamento.dto.StripeResponse;
import com.controle.pagamento.service.StripeService;
import com.stripe.Stripe;
import com.stripe.model.Token;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/pagamento")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PagamentoController {

    private final StripeService stripeService;

    @PostMapping("/criar")
    public ResponseEntity<StripeResponse> criarPagamento(@RequestBody CreatePaymentRequest createPaymentRequest) {
        StripeResponse stripeResponse = stripeService.criarPagamento(createPaymentRequest);
        return ResponseEntity
                .status(stripeResponse.getHttpStatus())
                .body(stripeResponse);
    }

    @GetMapping("/capturar")
    public ResponseEntity<StripeResponse> capturarPagamento(@RequestParam String sessionId) {
        StripeResponse stripeResponse = stripeService.capturarPagamento(sessionId);
        return ResponseEntity
                .status(stripeResponse.getHttpStatus())
                .body(stripeResponse);
    }

    @PostMapping("/gerar-token")
    public ResponseEntity<String> gerarTokenCartao(@RequestBody DadosCartao dadosCartao) {
        return stripeService.gerarTokenCartao(dadosCartao);
    }
}
