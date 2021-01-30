package Bproject.Bprojectsystem.ejbClasses;

import Bproject.Bprojectsystem.ejbClasses.EjbImport.PunchBrokerAnswer;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

public class EjbCaller {

        //Конфликтует с новым Spring на уровне JBOSS не даёт создавать бины, ломает.
//    @EJB(mappedName = "java:global/Aproject-system-1.0/PunchBroker!Aproject.Aprojectsystem.ejbClasses.PunchBrokerAnswer")
//    private PunchBrokerAnswer punchBrokerAnswer;


    //Конфликтует со старым JAXB конвертером.
    //JAXB конвертером обязательно нужен javaee-api 7.0, а он тянет старую зависимость
    //EJB которая конфликтует с новым спрингом.
//    private static PunchBrokerAnswer lookupAccountEJB() throws NamingException {
//        final Hashtable jndiProperties = new Hashtable();
//
//        jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,  "org.wildfly.naming.client.WildFlyInitialContextFactory");
//        jndiProperties.put(Context.PROVIDER_URL,"https-remoting://localhost:8080");
//        final Context ctx = new InitialContext(jndiProperties);
//        return (PunchBrokerAnswer) ctx
//                .lookup("ejb:/Aproject-system-1.0/PunchBroker!Aproject.Aprojectsystem.ejbClasses.PunchBrokerAnswer");
//    }
//
//    public void callBroker() throws NamingException {
//        lookupAccountEJB().getClientById(1);
//    }

}
