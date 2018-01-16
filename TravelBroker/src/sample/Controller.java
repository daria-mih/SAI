package sample;

import javafx.scene.control.ListView;
import shared.AgencyRequest;
import shared.Booking;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class Controller {

    public ListView list;
    ClientBrokerAppGateway clientGateway;
    AgencyBrokerAppGateway agencyGateway;

    public Controller() throws JMSException, NamingException {
        clientGateway = new ClientBrokerAppGateway() {
            @Override
            public void onBookingRequestArrived(Booking request) throws JMSException {
                list.getItems().add(request);
                agencyGateway.sendRequestBank(request);
            }
        };

        agencyGateway = new AgencyBrokerAppGateway() {
            @Override
            public void onAgencyReplyArrived(AgencyRequest request) throws JMSException, NamingException {
                clientGateway.sendBookingReply(request);
                list.refresh();
            }
        };
    }
}
