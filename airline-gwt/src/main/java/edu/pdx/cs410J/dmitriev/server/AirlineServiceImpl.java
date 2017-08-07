package edu.pdx.cs410J.dmitriev.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.dmitriev.client.Airline;
import edu.pdx.cs410J.dmitriev.client.Flight;
import edu.pdx.cs410J.dmitriev.client.AirlineService;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  Airline airline;

  @Override
  public Airline getAirline() {
    airline.addFlight(new Flight());
    return airline;
  }

  @Override
  public String getAirlineName()
  {
    return airline.getName();
  }

  @Override
  public Airline createAirline(String airlineName) {
    airline = new Airline();
    airline.createNewAirline(airlineName);
    return airline;
  }

  @Override
  public void throwUndeclaredException() {
    throw new IllegalStateException("Expected undeclared exception");
  }

  @Override
  public void throwDeclaredException() throws IllegalStateException {
    throw new IllegalStateException("Expected declared exception");
  }

  /**
   * Log unhandled exceptions to standard error
   *
   * @param unhandled
   *        The exception that wasn't handled
   */
  @Override
  protected void doUnexpectedFailure(Throwable unhandled) {
    unhandled.printStackTrace(System.err);
    super.doUnexpectedFailure(unhandled);
  }
}
