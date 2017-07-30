package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.web.HttpRequestHelper.Response;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }



  @Test
  public void sendAirlineWithFlight() throws IOException
  {
    AirlineRestClient client = newAirlineRestClient();
    Response response = client.sendFlight("CS410J","42", "PDX", "3/11/2017",
           "12:40", "am", "ABI", "4/11/2017", "20:15", "pm", "nope");
    //Response response = client.searchFlight("CS410J", "PDX", "SPB");
    String content = response.getContent();
    System.out.println(content);
    //client.getAirline("CS410J");
  }
}
