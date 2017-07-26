package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AbstractAirline;
import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.util.*;

/**
 * The main class for the CS410J airline Project
 * This class handles the requirements for Project 1.
 * It creates Airline and Flight objects and works with the arguments passed.
 * It checks for the options and then performs a check of the other arguments.
 * If there's a problem with one of them, the program displays error and exists
 */
public class Project3 {

    private static List<String> allOptions = Arrays.asList("-print", "-textFile", "-README", "-pretty");
    private static int MAX_ARGS = 16;

    /**
     * The main function that performs the task as per Project 1 requirements
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

        Airline airline = null;
        TextParser parser;
        TextDumper dumper;
        PrettyPrinter printer;
        boolean resultCheck;
        int readmeIndex = -1;
        int printIndex;
        int textFileIndex;
        int prettyIndex;
        int prettyFileIndex = -1;
        int firstIndex;
        int fileIndex = -1;
        String[] parts = null;
        List<String> arguments = Arrays.asList(args);
        List<Integer> optionIndexes;
        List<Integer> fileIndexes;

        //Find the indexes of the options in the args
        if (args != null)
            readmeIndex = searchOption("-README", arguments);
        if (readmeIndex != -1) {
            printREADME();
            System.exit(0);
        }
        checkArgs(args);
        printIndex = searchOption("-print", arguments);
        textFileIndex = searchOption("-textFile", arguments);
        prettyIndex = searchOption("-pretty", arguments);

        if(textFileIndex != -1) {
            if(allOptions.contains(args[textFileIndex+1]))
            {
                System.err.println("Please, make sure to add file name after -textFile");
                System.exit(1);
            }
            fileIndex = textFileIndex + 1;
        }

        if(prettyIndex != -1) {
            if(allOptions.contains(args[prettyIndex+1]))
            {
                System.err.println("Please, make sure to add file name after -pretty");
                System.exit(1);
            }
            prettyFileIndex = prettyIndex + 1;
        }
        //Make a list of indexes to find the max. max+1 is the first argument for creating objects
        optionIndexes = Arrays.asList(printIndex, textFileIndex);
        fileIndexes = Arrays.asList(fileIndex, prettyFileIndex);
        int max = Math.max(Collections.max(optionIndexes),Collections.max(fileIndexes));
        firstIndex = max + 1;

        //Make sure the arguments are correct
        resultCheck = ArgumentChecker.checkArguments(args, firstIndex);
        if (!resultCheck)
            System.exit(1);

        //Let the parser do its job
        if (textFileIndex != -1) {
            parts = args[textFileIndex].split(" ");
            parser = new TextParser(args[fileIndex],args[firstIndex]);
            try { airline = parser.parse(); }
            catch (ParserException e)
            {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        else airline = new Airline(args[firstIndex]);
        Flight flight = new Flight(args[firstIndex + 1], args[firstIndex + 2], args[firstIndex + 3], args[firstIndex + 4],
                args[firstIndex + 5], args[firstIndex + 6], args[firstIndex + 7], args[firstIndex + 8], args[firstIndex + 9]);

        if(!airline.getFlights().contains(flight))
        airline.addFlight(flight);

        //Work with options
        if (printIndex != -1) {
            System.out.println(flight.toString());
        }
        if (textFileIndex != -1) {
            dumper = new TextDumper(args[fileIndex]);
            try { dumper.dump(airline); }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }
        if (prettyIndex != -1){
            printer = new PrettyPrinter(args[prettyFileIndex]);
            try { printer.dump(airline); }
            catch (IOException e)
            {
                System.err.println(e.getMessage());
                System.exit(1);
            }
        }

        System.exit(0);
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
        System.out.println("CS410P Project 3 - Pretty Printing Your Airline\n");
        System.out.println("This project creates Airline and Flight objects.");
        System.out.println("Airline has a name and the list of the flights.");
        System.out.println("Flight has a flight number, a source and destination, departure and arrival dates.");
        System.out.println("Project 3 creates those objects and adds Flight to Airline by using the command line arguments:");
        System.out.println("\njava edu.pdx.cs410J.<login-id>.Project3 [options] <args>\n");
        System.out.println("In Project 3, the program can optionally read/write the file with an information.");
        System.out.println("It cal also pretty print, in text file or standard out");
        System.out.println("about Airline and/or Flights in it.");
        System.out.println("The program also accepts 3 possible options:");
        System.out.println("-print - prints out the description of the new flight");
        System.out.println("-README - prints a readme for this project");
        System.out.println("-textFile file - Where to read/write the airline info");
        System.out.println("-pretty file - Pretty print the airline’s flights to\n" +
                            "a text file or standard out (file -)");
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
        if (args.length < 8) {
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