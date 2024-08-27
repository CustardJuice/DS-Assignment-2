package Lamport;

import java.util.Vector;

public class NetworkImpl implements Network {
  // initialCapacity set to 10 arbitrarily
  Vector<Node> net = new Vector<Node>(10);

  @Override
  public Network getNetwork() {
    return this;
  }

  @Override
  public int addNode(Node node) throws IndexOutOfBoundsException {
    net.add(node);
    return net.size() - 1;
  }

  @Override
  public SendStatus sendTo(Message message, int identifier) {
    // identifier out of bounds... node does not exist
    if (identifier < 0 || identifier > net.size()) {
      return SendStatus.NO_SUCH_NODE;
    }

    return SendStatus.SUCCESS;
  }

  @Override
  public int lookup() {
    return 0;
  }
}
