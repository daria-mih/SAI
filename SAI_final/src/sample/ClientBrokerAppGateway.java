package sample;

import com.owlike.genson.Genson;
import gateways.MessageReceiver;
import gateways.MessageSender;
import shared.Booking;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.util.HashMap;

public abstract class ClientBrokerAppGateway {

    MessageSender sender;
    HashMap<String, Booking> hashmap;
    MessageReceiver receiver;

    ClientBrokerAppGateway(String Channel) throws JMSException, NamingException {
        hashmap = new HashMap<>();
        //sender = new MessageSenderGateway("ClientToBroker");
        receiver = new MessageReceiver("BrokerToClient" + Channel);
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    Booking bookingReply = new Genson().deserialize(((TextMessage) message).getText(), Booking.class);
                    String msgID = message.getJMSCorrelationID();
                    if (hashmap.get(msgID) != null) {
                        hashmap.get(msgID).setReply(bookingReply.getReply());
                    } else {
                        hashmap.put(msgID, bookingReply);
                    }
                    onBookingReplyArrived();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void bookTravel (Booking bookingRequest) throws JMSException, NamingException {
        sender = new MessageSender("ClientToBroker");
        String json = new Genson().serialize(bookingRequest);
        Message msg = sender.createTextMessage(json);
        msg.setJMSReplyTo(receiver.getDestination());
        sender.send(msg);
        hashmap.put(msg.getJMSMessageID(), bookingRequest);
    }

    public abstract void onBookingReplyArrived();

}
