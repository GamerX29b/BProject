package BrokerClass;


import Entitys.Client;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/*
Клиент для отправки сообщений на AProject
 */
public class Senders {


    // Адрес JMS сервера, пока можно оставить дефолтный ибо пофиг tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static String subjectClient = "AProjectClient"; // Имя очереди, кому надо слать
    private static String subjectProduct = "AProjectProduct";
    private static String subjectOrder = "AProjectOrder";

    public boolean ClientSender(Client client) {

        try {
            Connection connection = getConnectionFactory().createConnection();
            connection.start();

            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subjectClient);

            MessageProducer producer = session.createProducer(destination); // Создаётся некий "режиссёр" сообщения

            TextMessage message = session.createTextMessage("Текстовая клиент"); // Тут то, что мы шлём

            session.createObjectMessage();
            // Here we are sending our message!
            producer.send(message);

            connection.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ProductSender(Product product) {
        try {
            Connection connection = getConnectionFactory().createConnection();
            connection.start();

            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subjectProduct);

            MessageProducer producer = session.createProducer(destination); // Создаётся некий "режиссёр" сообщения

            TextMessage message = session.createTextMessage("Текстовая продукт"); // Тут то, что мы шлём

            session.createObjectMessage();
            producer.send(message);

            connection.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean ClientSender(ClientOrder clientOrder) {
        try {
            Connection connection = getConnectionFactory().createConnection();
            connection.start();

            Session session = connection.createSession(false,
                    Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(subjectOrder);

            MessageProducer producer = session.createProducer(destination); // Создаётся некий "режиссёр" сообщения

            TextMessage message = session.createTextMessage("Текстовая заказ"); // Тут то, что мы шлём

            session.createObjectMessage();
            producer.send(message);

            connection.close();
            return true;
        } catch (JMSException e) {
            e.printStackTrace();
            return false;
        }
    }

    private ActiveMQConnectionFactory getConnectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        return connectionFactory;
    }
}
