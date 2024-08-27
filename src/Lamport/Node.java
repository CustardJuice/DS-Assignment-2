package Lamport;

public interface Node {	
	public enum Status{
		SUCCESS,
		INPUT_BUFFER_FULL
	}
	
	/*
	 * Start the node, performs any needed activities to make the node ready
	 * 
	 * @return True of the node started up correctly, false if there was a problem
	 */
	public boolean startup();
	
	/*
	 * @return True if node is in the running state, False if it has terminated
	 */
	public boolean isRunning();
	
	/*
	 * @return The integer identifier that this node receives and sends messages with
	 */
	public int getNodeId();
	
	/*
	 * Send a message to this node.
	 * 
	 * @return status of message send operation. Only means the message was provided to the node. 
	 * Does not reflect what happens afterwards. 
         * Method will not block if there is not enough space to queue a message.
	 */
	public Status sendTo(Message message);
	
}