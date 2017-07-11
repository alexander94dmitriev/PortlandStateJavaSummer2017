package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AbstractFlight;

import java.util.Date;

/**
 * This class contains the major definition of flight and extends <code>AbstractFlight</code>.
 * The class has flight number, source, departure date and time, arrival date and time,
 * destination
 */
public class Flight extends AbstractFlight {

  private String flightNumber;
  private String src;
  private String departDate;
  private String departTime;
  private String dest;
  private String arriveDate;
  private String arriveTime;

  /**
   * Creates a new <code>Flight</code>
   * Empty object
   */
  Flight()
  {
    this.flightNumber = null;
    this.src = null;
    this.departTime = null;
    this.dest = null;
    this.arriveTime = null;
  }

  /**
   * Creates a new <code>Flight</code>
   * @param newFlightNumber
   *        a new Flight number
   * @param newSrc
   *        a new Source
   * @param newDepartDate
   *        a new Departure date
   * @param newDepartTime
   *        a new Departure time
   * @param newDest
   *        a new Destination
   * @param newArriveDate
   *        a new Arrival date
   * @param newArriveTime
   *        a new Arrival time
   */
  Flight(String newFlightNumber, String newSrc, String newDepartDate,
         String newDepartTime, String newDest, String newArriveDate, String newArriveTime)
  {
    this.flightNumber = newFlightNumber;
    this.src = newSrc;
    this.departDate = newDepartDate;
    this.departTime = newDepartTime;
    this.dest = newDest;
    this.arriveDate = newArriveDate;
    this.arriveTime = newArriveTime;
  }

    /**
     * Getter for flight number
     * @return
     *          a flight number
     */
  @Override
  public int getNumber() {
    return Integer.parseInt(flightNumber);
  }

    /**
     * Getter for source
     * @return
     *          a source
     */
  @Override
  public String getSource() {
    return this.src;
  }

    /**
     * Getter for departure date and time
     * @return
     *          departure date and time
     */
  @Override
  public String getDepartureString() {
    return this.departDate + " " + this.departTime;
  }

    /**
     * Getter for destination
     * @return
     *          a destination
     */
  @Override
  public String getDestination() {
    return this.dest;
  }

    /**
     * Getter for arrival date and time
     * @return
     *          arrival date and time
     */
  @Override
  public String getArrivalString() {
    return this.arriveDate + " " + this.arriveTime;
  }

}
