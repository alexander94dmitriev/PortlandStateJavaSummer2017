package edu.pdx.cs410J.dmitriev;

import com.google.common.annotations.VisibleForTesting;
import com.sun.corba.se.pept.transport.ResponseWaitingRoom;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.util.Map;

import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send key/value pairs.
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    private final String url;

    /**
     * Creates a client to the airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET);
    }

    public Response sendFlight(String airlineName, String newFlightNumber, String newSrc, String newDepartDate, String newDepartTime,
                           String newDepart_am_pm, String newDest, String newArriveDate, String newArriveTime, String newArrive_am_pm, String print) throws IOException
    {
        String departure = newDepartDate + " " + newDepartTime + " " + newDepart_am_pm;
        String arrival = newArriveDate + " " + newArriveTime + " " + newArrive_am_pm;

        Response response = postToMyURL("name",airlineName,"flightNumber",newFlightNumber,"src",newSrc, "departDate", departure,
                                       "dest", newDest, "arriveDate", arrival, "-print", print);
        throwExceptionIfNotOkayHttpStatus(response);

        return response;
    }

    public Response searchFlight(String airlineName, String src, String dest) throws IOException
    {
        Response response = get(this.url, "name", airlineName, "src", src, "dest", dest);
        throwExceptionIfNotOkayHttpStatus(response);

        return response;
    }

    public Response getAirline(String airlineName) throws IOException
    {
        Response response = get(this.url, "name", airlineName);
        throwExceptionIfNotOkayHttpStatus(response);

        return response;
    }


    @VisibleForTesting
    Response postToMyURL(String... keysAndValues) throws IOException {
        return post(this.url, keysAndValues);
    }

    private Response throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getCode();
        if (code != HTTP_OK) {
            throw new AppointmentBookRestException(code);
        }
        return response;
    }

    private class AppointmentBookRestException extends RuntimeException {
        public AppointmentBookRestException(int httpStatusCode) {
            super("Got an HTTP Status Code of " + httpStatusCode);
        }
    }
}