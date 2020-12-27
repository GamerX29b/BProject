package Controllers;




import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Path("/hello")
public class GeneralPage extends Application {

   // @EJB
    // JavaBean javaBean;
    @GET
    public String getOrderById() throws IOException {

        String string = Files.readString(Paths.get("D:\\Lab\\Для ИПР\\BProject\\src\\main\\resources\\hello.html"), StandardCharsets.UTF_8);

        return string;
    }

}
