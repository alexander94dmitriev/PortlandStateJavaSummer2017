package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AirportNames;

/**
 This class handles the check of the arguments for Airline and Flight
 */
public class ArgumentChecker {
    /**
     * Checks if the name length is more than 30 characters. If so, displays error and returns false. Returns true, otherwise.
     *
     * @param name the name of the flight
     * @return the result of the check
     */
    static boolean checkName(String name) {
        if (name.length() > 30) {
            System.err.println("The name of the airline cannot be more than 30 symbols");
            return false;
        } else return true;
    }

    /**
     * Checks if flight number can be parsed as integer. If not, catches the exception, displays error returns false. Return true, otherwise.
     *
     * @param flightNumber a flight number
     * @return the result of the check
     */
    static boolean checkFlightNumber(String flightNumber) {
        try {
            int result = Integer.parseInt(flightNumber);
        } catch (NumberFormatException ex) {
            System.err.println("Unable to parse flightNumber. It should be an integer.");
            return false;
        }

        return true;
    }

    /**
     * Checks if the source length is more than 3 characters. If so, displays error and returns false. Returns true, otherwise
     *
     * @param src the three-letter code of departure airport
     * @return the result of the check
     */
    public static boolean checkSrc(String src) {
        if (src.length() != 3) {
            System.err.println("The source code cannot be more/less than 3 digits");
            return false;
        }
        if(!src.isEmpty()){
            for(char c: src.toCharArray()){
                if(Character.isDigit(c))
                {
                    System.err.println("The source code cannot contain digit");
                    return false;
                }
            }

            if(AirportNames.getName(src.toUpperCase()) == null)
            {
                System.err.println("Unable to find a source code of the airport");
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if the date match the regex pattern MM/DD/YYYY (month and day can be 1 digit).
     * If no, displays error and returns false. Returns true, otherwise.
     *
     * @param date the date of departure/arrival
     * @return the result of the check
     */
    public static boolean checkDate(String date) {
        String pattern = "[0-9]{1,2}[/][0-9]{1,2}[/][0-9]{4}";

        if (!date.matches(pattern)) {
            System.err.println("The date does not matches the pattern MM/DD/YYYY");
            return false;
        } else return true;
    }

    /**
     * Checks if the time match the regex pattern HH:MM (hour can be 1 digit).
     * If no, displays error and returns false. Returns true, otherwise.
     *
     * @param time the time of departure/arrival
     * @return the result of the check
     */
    public static boolean checkTime(String time) {
        String pattern = "[0-9]{1,2}[:][0-9]{2}";

        if (!time.matches(pattern)) {
            System.err.println("The time does not matches the pattern HH:MM");
            return false;
        } else return true;
    }

    /**
     * Checks if the destination length is more than 3 characters. If so, displays error and returns false. Returns true, otherwise.
     *
     * @param dest the three-letter code of arrival airport
     * @return the result of the check
     */
    public static boolean checkDest(String dest) {
        if (dest.length() != 3) {
            System.err.println("The destination code cannot be more/less than 3 digits");
            return false;
        }
        if(!dest.isEmpty()){
            for(char c: dest.toCharArray()){
                if(Character.isDigit(c))
                {
                    System.err.println("The destination code cannot contain digit");
                    return false;
                }
            }

            if(AirportNames.getName(dest.toUpperCase()) == null)
            {
                System.err.println("Unable to find a destination code of the airport");
                return false;
            }
        }
        return true;
    }

    /**
     * Check if there's any other arguments after the last flight argument given. Display error, if so
     * @param args
     *              the list of arguments passed
     * @param index
     *              the index of the last flight argument
     * @return
     *              true, if no other arguments (passed), false otherwise (not passed)
     */
    public  static boolean checkNoOtherArgs(String[] args, int index)
    {
        if (args.length-index > 10)
        {
            System.err.println("There're other args after Airline/Flight arguments");
            return false;
        }
        else return true;
    }

    public static boolean check_am_pm(String am_pm)
    {
        if(!am_pm.equals("am") && !am_pm.equals("pm"))
        {
            System.err.println("No am/pm added with a time");
            return false;
        }
        else return true;
    }

    /**
     * Checks the arguments passed. Returns false if one of the check fails. True, otherwise
     *
     * @param array      command line arguments
     * @param firstIndex the first index of he actual argument (not option)
     * @return the result of the check
     */
    public static boolean checkArguments(String[] array, int firstIndex) {
        String departureDate;
        String departureTime;
        String arrivalDate;
        String arrivalTime;
        int keepFirstIndex = firstIndex;

        if (!checkName(array[firstIndex]) ||
                !checkFlightNumber(array[++firstIndex]) ||
                !checkSrc(array[++firstIndex]) ||
                !checkDate(array[++firstIndex]) ||
                !checkTime(array[++firstIndex]) ||
                !check_am_pm(array[++firstIndex]) ||
                !checkDest(array[++firstIndex]) ||
                !checkDate(array[++firstIndex]) ||
                !checkTime(array[++firstIndex]) ||
                !check_am_pm(array[++firstIndex]) ||
                !checkNoOtherArgs(array,keepFirstIndex)) {
            return false;
        } else return true;
    }
}
