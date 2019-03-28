package csuebswe.salesforcelite.model;

import java.io.Serializable;
import java.util.*;

public class EmployeeModel implements Serializable, User {
    // Member fields
    private Map<Integer, Sale> openSales;
    private Map<Integer, Sale> closedSales;
    private AllCustomers customers;
    private List<String> options; // TODO: delete
    private Logger logger;
    private final int MAX_SALE_ID = 10000;
    private String username, password;

    // Constructor
    public EmployeeModel(String usr, String pw, Logger log, AllCustomers customers) {
        logger = log;
        closedSales = new LinkedHashMap<>();
        openSales = new LinkedHashMap<>();
        this.customers = customers;
        username = usr;
        password = pw;
    }


    // Create a new sale with a unique id then offer it a specific customer
    public void offerNewSale(String customer_username, String saleName) {
        Random rand = new Random();
        int n = rand.nextInt(MAX_SALE_ID);

        while (logger.doesSaleIdExist(n))
            n = rand.nextInt(MAX_SALE_ID);

        // Create sale object
        Sale newSale;
        newSale = new Sale(this, getCustomer(customer_username), n, saleName);

        openSales.put(n, newSale);
        logger.acceptNewSale(newSale);
    }

    // Move an open sale to a closed sale
    public void closeSale(int sale_id) {
        Sale closedSale = openSales.remove(sale_id);
        closedSales.put(sale_id, closedSale);
    }

    /**
     * Getters
     */

    // Retrieve a customer by their username -- helper method
    private CustomerModel getCustomer(String username) {
        return customers.getCustomer(username);
    }

    String getPassword() {
        return password;
    }

    public Map<Integer, Sale> getClosedSales() {
        return closedSales;
    }

    public Map<Integer, Sale> getOpenSales() {
        return openSales;
    }
}