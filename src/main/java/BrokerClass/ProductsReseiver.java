package BrokerClass;

import javax.jms.*;

import Entitys.Client;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.ArrayList;
import java.util.Arrays;

public class ProductsReseiver {

    // URL of the JMS server
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    // default broker URL is : tcp://localhost:61616"

    // Name of the queue we will receive messages from
    private static String subject = "JCG_QUEUE";

    public void takeProduct() {

        // Getting JMS connection from the server
        ActiveMQConnectionFactory  connectionFactory = new ActiveMQConnectionFactory(url);
        connectionFactory.setTrustAllPackages(true);
        Connection connection;

        try {
            connection = connectionFactory.createConnection();

        connection.start();


        // Creating session for seding messages
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        // Getting the queue 'JCG_QUEUE'
        Destination destination = session.createQueue(subject);

        // MessageConsumer is used for receiving (consuming) messages
        MessageConsumer consumer = session.createConsumer(destination);

        // Here we receive the message.
        Message message = consumer.receive();

        // We will be using TestMessage in our example. MessageProducer sent us a TextMessage
        // so we must cast to it to get access to its .getText() method.
        if (message instanceof ObjectMessage) {
            System.out.println(message);
            Client client = (Client) message;
            System.out.println("Received message '" + client.getClientName() + " " + client.getClientAdres() + "'");
        }
        connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
