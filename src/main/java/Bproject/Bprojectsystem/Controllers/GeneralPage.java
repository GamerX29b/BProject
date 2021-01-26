package Bproject.Bprojectsystem.Controllers;




import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class GeneralPage extends HttpServlet {


    @RequestMapping(value = { "/", "/hello" }, method = RequestMethod.GET)
    public String StartedPage(Model model) {

        String message = "Hello Spring Boot + JSP";

        //model.addAttribute("message", message);

        return "StartedPage";
    }
}
