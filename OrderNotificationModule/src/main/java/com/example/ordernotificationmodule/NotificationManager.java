package com.example.ordernotificationmodule;

import com.example.ordernotificationmodule.api.model.Notification;

import java.util.*;

public class NotificationManager {
    public static Queue<Notification> notificationQueue = new LinkedList<>();
    public static Map<String, Integer> recepientStatistics = new HashMap<>();
    public static Map<String, Integer> templateStatistics = new HashMap<>();
}
