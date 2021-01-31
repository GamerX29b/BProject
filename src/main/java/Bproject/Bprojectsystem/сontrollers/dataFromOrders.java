package Bproject.Bprojectsystem.сontrollers;


import Bproject.Bprojectsystem.brokerClass.BrokerReceiver;
import Bproject.Bprojectsystem.jaxbComponent.Client;
import Bproject.Bprojectsystem.jaxbComponent.Order;
import Bproject.Bprojectsystem.jaxbComponent.Product;
import Bproject.Bprojectsystem.puncherBroker.AsyncCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
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

@RestController
@RequestMapping("/orders")
public class dataFromOrders {

    Logger log = Logger.getLogger(dataFromOrders.class.getName());

    @Autowired
    BrokerReceiver brokerReceiver;
    AsyncCall asyncCall;

    public dataFromOrders(BrokerReceiver brokerReceiver, AsyncCall asyncCall) {
        this.brokerReceiver = brokerReceiver;
        this.asyncCall = asyncCall;
    }

    /**
     * Получить продукты по айди ордера
     * @param
     * @return
     */
    @POST
    @PostMapping("/product")
    public String getProductById(String productId) {

        asyncCall.callToOrderDataFromIdThread(productId);
        Order order = brokerReceiver.receiveOrder();
        List<Product> listProduct = order.getProduct();
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
    @PostMapping(value = { "/orders" })
    public String getOrdersById(@RequestParam(value = "orderId", defaultValue = "0")String orderId) throws InterruptedException {
        final RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("id", orderId);
        System.out.println("Вызов потока0");
        asyncCall.callToClientDataFromIdThread(orderId);
        Client client = new Client();
        Thread.sleep(10000L);
        System.out.println("Вне потока");
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
