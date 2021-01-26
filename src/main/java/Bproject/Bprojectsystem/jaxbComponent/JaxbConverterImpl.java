package Bproject.Bprojectsystem.jaxbComponent;

import Bproject.Bprojectsystem.Client;
import Bproject.Bprojectsystem.Product;

public interface JaxbConverterImpl {

    public String productToXml(Product product);

    public Product xmlToProduct(String textXml);

    public String orderToXml(Order order);

    public Order xmlToOrder(String textXml);

    public String clientToXml(Client client);

    public Client xmlToClient(String textXml);
}
