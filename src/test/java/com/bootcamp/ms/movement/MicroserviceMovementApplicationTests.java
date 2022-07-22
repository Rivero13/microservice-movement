package com.bootcamp.ms.movement;

import com.bootcamp.ms.movement.entity.Movement;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MicroserviceMovementApplicationTests {

	private final Movement movement = Movement.builder()
			.id("62dae41d31892619eefeaed7")
			.type("Crédito")
			.description("C")
			.date(new Date())
			.amount(500)
			.idBankCredit("62dae2f624f70813e1068adf")
			.build();

	@Test
	void create() {
		assertAll(
				() -> assertNotNull(movement)
		);
	}

	@Test
	void testNotNull() {
		assertAll(
				() -> assertNotNull(movement.getType()),
				() -> assertNotNull(movement.getDate()),
				() -> assertNotNull(movement.getAmount()),
				() -> assertNotNull(movement.getDescription()),
				//() -> assertNotNull(movement.getIdBankAccount()),
				() -> assertNotNull(movement.getIdBankCredit())
				//,() -> assertNotNull(movement.getIdBankAccountDestination()),
				//() -> assertNotNull(movement.getIdBankCreditDestination()),
				//() -> assertNotNull(movement.getIdClient())
		);
	}

	@Test
	void findById() {
		assertAll(
				() -> assertNotNull(movement.getId()),
				() -> assertEquals("62dae41d31892619eefeaed7", movement.getId())
		);
	}

}
