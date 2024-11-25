package com.example.ordernotificationmodule.api.controller;

import com.example.ordernotificationmodule.service.*;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.http.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryService InventoryService = new InventoryService();

    // a method with all categories needed

    @GetMapping("/category/{category}")
    int viewRemain(@PathVariable("category") String category){
//        System.out.println("get remain");
        // System.out.println(service.getRemainProducts(category).get(0).getName());
        return InventoryService.getRemainProducts(category);
    }

    @GetMapping("/getall")
    ArrayList<Product> getAllProducts(){
        return InventoryService.getAvailableProducts();
    }


    @PostMapping("/add")
    Response addProduct(@RequestBody Product product){
        System.out.println("add product");
        boolean ser = InventoryService.newProduct(product);
        Response response = new Response();
        if (ser) {
            response.setStatus(true);
            response.setResult("Product Added Successfully");
        }

        else{
            response.setStatus(false);
            response.setResult("Couldn't add product");
        }

        return response;
    }

}