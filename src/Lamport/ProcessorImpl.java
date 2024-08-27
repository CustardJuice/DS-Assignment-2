package Lamport;

import java.util.concurrent.Executor;

public class ProcessorImpl implements Processor {

  @Override
  public boolean start() {
    return false;
  }

  @Override
  public void send(Message message, int to) {

  }

  @Override
  public void terminate() {

  }

  @Override
  public void setExecutor(Executor executor) {
  }

}
