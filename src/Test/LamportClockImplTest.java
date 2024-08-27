package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Lamport.LamportClock;
import Lamport.LamportClockImpl;
import Lamport.Message;
import Lamport.Payload;

class LamportClockImplTest {
	LamportClock clock = new LamportClockImpl();
	/*
	 * Derive tests from a set of stories and expectations about how the clock will
	 * behave.
	 */

	@Test
	void test() {
		int time;
		Payload payload = new Payload(null, 1);
		Message message;

		time = clock.getTime();
		assertEquals(1, time);

		time = clock.processEvent();
		assertEquals(2, time);

		time = clock.getTime();
		assertEquals(2, time);

		message = new Message(0, 1, 1, payload);
		time = clock.processEvent(message);
		assertEquals(3, time);

		message = new Message(0, 1, 5, payload);
		time = clock.processEvent(message);
		assertEquals(6, time);
	}

	// @Test
	// void processEventMessage(Message message) {
	// }

	// @Test
	// void processEvent() {
	// }

	// @Test
	// void getTime() {
	// }
}