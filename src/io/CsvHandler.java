package io;

import models.Airport;
import models.Flight;
import java.nio.file.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CsvHandler {
    public static ArrayList<Airport> getAirports(String path) throws Exception {
        ArrayList<Airport> airports = new ArrayList<>();
        Path p = Paths.get(path);
        if (Files.notExists(p)) {
            throw new Exception("Fajl " + path + " ne postoji ili je uneta losa putanja!");
        }
        try (BufferedReader reader=Files.newBufferedReader(p)) {
            //Za unos prve linije CODE,..
            String line = reader.readLine();
            int n = 1;
            while ((line = reader.readLine()) != null) {
                n++;
                String[] parts = line.split(",");

                String code = parts[0].trim();
                String name = parts[1].trim();
                float x = Float.parseFloat(parts[2].trim());
                float y = Float.parseFloat(parts[3].trim());
                airports.add(new Airport(code, name, x, y));
            }
        }
        return airports;
    }
    public static ArrayList<Flight> getFlights(String path, HashMap<String, Airport> airports) throws Exception {
        ArrayList<Flight> flights = new ArrayList<>();
        Path p = Paths.get(path);
        if (Files.notExists(p))
            throw new Exception("Fajl " + path + " ne postoji ili je uneta losa putanja!");

        try (BufferedReader reader=Files.newBufferedReader(p)) {
            String line=reader.readLine();

            int n=1;
            while ((line = reader.readLine()) != null) {
                n++;
                String[] parts = line.split(",");

                String srcCode = parts[0].trim();
                String dstCode = parts[1].trim();

                Airport src=airports.get(srcCode);
                Airport dst=airports.get(dstCode);

                if (src==null)
                    throw new Exception("Kod za odlazni aerodom: " + srcCode + " ne postoji!");
                if (dst == null)
                    throw new Exception("Kod za dolazni aerodom: " + dstCode + " ne postoji!");

                String[] timeParts = parts[2].trim().split(":");

                int hours = Integer.parseInt(timeParts[0]);
                int minutes = Integer.parseInt(timeParts[1]);
                int duration = Integer.parseInt(parts[3].trim());
                flights.add(new Flight(src, dst, hours, minutes, duration));
            }
        }
        return flights;
    }
    public static void saveAirports(String path, HashMap<String, Airport> airports) throws Exception {
        Path p=Paths.get(path);
        try (BufferedWriter writer=Files.newBufferedWriter(p)) {
            writer.write("CODE,NAME,X,Y");
            writer.newLine();
            for (Airport a : airports.values()) {
                writer.write(a.getCode() + "," + a.getName() + "," + a.getX() + "," + a.getY());
                writer.newLine();
            }
        }
    }
    public static void saveFlights(String path, ArrayList<Flight> flights) throws Exception {
        Path p = Paths.get(path);
        try (BufferedWriter writer = Files.newBufferedWriter(p)) {
            writer.write("FROM,TO,DEPARTURE,DURATION");
            writer.newLine();
            for (Flight f : flights) {
                writer.write(String.format("%s,%s,%02d:%02d,%d", f.getSrc().getCode(), f.getDst().getCode(), f.getStartHours(), f.getStartMinutes(), f.getDuration()));
                writer.newLine();
            }
        }
    }

}
