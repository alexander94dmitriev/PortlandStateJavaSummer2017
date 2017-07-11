package edu.pdx.cs410J.dmitriev;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Unit tests for the {@link Flight} class.
 */
public class FlightTest {

  Flight flight = new Flight("42", "Portland", "3/11/2017", "12:40",
                             "Saint Petersburg", "4/11/2017", "20:15");

  @Test
  public void getArrivalStringNeedsToBeImplemented() {
    assertThat(this.flight.getArrivalString(), equalTo("4/11/2017 20:15"));
  }

  @Test
  public void initiallyAllFlightsHaveTheSameNumber() {
    assertThat(this.flight.getNumber(), equalTo(42));
  }

  @Test
  public void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
    assertThat(this.flight.getDeparture(), is(nullValue()));
  }

  @Test
  public void getDepartureAndArrival()
  {
    assertThat(this.flight.getDepartureString(), equalTo("3/11/2017 12:40"));
    assertThat(this.flight.getArrivalString(), equalTo("4/11/2017 20:15"));
  }

  @Test
  public void makeSureToGetSourceAndDestination() {
    assertThat(this.flight.getSource(), equalTo("Portland"));
    assertThat(this.flight.getDestination(), equalTo("Saint Petersburg"));
  }

  
}
