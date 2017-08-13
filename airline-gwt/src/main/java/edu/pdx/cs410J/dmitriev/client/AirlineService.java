package edu.pdx.cs410J.dmitriev.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * A GWT remote service that returns a dummy airline
 */
@RemoteServiceRelativePath("airline")
public interface AirlineService extends RemoteService {

  /**
   * Returns airline
   * @return
   * @throws Throwable
   */
  Airline getAirline() throws Throwable;

  /**
   * Returns airline name
   * @return
   * @throws Throwable
   */
  String getAirlineName() throws Throwable;

  /**
   * Create airline
   * @param airlineName
   *        airline name
   * @return
   */
  Airline createAirline(String airlineName);

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
  String checkArguments(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                        String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable;

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
  void addFlight(String flightNumber, String src, String departDate, String departTime, String departAmPm,
                 String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) throws Throwable;

  /**
   * Pretty print an airline to a string
   * @return
   * @throws Throwable
   */
  String prettyPrint() throws Throwable;

  void checkAirlineExistence() throws Throwable;

  /**
   * Search the flights and pretty print the result
   * @param src
   * @param dest
   * @return
   * @throws Throwable
   */
  String searchFlights(String src, String dest) throws Throwable;
}
