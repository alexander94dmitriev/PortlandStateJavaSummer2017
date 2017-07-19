package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import javax.imageio.IIOException;
import java.io.*;

/**
 * Created by sanek94cool on 14.07.2017.
 */
public class TextParser implements AirlineParser<Airline> {
    private String fileName;
    private String airlineToRead;

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

    private boolean checkFileExistence()
    {
        File f = new File(fileName);
        if (f.exists())
            return true;
        else return false;
    }

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
