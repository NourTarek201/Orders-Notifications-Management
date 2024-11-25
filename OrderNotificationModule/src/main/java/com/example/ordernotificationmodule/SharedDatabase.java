package com.example.ordernotificationmodule;

import com.example.ordernotificationmodule.api.model.*;
import java.util.*;

public class SharedDatabase {
    public static Map<Integer, Product> products = new HashMap<>();
    public static Map<Integer, CustomerModel> customers = new HashMap<>();
    public static Map<Integer, Order> orders = new HashMap<>();
}