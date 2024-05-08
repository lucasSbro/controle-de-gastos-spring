package com.controle.pagamento.service;

import com.controle.pagamento.dto.*;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.model.Token;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class StripeService {

    @Autowired
    private KeyService keyService;

    public StripeService() {
    }
    public String getSecretKey() throws Exception {
       return keyService.getKey();
    }
    //        Stripe.apiKey = "sk_test_51PC0PSCCdl0sSrgeYOjMwVYZ1WC5S0ORdC4lgCStWxfecMjMEVZWQDQ99n8ILdcXVOAWZwb28HN2k3DzGp4WlCKg00Z92Eio6r";

    public StripeResponse criarPagamento(CreatePaymentRequest createPaymentRequest) throws Exception {

        Stripe.apiKey = getSecretKey();

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(createPaymentRequest.getName())
                        .build();


        PaymentIntent.PaymentMethodOptions.Card.Installments.Plan plan = new PaymentIntent.PaymentMethodOptions.Card.Installments.Plan();
        plan.setCount(10L);

//        // Defina as opções de parcelamento (quantidade de parcelas e percentual de juros, se houver)
//        SessionCreateParams.PaymentMethodOptions.Card.Installments installments =
//                SessionCreateParams.PaymentMethodOptions.Card.Installments
//                        .builder()
//                        .setEnabled(true)
////                        .putExtraParam("installments.plan", 3)
//                        .build();
//
//
//        SessionCreateParams.PaymentMethodOptions paymentMethodOptions =
//                SessionCreateParams.PaymentMethodOptions.builder()
//                        .setPix(SessionCreateParams.PaymentMethodOptions.Pix.builder().build())
//                        .setBoleto(SessionCreateParams.PaymentMethodOptions.Boleto.builder().build())
//                        .setCard(SessionCreateParams.PaymentMethodOptions.Card.builder()
//                                .setInstallments(installments)
//                        .build()).build();


        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency(createPaymentRequest.getCurrency())
                        .setUnitAmount(createPaymentRequest.getAmount())
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams
                        .LineItem.builder()
                        .setQuantity(createPaymentRequest.getQuantity())
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(createPaymentRequest.getSuccessUrl())
                        .setCancelUrl(createPaymentRequest.getCancelUrl())
                        .addLineItem(lineItem)
//                        .setPaymentMethodOptions(paymentMethodOptions)
                        .build();

        Session session;
        try {
            session = Session.create(params);
        } catch (StripeException e) {
            e.printStackTrace();
            return StripeResponse
                    .builder()
                    .status(Constant.FAILURE)
                    .message("Criação de sessão de pagamento falhou")
                    .httpStatus(400)
                    .data(null)
                    .build();
        }

        CreatePaymentResponse responseData = CreatePaymentResponse
                .builder()
                .sessionId(session.getId())
                .sessionUrl(session.getUrl())
                .build();

        return StripeResponse
                .builder()
                .status(Constant.SUCCESS)
                .message("Sessão de pagamento criada com sucesso.")
                .httpStatus(200)
                .data(responseData)
                .build();
    }

    public StripeResponse capturarPagamento(String sessionId) throws Exception {

        Stripe.apiKey = getSecretKey();
        try {
            Session session = Session.retrieve(sessionId);
            String status = session.getStatus();

            if (status.equalsIgnoreCase(Constant.STRIPE_SESSION_STATUS_SUCCESS)) {
                log.info("Pagamento capturado com sucesso.");
            }

            CapturePaymentResponse responseData = CapturePaymentResponse
                    .builder()
                    .sessionId(sessionId)
                    .sessionStatus(status)
                    .paymentStatus(session.getPaymentStatus())
                    .build();

            return StripeResponse
                    .builder()
                    .status(Constant.SUCCESS)
                    .message("Pagamento capturado com sucesso.")
                    .httpStatus(200)
                    .data(responseData)
                    .build();
        } catch (StripeException e) {
            e.printStackTrace();
            return StripeResponse
                    .builder()
                    .status(Constant.FAILURE)
                    .message("Captura do pagamento falhou erro no servidor")
                    .httpStatus(500)
                    .data(null)
                    .build();
        }
    }

    public ResponseEntity<String> gerarTokenCartao(DadosCartao dadosCartao) {
        try {
            Stripe.apiKey = getSecretKey();

            Token token = Token.create(Map.of(
                    "card", Map.of(
                            "number", dadosCartao.getNumero(),
                            "exp_month", dadosCartao.getMesExpiracao(),
                            "exp_year", dadosCartao.getAnoExpiracao(),
                            "cvc", dadosCartao.getCvc()
                    )
            ));
            return ResponseEntity.ok(token.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar dados do cartão");
        }
    }
    public ResponseEntity<?> addCartao(String cardNumber, int expirationMonth, int expirationYear, String cvc, String email) throws Exception {
        Stripe.apiKey = getSecretKey();

        Map<String, Object> cardParams = new HashMap<>();
        cardParams.put("number", cardNumber);
        cardParams.put("exp_month", expirationMonth);
        cardParams.put("exp_year", expirationYear);
        cardParams.put("cvc", cvc);

        Map<String, Object> paymentMethodParams = new HashMap<>();
        paymentMethodParams.put("type", "card");
        paymentMethodParams.put("card", cardParams);

        try {
            PaymentMethod paymentMethod = PaymentMethod.create(paymentMethodParams);

            Map<String, Object> customerParams = new HashMap<>();
            customerParams.put("email", email);
            customerParams.put("payment_method", paymentMethod.getId());

            Customer customer = Customer.create(customerParams);

            return ResponseEntity.ok().body("Card added successfully to customer " + customer.getId());
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}