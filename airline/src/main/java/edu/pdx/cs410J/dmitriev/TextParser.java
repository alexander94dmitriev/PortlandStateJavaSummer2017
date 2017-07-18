package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.*;

/**
 * Created by sanek94cool on 14.07.2017.
 */
public class TextParser implements AirlineParser<Airline> {
    private String fileName;

    TextParser(String newName)
    {
        this.fileName = newName;
    }


    /**
     * call the readFile() method which will give the complete Airline object.
     * The method catches IOException and then throws ParserException with an
     * error message, if anything wrong with IO
     * @return
     * @throws ParserException
     */
    @Override
    public Airline parse() throws ParserException {
        //Read text file to object Airline

        Airline airline = new Airline(null);

        try {
            airline = readFile();
        }
        catch (IOException e)
        {
            throw new ParserException("Error while parsing the Airline");
        }
        return null;
    }

    /**
     * read the flie with given name, create Airline object and add all of the
     * Flight objects found in file
     * @return
     *              Airline with flights from file
     * @throws IOException
     *              IF anny IO error found
     */
    public Airline readFile() throws IOException {
        Airline returnAirline = null;

        try {

            File f = new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\"+fileName+".txt");

            BufferedReader b = new BufferedReader(new FileReader(f));

            String readLine = "";

            Airline airline = new Airline(b.readLine());
            while ((readLine = b.readLine()) != null) {
                String[] split = readLine.split(";|\n");
                Flight newFlight = new Flight(split[0], split[1], split[2], split[3], split[4], split[5], split[6]);
                airline.addFlight(newFlight);
                readLine = null;
            }

            b.close();
            returnAirline = airline;

        } catch (FileNotFoundException e) {
            returnAirline = new Airline(fileName);
            Writer writer = new BufferedWriter(new FileWriter(new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\"+
                    returnAirline.getName()+".txt")));
            writer.write(returnAirline.getName()+"\n");
            writer.close();
            return returnAirline;
        }

        return returnAirline;

    }
}
