package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The main class that parses the command line and communicates with the
 * Airline server using REST.
 * The main class for the CS410J airline Project
 * This class handles the requirements for Project 4.
 * It creates Airline and Flight objects and works with the arguments passed.
 * It checks for the options and then performs a check of the other arguments.
 * If there's a problem with one of them, the program displays error and exists
 */
public class Project4 {

    private static List<String> allOptions = Arrays.asList("-print", "-host", "-README", "-port", "-search");
    private static int MAX_ARGS = 17;
    public static final String MISSING_ARGS = "Missing command line arguments";

    public static void main(String[] args) {
        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        HttpRequestHelper.Response response;
        AirlineRestClient client = null;
        boolean resultCheck;
        int readmeOptionIndex = -1;
        int printOptionIndex;
        int searchOptionIndex;
        int hostOptionIndex;
        int hostIndex = -1;
        int portOptionIndex;
        int portIndex = -1;
        int port = 0;
        int firstIndex = -1;
        List<String> arguments = Arrays.asList(args);
        List<Integer> optionIndexes;
        List<Integer> optionArgumentsIndexes;

        //Find the indexes of the options in the args
        if (args != null)
            readmeOptionIndex = searchOption("-README", arguments);
        if (readmeOptionIndex != -1) {
            printREADME();
            System.exit(0);
        }
        checkArgs(args);
        printOptionIndex = searchOption("-print", arguments);
        hostOptionIndex = searchOption("-host", arguments);
        portOptionIndex = searchOption("-port", arguments);
        searchOptionIndex = searchOption("-search", arguments);

        if(hostOptionIndex != -1) {
            if(allOptions.contains(args[hostOptionIndex+1]))
            {
                System.err.println("Please, make sure to add host name after -host");
                System.exit(1);
            }
            hostIndex = hostOptionIndex + 1;
        }
        if(portOptionIndex != -1)
        {
            if(allOptions.contains(args[portOptionIndex+1]))
            {
                System.err.println("Please, make sure to add host name after -host");
                System.exit(1);
            }
            portIndex = portOptionIndex + 1;
        }
        if(searchOptionIndex != -1)
        {
            if(allOptions.contains(args[searchOptionIndex+1]))
            {
                System.err.println("Please, make sure to add airline name, source and destination after -search");
                System.exit(1);
            }
        }

            //Make a list of indexes to find the max. max+1 is the first argument for creating objects
            optionIndexes = Arrays.asList(printOptionIndex, hostOptionIndex, searchOptionIndex);
            optionArgumentsIndexes = Arrays.asList(hostIndex, portIndex);
            int max = Math.max(Collections.max(optionIndexes),Collections.max(optionArgumentsIndexes));
            firstIndex = max + 1;


        if(searchOptionIndex == -1) {
            //Make sure the arguments are correct
            if(firstIndex == -1)
            {
                error("Please, make sure to provide arguments for airline and flight");
            }
            try {
                resultCheck = ArgumentChecker.checkArguments(args, firstIndex);
                if (!resultCheck)
                    System.exit(1);
            }
            catch (ArrayIndexOutOfBoundsException ex)
            {
                error("Unable to check arguments correctly. Please, make sure to add them for airline and flight information");
            }
        }

        try
        {
            if(portIndex != -1)
            port = Integer.parseInt(args[portIndex]);
        }
        catch (NumberFormatException ex)
        {
            error("Port \"" + args[portIndex] + "\" must be an integer");
            return;
        }

        //Work with server
        try {
            if(hostIndex != -1 && portIndex == -1)
            {
                error("Please, specify the port");
            }
            else if(hostIndex == -1 && portIndex != -1)
            {
                error("Please, specify the host");
            }
            if(hostIndex != -1 && portIndex != -1) {
                client = new AirlineRestClient(args[hostIndex], port);

                //Work with options
                if (printOptionIndex != -1) {
                    if (searchOptionIndex != -1) {
                        error("Please, choose between -print and -search options and use only one");
                    }
                    response = client.sendFlight(args[firstIndex], args[firstIndex + 1], args[firstIndex + 2], args[firstIndex + 3], args[firstIndex + 4],
                            args[firstIndex + 5], args[firstIndex + 6], args[firstIndex + 7], args[firstIndex + 8], args[firstIndex + 9], "-print");
                    String contents = response.getContent();
                    System.out.println(contents);
                } else if (searchOptionIndex != -1) {
                    if (args.length - firstIndex > 3) {
                        error("Please, provide only airline name, source and destination for search");
                    }
                    response = client.searchFlight(args[firstIndex], args[firstIndex + 1], args[firstIndex + 2]);
                    String contents = response.getContent();
                    System.out.println(contents);
                } else {
                    response = client.sendFlight(args[firstIndex], args[firstIndex + 1], args[firstIndex + 2], args[firstIndex + 3], args[firstIndex + 4],
                            args[firstIndex + 5], args[firstIndex + 6], args[firstIndex + 7], args[firstIndex + 8], args[firstIndex + 9], "nope");
                    String contents = response.getContent();
                    System.out.println(contents);
                }
            }
            else
            {
                //Work with it as in the first project
                Airline airline = new Airline(args[firstIndex]);
                Flight flight = new Flight(args[firstIndex + 1], args[firstIndex + 2], args[firstIndex + 3], args[firstIndex + 4],
                        args[firstIndex + 5], args[firstIndex + 6], args[firstIndex + 7], args[firstIndex + 8], args[firstIndex + 9]);

                if(!airline.getFlights().contains(flight))
                    airline.addFlight(flight);
                if (printOptionIndex != -1) {
                    System.out.println(flight.toString());
                }
            }
        }
        catch ( IOException ex )
        {
            error("While contacting server: " + ex);
            return;
        }

        System.exit(0);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);

