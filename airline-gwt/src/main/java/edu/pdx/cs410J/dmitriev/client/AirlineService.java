package edu.pdx.cs410J.dmitriev.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.io.IOException;

/**
 * A GWT remote service that returns a dummy airline
 */
@RemoteServiceRelativePath("airline")
public interface AirlineService extends RemoteService {

  /**
   * Returns the current date and time on the server
   */
  Airline getAirline() throws Throwable;

  String getAirlineName() throws Throwable;

  Airline createAirline(String airlineName);

  String checkArguments(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                        String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable;

  /**
   * Always throws an undeclared exception so that we can see GWT handles it.
   */
  void throwUndeclaredException();

  /**
   * Always throws a declared exception so that we can see GWT handles it.
   */
  void throwDeclaredException() throws IllegalStateException;

  void checkAirlineExistence() throws Throwable;

  void addFlight(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                 String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable;

}
