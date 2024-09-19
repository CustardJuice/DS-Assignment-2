package Test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.UnknownHostException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import AggregationServer.AggregationServer;

import java.io.ByteArrayOutputStream;

import Client.GETClient;

public class ClientTest {
  private final PrintStream standardOut = System.out;
  private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @After
  public void tearDown() {
    System.setOut(standardOut);
  }

  @Test
  public void GETClientConnectsNoID() {
    GETClient client = new GETClient();
    String url = "http://127.0.0.1:4567";
    String response = client.run(url);

    // Assert.assertEquals("", outputStreamCaptor.toString().trim());
  }
}
