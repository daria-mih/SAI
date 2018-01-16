package gateways;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class MessageSender {

    Connection connection;
    Session session;
    Destination destination;
    MessageProducer producer;

    public MessageSender(String ChannelName) throws NamingException, JMSException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,
                "tcp://localhost:61616");
        props.put(("queue." + ChannelName), ChannelName);
        Context jndiContext = new InitialContext(props);
        ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) jndiContext
                .lookup("ConnectionFactory");
        connectionFactory.setTrustAllPackages(true);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = (Destination) jndiContext.lookup(ChannelName);
        producer = session.createProducer(destination);
        connection.start();
    }

    public MessageSender() throws NamingException, JMSException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,
                "tcp://localhost:61616");
        Context jndiContext = new InitialContext(props);
        ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) jndiContext
                .lookup("ConnectionFactory");
        connectionFactory.setTrustAllPackages(true);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        producer = session.createProducer(null);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        connection.start();
    }


    public Message createTextMessage(String body) throws JMSException {

        Message m = session.createTextMessage(body);
        return m;
    }

    public void send(Message msg) throws JMSException {
        producer.send(msg);
    }

    public void send(Message msg, Destination dest) throws JMSException {
        producer.send(dest, msg);
    }
}
