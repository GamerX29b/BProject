package Controllers;

import BrokerClass.ProductsReseiver;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;



@Path("/orders")
public class AddNewClient {

        @POST
        @Path("/newClient")
        public String getOrderById(String orderId) {
            System.out.println(orderId);
            ProductsReseiver productsReseiver = new ProductsReseiver();
            productsReseiver.takeProduct();
            return "returning order with id " + orderId;
        }
    }
