package sample;

import com.owlike.genson.Genson;
import gateways.MessageReceiver;
import gateways.MessageSender;
import shared.AgencyRequest;
import shared.Booking;
import javax.jms.*;

import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.util.HashMap;

public abstract class ClientBrokerAppGateway {

    MessageReceiver receiver;
    MessageSender sender;
    HashMap<Integer, Message> hashMap;


    ClientBrokerAppGateway() throws JMSException, NamingException {
        hashMap = new HashMap<>();

        receiver = new MessageReceiver("ClientToBroker");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    Booking booking = new Genson().deserialize(((TextMessage) message).getText(), Booking.class);
                    hashMap.put(booking.getId(), message);
                    onBookingRequestArrived(booking);
                } catch (JMSException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    public void sendBookingReply(AgencyRequest bookingreply) throws JMSException, NamingException {
        sender = new MessageSender();
        Booking booking = new Booking(bookingreply.getAirportFrom(), bookingreply.getAirportTo(), bookingreply.getNrPeople(), false, "", "", 0);
        booking.setId(bookingreply.getId());
        String json = new Genson().serialize(booking);
        Message msg = sender.createTextMessage(json);
        msg.setJMSCorrelationID(hashMap.get(booking.getId()).getJMSMessageID());
        sender.send(msg, hashMap.get(booking).getJMSReplyTo());
    }

    public abstract void onBookingRequestArrived(Booking request) throws JMSException;

}

