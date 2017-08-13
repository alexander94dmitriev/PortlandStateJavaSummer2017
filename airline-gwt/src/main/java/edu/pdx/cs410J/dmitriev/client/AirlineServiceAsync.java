package edu.pdx.cs410J.dmitriev.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The client-side interface to the airline service
 */
public interface AirlineServiceAsync {

  /**
   * Return an airline created on the server
   */
  void getAirline(AsyncCallback<Airline> async);

  void createAirline(String airlineName, AsyncCallback<Airline> async);

  void getAirlineName(AsyncCallback<String> async);

  void checkArguments(String flightNumber, String src, String departDate, String departTime, String departAmPm, String dest,
                      String arrivalDate, String arrivalTime, String arrivalAmPm, AsyncCallback<String> async);

  void checkAirlineExistence(AsyncCallback<Void> async);

  void addFlight(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                 String dest, String arrivalDate, String arrivalTime, String arrivalAmPm, AsyncCallback<Void> async);

  void prettyPrint(AsyncCallback<String> async);

  void searchFlights(String src, String dest, AsyncCallback<String> async);
}
