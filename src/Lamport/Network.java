package Lamport;

/*
 * Interface describes the singleton object that represents the network.
 * Nodes can get the singleton and add themselves to it. They can then send messages to other nodes.
 * 
 */

public interface Network {

  public enum SendStatus {
    SUCCESS,
    NO_SUCH_NODE,
    NODE_TERMINATED,
    NETWORK_FAILURE
  }

  /*
   * Returns the singleton Network object
   */

  public Network getNetwork();

  /*
   * Adds a node to the network system
   * 
   * @return the unique identifier (indexed at 0) the node will have in the network.
   * Messages can be sent to it on this identifier, and will come from this
   * identifier when sent.
   * 
   * @throws IndexOutOfBoundsException if there is no more space to add nodes
   */

  public int addNode(Node node) throws IndexOutOfBoundsException;

  /*
   * Send a message to the given node
   * 
   * @param message The message to send
   * 
   * @parm identifier The identifier of the node to send to
   * 
   * @return SUCCESS if the message was send, otherwise reason for failure
   */
  public SendStatus sendTo(Message message, int identifier);

  /*
   * UNIMPLEMENTED FOR NOW
   *  Lookup node by name
   * 
   * @param 
   * 
   * @return 
   */
  public int lookup();
}