package Bproject.Bprojectsystem.сontrollers;




import Bproject.Bprojectsystem.brokerClass.BrokerReceiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ejb.EJB;
import javax.servlet.http.HttpServlet;

@Controller
public class GeneralPage extends HttpServlet {

    Logger logger = LoggerFactory.getLogger(GeneralPage.class);

    @Autowired
    BrokerReceiver brokerReceiver;

    @EJB
    private PunchBroker ejb;

    @Autowired
    public GeneralPage(BrokerReceiver brokerReceiver) {
        this.brokerReceiver = brokerReceiver;
    }

    @RequestMapping(value = { "/", "/hello" }, method = RequestMethod.GET)
    public String StartedPage(Model model) {

       // Client client = brokerReceiver.receiveClient();


        return "StartedPage";
    }

}
