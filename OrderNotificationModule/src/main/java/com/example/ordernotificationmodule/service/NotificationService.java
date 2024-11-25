package com.example.ordernotificationmodule.service;

import com.example.ordernotificationmodule.NotificationManager;
import com.example.ordernotificationmodule.api.model.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;

@Service
public class NotificationService {

    public void add(Notification notification){
        NotificationManager.notificationQueue.add(notification);
        scheduleRemoval();
    }
    public Notification[] getAll(){
        int i = 0;
        Notification[] notifications = new Notification[NotificationManager.notificationQueue.size()];
        for (Notification n : NotificationManager.notificationQueue) {
            notifications[i] = n;
            i++;
        }
        return notifications;
    }

    // BONUS PART

    private void remove() {
        if (!NotificationManager.notificationQueue.isEmpty()) {
            Notification notification = NotificationManager.notificationQueue.poll();
            notification.sendNotification();
            ArrayList<String> keys = notification.getRecepients();
            for(int i = 0; i < keys.size(); i++) {
                if (NotificationManager.recepientStatistics.containsKey(keys.get(i))) {
                    int currentValue = NotificationManager.recepientStatistics.get(keys.get(i));
                    NotificationManager.recepientStatistics.put(keys.get(i), currentValue + 1);
                } else {
                    NotificationManager.recepientStatistics.put(keys.get(i), 1);
                }
            }
            String type = notification.getTemplateType();
            if (NotificationManager.templateStatistics.containsKey(type)) {
                int currentValue = NotificationManager.templateStatistics.get(type);
                NotificationManager.templateStatistics.put(type, currentValue + 1);
            } else {
                NotificationManager.templateStatistics.put(type, 1);
            }
        }
    }

    private void scheduleRemoval() {
        long size = NotificationManager.notificationQueue.size();
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(this::remove, 30+(30*size), TimeUnit.SECONDS);
        executorService.shutdown();
    }

    public String getMostNotified(){
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : NotificationManager.recepientStatistics.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        if (maxEntry == null) {
            return "none";
        }
        return maxEntry.getKey();
    }

    public String getMostSentTemplate(){
        Map.Entry<String, Integer> maxEntry = null;
        for (Map.Entry<String, Integer> entry : NotificationManager.templateStatistics.entrySet()) {
            if (maxEntry == null || entry.getValue() > maxEntry.getValue()) {
                maxEntry = entry;
            }
        }
        if (maxEntry == null) {
            return "none";
        }
        return maxEntry.getKey();
    }
}
