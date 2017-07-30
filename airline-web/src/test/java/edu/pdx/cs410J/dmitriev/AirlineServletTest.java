package edu.pdx.cs410J.dmitriev;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AirlineServletTest {

    Airline airline = new Airline("CS410J Airline");
    Flight flight = new Flight("42", "Portland", "3/11/2017", "12:40", "am",
            "Saint Petersburg", "4/11/2017", "20:15", "pm");

    @Test
    public void test0SendAirlineWithFlight ()
    {

    }
}
