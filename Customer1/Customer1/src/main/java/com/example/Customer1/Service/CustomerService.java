package com.example.Customer1.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Customer1.Model.Customer;

@Service
public class CustomerService {
private List<Customer> customers=new ArrayList<Customer>();

Customer customerArray[]= {new Customer(1L,"customer1","customer1@gmail.com"),
        new Customer(2L,"customer2","customer2@gmail.com"),
        new Customer(3L,"customer3","customer3@gmail.com")};

public CustomerService() {
    for (Customer customer : customerArray) {
        customers.add(customer);
    }
}

public List<Customer> getAllCustomers() {
    /*
     * customers.clear(); for (Customer customer : customerArray) {
     * customers.add(customer); }
     */
    return customers;
}

public Optional<Customer> getCustomerById(long id) {
     for (Customer customer : customers) {
        if (customer.getId() == id) {
            return Optional.of(customer);
        }
    }
    return Optional.empty(); 
}

public Customer addCustomer(Customer customer) {
    customers.add(customer);
    return customer;
}

public Customer updateCustomer(Long id, Customer newCustomer) {
    return getCustomerById(id)
            .map(customer -> {
                customer.setName(newCustomer.getName());
                customer.setEmail(newCustomer.getEmail());
                return customer;
            })
            .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + id));
}
}