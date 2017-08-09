package edu.pdx.cs410J.dmitriev.client;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.dmitriev.client.AirlineGwt;

/**
 This class handles the check of the arguments for Airline and Flight
 */
public class ArgumentChecker  {


    /**
     * Checks if the name length is more than 30 characters. If so, displays error and returns false. Returns true, otherwise.
     *
     * @param name the name of the flight
     * @return the result of the check
     */
    String checkName(String name) {
        if (name.length() > 30) {
            return "The name of the airline cannot be more than 30 symbols";

        } else return null;
    }

    /**
     * Checks if flight number can be parsed as integer. If not, catches the exception, displays error returns false. Return true, otherwise.
     *
     * @param flightNumber a flight number
     * @return the result of the check
     */
    String checkFlightNumber(String flightNumber) {
        for (char c: flightNumber.trim().toCharArray())
        {
            if(!Character.isDigit(c))
            {
                return "Unable to parse flightNumber. It should be an integer.";
            }
        }

        return null;
        /*
        try {
            int result = Integer.parseInt(flightNumber);
        } catch (NumberFormatException ex) {
            alerter.alert("Unable to parse flightNumber. It should be an integer.");
            return false;
        }

        return true;
*/
    }

    /**
     * Checks if the source length is more than 3 characters. If so, displays error and returns false. Returns true, otherwise
     *
     * @param src the three-letter code of departure airport
     * @return the result of the check
     */
    public String checkSrc(String src) {
        if (src.length() != 3) {
            return "The source code cannot be more/less than 3 digits";
        }
        if(!src.isEmpty()){
            for(char c: src.toCharArray()){
                if(Character.isDigit(c))
                {
                    return "The source code cannot contain digit";
                }
            }

            if(AirportNames.getName(src.toUpperCase()) == null)
            {
                return "Unable to find a source code of the airport";
            }
        }

        return null;
    }

    /**
     * Checks if the date match the regex pattern MM/DD/YYYY (month and day can be 1 digit).
     * If no, displays error and returns false. Returns true, otherwise.
     *
     * @param date the date of departure/arrival
     * @return the result of the check
     */
    public String checkDate(String date) {
        String pattern = "[0-9]{1,2}[/][0-9]{1,2}[/][0-9]{4}";

        if (!date.matches(pattern)) {
            return "The date does not matches the pattern MM/DD/YYYY";
        } else return null;
    }

    /**
     * Checks if the time match the regex pattern HH:MM (hour can be 1 digit).
     * If no, displays error and returns false. Returns true, otherwise.
     *
     * @param time the time of departure/arrival
     * @return the result of the check
     */
    public String checkTime(String time) {
        String pattern = "[0-9]{1,2}[:][0-9]{2}";

        if (!time.matches(pattern)) {
            return "The time does not matches the pattern HH:MM";
        } else return null;
    }

    /**
     * Checks if the destination length is more than 3 characters. If so, displays error and returns false. Returns true, otherwise.
     *
     * @param dest the three-letter code of arrival airport
     * @return the result of the check
     */
    public String checkDest(String dest) {
        if (dest.length() != 3) {
            return "The destination code cannot be more/less than 3 digits";
        }
        if(!dest.isEmpty()){
            for(char c: dest.toCharArray()){
                if(Character.isDigit(c))
                {
                    return "The destination code cannot contain digit";
                }
            }

            if(AirportNames.getName(dest.toUpperCase()) == null)
            {
                return "Unable to find a destination code of the airport";
            }
        }
        return null;
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
    public  String checkNoOtherArgs(String[] args, int index)
    {
        if (args.length-index > 10)
        {
            return "There're other args after Airline/Flight arguments";
        }
        else return null;
    }

    public String check_am_pm(String am_pm)
    {
        if(!am_pm.equals("am") && !am_pm.equals("pm"))
        {
            return "No am/pm added with a time";
        }
        else return null;
    }

    /**
     * Checks the arguments passed. Returns false if one of the check fails. True, otherwise
     *
     * @param array      command line arguments
     * @param firstIndex the first index of he actual argument (not option)
     * @return the result of the check
     */
    public String checkArguments(String[] array, int firstIndex) throws ArrayIndexOutOfBoundsException {
        String departureDate;
        String departureTime;
        String arrivalDate;
        String arrivalTime;
        int keepFirstIndex = firstIndex;
        String result = checkFlightNumber(array[firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = checkSrc(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = checkDate(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = checkTime(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = check_am_pm(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = checkDest(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }        result = checkDate(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = checkTime(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }
        result = check_am_pm(array[++firstIndex]);
        if (result != null)
        {
            return result;
        }


        return null;
    }
}
