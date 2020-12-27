package Controllers;

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
            return "returning order with id " + orderId;
        }
    }
