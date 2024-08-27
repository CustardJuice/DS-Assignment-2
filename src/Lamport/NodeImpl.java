package Lamport;

/**
 * NodeImpl
 */
public class NodeImpl implements Node {
  boolean running = false;
  int id;

  @Override
  public boolean startup() {
    running = true;
    return false;

  }

  @Override
  public boolean isRunning() {
    return running;

  }

  @Override
  public int getNodeId() {
    return id;
  }

  @Override
  public Status sendTo(Message message) {
    return null;
  }

}