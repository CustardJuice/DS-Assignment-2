package Lamport;

public class LamportClockImpl implements LamportClock {
	int local_time;

	public LamportClockImpl() {
		local_time = 1;
	}

	@Override
	public int processEvent(Message message) {
		// PARTIAL ORDERING
		if (local_time < message.time()) {
			// if time in sent message is greater than local time
			local_time = message.time();
		}
		// and also increment local_time as normal
		return ++local_time;
	}

	@Override
	public int processEvent() {
		// increment local time by 1
		return ++local_time;
	}

	@Override
	public int getTime() {
		return local_time;
	}
}