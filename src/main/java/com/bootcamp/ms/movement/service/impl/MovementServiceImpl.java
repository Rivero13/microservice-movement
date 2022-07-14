package com.bootcamp.ms.movement.service.impl;

import com.bootcamp.ms.movement.entity.Movement;
import com.bootcamp.ms.movement.repository.MovementRepository;
import com.bootcamp.ms.movement.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    @Override
    public Flux<Movement> findAll() {
        return movementRepository.findAll();
    }

    @Override
    public Flux<Movement> findByBankAccount(String id) {

        return movementRepository.findAll()
                .filter(movement -> movement.getIdBankAccount() != null ? movement.getIdBankAccount().equals(id) : movement.getIdBankCredit().equals(id));
    }

    @Override
    public Mono<Movement> findById(String id) {
        return movementRepository.findById(id);
    }

    @Override
    public Mono<Movement> save(Movement movement) {
        return movementRepository.save(movement);
    }

    @Override
    public Mono<Void> delete(Movement movement) {
        return movementRepository.delete(movement);
    }
}
