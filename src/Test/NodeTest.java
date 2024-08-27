package Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import Lamport.Message;
import Lamport.Node;
import Lamport.NodeImpl;

public class NodeTest {
  @Test
  public void startup() {
    Node node = new NodeImpl();
    boolean bool;

    // check hasn't started
    bool = node.isRunning();
    assertEquals(false, bool);

    // check starts up correctly
    bool = node.startup();
    assertEquals(true, bool);
  }

  @Test
  public void isRunning() {
    Node node = new NodeImpl();
    boolean bool;

    // check when node hasn't started
    bool = node.isRunning();
    assertEquals(false, bool);

    // check starts up correctly
    bool = node.startup();
    assertEquals(true, bool);
  }

  @Test
  public void getNodeId() {
  }

  @Test
  public void sendTo() {
  }

}
