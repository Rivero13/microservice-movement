package com.bootcamp.ms.movement.controller;

import com.bootcamp.ms.commons.entity.BankCredit;
import com.bootcamp.ms.movement.entity.Movement;
import com.bootcamp.ms.movement.repository.MovementRepository;
import com.bootcamp.ms.movement.service.MovementService;
import com.bootcamp.ms.movement.service.impl.MovementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BankMovementControllerTest {

    MovementRepository movementRepository;
    MovementService movementService;

    @BeforeEach
    void setUp() {
        movementRepository = Mockito.mock(MovementRepository.class);
        movementService = new MovementServiceImpl(movementRepository);
    }

    @Test
    void findByIdOpt() {
        Mockito.when(movementRepository.findAll()).thenReturn(Datos.MOVEMENT);
        Optional<Movement> bankAccount = movementService.findByIdOpt("62dae2f624f70813e1068adf");

        assertAll(
                () -> assertTrue(bankAccount.isPresent()),
                () -> assertTrue(bankAccount.orElseThrow().getAmount() > 100),
                () -> assertEquals("Crédito", bankAccount.orElseThrow().getType())
        );
    }
}