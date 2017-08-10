package edu.pdx.cs410J.dmitriev.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.dmitriev.client.Airline;
import edu.pdx.cs410J.dmitriev.client.ArgumentChecker;
import edu.pdx.cs410J.dmitriev.client.Flight;
import edu.pdx.cs410J.dmitriev.client.AirlineService;

import java.io.IOException;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  Airline airline;

  @Override
  public Airline getAirline() throws Throwable {
    if(airline == null)
      throw new Throwable("There's no airline on server");
    //Flight flight = new Flight();
    //flight.createFlight("42", "PDX", "3/11/2017", "12:40", "am",
    //       "SPB", "4/11/2017", "20:15", "pm");
    //airline.addFlight(flight);
    return airline;
  }

  @Override
  public String getAirlineName() throws Throwable
  {
    try
    {
      return airline.getName();
    }
    catch (Throwable ex)
    {
      throw new Throwable("There's no airline on the server");
    }
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

  public String checkArguments(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                               String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable
  {
    String name = getAirlineName();
    ArgumentChecker checker = new ArgumentChecker();

    String[] args = {flightNumber, src, departDate, departTime, departAmPm, dest, arrivalDate, arrivalTime, arrivalAmPm};
    String result = checker.checkArguments(args,0);
    if (result != null)
      throw new Throwable(result);
    return null;
  }

  @Override
  public void checkAirlineExistence() throws Throwable
  {
    if(airline.getName() == null)
      throw new Throwable("The airline does not exists on the server");
  }

  @Override
  public void addFlight(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                 String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable
  {
    Flight flight = new Flight();
    flight.createFlight(flightNumber, src, departDate, departTime, departAmPm,
            dest, arrivalDate, arrivalTime, arrivalAmPm);
    if(!airline.getFlights().contains(flight))
    airline.addFlight(flight);
    else throw new Exception("This flight already exists");
  }
}
