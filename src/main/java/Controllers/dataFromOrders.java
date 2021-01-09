package Controllers;

import BrokerClass.BrokerReceiver;
import XSDSchema.Client;
import XSDSchema.Order;
import XSDSchema.Product;

import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/orders")
public class dataFromOrders {

    Logger log = Logger.getLogger(dataFromOrders.class.getName());
    @POST
    @Path("/product")
    public String getProductById(String orderId) {
        System.out.println(orderId);

        try {
            URL url = new URL("http://localhost:8082/Aproject-system-1.0/fromBase");

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
        } catch (IOException e) {
            e.printStackTrace();
        }

        BrokerReceiver productsReseiver = new BrokerReceiver();
        Client client = productsReseiver.takeClient();
        System.out.println(client.getClientName());

        return "returning order with id " + orderId;
    }

    /**
     * Отправляет запрос с ID в БД, получает  из брокера сообщение с информацией о заказах клиента
     * @param orderId
     * @return
     */
    @POST
    @Path("/orders")
    public String getOrdersById(String orderId) {
//        try {
//            URL url = new URL("http://localhost:8082/Aproject-system-1.0/fromBase");
//
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        int id = 0;
        try {
            id = Integer.getInteger(orderId.replaceAll("[^0-9]",""));
        } catch (IncompatibleClassChangeError e){
            log.log(Level.FINE, e.getMessage());
        }
        Order order = new Order();

        order.setId(BigInteger.valueOf(5));
        order.setQuantity(BigInteger.valueOf(2));
        try {
            order.setDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                .add("id", order.getId().toString())
                .add("quantity", order.getQuantity().toString())
                .add("date", order.getDate().getDay()+"."+order.getDate().getMonth()+"."+order.getDate().getYear());
        JsonObject jsonObject = objectBuilder.build();


        return "{ \"data\": [ " + jsonObject.toString() + "]}";
    }
}
