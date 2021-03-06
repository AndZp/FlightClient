package ua.com.ukrelektro.flight.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import ua.com.ukrelektro.flight.object.ExtCity;
import ua.com.ukrelektro.flight.ws.ArgumentException_Exception;
import ua.com.ukrelektro.flight.ws.City;
import ua.com.ukrelektro.flight.ws.Flight;
import ua.com.ukrelektro.flight.ws.Passenger;
import ua.com.ukrelektro.flight.ws.Place;
import ua.com.ukrelektro.flight.ws.Reservation;
import ua.com.ukrelektro.flight.ws.FlightWSService;
import ua.com.ukrelektro.flight.ws.FlightWS;

public class FlightWSClient {

    private static FlightWSClient searchClient;

    public static FlightWSClient getInstance() {
        if (searchClient == null) {
            searchClient = new FlightWSClient();
        }

        return searchClient;
    }
    private FlightWSService flightService;
    private FlightWS flightWS;

    private FlightWSClient() {
        flightService = new FlightWSService();
        flightWS = flightService.getFlightWSPort();
    }

    public ArrayList<ExtCity> getAllCities() {
        ArrayList<ExtCity> cityList = new ArrayList<>();
        
        for (City city : flightWS.getAllCities()) {
            ExtCity extCity = new ExtCity();
            extCity.setCode(city.getCode());
            extCity.setCountry(city.getCountry());
            extCity.setDesc(city.getDesc());
            extCity.setId(city.getId());
            extCity.setName(city.getName());
            cityList.add(extCity);
        }
        
        Collections.sort(cityList, new Comparator<City>() {
            @Override
            public int compare(City t, City t1) {
                return t.getName().compareTo(t1.getName());
            }
        });
        return cityList;
    }

    public ArrayList<Flight> searchFlight(long date, City cityFrom, City cityTo) throws ArgumentException_Exception {
        ArrayList<Flight> flightList = new ArrayList<>();
        flightList.addAll(flightWS.searchFlight(date, cityFrom, cityTo));
        return flightList;
    }

    public boolean buyTicket(Flight flight, Place place, Passenger passenger, String addInfo) throws ArgumentException_Exception {
        return flightWS.buyTicket(flight, place, passenger, addInfo);
    }

    public Reservation checkReservationByCode(String code) throws ArgumentException_Exception {
        return flightWS.checkReservationByCode(code);
    }

}
