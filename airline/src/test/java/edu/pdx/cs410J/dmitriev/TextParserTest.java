package edu.pdx.cs410J.dmitriev;

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
    Airline airline = new Airline("CS410J Airline1");
    TextParser parser = new TextParser("CS410J Airline");


    @Test
    public void createNewFileAndAddAirlineWithFlights() throws IOException {
        TextDumper dumper = new TextDumper();
        Flight flight_a = new Flight("42", "PTR", "3/11/2017", "12:40",
                "SPB", "4/11/2017", "20:15");
        Flight flight_b = new Flight("42", "AAA", "3/11/2017", "12:40",
                "BBB", "4/11/2017", "20:15");
        airline.addFlight(flight_a);
        airline.addFlight(flight_b);
        dumper.writeFile(airline);

        airline_read = parser.readFile();
        Collection<Flight> allFligths = airline.getFlights();
        assertThat(airline_read.getName(), equalTo("CS410J Airline"));

        for(Flight flight : allFligths)
        {
            assertThat(flight.toString(), equalTo("Flight "+flight.getNumber()+" departs "+flight.getSource()+" at "+
                    flight.getDepartDate()+" "+flight.getDepartTime()+" arrives "+
                    flight.getDestination()+" at "+flight.getArriveDate()+" "+flight.getArriveTime()));
        }
    }

    @AfterClass
    public static void deleteFile()
    {
        File file = new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\CS410J Airline1.txt");
        file.delete();
    }


}
