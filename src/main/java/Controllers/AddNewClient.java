package Controllers;

import BrokerClass.BrokerReceiver;

import javax.ws.rs.POST;
import javax.ws.rs.Path;


@Path("/orders")
public class AddNewClient {

        @POST
        @Path("/newClient")
        public String getOrderById(String orderId) {
            System.out.println(orderId);
            BrokerReceiver productsReseiver = new BrokerReceiver();
            productsReseiver.takeProduct();
            return "returning order with id " + orderId;
        }
    }
