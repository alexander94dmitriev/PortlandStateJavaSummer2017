package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AirlineDumper;

import javax.xml.soap.Text;
import java.io.*;
import java.util.Collection;

/**
 * Created by sanek94cool on 14.07.2017.
 */
public class TextDumper implements AirlineDumper<Airline>{
    /**
     * Given an Airline object write its info to a file by calling
     * writeFile() method.
     * @param airline
     * @throws IOException
     */

    private String fileName;

    TextDumper(String newFileName)
    {
        this.fileName = newFileName;
    }
    @Override
    public void dump(Airline airline) throws IOException {
        writeFile(airline);
    }

    /**
     * given the Airline objects with (maybe?) some of the
     * Flight objects inside write their info to a text file
     * With an file name being airline name. The first line is always
     * an airline name. The next ones are lines containing the info about
     * each flight.
     * @param airline
     *                  given Airline object that should be written to a text file
     */
    public void writeFile(Airline airline) throws IOException
    {
        Writer writer = null;

        try {
            writer = new BufferedWriter(new FileWriter(new File(fileName)));

            //writer = new BufferedWriter(new FileWriter(new File("src\\main\\java\\edu\\pdx\\cs410J\\dmitriev\\"+
                                                                //airline.getName()+".txt")));
            writer.write(airline.getName()+"\n");
            Collection<Flight> flightList = airline.getFlights();

            for (Flight flight : flightList)
            {
                writer.write(flight.getNumber()+";"+flight.getSource()+";"+flight.getDepartDate()+";"+
                        flight.getDepartTime()+";"+flight.getDestination()+";"+flight.getArriveDate()+";"+flight.getArriveTime()+"\n");
            }

            writer.close();
            System.gc();
        } catch (IOException ex) {
            writer.close();
            System.gc();
            ex.printStackTrace();
        }
    }
}
