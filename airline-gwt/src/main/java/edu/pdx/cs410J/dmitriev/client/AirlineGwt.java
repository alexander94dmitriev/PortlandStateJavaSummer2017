package edu.pdx.cs410J.dmitriev.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.shared.UmbrellaException;
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
  Button showAirlineButton;

  @VisibleForTesting
  Button showUndeclaredExceptionButton;

  @VisibleForTesting
  Button showDeclaredExceptionButton;

  @VisibleForTesting
  Button showClientSideExceptionButton;

  @VisibleForTesting
  Button createAirline;

  @VisibleForTesting
  Button helpMenu;


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

  private void addWidgets(VerticalPanel panel) {
    showAirlineButton = new Button("<b>Show Airline</b>");
    showAirlineButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showAirline();
      }
    });

    createAirline = new Button("Create new Airline");
    createAirline.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) { showAirlineName(); }
    });

    helpMenu = new Button("Help");
    helpMenu.addAttachHandler(new AttachEvent.Handler() {
      @Override
      public void onAttachOrDetach(AttachEvent attachEvent) {
        alerter.alert("This should be a menu with README item");
      }
    });

    showUndeclaredExceptionButton = new Button("Show undeclared exception");
    showUndeclaredExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showUndeclaredException();
      }
    });

    showDeclaredExceptionButton = new Button("Show declared exception");
    showDeclaredExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        showDeclaredException();
      }
    });

    showClientSideExceptionButton= new Button("Show client-side exception");
    showClientSideExceptionButton.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        throwClientSideException();
      }
    });

    final TextBox airlineNameBox = new TextBox();
    airlineNameBox.setWidth("220");
    airlineNameBox.setName("airlineName");
    Label airlineTitle = new Label("Create Airline");
    panel.add(airlineTitle);
    panel.add(airlineNameBox);
    panel.add(new Button("Submit", new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        createAirline(airlineNameBox.getText());
        alerter.alert("Airline has been created: "+ airlineNameBox.getText());
        //createAirlinePanel.submit();
      }
    }));

    panel.add(createAirline);
    panel.add(showAirlineButton);
    panel.add(showUndeclaredExceptionButton);
    panel.add(showDeclaredExceptionButton);
    panel.add(showClientSideExceptionButton);

  }

  private void throwClientSideException() {
    logger.info("About to throw a client-side exception");
    throw new IllegalStateException("Expected exception on the client side");
  }

  private void showUndeclaredException() {
    logger.info("Calling throwUndeclaredException");
    airlineService.throwUndeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
  }

  private void showDeclaredException() {
    logger.info("Calling throwDeclaredException");
    airlineService.throwDeclaredException(new AsyncCallback<Void>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Void aVoid) {
        alerter.alert("This shouldn't happen");
      }
    });
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
        alertOnException(throwable);
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
        alertOnException(ex);
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

  private void createAirline(String airlineName) {
    logger.info("Calling createAirline");
    airlineService.createAirline(airlineName, new AsyncCallback<Airline>() {
      @Override
      public void onFailure(Throwable ex) {
        alertOnException(ex);
      }

      @Override
      public void onSuccess(Airline airline) {
        String airlineName = airline.getName();
        alerter.alert("New Airline name: "+ airlineName);
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
    VerticalPanel panel = new VerticalPanel();
    rootPanel.add(panel);
    //final FormPanel createAirline = new FormPanel();

    addWidgets(panel);
    //setCreateAirlinePanel(createAirline);
  }

  private void setCreateAirlinePanel(FormPanel createAirlinePanel) {
    final TextBox airlineNameBox = new TextBox();
    airlineNameBox.setWidth("220");
    airlineNameBox.setName("airlineName");
    Label airlineTitle = new Label("Create Airline");
    createAirlinePanel.add(airlineTitle);
    createAirlinePanel.add(airlineNameBox);
    createAirlinePanel.add(new Button("Submit", new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        createAirline(airlineNameBox.getText());
        //createAirlinePanel.submit();
      }
    }));
    createAirlinePanel.addSubmitHandler(new FormPanel.SubmitHandler() {
      @Override
      public void onSubmit(FormPanel.SubmitEvent submitEvent) {
        if(airlineNameBox.getText().length() == 0)
        {
             alerter.alert("Please, add the name before clicking Submit");
             submitEvent.cancel();
        }

      }
    });
    createAirlinePanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
      @Override
      public void onSubmitComplete(FormPanel.SubmitCompleteEvent submitCompleteEvent) {

        alerter.alert("New airline has been created");
      }
    });

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
