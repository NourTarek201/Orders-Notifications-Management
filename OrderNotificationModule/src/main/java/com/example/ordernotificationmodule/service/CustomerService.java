package com.example.ordernotificationmodule.service;

import com.example.ordernotificationmodule.SharedDatabase;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.stereotype.*;
import java.util.*;

@Service
public class CustomerService {

    public CustomerModel getCustomer(int id){
        return SharedDatabase.customers.get(id);
    }

    public boolean addCustomer(CustomerModel customer){
        if(customer.getName() != null && customer.getAddress() != null &&
                customer.getEmail() != null && customer.getPhoneNumber() != null&&
                customer.getPassword() != null && ! isDuplicateId(customer.getId())){
            SharedDatabase.customers.put(customer.getId(), customer);
            return true;
        }
        return false;
    }

    public boolean isLogged(int id, String pass){
        if(isDuplicateId(id) && SharedDatabase.customers.get(id).getPassword().equals(pass)){
            return true;
        }
        return false;
    }


    public boolean isDuplicateId(int customerId) {
        return SharedDatabase.customers.containsKey(customerId);
    }


    public boolean deleteCustomer(int id){
        int index = -1;
        for(int i = 0; i < SharedDatabase.customers.size(); i++){
            if(SharedDatabase.customers.get(i).getId() == id){
                index = i;
            }
        }
        if(index != -1){
            SharedDatabase.customers.remove(index);
            return true;
        }
        return false;
    }

    public ArrayList<CustomerModel> getAllCustomers(){
        return new ArrayList<>(SharedDatabase.customers.values());
    }

    public boolean deductAmount(int id, double amount){
        if(SharedDatabase.customers.get(id) == null){
            return false;
        }
        CustomerModel c = SharedDatabase.customers.get(id);
        if(c.getBalance()-amount < 0){
            return false;
        }
        c.setBalance(c.getBalance()-amount);
        return true;
    }

    public boolean addAmount(int id, double amount){
        if(SharedDatabase.customers.get(id) == null){
            return false;
        }
        CustomerModel c = SharedDatabase.customers.get(id);
        c.setBalance(c.getBalance()+amount);
        return true;
    }
}
