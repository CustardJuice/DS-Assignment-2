package Lamport;

import java.util.concurrent.Executor;

public interface Processor {
	
	
	/*
	 * Start the processor running. 
	 * Will start accepting messages, and performing actions based upon them.
	 */
	public boolean start();
	
	/*
	 * Send a message to another node
	 * 
	 * @param message the message to send
	 * @param the identifier of the node to send the message to
	 */
	public void send(Message message, int to);
	
	
	/*
	 * Terminate this process. Stops accepting messages, and will not send any more messages.
	 */
	public void terminate();
	
	/*
	 * Sets the specific Executor that will be used to run the processor thread.
	 * If this is not set, default to using a simple thread create.
	 * 
	 * @param the executor instance to use.
	 */
	void setExecutor(Executor executor);

}