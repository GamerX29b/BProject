package Bproject.Bprojectsystem.сontrollers;


import Bproject.Bprojectsystem.jaxbComponent.Order;
import Bproject.Bprojectsystem.Product;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class dataFromOrders {

    Logger log = Logger.getLogger(dataFromOrders.class.getName());

    /**
     * Получить продукты по айди ордера
     * @param orderId
     * @return
     */
    @POST
    @Path("/orders/product")
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

    @RequestMapping(value = { "/orders/orders" }, method = RequestMethod.POST)
    public String getOrdersById(@RequestParam(value = "orderId", defaultValue = "0")String orderId) {


        Order order = new Order();
        order.setId(BigInteger.valueOf(3));
        order.setQuantity(BigInteger.valueOf(4));

        List<Order> listOrders = new ArrayList<>();
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
