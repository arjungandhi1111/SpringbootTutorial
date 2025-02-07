package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.CustomerException;
import com.example.demo.pojo.Customer;
import com.example.demo.repository.CustomerRepo;

@Service
public class CustomerService {
@Autowired
private CustomerRepo customerRepository;
public List<Customer> getAllCustomer(){
    return customerRepository.findAll();
}

public Optional<Customer> getCustomerById(long id) throws CustomerException {
    Optional<Customer> customer = customerRepository.findById(id);
    if (!customer.isPresent()) {
    	throw new CustomerException("customer with this id is not found");
    }
    return customer;
}

public Customer addCustomer(Customer customer) {
    return customerRepository.save(customer);
}

public Optional<Customer> updateCustomer(long id, Customer updatedCustomer) {
    return customerRepository.findById(id).map(existingCustomer -> {
        if (updatedCustomer.getName() != null) {
            existingCustomer.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getEmail() != null) {
            existingCustomer.setEmail(updatedCustomer.getEmail());
        }
        return customerRepository.save(existingCustomer);
    });
}


public boolean deleteCustomer(long id) {
    if (customerRepository.existsById(id)) {
        customerRepository.deleteById(id);
        return true;
    }
    return false;
}

}