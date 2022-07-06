package com.bootcamp.ms.movement.service.impl;

import com.bootcamp.ms.commons.entity.BankAccount;
import com.bootcamp.ms.commons.entity.Client;
import com.bootcamp.ms.movement.BankAccountConfig;
import com.bootcamp.ms.movement.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
public class BankAccountSerivceImpl implements BankAccountService {

    @Autowired
    private WebClient client;

    @Autowired
    private BankAccountConfig bankAccountConfig;

    @Override
    public Mono<BankAccount> findById(String id) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return client.get()
                .uri(bankAccountConfig.getUrl().concat("/{id}"), params)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(response -> response.bodyToMono(BankAccount.class));
    }

    @Override
    public Mono<BankAccount> save(BankAccount bankAccount) {
        return client.post()
                .uri(bankAccountConfig.getUrl().concat("/create"))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(bankAccount)
                .retrieve()
                .bodyToMono(BankAccount.class);
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
