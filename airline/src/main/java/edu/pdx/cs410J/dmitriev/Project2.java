package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Date;

/**
 * The main class for the CS410J airline Project
 * This class handles the requirements for Project 1.
 * It creates Airline and Flight objects and works woth the arguments passed.
 * It checks for the options and then performs a check of the other arguments.
 * If there's a problem with one of them, the program displays error and exists
 */
public class Project2 {

  /**
   * The main function that performs the task as per Project 1 requirements
   * @param args
   *        command line arguments
   */
  public static void main(String[] args) {
    Class c = AbstractAirline.class;  // Refer to one of Dave's classes so that we can be sure it is on the classpath

    boolean resultCheck;
    int readmeResult;
    int printIndex;
    int firstIndex;

    readmeResult = checkREADME(args);
    if(readmeResult != -1)
    {
        printREADME();
        System.exit(0);
    }
    checkArgs(args);
    printIndex = checkPrint(args);


    if(printIndex == -1)
      firstIndex = 0;
    else firstIndex = printIndex + 1;

    resultCheck = checkArguments(args, firstIndex);

    if(!resultCheck)
      System.exit(1);

    Airline airline = new Airline(args[firstIndex]);
    Flight flight = new Flight(args[firstIndex+1], args[firstIndex+2], args[firstIndex+3], args[firstIndex+4],
                               args[firstIndex+5], args[firstIndex+6], args[firstIndex+7]);

    airline.addFlight(flight);

    if(printIndex != -1)
    {
      System.out.println(flight.toString());
    }

    System.exit(0);
  }

    private static void printREADME()
    {
        System.out.println("Alexander Dmitriev");
        System.out.println("CS410P Project 1 - Designing an Airline Application\n");
        System.out.println("This project creates Airline and Flight objects.");
        System.out.println("Airline has a name and the list of the flights.");
        System.out.println("Flight has a flight number, a source and destination, departure and arrival dates.");
        System.out.println("Project 1 creates those objects and adds Flight to Airline by using the command line arguments:");
        System.out.println("\njava edu.pdx.cs410J.<login-id>.Project2 [options] <args>\n");
        System.out.println("The program also accepts two possible options:");
        System.out.println("-print - prints out the description of the new flight");
        System.out.println("-README - prints a readme for this project");
    }

    /**
   *Checks if there's required number of arguments
   * It will exit, if:
   * - There're no arguments
   * - There're less than 8 arguments
   * - There're more than 10 arguments
   * *@param args command line arguments
   */
  private static void checkArgs(String[] args)
  {
    if(args.length <= 0)
    {
      System.err.println("Missing command line arguments");
      System.exit(1);
    }
    if(args.length < 8)
      {
          System.err.println("Not enough arguments");
          System.exit(1);
      }
    if(args.length > 10)
    {
      System.err.println("There're too many arguments");
      System.exit(1);
    }
  }

  /**
   * Checks if there's -README option in the arguments
   * @param array
   *        command line arguments
   * @return -1 if not found, or the index of -README
   */
  private static int checkREADME(String[] array)
  {
    int result;

    if(array.length >= 1 && array[0].equals("-README"))
    {
      result = 0;
    }
    else if(array.length >= 2 && array[1].equals("-README"))
    {
      result = 1;
    }
    else result = -1;

    return result;
  }

  /**
   * Checks if there's a -print option in the first 2 arguments
   * @param array
   *        command line arguments
   * @return
   *        the index found for the option in the first two elements or -1 if not
  */
  private static int checkPrint(String[] array)
  {
    int result;

    if(array[0].equals("-print"))
    {
      result = 0;
    }
    else if(array[1].equals("-print"))
    {
      result = 1;
    }
    else result = -1;

    return result;
  }

  /**
   * Checks if the name length is more than 30 characters. If so, displays error and returns false. Returns true, otherwise.
   * @param name
   *        the name of the flight
   * @return
   *        the result of the check
   */
  private static boolean checkName(String name) {
    if (name.length() > 30) {
      System.err.println("The name of the airline cannot be more than 30 symbols");
      return false;
    }
    else return true;
  }

  /**
   * Checks if flight number can be parsed as integer. If not, catches the exception, displays error returns false. Return true, otherwise.
   * @param flightNumber
   *        a flight number
   * @return
   *        the result of the check
   */
  private static boolean checkFlightNumber(String flightNumber)
  {
    try
    {
      int result = Integer.parseInt(flightNumber);
    }
    catch (NumberFormatException ex)
    {
      System.err.println("Unable to parse flightNumber. It should be an integer.");
      return false;
    }

    return true;
  }

  /**
   * Checks if the source length is more than 3 characters. If so, displays error and returns false. Returns true, otherwise
   * @param src
   *        the three-letter code of departure airport
   * @return
   *        the result of the check
   */
  private static boolean checkSrc(String src)
  {
    if(src.length() > 3)
    {
      System.err.println("The source code cannot be more than 3 digits");
      return false;
    }
    else return true;
  }

  /**
   * Checks if the date match the regex pattern MM/DD/YYYY (month and day can be 1 digit).
   * If no, displays error and returns false. Returns true, otherwise.
   * @param date
   *        the date of departure/arrival
   * @return
   *        the result of the check
   */
  private static boolean checkDate(String date)
  {
    String pattern = "[0-9]{1,2}[/][0-9]{1,2}[/][0-9]{4}";

    if(!date.matches(pattern))
    {
      System.err.println("The date does not matches the pattern MM/DD/YYYY");
      return false;
    }
    else return true;
  }

  /**
   * Checks if the time match the regex pattern HH:MM (hour can be 1 digit).
   * If no, displays error and returns false. Returns true, otherwise.
   * @param time
   *        the time of departure/arrival
   * @return
   *        the result of the check
   */
  private static boolean checkTime(String time)
  {
    String pattern = "[0-9]{1,2}[:][0-9]{2}";

    if(!time.matches(pattern))
    {
      System.err.println("The time does not matches the pattern HH:MM");
      return false;
    }
    else return true;
  }

  /**
   * Checks if the destination length is more than 3 characters. If so, displays error and returns false. Returns true, otherwise.
   * @param dest
   *        the three-letter code of arrival airport
   * @return
   *        the result of the check
   */
  private static boolean checkDest(String dest)
  {
    if(dest.length() > 3)
    {
      System.err.println("The destination code cannot be more than 3 digits");
      return false;
    }
    else return true;
  }

  /**
   * Checks the arguments passed. Returns false if one of the check fails. True, otherwise
   * @param array
   *        command line arguments
   * @param firstIndex
   *        the first index of he actual argument (not option)
   * @return
   *        the result of the check
   */
  public static boolean checkArguments(String[] array, int firstIndex)
  {
    String departureDate;
    String departureTime;
    String arrivalDate;
    String arrivalTime;

    if(!checkName(array[firstIndex]) ||
       !checkFlightNumber(array[++firstIndex]) ||
       !checkSrc(array[++firstIndex]) ||
       !checkDate(array[++firstIndex]) ||
       !checkTime(array[++firstIndex]) ||
       !checkDest(array[++firstIndex]) ||
       !checkDate(array[++firstIndex]) ||
       !checkTime(array[++firstIndex]))
    {
      return false;
    }
    else return true;
  }

}