package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Lamport.Message;
import Lamport.Network;
import Lamport.NetworkImpl;
import Lamport.Node;
import Lamport.NodeImpl;

/**
 * NetworkTest
 */
public class NetworkTest {
  @Test
  public void getNetwork() {
    Network net = new NetworkImpl();
    net.getNetwork();
  }

  @Test
  public void addNode() throws IndexOutOfBoundsException {
    Network net = new NetworkImpl();
    Node node;
    int id;

    node = new NodeImpl();
    id = net.addNode(node);
    assertEquals(0, id);

    node = new NodeImpl();
    id = net.addNode(node);
    assertEquals(1, id);

    node = new NodeImpl();
    id = net.addNode(node);
    assertEquals(2, id);
  }

  @Test
  public void sendTo() {
    Network net = new NetworkImpl();
    Message message;
    Network.SendStatus status;
    
    message = new Message(0, 1, 0, null);
    status = net.sendTo(null, message.to());
    assertEquals("SUCCESS", status.toString());
  }
}