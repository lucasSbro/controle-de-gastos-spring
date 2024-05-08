package com.controle.controllers;

import com.controle.pagamento.dto.CreatePaymentRequest;
import com.controle.pagamento.dto.DadosCartao;
import com.controle.pagamento.dto.SPlan;
import com.controle.pagamento.dto.StripeResponse;
import com.controle.pagamento.service.PlanService;
import com.controle.pagamento.service.StripeService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Hidden
@RestController
@RequestMapping("/api/pagamento")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class PagamentoController {

    private final StripeService stripeService;
    @Autowired
    PlanService planService;

    @PostMapping("/criar")
    public ResponseEntity<StripeResponse> criarPagamento(@RequestBody CreatePaymentRequest createPaymentRequest) throws Exception {
        StripeResponse stripeResponse = stripeService.criarPagamento(createPaymentRequest);
        return ResponseEntity
                .status(stripeResponse.getHttpStatus())
                .body(stripeResponse);
    }

    @GetMapping("/capturar")
    public ResponseEntity<StripeResponse> capturarPagamento(@RequestParam String sessionId) throws Exception {
        StripeResponse stripeResponse = stripeService.capturarPagamento(sessionId);
        return ResponseEntity
                .status(stripeResponse.getHttpStatus())
                .body(stripeResponse);
    }

    @PostMapping("/gerar-token")
    public ResponseEntity<String> gerarTokenCartao(@RequestBody DadosCartao dadosCartao) {
        return stripeService.gerarTokenCartao(dadosCartao);
    }

    @PostMapping("/adicionar-plano")
    public ResponseEntity<String> addPlan(@RequestBody SPlan sp) throws Exception {
         String plan = planService.addPlan(sp);
         return ResponseEntity.ok(plan);
    }

    @PostMapping("/adicionar-cartao")
    public ResponseEntity<?> adicionarCartao(@RequestBody DadosCartao dadosCartao) throws Exception {
        Object response = stripeService.addCartao(dadosCartao.getNumero(), dadosCartao.getMesExpiracao(), dadosCartao.getAnoExpiracao(), dadosCartao.getCvc(), "");
        return ResponseEntity.ok(response);
    }
}
