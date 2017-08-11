package edu.pdx.cs410J.dmitriev.client;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Airline extends AbstractAirline<Flight> implements Comparable<Airline>
{
  private Collection<Flight> flights = new ArrayList<>();
  private String name;
  /**
   * In order for GWT to serialize this class (so that it can be sent between
   * the client and the server), it must have a zero-argument constructor.
   */
  public Airline() {
      name = null;
  }

  public boolean airlineExists()
  {
    if(name == null)
      return false;
    else return true;
  }
  /**
   * Find a particular flight in the list and return it if found
   * @param flight
   *        a flight to find
   * @return
   *        the flight found or null
   */
  public boolean findFlight(Flight flight) { return this.flights.contains(flight);}

  /**
   * Getter for the name of airline
   * @return
   *          a name of airline
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Add a new flight to the list of flights in the airline
   * @param flight
   *        a new flight
   */
  @Override
  public void addFlight(Flight flight) {
    this.flights.add(flight);
    //ArrayList<Flight> flightList = new ArrayList<Flight>(this.flights);
    //Collections.sort(flightList);
    //this.flights = flightList;
  }

  /**
   * Getter for the list of flights
   * @return
   *        the list of flights
   */
  @Override
  public Collection<Flight> getFlights() {
    return this.flights;
  }

  public void createNewAirline(String airlineName)
  {
    this.name = airlineName;
  }


  @Override
  public int compareTo(Airline other) {
    if(other.getName().equals(this.getName()))
      return 0;
    else return other.getName().compareTo(this.getName());
  }

  @Override
  public boolean equals(Object other)
  {
    if (other == null) return false;
    if (other == this) return true;
    if (!(other instanceof Airline)) return false;
    Airline otherAirline = (Airline)other;
    if(otherAirline.getName().equals(this.getName()))
      return true;
    return false;
  }

  @Override
  public String toString() {
    return "********AIRLINE********\n" + "Airline: " + this.getName() + "\nNumber of flights: " + this.getFlights().size() +
            " flights\n\n";
  }
}
