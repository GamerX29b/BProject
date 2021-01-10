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
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.IOException;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Path("/orders")
public class dataFromOrders {

    Logger log = Logger.getLogger(dataFromOrders.class.getName());

    /**
     * Получить продукты по айди ордера
     * @param orderId
     * @return
     */
    @POST
    @Path("/product")
    public String getProductById(String productId) {
        System.out.println(productId);


//        BrokerReceiver brokerReceiver = new BrokerReceiver();
//        int id = 0;
//        try {
//            id = Integer.valueOf(orderId.replaceAll("[^0-9]",""));
//        } catch (IncompatibleClassChangeError e){
//            log.log(Level.SEVERE, e.getMessage());
//        }
//        try {
//            URL url = new URL("http://localhost:8082/Aproject-system-1.0/fromBase");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//        } catch (IOException e) {
//            log.log(Level.SEVERE, e.getMessage());
//        }
//
//        Product product = brokerReceiver.takeClient();
//        List<Product> listProduct = order.getProduct();
//        //TODO дописать когда будет готов модуль получения массива из брокера
        //test
        Product product = new Product();
        product.setId(BigInteger.valueOf(1));
        product.setQuantity(BigInteger.valueOf(4));
        product.setNameProduct("Сосопыл");



        List<Product> listProduct = new ArrayList();
        listProduct.add(product);
        //test

        StringBuilder readyJson = new StringBuilder();
        for (int i = 0 ; i < listProduct.size() ; i++){
            Product oneProduct = listProduct.get(i);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                    .add("id", oneProduct.getId().toString())
                    .add("quantity", oneProduct.getQuantity().toString())
                    .add("name", oneProduct.getNameProduct());
            JsonObject jsonObject = objectBuilder.build();
            try (Writer writer = new StringWriter()){
                Json.createWriter(writer).write(jsonObject);
                readyJson.append(writer.toString());
                if (i < listProduct.size()-1) readyJson.append(",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "{ \"data\": [ " + readyJson.toString() + "]}";
    }

    /**
     * Отправляет запрос с ID в БД, получает  из брокера сообщение с информацией о заказах клиента
     * @param orderId
     * @return
     */
    @POST
    @Path("/orders")
    public String getOrdersById(String orderId) {

//        BrokerReceiver brokerReceiver = new BrokerReceiver();
//        int id = 0;
//        try {
//            id = Integer.valueOf(orderId.replaceAll("[^0-9]",""));
//        } catch (IncompatibleClassChangeError e){
//            log.log(Level.SEVERE, e.getMessage());
//        }
//        try {
//            URL url = new URL("http://localhost:8082/Aproject-system-1.0/fromBase");
//            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
//            urlConnection.setRequestMethod("GET");
//        } catch (IOException e) {
//            log.log(Level.SEVERE, e.getMessage());
//        }
//
//        Client client = brokerReceiver.takeClient();
//        List<Order> listOrders = client.getOrder();
//        //TODO дописать когда будет готов модуль получения массива из брокера
        //test
        Order order = new Order();
        order.setId(BigInteger.valueOf(1));
        order.setQuantity(BigInteger.valueOf(4));
        order.setOrderGroupId("40");
        XMLGregorianCalendar xmlGregorianCalendar = null;
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        try {
            xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        order.setDate(xmlGregorianCalendar);
        List<Order> listOrders = new ArrayList();
        listOrders.add(order);
        //test

        StringBuilder readyJson = new StringBuilder();
        for (int i = 0 ; i < listOrders.size() ; i++){
            Order orderList = listOrders.get(i);
            JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                    .add("id", orderList.getId().toString())
                    .add("quantity", orderList.getQuantity().toString())
                    .add("date", orderList.getDate().getDay()+"."+orderList.getDate().getMonth()+"."+orderList.getDate().getYear());
            JsonObject jsonObject = objectBuilder.build();
            try (Writer writer = new StringWriter()){
                Json.createWriter(writer).write(jsonObject);
                readyJson.append(writer.toString());
                if (i < listOrders.size()-1) readyJson.append(",");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "{ \"data\": [ " + readyJson.toString() + "]}";
    }
}
