package Bproject.Bprojectsystem.brokerClass;

import Bproject.Bprojectsystem.Client;
import Bproject.Bprojectsystem.jaxbComponent.JaxbConverterImpl;
import Bproject.Bprojectsystem.jaxbComponent.Order;
import Bproject.Bprojectsystem.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;

@Component
public class BrokerReceiver{ //Reseiver это приёмщик!!! Почти как Ресивер!

    private static String subject = "BProject";    // Имя, кому принимать данные

    @Autowired
    JmsTemplate jmsTemplate;
    JaxbConverterImpl jaxbConverter;

    public BrokerReceiver(JaxbConverterImpl jaxbConverter, JmsTemplate jmsTemplate) {
        this.jaxbConverter = jaxbConverter;
        this.jmsTemplate = jmsTemplate;
    }

    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */
    @JmsListener(destination = "AProjectProduct")
    @SendTo("BProjectProduct")
    public Product takeProduct(final Message productMessage) throws JMSException {
        Product product = new Product();
            if (productMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) productMessage;  //Приведение к типу textMessage
                product = jaxbConverter.xmlToProduct(textMessage.getText());
            }
        return product;
    }
    /**
     * Класс который будет получать класс клиента из брокера
     * @return
     */
    @JmsListener(destination = "AProjectClient")
    @SendTo("BProjectClient")
    public Client takeClient(final Message clientMessage) throws JMSException {
        Client client = new Client();
            if (clientMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) clientMessage;  //Приведение к типу textMessage
                client = jaxbConverter.xmlToClient(textMessage.getText());
            }
        return client;
    }
    /**
     * Класс который будет получать класс продукта из брокера
     * @return
     */
    @JmsListener(destination = "AProjectOrder")
    @SendTo("BProjectOrder")
    public Order takeOrder(final Message orderMessage) throws JMSException {
        Order order = new Order();
            if (orderMessage instanceof TextMessage) {
                TextMessage textMessage  = (TextMessage) orderMessage;  //Приведение к типу textMessage
                order = jaxbConverter.xmlToOrder(textMessage.getText());
            }
        return order;
    }

    public Client receiveClient() {
        Client client = new Client();
        Message message = jmsTemplate.receive("BProjectClient");
        TextMessage textMessage = (TextMessage) message;
        try {
            client = jaxbConverter.xmlToClient(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return client;
    }
    public Order receiveOrder () {
        Order order = new Order();
        Message message = jmsTemplate.receive("BProjectOrder");
        TextMessage textMessage = (TextMessage) message;
        try {
            order = jaxbConverter.xmlToOrder(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return order;
    }

    public Product receiveProduct () {
        Product product = new Product();
        Message message = jmsTemplate.receive("BProjectProduct");
        TextMessage textMessage = (TextMessage) message;
        try {
            product = jaxbConverter.xmlToProduct(textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return product;
    }
}
