package views;

import java.awt.*;
import controllers.DataController;
import models.Airport;
import models.Flight;

public class FlightView extends Panel {
    private DataController controller;
    private TextField tfFrom, tfTo, tfHours, tfMinutes, tfDuration;
    private TextArea taDisplay;

    public FlightView(DataController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        Panel form = new Panel(new GridLayout(6, 2, 5, 5));
        form.add(new Label("Od (kod):"));
        tfFrom = new TextField();
        form.add(tfFrom);

        form.add(new Label("Do (kod):"));
        tfTo = new TextField();
        form.add(tfTo);

        form.add(new Label("Sat polaska (0-23):"));
        tfHours = new TextField();
        form.add(tfHours);

        form.add(new Label("Minut polaska (0-59):"));
        tfMinutes = new TextField();
        form.add(tfMinutes);

        form.add(new Label("Trajanje (min):"));
        tfDuration = new TextField();
        form.add(tfDuration);

        Button btnAdd = new Button("Dodaj let");
        form.add(btnAdd);

        Button btnLoad = new Button("Učitaj CSV");
        form.add(btnLoad);

        add(form, BorderLayout.NORTH);

        taDisplay = new TextArea();
        taDisplay.setEditable(false);
        add(taDisplay, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            try {
                String fromCode = tfFrom.getText().trim().toUpperCase();
                String toCode = tfTo.getText().trim().toUpperCase();

                Airport src = controller.getAirports().get(fromCode);
                Airport dst = controller.getAirports().get(toCode);

                if (src == null)
                    throw new IllegalArgumentException("Aerodrom " + fromCode + " ne postoji.");
                if (dst == null)
                    throw new IllegalArgumentException("Aerodrom " + toCode + " ne postoji.");

                int h = Integer.parseInt(tfHours.getText().trim());
                int m = Integer.parseInt(tfMinutes.getText().trim());
                int dur = Integer.parseInt(tfDuration.getText().trim());

                controller.addFlight(new Flight(src, dst, h, m, dur));
                refreshDisplay();
                clearFields();
            } catch (NumberFormatException ex) {
                showError("Sati, minuti i trajanje moraju biti brojevi.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        btnLoad.addActionListener(e -> {
            FileDialog fd = new FileDialog((Frame) getParent().getParent(), "Izaberi CSV", FileDialog.LOAD);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                try {
                    String path = fd.getDirectory() + fd.getFile();
                    var flights = io.CsvHandler.getFlights(path, controller.getAirports());
                    for (Flight f : flights) {
                        controller.addFlight(f);
                    }
                    refreshDisplay();
                } catch (Exception ex) {
                    showError(ex.getMessage());
                }
            }
        });
        refreshDisplay();
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-6s %-6s %-10s %-10s%n", "OD", "DO", "POLAZAK", "TRAJANJE"));
        sb.append("─".repeat(35) + "\n");
        for (Flight f : controller.getFlights()) {
            sb.append(String.format("%-6s %-6s %02d:%02d      %-10d%n",
                    f.getSrc().getCode(), f.getDst().getCode(),
                    f.getStartHours(), f.getStartMinutes(), f.getDuration()));
        }
        taDisplay.setText(sb.toString());
    }

    private void clearFields() {
        tfFrom.setText("");
        tfTo.setText("");
        tfHours.setText("");
        tfMinutes.setText("");
        tfDuration.setText("");
    }

    private void showError(String msg) {
        Dialog d = new Dialog((Frame) getParent().getParent(), "Greška", true);
        d.setLayout(new FlowLayout());
        d.add(new Label(msg));
        Button ok = new Button("OK");
        ok.addActionListener(ev -> d.dispose());
        d.add(ok);
        d.setSize(400, 100);
        d.setVisible(true);
    }
}