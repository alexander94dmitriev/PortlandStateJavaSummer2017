package edu.pdx.cs410J.dmitriev.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A basic GWT class that makes sure that we can send an airline back from the server
 */
public class AirlineGwt implements EntryPoint {

  private final Alerter alerter;
  private final AirlineServiceAsync airlineService;
  private final Logger logger;

  @VisibleForTesting
  Button submit;

  @VisibleForTesting
  Button showAirlineButton;

  @VisibleForTesting
  Button searchFlight;

  @VisibleForTesting
  Button prettyPrint;

  @VisibleForTesting
  Button showAirlineName;

  @VisibleForTesting
  Button createFlightDialogBox;

  @VisibleForTesting
  DialogBox createFlightBox;

  @VisibleForTesting
  MenuBar helpBar;


  public AirlineGwt() {
    this(new Alerter() {
      @Override
      public void alert(String message) {
        Window.alert(message);
      }
    });
  }

  @VisibleForTesting
  AirlineGwt(Alerter alerter) {
    this.alerter = alerter;
    this.airlineService = GWT.create(AirlineService.class);
    this.logger = Logger.getLogger("airline");
    Logger.getLogger("").setLevel(Level.INFO);  // Quiet down the default logging
  }

  private void alertOnException(Throwable throwable) {
    Throwable unwrapped = unwrapUmbrellaException(throwable);
    StringBuilder sb = new StringBuilder();
    sb.append(unwrapped.toString());
    sb.append('\n');

    for (StackTraceElement element : unwrapped.getStackTrace()) {
      sb.append("  at ");
      sb.append(element.toString());
      sb.append('\n');
    }

    this.alerter.alert(sb.toString());
  }

  private Throwable unwrapUmbrellaException(Throwable throwable) {
    if (throwable instanceof UmbrellaException) {
      UmbrellaException umbrella = (UmbrellaException) throwable;
      if (umbrella.getCauses().size() == 1) {
        return unwrapUmbrellaException(umbrella.getCauses().iterator().next());
      }

    }

    return throwable;
  }

