package com.example.ordernotificationmodule.api.controller;

import com.example.ordernotificationmodule.service.*;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService = new CustomerService();

    //login function
    @GetMapping("/login")
    public Response login(@RequestBody CustomerModel customer) {
        Response response = new Response();
        if(customerService.isLogged(customer.getId(), customer.getPassword())){
            response.setStatus(true);
            CustomerModel c = customerService.getCustomer(customer.getId());
            Map<String,CustomerModel> map = new HashMap<>();
            map.put("account data", c);
            response.setResult(map);
        }
        else{
            response.setStatus(false);
            response.setResult("Invalid credentials");
        }
        return response;
    }

    @GetMapping("/check")
    public Response check(@RequestParam("id") int id, @RequestParam("password") String password) {
        Response response = new Response();
        if(customerService.isLogged(id,password)){
            response.setStatus(true);
            CustomerModel customer = customerService.getCustomer(id);
            Map<String,CustomerModel> map = new HashMap<>();
            map.put("account data", customer);
            response.setResult(map);
        }
        else{
            response.setStatus(false);
            response.setResult("Invalid credentials");
        }
        return response;
    }





    @PostMapping("/add") // /person/add
    public Response addCustomer(@RequestBody CustomerModel customer) {
        boolean state = customerService.addCustomer(customer);
        Response response = new Response();
        if(state){
            response.setStatus(true);

            response.setResult("Customer added successfully");
        }
        else{
            response.setStatus(false);
            response.setResult("Customer already exists");
        }
        return response;
    }


    @GetMapping("/get/{id}")
    public Response getCustomer(@PathVariable("id") int id){
        CustomerModel customer = customerService.getCustomer(id);
        Response response = new Response();
        if(customer != null){
            response.setStatus(true);
            response.setResult(customer);
        }
        else{
            response.setStatus(false);
            response.setResult("Customer doesn't exist");
        }
        return response;
    }



    @GetMapping("/getall")
    public Response getAllCustomers(){
        Response response = new Response();
        response.setStatus(true);
        response.setResult(customerService.getAllCustomers());
        return response;
    }



    @DeleteMapping("/delete/{id}")
    public Response deletePerson(@PathVariable("id") int id){
        Response response = new Response();
        boolean status = customerService.deleteCustomer(id);
        if(status){
            response.setStatus(true);
            response.setResult("Customer deleted successfully");
        }
        else{
            response.setStatus(false);
            response.setResult("Customer doesn't exist");
        }
        return response;
    }


//    @RequestMapping("*")
//    public Response error(){
//        Response response = new Response();
//        response.setStatus(false);
//        response.setResult("Invalid request");
//        return response;
//    }



}
