package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AbstractAirline;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class contains the major definition of airline and extends <code>AbstractAirline</code>.
 * The class has name of the airline and the list of flights
 */
public class Airline extends AbstractAirline<Flight>
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

}