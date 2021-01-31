package Bproject.Bprojectsystem.сontrollers;


import Bproject.Bprojectsystem.brokerClass.BrokerReceiver;
import Bproject.Bprojectsystem.jaxbComponent.Client;
import Bproject.Bprojectsystem.jaxbComponent.Order;
import Bproject.Bprojectsystem.jaxbComponent.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.HttpMethod;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.DataOutputStream;
import java.io.IOException;

import java.io.StringWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class dataFromOrders {

    Logger log = Logger.getLogger(dataFromOrders.class.getName());

    @Autowired
    BrokerReceiver brokerReceiver;

    public dataFromOrders(BrokerReceiver brokerReceiver) {
        this.brokerReceiver = brokerReceiver;
    }

    /**
     * Получить продукты по айди ордера
     * @param
     * @return
     */
    @POST
    @PostMapping("/orders/product")
    public String getProductById(String productId) {

        final RestTemplate restTemplate = new RestTemplate();
        int id = 0;
        try {
            id = Integer.valueOf(productId.replaceAll("[^0-9]",""));
            restTemplate.put("http://localhost:8082/Aproject-system-1.0/getProductDataFromClientId", id);
        } catch (IncompatibleClassChangeError e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        Order order = brokerReceiver.receiveOrder();
        List<Product> listProduct = order.getProduct();
        //TODO дописать когда будет готов модуль получения массива из брокера
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

    @PostMapping(value = { "/orders/orders" })
    public String getOrdersById(@RequestParam(value = "orderId", defaultValue = "0")String orderId) {
        final RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("id", orderId);
        try {
           URL url = new URL("http://localhost:8082/Aproject-system-1.0/getClientDataFromId?id="+orderId);
           HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            log.log(Level.INFO, String.valueOf(urlConnection.getResponseCode()));
        } catch (IncompatibleClassChangeError | MalformedURLException | ProtocolException e) {
            log.log(Level.SEVERE, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Client client = new Client();
            client = brokerReceiver.receiveClient();
            List<Order> listOrders = client.getOrder();
            StringBuilder readyJson = new StringBuilder();
            for (int i = 0; i < listOrders.size(); i++) {
                Order orderList = listOrders.get(i);
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder()
                        .add("id", orderList.getId().toString())
                        .add("quantity", orderList.getQuantity().toString())
                        .add("date", orderList.getDate().getDay() + "." + orderList.getDate().getMonth() + "." + orderList.getDate().getYear());
                JsonObject jsonObject = objectBuilder.build();
                try (Writer writer = new StringWriter()) {
                    Json.createWriter(writer).write(jsonObject);
                    readyJson.append(writer.toString());
                    if (i < listOrders.size() - 1) readyJson.append(",");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        return "{ \"data\": [ " + readyJson.toString() + "]}";
    }
}
