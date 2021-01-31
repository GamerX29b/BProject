package Bproject.Bprojectsystem.сontrollers;

import Bproject.Bprojectsystem.brokerClass.BrokerReceiver;
import Bproject.Bprojectsystem.ejbClasses.EjbCaller;
import Bproject.Bprojectsystem.jaxbComponent.Client;
import Bproject.Bprojectsystem.jaxbComponent.JaxbConverterImpl;
import Bproject.Bprojectsystem.jaxbComponent.Order;
import Bproject.Bprojectsystem.jaxbComponent.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.naming.NamingException;
import javax.servlet.http.HttpServlet;
import java.util.LinkedList;
import java.util.List;

@Controller
public class GeneralPage extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(GeneralPage.class);

    @Autowired
    BrokerReceiver brokerReceiver;

    @Autowired
    JaxbConverterImpl jaxbConverter;

    @Autowired
    public GeneralPage(BrokerReceiver brokerReceiver, JaxbConverterImpl jaxbConverter) {
        this.brokerReceiver = brokerReceiver;
        this.jaxbConverter = jaxbConverter;
    }

    @RequestMapping(value = { "/", "/hello" }, method = RequestMethod.GET)
    public String StartedPage(Model model) throws NamingException {

        logger.info("Контроллер работает");

        return "StartedPage";
    }

}
