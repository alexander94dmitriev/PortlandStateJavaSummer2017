package edu.pdx.cs410J.dmitriev;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple key/value pairs.
 */
public class AirlineServlet extends HttpServlet {
    private final Map<String, String> data = new HashMap<>();
    private final List<Airline> airlineData = new ArrayList<Airline>();
    private Airline finalAirline = null;

    /**
     * Handles an HTTP GET request from a client by writing the value of the key
     * specified in the "key" HTTP parameter to the HTTP response.  If the "key"
     * parameter is not specified, all of the key/value pairs are written to the
     * HTTP response.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String airlineName = getParameter("name", request);
        String src = getParameter("src", request);
        String dest = getParameter("dest", request);
        PrintWriter pw = response.getWriter();
        if(airlineName == null)
        {
            pw.println("Please, specify the airline to find");
            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
        }
        else if(!finalAirline.getName().equals(airlineName))
        {
            pw.println("No such airline found on the server");
        }
        else if(src != null & dest != null)
        {
            ArrayList<Flight> flightList = new ArrayList<Flight>(finalAirline.getFlights());
            pw.println("The search result\n");
            for(Flight flight: flightList)
            {
                if(finalAirline.getName().equals(airlineName))
                {
                    if(flight.getSource().equals(src) && flight.getDestination().equals(dest))
                    {
                        pw.println("- flight number: "+flight.getNumber() + "\n");
                        pw.println("- flight source: "+flight.getSource() + "\n");
                        pw.println("- flight departure date and time: "+flight.getDepartureString() + "\n");
                        pw.println("- flight destination: "+flight.getDestination() + "\n");
                        pw.println("- flight arrival date and time: "+flight.getArrivalString() + "\n");
                        pw.println("- flight total duration: "+flight.Interval() + " minutes\n\n");
                    }
                }
            }
        }
        else
        {
            ArrayList<Flight> flightList = new ArrayList<Flight>(finalAirline.getFlights());
            Collections.sort(flightList);
            pw.println("\n\t**********AIRLINE INFORMATION**********\n");
            pw.println("Airline name: " + finalAirline.getName() + "\n\n");
            pw.println("\t**********FLIGHTS**********\n");
            for (Flight flight : flightList)
            {
                pw.println("- flight number: "+flight.getNumber() + "\n");
                pw.println("- flight source: "+flight.getSource() + "\n");
                pw.println("- flight departure date and time: "+flight.getDepartureString() + "\n");
                pw.println("- flight destination: "+flight.getDestination() + "\n");
                pw.println("- flight arrival date and time: "+flight.getArrivalString() + "\n");
                pw.println("- flight total duration: "+flight.Interval() + " minutes\n\n");
            }

        }

        pw.flush();
        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP POST request by storing the key/value pair specified by the
     * "key" and "value" request parameters.  It writes the key/value pair to the
     * HTTP response.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain");

        String airlineName = getParameter("name", request);
        if(airlineName == null)
            missingRequiredParameter(response, "name");
        String flightNumber = getParameter("flightNumber", request);
        if(flightNumber == null)
            missingRequiredParameter(response, "flightNumber");
        String src = getParameter("src", request);
        if(src == null)
            missingRequiredParameter(response, "src");
        String departDate = getParameter("departDate", request);
        if(departDate == null)
            missingRequiredParameter(response, "departDate");
        String departTime = getParameter("departTime",request);
        if(departTime == null)
            missingRequiredParameter(response, "departTime");
        String depart_am_pm = getParameter("departampm", request);
        if(depart_am_pm == null)
            missingRequiredParameter(response, "departampm");
        String dest = getParameter("dest",request);
        if(dest == null)
            missingRequiredParameter(response, "dest");
        String arriveDate = getParameter("arriveDate", request);
        if(arriveDate == null)
            missingRequiredParameter(response, "arriveDate");
        String arriveTime = getParameter("arriveTime",request);
        if(arriveTime == null)
            missingRequiredParameter(response, "arriveTime");
        String arrive_am_pm = getParameter("arriveampm", request);
        if(arrive_am_pm == null)
            missingRequiredParameter(response, "arriveampm");
        String print = getParameter("-print", request);


        if(finalAirline == null || !finalAirline.getName().equals(airlineName))
        finalAirline = new Airline(airlineName);
        Flight newFlight = new Flight(flightNumber,src,departDate,departTime,depart_am_pm,dest,arriveDate,arriveTime,arrive_am_pm);

        if(!finalAirline.getFlights().contains(newFlight))
        finalAirline.addFlight(newFlight);

        PrintWriter pw = response.getWriter();
        if(print.equals("-print"))
        {
            pw.println(newFlight.toString());
            pw.flush();

            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }
        pw.println("The flight has been created to the specified airline");
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * <p>
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter(HttpServletResponse response, String parameterName)
            throws IOException {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @return <code>null</code> if the value of the parameter is
     * <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

}