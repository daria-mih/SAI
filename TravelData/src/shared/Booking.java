package shared;

import java.io.Serializable;

public class Booking implements Serializable{

    private String airportFrom;
    private String airportTo;
    private int nrPeople;
    private String city;
    private String street;
    private int houseNr;
    private String reply;
    private String agencyName;
    private boolean transfer;
    private int id;

    public Booking(String from, String to, int people, boolean _transfer, String _city, String _street, int house)
    {
        airportFrom = from;
        airportTo = to;
        nrPeople = people;
        city = _city;
        street = _street;
        houseNr = house;
        reply = null;
        agencyName = null;
        transfer = _transfer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isTransfer() {
        return transfer;
    }

    public void setTransfer(boolean transfer) {
        this.transfer = transfer;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getReply() {
        return reply;
    }


    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public int getHouseNr() {
        return houseNr;
    }

    public int getNrPeople() {
        return nrPeople;
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public String getStreet() {
        return street;
    }

    public void setAirportFrom(String airportFrom) {
        this.airportFrom = airportFrom;
    }

    public void setAirportTo(String airportTo) {
        this.airportTo = airportTo;
    }

    public void setHouseNr(int houseNr) {
        this.houseNr = houseNr;
    }

    public void setNrPeople(int nrPeople) {
        this.nrPeople = nrPeople;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public String toString()
    {
        return "Request: from " + airportFrom + " to " + airportTo + " for "
                 + nrPeople + ((nrPeople == 1) ? " person": " people" ) +  ((isTransfer()) ? " with transfer to " + city + ", " + street + " " + houseNr :
        " without transfer") + ((reply != null) ? " | the best offer from agency " + agencyName + " :" + reply : " | Waiting...");
    }
}

