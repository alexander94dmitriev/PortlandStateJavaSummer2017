package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import javax.imageio.IIOException;
import java.io.*;

/**
 * TextParser get the name of the file along with the name of the Airline being read.
 * It supposed to correctly read the data about the Airline on a specific file along with the flights.
 * It should also check that flights data is correct by using the same method that were used to check
 * program line arguments.
 * If the file does not exist, the class should create it with an empty Airline being added
 * It also handles IO and Parser exceptions
 */
public class TextParser implements AirlineParser<Airline> {
    private String fileName;
    private String airlineToRead;

    /**
     * Constructor for TextParser
     * @param newName
     *                  the name of the file being read/ created with empty Airline
     * @param newAirlineToRead
     *                  the name of Airline to read/ add to an empty file
     */
    TextParser(String newName, String newAirlineToRead)
    {
        this.fileName = newName;
        this.airlineToRead = newAirlineToRead;
    }

    /**
     * call the readFile() method which will give the complete Airline object.
     * The method catches IOException and then throws ParserException with an
     * error message, if anything wrong with IO
     * @return
     *          the airline with a given name and/or flights
     * @throws ParserException
     */
    @Override
    public Airline parse() throws ParserException {
        //Read text file to object Airline

        Airline airline = null;

        try {
            if(checkFileExistence())
            airline = readFile();
            else airline = createNewFile();
        }
        catch (IOException e)
        {
            throw new ParserException("Error while parsing the Airline: "+ e.getMessage());
        }
        return airline;
    }

    /**
     * Check that the file exists
     * @return
     *          true if exists, false otherwise
     */
    private boolean checkFileExistence()
    {
        File f = new File(fileName);
        if (f.exists())
            return true;
        else return false;
    }

    /**
     * Create a new file with an Airline added
     * @return
     *         an airline being added
     * @throws IOException
     *          if IO error found
     */
    private Airline createNewFile() throws IOException {
        Writer writer = new BufferedWriter(new FileWriter(new File(fileName)));
        writer.write(airlineToRead+"\n");
        Airline airline = new Airline(airlineToRead);
        writer.close();

        return airline;
    }

    /**
     * read the file with given name, create Airline object and add all of the
     * Flight objects found in file
     * @return
     *              Airline with flights from file
     * @throws IOException
     *              IF anny IO/Parsing error found
     */
    private Airline readFile() throws IOException {
        Airline returnAirline = null;
        boolean resultCheck;

            //File f = new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\"+fileName+".txt");
            File f = new File(fileName);
            if (!f.exists())
            {
                throw new IOException("File not found");
            }
            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";
            String fileAirlineName = b.readLine();
            if(!airlineToRead.equals(fileAirlineName))
            {
                b.close();
                System.gc();
                throw new IOException("The name of the airline you looking for does not match the airline found in the file");
            }
            Airline airline = new Airline(fileAirlineName);

            while ((readLine = b.readLine()) != null) {
                String[] split = readLine.split(";|\n");
                resultCheck = checkFlightArguments(split);
                if(!resultCheck)
                {
                    b.close();
                    System.gc();
                    throw new IOException("Unable to parse one of the flights, malformated data");
                }
                Flight newFlight = new Flight(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
                airline.addFlight(newFlight);
                readLine = null;
            }

            b.close();
            System.gc();
            returnAirline = airline;

        return returnAirline;

    }

    /**
     * Use ArgumentChecker methods to make sure flights data is formatted correctly.
     * Jsut as with program line arguments
     * @param array
     *              a list of flight data
     * @return
     *              true if passes. False if fails.
     */
    private boolean checkFlightArguments(String[] array)
    {
        if (!ArgumentChecker.checkFlightNumber(array[0]) ||
                !ArgumentChecker.checkSrc(array[1]) ||
                !ArgumentChecker.checkDate(array[2]) ||
                !ArgumentChecker.checkTime(array[3]) ||
                !ArgumentChecker.checkDest(array[4]) ||
                !ArgumentChecker.checkDate(array[5]) ||
                !ArgumentChecker.checkTime(array[6]) ||
                !ArgumentChecker.checkNoOtherArgs(array,0)) {
            return false;
        } else return true;
    }
}
