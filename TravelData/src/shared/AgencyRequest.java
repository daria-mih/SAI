package shared;

import java.io.Serializable;

public class AgencyRequest implements Serializable{

    private String airportFrom;
    private String airportTo;
    private int nrPeople;
    private String reply;
    private String name;
    private float transferDistance;
    private int id;

    public AgencyRequest(String from, String to, int people, float distance)
    {
        airportFrom = from;
        airportTo = to;
        nrPeople = people;
        transferDistance = distance;
        name = null;
        reply = null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public void setNrPeople(int nrPeople) {
        this.nrPeople = nrPeople;
    }

    public void setAirportTo(String airportTo) {
        this.airportTo = airportTo;
    }

    public void setAirportFrom(String airportFrom) {
        this.airportFrom = airportFrom;
    }

    public String getAirportTo() {
        return airportTo;
    }

    public String getAirportFrom() {
        return airportFrom;
    }

    public int getNrPeople() {
        return nrPeople;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getTransferDistance() {
        return transferDistance;
    }

    public void setTransferDistance(float transferDistance) {
        this.transferDistance = transferDistance;
    }

    @Override
    public String toString()
    {
        return "Request: from " + airportFrom + " to " + airportTo
                + nrPeople + ((nrPeople == 1) ? " person": " people" ) +  ((transferDistance > 0) ? " | with transfer distance " + transferDistance + " km" :
                " | without transfer") + ((reply != null) ? " | offer: "  + reply : "Waiting...");
    }
}
