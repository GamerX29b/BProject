package Bproject.Bprojectsystem.сontrollers;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


@Path("/orders")
public class AddNewClient {

        @POST
        @Path("/newClient")
        public String getOrderById(String orderId) {
            System.out.println(orderId);

            try {
            URL url = new URL("http://localhost:8082/Aproject-system-1.0/fromBase");

                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Client client = productsReseiver.takeClient();
            //System.out.println(client.getClientName());

            return "returning order with id " + orderId;
        }
    }
