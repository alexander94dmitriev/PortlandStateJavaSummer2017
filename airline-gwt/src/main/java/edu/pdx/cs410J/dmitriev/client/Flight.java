package edu.pdx.cs410J.dmitriev.client;

import edu.pdx.cs410J.AbstractFlight;

import java.util.Date;

public class Flight extends AbstractFlight  implements Comparable<Flight>
{
  private String flightNumber;
  private String src;
  private String departDate;
  private String departTime;
  private String depart_am_pm;
  private String dest;
  private String arriveDate;
  private String arriveTime;
  private String arrive_am_pm;
  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public Flight()  {
    this.flightNumber = "";
    this.src = "";
    this.departDate = "";
    this.departTime = "";
    this.depart_am_pm = "";
    this.dest = "";
    this.arriveDate = "";
    this.arriveTime = "";
    this.arrive_am_pm = "";
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
  public void createFlight(String newFlightNumber, String newSrc, String newDepartDate,
                           String newDepartTime, String newDepart_am_pm, String newDest, String newArriveDate, String newArriveTime, String newArrive_am_pm)
  {
    this.flightNumber = newFlightNumber;
    this.src = newSrc.toUpperCase();
    this.departDate = newDepartDate;
    this.departTime = newDepartTime;
    this.depart_am_pm = newDepart_am_pm;
    this.dest = newDest.toUpperCase();
    this.arriveDate = newArriveDate;
    this.arriveTime = newArriveTime;
    this.arrive_am_pm = newArrive_am_pm;
  }

  public void createFlight(String newFlightNumber, String newSrc, String newDepartDate, String newDest, String newArriveDate)
  {
    this.flightNumber = newFlightNumber;
    this.src = newSrc.toUpperCase();
    String []parts = newDepartDate.split(" ");
    this.departDate = parts[0];
    this.departTime = parts[1];
    this.depart_am_pm = parts[2];
    this.dest = newDest.toUpperCase();
    parts = newArriveDate.split(" ");
    this.arriveDate = parts[0];
    this.arriveTime = parts[1];
    this.arrive_am_pm = parts[2];
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
    //return this.departDate + " " + this.departTime;
    //String finalDate = this.getDeparture().toString();

    //DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.US);
    //String finalDate = formatter.format(this.getDeparture());

    //String finalDate = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").format(this.getDeparture());

    //return finalDate;
    //DateTimeFormat formater = DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_SHORT);

    //String result = formater.format(getDeparture());
    //return result;
    return this.getDeparture().toString();
  }

  /**
   * Getter for departure date
   * @return
   *          departure date
   */
  public String getDepartDate() { return this.departDate; }

  /**
   * Getter for departure time
   * @return
   *          departure time
   */
  public String getDepartTime() { return this.departTime; }

  /**
   * Getter for destination
   * @return
   *          a destination
   */

  /**
   * getter for Departure time am/pm
   * @return
   */
  public String getDeparture_am_pm() {return this.depart_am_pm;}

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
    //return this.arriveDate + " " + this.arriveTime;
    //String finalDate = this.getArrival().toString();
    //String finalDate = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a").format(this.getArrival());

    //return finalDate;
    return this.getArrival().toString();
  }

  /**
   * Getter for arrival date
   * @return
   *          arrival date
   */
  public String getArriveDate() { return this.arriveDate; }

  /**
   * Getter for arrival time
   * @return
   *          arrival time
   */
  public String getArriveTime() { return this.arriveTime; }

  /**
   * getter for Arrival time am/pm
   * @return
   */
  public String getArrival_am_pm() {return this.arrive_am_pm;}

  /**
   * Get the duration of flight in minutes
   * @return
   */
  public long Interval()
  {
    long result = this.getArrival().getTime() - this.getDeparture().getTime();
    return result/60000;
  }

  /**
   * Get full Departure Date
   * @return
   */
  @Override
  public Date getDeparture() {
    //DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    //DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy h:mm a");
    Date newFormat = new Date(this.departDate + " " + this.departTime + " " + this.depart_am_pm);

    return newFormat;
  }

  /**
   * Get full Arrival Date
   * @return
   */
  @Override
  public Date getArrival() {
   // SimpleDateFormat format;
    Date newFormat = new Date(this.arriveDate + " " + this.arriveTime + " " + this.arrive_am_pm);

    return newFormat;
  }

  /**
   * Override to compare with another Flight for sorting
   * @param flight
   *        other flight
   * @return
   */
  @Override
  public int compareTo(Flight flight) {
    if(this.getSource().equals(flight.getSource()))
    {
      long diff = this.getDeparture().getTime() - flight.getDeparture().getTime();
      if(diff == 0)
        return 0;
      else if(diff < 0)
        return -1;
      else return 1;
    }
    return this.getSource().compareTo(flight.getSource());
  }

  /**
   * Override to check if the objects are equal
   * @param other
   *        other object
   * @return
   */
  @Override
  public boolean equals(Object other) {
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof Flight)) return false;
    Flight otherFlight = (Flight)other;
    if(this.getSource().equals(otherFlight.getSource()))
    {
      if(this.getDepartureString().equals(otherFlight.getDepartureString()))
        return true;
    }
    return false;
  }


}
