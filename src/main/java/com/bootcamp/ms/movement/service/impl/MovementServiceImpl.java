package com.bootcamp.ms.movement.service.impl;

import com.bootcamp.ms.movement.entity.Movement;
import com.bootcamp.ms.movement.repository.MovementRepository;
import com.bootcamp.ms.movement.service.MovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.Optional;

@Service
public class MovementServiceImpl implements MovementService {

    @Autowired
    private MovementRepository movementRepository;

    public MovementServiceImpl(MovementRepository movementRepository) {
        this.movementRepository = movementRepository;
    }

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
    public Flux<Movement> findLastTenByClient(String id) {
        Flux<Movement>  movementFlux = movementRepository.findAll()
                .sort(Comparator.comparing(Movement::getDate, Comparator.reverseOrder()))
                .filter(movement -> movement.getIdClient().equals(id));
        return movementFlux.take(10);
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

    @Override
    public Optional<Movement> findByIdOpt(String id) {
        return movementRepository.findAll()
                .toStream()
                .filter(b -> b.getId().contains(id))
                .findFirst();
    }
}
