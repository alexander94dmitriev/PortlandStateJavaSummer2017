package edu.pdx.cs410J.dmitriev;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  Flight flight = new Flight("42", "Portland", "4/11/2017", "1:40", "pm",
                             "Saint Petersburg", "4/11/2017", "2:00", "pm");




  @Test
  public void makeSureToGetSourceAndDestination() {
    assertThat(this.flight.getSource(), equalTo("Portland"));
    assertThat(this.flight.getDestination(), equalTo("Saint Petersburg"));
  }

  @Test
  public void CorrectDates() {
    //assertThat(this.flight.getDepartureString(), equalTo("4/11/17 1:40 PM"));
    //assertThat(this.flight.getArrivalString(), equalTo("4/11/17 2:00 PM"));
  }

  @Test
  public void CheckInterval() {
    assertThat(this.flight.Interval(), equalTo((long)20));
  }
  
}
