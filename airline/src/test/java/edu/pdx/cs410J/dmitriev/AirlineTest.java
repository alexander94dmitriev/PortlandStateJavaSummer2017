package edu.pdx.cs410J.dmitriev;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Airline} class.
 */
public class AirlineTest {

    Airline airline = new Airline("CS410J Airline");

    @Test
    public void getAirlineName() {
        assertThat(airline.getName(), equalTo("CS410J Airline"));
    }

    @Test
    public void addAndGetFlight() {
        Flight flight = new Flight("42", "Portland", "3/11/2017", "12:40", "am",
                "Saint Petersburg", "4/11/2017", "20:15", "pm");
        airline.addFlight(flight);
        assertThat(airline.findFlight(flight), equalTo(true));
    }



}
