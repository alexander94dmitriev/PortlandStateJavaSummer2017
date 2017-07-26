package edu.pdx.cs410J.dmitriev;

import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link TextDumper} class.
 */
public class TextDumperTest {

    Airline airline = new Airline("CS410J Airline");
    TextDumper dump = new TextDumper("CS410J Airline");

    @Test
    public void getAirlineName() {
        assertThat(airline.getName(), equalTo("CS410J Airline"));
    }

    @Test
    public void createNewFileAndAddAirlineWithFlights() throws IOException {
        Flight flight_a = new Flight("42", "PTR", "3/11/2017", "12:40", "am",
                "SPB", "4/11/2017", "20:15", "am");
        Flight flight_b = new Flight("42", "AAA", "3/11/2017", "12:40", "am",
                "BBB", "4/11/2017", "20:15", "am");
        airline.addFlight(flight_a);
        airline.addFlight(flight_b);
        dump.writeFile(airline);
}

    @AfterClass
    public static void deleteFile()
    {
        File file = new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\CS410J Airline.txt");
        file.delete();
    }

}
