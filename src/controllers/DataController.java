package controllers;

import models.Airport;
import models.Flight;
import io.CsvHandler;
import java.util.ArrayList;
import java.util.HashMap;

public class DataController {
    private HashMap<String, Airport> airports = new HashMap<>();
    private ArrayList<Flight> flights=new ArrayList<>();

    public void loadFromCsv(String airportsPath, String flightsPath) throws Exception {
        var loadedAirports = CsvHandler.getAirports(airportsPath);
        for (Airport a : loadedAirports) {
            addAirport(a);
        }
        var loadedFlights = CsvHandler.getFlights(flightsPath, airports);
        for (Flight f : loadedFlights) {
            addFlight(f);
        }
    }

    public void addAirport(Airport airport){
        if(airports.containsKey(airport.getCode())){
            throw new IllegalArgumentException("Kod aerodram se poklapa sa postojecim!");
        }
        airports.put(airport.getCode(),airport);
    }
    public void addFlight(Flight flight){
        flights.add(flight);
    }
    public Airport find(String code){
        return airports.get(code);
    }

    public HashMap<String, Airport> getAirports() {
        return airports;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }
}
