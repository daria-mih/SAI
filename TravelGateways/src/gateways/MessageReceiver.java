package gateways;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class MessageReceiver {
    Connection connection;
    Session session;
    Destination destination;
    MessageConsumer consumer;

    public MessageReceiver(String channelName) throws NamingException, JMSException {
        Properties props = new Properties();
        props.setProperty(Context.INITIAL_CONTEXT_FACTORY,
                "org.apache.activemq.jndi.ActiveMQInitialContextFactory");
        props.setProperty(Context.PROVIDER_URL,
                "tcp://localhost:61616");
        props.put(("queue." + channelName), channelName);
        Context jndiContext = new InitialContext(props);
        ActiveMQConnectionFactory connectionFactory = (ActiveMQConnectionFactory) jndiContext
                .lookup("ConnectionFactory");
        connectionFactory.setTrustAllPackages(true);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = (Destination) jndiContext.lookup(channelName);
        consumer = session.createConsumer(destination);
        connection.start();
    }

    public void setListener(MessageListener ml) throws JMSException {
        consumer.setMessageListener(ml);
    }

    public Destination getDestination() {
        return destination;
    }
}
