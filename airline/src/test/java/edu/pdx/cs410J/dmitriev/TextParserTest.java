package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.ParserException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link TextDumper} class.
 */
public class TextParserTest {

    Airline airline_read = null;
    Airline airline = new Airline("CS410J Airline");
    TextParser parser = new TextParser("CS410J Airline", "CS410J Airline");


    @Test
    public void createNewFileAndAddAirlineWithFlights() throws IOException {
        TextDumper dumper = new TextDumper("CS410J Airline");
        Flight flight_a = new Flight("42", "PDX", "3/11/2017", "12:40", "am",
                "LAX", "4/11/2017", "20:15", "am");
        Flight flight_b = new Flight("42", "LAX", "3/11/2017", "12:40", "pm",
                "LAX", "4/11/2017", "20:15", "am");
        airline.addFlight(flight_a);
        airline.addFlight(flight_b);
        dumper.writeFile(airline);

        try {
            airline_read = parser.parse();
        }
        catch (ParserException e)
        {

        }
        Collection<Flight> allFligths = airline.getFlights();
        assertThat(airline_read.getName(), equalTo("CS410J Airline"));
    }

    @Test
    public void createEmptyAirline() throws IOException {
        try
        {
            airline_read = parser.parse();
        }
        catch (ParserException e)
        {

        }
    }

    //AfterClass
    public static void deleteFile()
    {
        File file = new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\CS410J Airline.txt");
        file.delete();
    }


}
