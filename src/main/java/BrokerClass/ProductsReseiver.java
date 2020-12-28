package BrokerClass;

import javax.jms.*;

import Entitys.Client;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ProductsReseiver { //Reseiver это приёмщик!!! Почти как Ресивер!

    // Адрес JMS сервера, пока можно оставить дефолтный ибо пофиг tcp://localhost:61616
    private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static String subject = "BProject";    // Имя, кому принимать данные ClientRes

    /*
    Получаем сообщение из брокера
     */
    public void takeProduct() {

        ActiveMQConnectionFactory  connectionFactory = new ActiveMQConnectionFactory(url); // Создание соединения почти как в JDBC
        Connection connection;

        try {
            connection = connectionFactory.createConnection();

        connection.start();
        //Создание сессии кому отправить сообщение
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);

        Destination destination = session.createQueue(subject); // отправляем сообщение в 'ClientRes'

        MessageConsumer consumer = session.createConsumer(destination); // MessageConsumer для получения сообщений

        Message message = consumer.receive();         // Тут получаем сообщение

        if (message instanceof TextMessage) {
            TextMessage textMessage  = (TextMessage) message;  //Приведение к типу textMessage

            System.out.println("Received message " + textMessage.getText()); //извлечение текста
        }
        connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