        System.exit(1);
    }


    /**
     * Search for a particular option passed as argument.
     *
     * @param optionToFind the option you want to find
     * @param arguments    the list of arguments passed
     * @return the index of option in arguments list or -1 if not found
     */
    public static int searchOption(String optionToFind, List<String> arguments) {
        for (String option : allOptions) {
            if (option.contains(optionToFind)) {
                for (String arg: arguments)
                {
                    if (arg.contains(optionToFind))
                        return arguments.indexOf(arg);
                }

            }
        }

        return -1;
    }

    private static void printREADME() {
        System.out.println("Alexander Dmitriev");
        System.out.println("CS410P Project 4 -  A REST-ful Airline Web Service\n");
        System.out.println("This project creates Airline and Flight objects.");
        System.out.println("Airline has a name and the list of the flights.");
        System.out.println("Flight has a flight number, a source and destination, departure and arrival dates.");
        System.out.println("Project 3 creates those objects and adds Flight to Airline by using the command line arguments:");
        System.out.println("\njava edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n");
        System.out.println("In Project 4, the program interacts with the server and allows to add a new flight and search for" +
                "a specific one.");
        System.out.println("about Airline and/or Flights in it.");
        System.out.println("The program also accepts 3 possible options:");
        System.out.println("-host hostname -  Host computer on which the server runs");
        System.out.println("-port port - Port on which the server is listening");
        System.out.println("-print - prints out the description of the new flight");
        System.out.println("-search - Search for flights");
        System.out.println("-README - prints a readme for this project");
    }

    /**
     * Checks if there's required number of arguments
     * It will exit, if:
     * - There're no arguments
     * - There're less than 8 arguments
     * - There're more than MAX_ARGS arguments
     * *@param args command line arguments
     */
    private static void checkArgs(String[] args) {
        if (args.length <= 0) {
            System.err.println("Missing command line arguments");
            System.exit(1);
        }
        if (args.length < 2) {
            System.err.println("Not enough arguments");
            System.exit(1);
        }
        if (args.length > MAX_ARGS) {
            System.err.println("There're too many arguments");
            System.exit(1);
        }
    }

    /**
     * Checks if there's -README option in the arguments
     *
     * @param array command line arguments
     * @return -1 if not found, or the index of -README
     */
    private static int checkREADME(String[] array) {
        int result;

        if (array.length >= 1 && array[0].equals("-README")) {
            result = 0;
        } else if (array.length >= 2 && array[1].equals("-README")) {
            result = 1;
        } else result = -1;

        return result;
    }

    /**
     * Checks if there's a -print option in the first 2 arguments
     *
     * @param array command line arguments
     * @return the index found for the option in the first two elements or -1 if not
     */
    private static int checkPrint(String[] array) {
        int result;

        if (array[0].equals("-print")) {
            result = 0;
        } else if (array[1].equals("-print")) {
            result = 1;
        } else result = -1;

        return result;
    }

}