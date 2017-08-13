package edu.pdx.cs410J.dmitriev.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.dmitriev.client.Airline;
import edu.pdx.cs410J.dmitriev.client.ArgumentChecker;
import edu.pdx.cs410J.dmitriev.client.Flight;
import edu.pdx.cs410J.dmitriev.client.AirlineService;

import java.text.DateFormat;
import java.util.Collection;
import java.util.Locale;

/**
 * The server-side implementation of the Airline service
 */
public class AirlineServiceImpl extends RemoteServiceServlet implements AirlineService
{
  Airline airline;

  /**
   * Return an airline
   * @return
   * @throws Throwable
   */
  @Override
  public Airline getAirline() throws Throwable {
    if(airline == null)
      throw new Exception("There's no airline on server");
    return airline;
  }

  /**
   * Return airline name
   * @return
   * @throws Throwable
   */
  @Override
  public String getAirlineName() throws Throwable
  {
    try
    {
      return airline.getName();
    }
    catch (Throwable ex)
    {
      throw new Exception("There's no airline on the server");
    }
  }

  @Override
  public void checkAirlineExistence() throws Throwable
  {
    if(airline == null) throw new Exception("There's no airline on server");
  }

  /**
   * Create airline
   * @param airlineName
   *        airline name
   * @return
   */
  @Override
  public Airline createAirline(String airlineName) {
    airline = new Airline();
    airline.createNewAirline(airlineName);
    return airline;
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

  /**
   * Check that the arguments are correctly passed to flight
   * @param flightNumber
   * @param src
   * @param departDate
   * @param departTime
   * @param departAmPm
   * @param dest
   * @param arrivalDate
   * @param arrivalTime
   * @param arrivalAmPm
   * @return
   * @throws Throwable
   */
  public String checkArguments(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                               String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable
  {
    String name = getAirlineName();
    ArgumentChecker checker = new ArgumentChecker();

    String[] args = {flightNumber, src, departDate, departTime, departAmPm, dest, arrivalDate, arrivalTime, arrivalAmPm};
    String result = checker.checkArguments(args,0);
    if (result != null)
      throw new Exception(result);
    return null;
  }

  /**
   * Create a flight from arguments
   * @param flightNumber
   * @param src
   * @param departDate
   * @param departTime
   * @param departAmPm
   * @param dest
   * @param arrivalDate
   * @param arrivalTime
   * @param arrivalAmPm
   * @throws Throwable
   */
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

  /**
   * Pretty print an airline to a string
   * @return
   * @throws Throwable
   */
  @Override
  public String prettyPrint() throws Throwable
  {
    if (airline == null) throw new Exception("Unable to pretty print. There's no airline on the server");

    StringBuilder sb = new StringBuilder("<div>*****************AIRLINE*****************<br>" + "Airline: " + airline.getName());
    Collection<Flight> flights = airline.getFlights();
    if(flights.isEmpty())
      return sb.toString();
    sb.append("<br>              ***********FLIGHTS***********<br><br>");
    for (Flight flight : flights)
    {
      sb.append("<br>- Flight Number: " + flight.getNumber());
      sb.append("<br>- Source Code: " + flight.getSource());
      DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
      String finalDate = formatter.format(flight.getDeparture());
      sb.append("<br>- Departure Date and Time: " + finalDate);
      sb.append("<br> - Destination Code: " + flight.getDestination());
      String arrivalDate = formatter.format(flight.getArrival());
      sb.append("<br>- Arrival Date and Time: " + arrivalDate);
      sb.append("<br> - Flight duration: " + flight.Interval() + " minutes");
      sb.append("<br><br>");
    }

    return sb.toString();
  }

  /**
   * Search the flights and pretty print the result
   * @param src
   * @param dest
   * @return
   * @throws Throwable
   */
    @Override
    public String searchFlights(String src, String dest) throws Throwable {
        if (airline == null) throw new Exception("Unable to search flights. There's no airline on the server");
        StringBuilder sb = new StringBuilder("SEARCH RESULTS: <br><br>");
        Collection<Flight> flights = airline.getFlights();
        if(flights.isEmpty())
            return "Nothing to search. Please add the flight first";
        for (Flight flight : flights)
        {
            if (flight.getSource().equals(src.toUpperCase()) && flight.getDestination().equals(dest.toUpperCase())) {
                sb.append("<br>- Flight Number: " + flight.getNumber());
                sb.append("<br>- Source Code: " + flight.getSource());
              DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
              String finalDate = formatter.format(flight.getDeparture());
              sb.append("<br>- Departure Date and Time: " + finalDate);
                sb.append("<br> - Destination Code: " + flight.getDestination());
              String arrivalDate = formatter.format(flight.getArrival());
              sb.append("<br>- Arrival Date and Time: " + arrivalDate);
                sb.append("<br> - Flight duration: " + flight.Interval() + " minutes");
                sb.append("<br><br>");

            }
        }

        return sb.toString();
    }
}
