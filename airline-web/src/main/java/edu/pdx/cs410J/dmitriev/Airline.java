package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.AirportNames;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class contains the major definition of airline and extends <code>AbstractAirline</code>.
 * The class has name of the airline and the list of flights
 */
public class Airline extends AbstractAirline<Flight> implements Comparable<Airline>
{
    private Collection<Flight> flights = new ArrayList<>();
    private String name;

    /**
     * Creates a new <code>Airline</code>
     * @param NewName
     *        a new name of the airline
     **/
    Airline(String NewName)
    {
        this.name = NewName;
    }

    /**
     * Getter for the name of airline
     * @return
     *          a name of airline
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Add a new flight to the list of flights in the airline
     * @param flight
     *        a new flight
     */
    @Override
    public void addFlight(Flight flight) {
        this.flights.add(flight);
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
     * Getter for the list of flights
     * @return
     *        the list of flights
     */
    @Override
    public Collection<Flight> getFlights() {
        return this.flights;
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
}