  private void addCreatorWidgets(VerticalPanel panel) {

    showAirlineName = new Button("Show Airline name");
    showAirlineName.setWidth("200px");
    showAirlineName.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) { showAirlineName(); }
    });
    final TextBox airlineNameBox = new TextBox();
    airlineNameBox.setWidth("200px");
    airlineNameBox.setName("airlineName");
    Label airlineTitle = new Label("Create Airline");

    submit = new Button("Submit");
    submit.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        createAirline(airlineNameBox.getText());
        alerter.alert("Airline has been created: "+ airlineNameBox.getText());
        //createAirlinePanel.submit();
      }
    });

    createFlightDialogBox = new Button("Add a new Flight to an Airline");
    createFlightDialogBox.setWidth("200px");
    createFlightDialogBox.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        createFlightBox = showCreateFlightDialogBox();
      }
    });

    panel.add(airlineTitle);
    panel.add(airlineNameBox);
    panel.add(submit);
    panel.add(new InlineHTML("<br/>"));
    panel.add(showAirlineName);
    panel.add(createFlightDialogBox);

    panel.setCellHorizontalAlignment(showAirlineName, HasHorizontalAlignment.ALIGN_CENTER);
    panel.setCellHorizontalAlignment(createFlightDialogBox, HasHorizontalAlignment.ALIGN_CENTER);

  }

  private void addDisplayWidgets(VerticalPanel panel)
  {
    showAirlineButton = new Button("<b>Show Airline</b>");
    showAirlineButton.setWidth("200px");
    showAirlineButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showAirline();
      }
    });
    prettyPrint = new Button("<b>Pretty Print Airline</b>");
    prettyPrint.setWidth("200px");
    prettyPrint.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        prettyPrintAirline();
      }
    });
    searchFlight = new Button("<b>Search the flight</b>");
    searchFlight.setWidth("200px");
    searchFlight.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        alerter.alert("Add search functionality");
      }
    });

    panel.add(showAirlineButton);
    panel.add(prettyPrint);
    panel.add(searchFlight);
  }

  private void addHelpWidgets(VerticalPanel panel)
  {
    Label label = new Label("Help Menu");
    helpBar = showHelpMenu();
    panel.add(label);
    panel.add(helpBar);
    label.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
    panel.setCellHorizontalAlignment(helpBar, HasHorizontalAlignment.ALIGN_CENTER);
  }

  private MenuBar showHelpMenu() {
    MenuBar HelpMenu = new MenuBar();
    HelpMenu.setAutoOpen(true);
    HelpMenu.setWidth("100px");
    HelpMenu.setAnimationEnabled(true);
    HelpMenu.setLayoutData("Help Menu");

    HelpMenu.addItem("README", new Command() {
      @Override
      public void execute() {
       alerter.alert("This program is GWT web application that allows\n" +
               "to create an airline and assign new flights to it.\n" +
               "First, don't forget to create an airline before adding flight.\n" +
               "Then, click 'Add a new flight to an Airline' and add all information.\n" +
               "Make sure to follow the requirements. Also, you can display the info\n" +
               "about an airline and its flights, pretty print it and search for the flights\n" +
               "with specific source and destination.\n" +
               "Good luck!\n");
      }
    });

    return HelpMenu;
  }


  private void getAirlineName() {
    logger.info("Calling getAirlineName");
    airlineService.getAirline(new AsyncCallback<Airline>() {
      @Override
      public void onFailure(Throwable throwable) {
        alerter.alert("Unable to get Airline Name");
      }

      @Override
      public void onSuccess(Airline airline) {
      }
    });
  }

  private void showAirlineName() {
    logger.info("calling show");
    airlineService.getAirlineName(new AsyncCallback<String>() {
      @Override
      public void onFailure(Throwable throwable) {
        alerter.alert(throwable.getMessage());
      }

      @Override
      public void onSuccess(String airlineName) {
        alerter.alert(airlineName);
      }
    });
  }

  private void showAirline() {
    logger.info("Calling getAirline");
    airlineService.getAirline(new AsyncCallback<Airline>() {

      @Override
      public void onFailure(Throwable ex) {
        alerter.alert("Error: " + ex.getMessage());
      }

      @Override
      public void onSuccess(Airline airline) {
        StringBuilder sb = new StringBuilder(airline.toString());
        Collection<Flight> flights = airline.getFlights();
        for (Flight flight : flights) {
          sb.append(flight);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }

  private void prettyPrintAirline() {
    logger.info("Calling getAirline");
    airlineService.getAirline(new AsyncCallback<Airline>() {

      @Override
      public void onFailure(Throwable ex) {
        alerter.alert("Error: " + ex.getMessage());
      }

      @Override
      public void onSuccess(Airline airline) {
        StringBuilder sb = new StringBuilder("\t*****************AIRLINE*****************\n" + "Airline: " + airline.getName());
        Collection<Flight> flights = airline.getFlights();
        for (Flight flight : flights) {
          sb.append(flight);
          sb.append("\n");
        }
        alerter.alert(sb.toString());
      }
    });
  }

  private void createAirline(String airlineName) {
    logger.info("Calling showAirlineName");
    airlineService.createAirline(airlineName, new AsyncCallback<Airline>() {
      @Override
      public void onFailure(Throwable ex) {
        alerter.alert("Error: " + ex.getMessage());
      }

      @Override
      public void onSuccess(Airline airline) {
        String airlineName = airline.getName();
        alerter.alert("New Airline name: "+ airlineName);
      }
    });
  }

  private DialogBox showCreateFlightDialogBox() {
    logger.info("Creating Dialog Box");
    logger.info("calling show");
    final DialogBox dialogBox = new DialogBox(false, true);
    airlineService.getAirlineName(new AsyncCallback<String>() {
      @Override
      public void onFailure(Throwable throwable) {
        alerter.alert(throwable.getMessage());
      }

      @Override
      public void onSuccess(String airlineName) {

        dialogBox.setAnimationType(PopupPanel.AnimationType.CENTER);
        dialogBox.setPopupPosition(230,40);

        dialogBox.setText("Create a new Flight");
        VerticalPanel panel = new VerticalPanel();
        final TextBox flightNumber = new TextBox();
        flightNumber.setName("flightNumber");
        flightNumber.getElement().setPropertyString("placeholder","Flight Number");
        panel.add(flightNumber);
        final TextBox src = new TextBox();
        src.setName("src");
        src.getElement().setPropertyString("placeholder","Source Code");
        panel.add(src);
        final TextBox departDate = new TextBox();
        departDate.setName("departDate");
        departDate.getElement().setPropertyString("placeholder","Departure Date");
        panel.add(departDate);
        final TextBox departTime = new TextBox();
        departTime.setName("departDate");
        departTime.getElement().setPropertyString("placeholder","Departure Time");
        panel.add(departTime);
        final TextBox departAmPm = new TextBox();
        departAmPm.setName("departAmPm");
        departAmPm.getElement().setPropertyString("placeholder","am/pm");
        panel.add(departAmPm);
        final TextBox dest = new TextBox();
        dest.setName("dest");
        dest.getElement().setPropertyString("placeholder","Destination Code");
        panel.add(dest);
        final TextBox arrivalDate = new TextBox();
        arrivalDate.setName("arrivalDate");
        arrivalDate.getElement().setPropertyString("placeholder","Arrival Date");
        panel.add(arrivalDate);
        final TextBox arrivalTime = new TextBox();
        arrivalTime.setName("arrivalTime");
        arrivalTime.getElement().setPropertyString("placeholder","Arrival Time");
        panel.add(arrivalTime);
        final TextBox arrivalAmPm = new TextBox();
        arrivalAmPm.setName("arrivalAmPm");
        arrivalAmPm.getElement().setPropertyString("placeholder","am/pm");
        panel.add(arrivalAmPm);

        panel.add(new Button("Submit", new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
            checkArgsAndAddFlight(flightNumber.getText(), src.getText(), departDate.getText(), departTime.getText(), departAmPm.getText(),
                    dest.getText(), arrivalDate.getText(), arrivalTime.getText(), arrivalAmPm.getText());

            //alerter.alert("Everything is ok");
            dialogBox.hide();
          }
        }));
        panel.add(new Button("Cancel", new ClickHandler() {
          @Override
          public void onClick(ClickEvent clickEvent) {
            dialogBox.hide();
          }
        }));
        dialogBox.setWidget(panel);
        dialogBox.show();
      }
    });

    return dialogBox;
  }

  private void checkArgsAndAddFlight(String flightNumber, String src, String departDate, String departTime, String departAmPm, String dest, String arrivalDate, String arrivalTime, String arrivalAmPm) {
    logger.info("Calling showAirlineName");
    final String newFlightNumber = flightNumber;
    final String newsrc = src;
    final String newdepartDate = departDate;
    final String newdepartTime = departTime;
    final String newdepartAmPm = departAmPm;
    final String newdest = dest;
    final String newarrivalDate = arrivalDate;
    final String newarrivalTime = arrivalTime;
    final String newarrivalAmPm = arrivalAmPm;

    airlineService.checkArguments(flightNumber, src, departDate, departTime, departAmPm, dest, arrivalDate, arrivalTime, arrivalAmPm, new AsyncCallback<String>() {
      @Override
      public void onFailure(Throwable ex) {
        alerter.alert("Error: " + ex.getMessage());
      }

      @Override
      public void onSuccess(String result) {
        if(result != null) {
          alerter.alert(result);
        }
        airlineService.checkAirlineExistence(new AsyncCallback<Void>() {
          @Override
          public void onFailure(Throwable ex) {
            alerter.alert("Error: " + ex.getMessage());
          }

          @Override
          public void onSuccess(Void aVoid) {
            airlineService.addFlight(newFlightNumber, newsrc, newdepartDate, newdepartTime, newdepartAmPm,
                    newdest, newarrivalDate, newarrivalTime, newarrivalAmPm, new AsyncCallback<Void>() {
                      @Override
                      public void onFailure(Throwable ex) {
                        alerter.alert("Error: " + ex.getMessage());
                      }

                      @Override
                      public void onSuccess(Void aVoid) {
                        alerter.alert("The airline has been added");
                      }
                    });
          }
        });
      }
    });
  }

  @Override
  public void onModuleLoad() {
    setUpUncaughtExceptionHandler();

    // The UncaughtExceptionHandler won't catch exceptions during module load
    // So, you have to set up the UI after module load...
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        setupUI();
      }
    });

  }

  private void setupUI() {
    RootPanel rootPanel = RootPanel.get();
    VerticalPanel creator = new VerticalPanel();
    VerticalPanel printer = new VerticalPanel();
    VerticalPanel help = new VerticalPanel();
    TabPanel Panel = new TabPanel();

    addCreatorWidgets(creator);
    addDisplayWidgets(printer);
    addHelpWidgets(help);

    Panel.add(creator,"Creator");
    Panel.add(printer, "Printer");
    Panel.add(help, "Help");
    Panel.setWidth("200px");
    rootPanel.add(Panel);
  }

  private void setUpUncaughtExceptionHandler() {
    GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
      @Override
      public void onUncaughtException(Throwable throwable) {
        alertOnException(throwable);
      }
    });
  }

  @VisibleForTesting
  interface Alerter {
    void alert(String message);
  }
}
