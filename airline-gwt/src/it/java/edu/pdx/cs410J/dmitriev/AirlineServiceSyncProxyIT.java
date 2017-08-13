package edu.pdx.cs410J.dmitriev;

import com.gdevelop.gwt.syncrpc.SyncProxy;
import edu.pdx.cs410J.dmitriev.client.Airline;
import edu.pdx.cs410J.dmitriev.client.AirlineService;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

public class AirlineServiceSyncProxyIT extends HttpRequestHelper {

  private final int httpPort = Integer.getInteger("http.port", 8080);
  private String webAppUrl = "http://localhost:" + httpPort + "/airline";



}
