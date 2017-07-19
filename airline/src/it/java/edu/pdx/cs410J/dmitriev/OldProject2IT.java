package edu.pdx.cs410J.dmitriev;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.io.*;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * An integration test that demonstrates the expected behavior of
 * creating a new airline file.
 */
public class OldProject2IT extends InvokeMainTestCase {

    final static  String pathname = "Airline.txt";
    final static File airline = new File(pathname);

    @AfterClass
    public static void deleteFile()
    {
        if (airline.exists()) {
            System.gc();
            System.out.println(airline.getAbsolutePath());
            assertTrue(airline.delete());
        }
    }

    /**
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project2.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    public void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getExitCode(), equalTo(1));
        assertThat(result.getTextWrittenToStandardError(), containsString("Missing command line arguments"));
    }

    @Test
    public void EverythingIsOk() {
        MainMethodResult result = invokeMain("-print", "CS410J Airline", "42", "PSR", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void sourceCodeHasDigit() {
        MainMethodResult result = invokeMain("-print", "CS410J Airline", "42", "P3R", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15");
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void sourceCodeHasLessThan3Characters() {
        MainMethodResult result = invokeMain("-print", "CS410J Airline", "42", "P", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15");
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void otherArgs() {
        MainMethodResult result = invokeMain("-print", "CS410J Airline", "42", "P", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15", "Blablabla");
        assertThat(result.getExitCode(), equalTo(1));
    }

    @Test
    public void README() {
        MainMethodResult result = invokeMain("-README","-textFile", pathname, "-print", "CS410J Airline", "42", "PPP", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15");
        assertThat(result.getExitCode(), equalTo(0));
        assertThat(result.getTextWrittenToStandardOut(), containsString("CS410P Project 2 - Storing An Airline in a Text File"));
    }

    @Test
    public void WriteNewFile() {
        assertThat(airline.exists(), equalTo(false));
        MainMethodResult result = invokeMain("-print", "-textFile", pathname, "CS410J Airline", "42", "PPP", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15");
        assertThat(result.getExitCode(), equalTo(0));
    }

    @Test
    public void WriteNewFileAgain() {
        assertThat(airline.exists(), equalTo(false));
        MainMethodResult result = invokeMain("-textFile", pathname, "-print", "CS410J Airline", "42", "PPP", "3/11/2017", "12:40", "BBB", "4/11/2017", "20:15");
        assertThat(result.getExitCode(), equalTo(0));
    }





}