package sample;

import com.owlike.genson.Genson;
import gateways.MessageReceiver;
import gateways.MessageSender;
import shared.AgencyRequest;
import shared.Booking;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.naming.NamingException;
import java.util.HashMap;

public class AgencyBrokerAppGateway {
    MessageReceiver receiver;
    MessageSender sender;
    static HashMap<String, AgencyRequest> hashMap;

    AgencyBrokerAppGateway() throws JMSException, NamingException {
        hashMap = new HashMap<>();
        sender = new MessageSender("BrokerToAgency");
        receiver = new MessageReceiver("AgencyToBroker");
        receiver.setListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    AgencyRequest request = new Genson().deserialize(((TextMessage) message).getText(), AgencyRequest.class);
                    hashMap.get(message.getJMSCorrelationID()).setReply(request.getReply());
                    onAgencyReplyArrived(hashMap.get(message.getJMSCorrelationID()));
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void sendRequestBank(Booking booking) throws JMSException {
        AgencyRequest request;
        float distance;
        if (booking.isTransfer())
        {
            //call API
            distance = 50;
        }
        else
        {
            distance = 0;
        }
        request = new AgencyRequest(booking.getAirportFrom(), booking.getAirportTo(), booking.getNrPeople(), distance);
        request.setId(booking.getId());
        String json = new Genson().serialize(request);
        Message m = sender.createTextMessage(json);
        sender.send(m);
        hashMap.put(m.getJMSMessageID(), request);
    }

    public void onAgencyReplyArrived(AgencyRequest request) throws JMSException, NamingException {

    }
}
