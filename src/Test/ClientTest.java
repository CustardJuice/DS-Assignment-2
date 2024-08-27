package Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import Client.Client;

public class ClientTest {
  @Test
  public void SimpleMessageSend() throws UnknownHostException, IOException {
    Client client = new Client();
    client.startConnection("127.0.0.1", 6666);
    String response = client.sendMessage("hello server");
    assertEquals("hello client", response);
  }
}
