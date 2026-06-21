package views;

import java.awt.*;
import controllers.DataController;
import models.Airport;

public class AirportView extends Panel {
    private DataController controller;
    private TextField tfCode, tfName, tfX, tfY;
    private TextArea taDisplay;

    public AirportView(DataController controller) {
        this.controller = controller;
        setLayout(new BorderLayout());

        Panel form = new Panel(new GridLayout(5, 2, 5, 5));
        form.add(new Label("Kod (3 slova):"));
        tfCode = new TextField();
        form.add(tfCode);

        form.add(new Label("Naziv:"));
        tfName = new TextField();
        form.add(tfName);

        form.add(new Label("X (-180 do 180):"));
        tfX = new TextField();
        form.add(tfX);

        form.add(new Label("Y (-90 do 90):"));
        tfY = new TextField();
        form.add(tfY);

        Button btnAdd = new Button("Dodaj aerodrom");
        form.add(btnAdd);

        Button btnLoad = new Button("Učitaj CSV");
        form.add(btnLoad);

        add(form, BorderLayout.NORTH);

        taDisplay = new TextArea();
        taDisplay.setEditable(false);
        add(taDisplay, BorderLayout.CENTER);

        btnAdd.addActionListener(e -> {
            try {
                String code = tfCode.getText().trim();
                String name = tfName.getText().trim();
                float x = Float.parseFloat(tfX.getText().trim());
                float y = Float.parseFloat(tfY.getText().trim());

                controller.addAirport(new Airport(code, name, x, y));
                refreshDisplay();
                clearFields();
            } catch (NumberFormatException ex) {
                showError("Koordinate moraju biti brojevi.");
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
                    var airports = io.CsvHandler.getAirports(path);
                    for (Airport a : airports) {
                        controller.addAirport(a);
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
        sb.append(String.format("%-6s %-30s %-8s %-8s%n", "KOD", "NAZIV", "X", "Y"));
        sb.append("─".repeat(55) + "\n");
        for (Airport a : controller.getAirports().values()) {
            sb.append(String.format("%-6s %-30s %-8.1f %-8.1f%n",
                    a.getCode(), a.getName(), a.getX(), a.getY()));
        }
        taDisplay.setText(sb.toString());
    }

    private void clearFields() {
        tfCode.setText("");
        tfName.setText("");
        tfX.setText("");
        tfY.setText("");
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