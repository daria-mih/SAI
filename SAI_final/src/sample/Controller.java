package sample;

import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import shared.Booking;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.naming.NamingException;
import java.awt.print.Book;

import static java.lang.Integer.parseInt;

public class Controller {

    public TextField txt_from;
    public TextField txt_to;
    public TextField txt_city;
    public TextField txt_street;
    public TextField txt_hnumber;
    public TextField txt_people;
    public CheckBox chbox_transfer;
    public Pane pane_travel;
    public Pane pane_login;
    public TextField txt_name;
    public ListView list;
    private static String client = "";
    private int id = 0;

    ClientBrokerAppGateway appGateway;

    public void checkBoxOn()
    {
        if (chbox_transfer.isSelected())
        {
            txt_city.setDisable(false);
            txt_street.setDisable(false);
            txt_hnumber.setDisable(false);
        }
        else
        {
            txt_city.setDisable(true);
            txt_street.setDisable(true);
            txt_hnumber.setDisable(true);
        }
    }

    public void pressContinue() throws NamingException, JMSException {
        client = txt_name.getText();
        pane_login.setVisible(false);
        pane_travel.setVisible(true);
        appGateway = new ClientBrokerAppGateway(client) {
            @Override
            public void bookTravel(Booking bookingRequest) throws NamingException, JMSException {
                super.bookTravel(bookingRequest);
            }

            @Override
            public void onBookingReplyArrived() {
                list.refresh();
            }
        };
    }
    public void sendBookingRequest() throws NamingException, JMSException {
        //api key AIzaSyCbX0pXI458GdPnlD
        // sMkPoH7uHrnNOkYNw
        Message m;
        Booking booking;
        String from = txt_from.getText();
        String to = txt_to.getText();
        int people = parseInt(txt_people.getText());
        if (chbox_transfer.isSelected()) {
            String city = txt_city.getText();
            String street = txt_street.getText();
            int house = parseInt(txt_hnumber.getText());
            booking = new Booking(from, to, people, true, city, street, house);
        }
        else
        {
            booking = new Booking(from, to, people, false, "", "", 0);
        }
        booking.setId(id);
        id++;
        appGateway.bookTravel(booking);
        list.getItems().add(booking);

    }
}