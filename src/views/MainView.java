package views;

import java.awt.*;
import controllers.DataController;

public class MainView extends Frame{
    private CardLayout cardLayout;
    private Panel mainView;
    private AirportView airportView;
    private FlightView flightView;
    private DataController controller;

    public MainView(DataController controller) {
        this.controller = controller;
        setTitle("Air Traffic Simulator");
        setSize(360, 180);

        Panel nav = new Panel(new FlowLayout());
        Button btnAirports = new Button("Aerodromi");
        Button btnFlights = new Button("Letovi");
        nav.add(btnAirports);
        nav.add(btnFlights);

        cardLayout = new CardLayout();
        mainView = new Panel(cardLayout);

        airportView= new AirportView(controller);
        flightView = new FlightView(controller);

        mainView.add(airportView, "airports");
        mainView.add(flightView, "flights");

        btnAirports.addActionListener(e -> cardLayout.show(mainView, "airports"));
        btnFlights.addActionListener(e -> cardLayout.show(mainView, "flights"));

        add(nav, BorderLayout.NORTH);
        add(mainView, BorderLayout.CENTER);

        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent e) {
                dispose();
                System.exit(0);
            }
        });

        setVisible(true);
    }

}
