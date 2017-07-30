package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.AirlineDumper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * This class creates a text file or outputs to standard out the info about the Airline and its flights in a good readable format
 */
public class PrettyPrinter implements AirlineDumper<Airline>{
    String fileName;

    PrettyPrinter(String newFileName)
    {
        if(newFileName.equals("-"))
            fileName = null;
        else fileName = newFileName;
    }



    /**
     * Call writeFile() method to dump the Airline and add it to text file
     * @param airline
     *                  Airline to dump
     * @throws IOException
     *                  If IO error is found
     */
    @Override
    public void dump(Airline airline) throws IOException {
        writeFile(airline);
    }

    /**
     * given the Airline objects with (maybe?) some of the
     * Flight objects inside write their info to a text file
     * Making it look good
     * @param airline
     *                  given Airline object that should be written to a text file
     */
    public void writeFile(Airline airline) throws IOException
    {
        Writer writer = null;

        try {
            if(fileName != null)
            writer = new BufferedWriter(new FileWriter(new File(fileName)));

            if(fileName != null) {
                writer.write("\n\t**********AIRLINE INFORMATION**********\n");
                writer.write("Airline name: " + airline.getName() + "\n\n");
                writer.write("\t**********FLIGHTS**********\n");
            }
            else {
                System.out.println("\n\t**********AIRLINE INFORMATION**********\n");
                System.out.println(airline.getName() + "\n\n");
                System.out.println("\t**********FLIGHTS**********\n");
            }
            ArrayList<Flight> flightList = new ArrayList<Flight>(airline.getFlights());
            Collections.sort(flightList);
            for (Flight flight : flightList)
            {
                if(fileName != null) {
                    writer.write("- flight number: "+flight.getNumber() + "\n");
                    writer.write("- flight source: "+flight.getSource() + "\n");
                    writer.write("- flight departure date and time: "+flight.getDepartureString() + "\n");
                    writer.write("- flight destination: "+flight.getDestination() + "\n");
                    writer.write("- flight arrival date and time: "+flight.getArrivalString() + "\n");
                    writer.write("- flight total duration: "+flight.Interval() + " minutes\n\n");

                }
                else {

                    System.out.println("- flight number: "+flight.getNumber() + "\n");
                    System.out.println("- flight source: "+flight.getSource() + "\n");
                    System.out.println("- flight departure date and time: "+flight.getDepartureString() + "\n");
                    System.out.println("- flight destination: "+flight.getDestination() + "\n");
                    System.out.println("- flight arrival date and time: "+flight.getArrivalString() + "\n");
                    System.out.println("- flight total duration: "+flight.Interval() + " minutes\n\n");
                }
            }

            if(fileName!= null)
            writer.close();
            //writer = null;
            System.gc();
        } catch (IOException ex) {
            if(fileName!= null)
            writer.close();
            System.gc();
            System.err.println("Error during pretty printing, please check your arguments");
        }
    }
}
