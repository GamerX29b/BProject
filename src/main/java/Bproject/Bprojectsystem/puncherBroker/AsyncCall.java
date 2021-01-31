package Bproject.Bprojectsystem.puncherBroker;

import Bproject.Bprojectsystem.сontrollers.dataFromOrders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class AsyncCall {
    Logger log = Logger.getLogger(dataFromOrders.class.getName());

    @Async
    public void callToClientDataFromIdThread(String orderId){
        try {

            System.out.println("Вызов потока2");
            URL url = new URL("http://localhost:8082/Aproject-system-1.0/getClientDataFromId?id=" + orderId);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            log.log(Level.INFO, String.valueOf(urlConnection.getResponseCode()));
            log.log(Level.INFO, String.valueOf(urlConnection.getURL()));
        } catch (IncompatibleClassChangeError | MalformedURLException | ProtocolException e) {
            log.log(Level.SEVERE, e.getMessage());
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    @Async
    public void callToOrderDataFromIdThread(String orderId){
        try {
            URL url = new URL("http://localhost:8082/Aproject-system-1.0/getProductDataFromClientId" + orderId);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            log.log(Level.INFO, String.valueOf(urlConnection.getResponseCode()));
            log.log(Level.INFO, String.valueOf(urlConnection.getURL()));
        } catch (IncompatibleClassChangeError | MalformedURLException | ProtocolException e) {
            log.log(Level.SEVERE, e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
