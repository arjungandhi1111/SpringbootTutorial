package com.example.Customer1.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Customer1.Model.Customer;
import com.example.Customer1.Service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
@Autowired
private CustomerService customerService;
@GetMapping
public List<Customer> getAllCustomers()
{
    return customerService.getAllCustomers();
}

@GetMapping("/{id}")
public ResponseEntity<Customer> getCustomerById(@PathVariable long id) {
       Optional<Customer> customer = (customerService.getCustomerById(id));
       return customer.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
}
@PostMapping
public Customer createCustomer(@RequestBody Customer customer)
{
    Customer savedCustomer=customerService.addCustomer(customer);
    return savedCustomer;
}
@PutMapping("/{id}")
public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
    Customer updatedCustomer = customerService.updateCustomer(id, customer);
    return ResponseEntity.ok(updatedCustomer);
}
}
