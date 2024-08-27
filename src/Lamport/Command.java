package Lamport;


/*
 * Simple command set that might be used to instruct a receiving Node what to do next
 * This can be expanded as and when useful
 */

public enum Command {
	WAIT,        // Wait for a short amount of time
	SEND_TO,     // Send a message to another Node
	TERMINATE    // Cease processing
}