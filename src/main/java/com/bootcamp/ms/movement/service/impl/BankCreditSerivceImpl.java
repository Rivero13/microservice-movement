package com.bootcamp.ms.movement.service.impl;

import com.bootcamp.ms.commons.entity.BankCredit;
import org.springframework.stereotype.Service;

@Service
public class BankCreditSerivceImpl {

    @Autowired
    private WebClient client;

    @Autowired
    private CreditBankConfig bankAccountConfig;

    @Override
    public Mono<BankCredit> findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return client.get()
                .uri(bankAccountConfig.getUrl().concat("/{id}"), params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(BankCredit.class));
    }

    @Override
    public Mono<BankCredit> save(BankCredit bankAccount) {
        return client.post()
                .uri(bankAccountConfig.getUrl().concat("/create"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(bankAccount)
                .retrieve()
                .bodyToMono(BankCredit.class);
    }

    @Override
    public Mono<Object> checkBalance(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return client.get()
                .uri(bankAccountConfig.getUrl().concat("checkBalance/{id}"), params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(BankAccount.class));
    }
}
