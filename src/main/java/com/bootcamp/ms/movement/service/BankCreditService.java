package com.bootcamp.ms.movement.service;

import com.bootcamp.ms.commons.entity.BankAccount;
import com.bootcamp.ms.commons.entity.BankCredit;
import reactor.core.publisher.Mono;

public interface BankCreditService {

    Mono<BankCredit> findById(String id);
    Mono<BankCredit> save(BankCredit bankAccount);
    Mono<Object> checkBalance(String id);
}
