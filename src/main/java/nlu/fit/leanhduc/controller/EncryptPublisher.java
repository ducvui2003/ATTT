package nlu.fit.leanhduc.controller;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class EncryptPublisher implements TaskPublisher {

    List<TaskSubscriber> subscribers;

    public EncryptPublisher() {
        this.subscribers = new ArrayList<>();
    }

    @Override
    public void addSubscriber(TaskSubscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(TaskSubscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifySubscribers(String status, int progress) {
        for (TaskSubscriber subscriber : subscribers) subscriber.onTaskUpdate(status, progress);
    }

    @Override
    public void doTask() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    System.out.println("Success");
                } catch (Exception e) {
                    System.out.println("Error");
                }
                return null;
            }

            @Override
            protected void done() {
                System.out.println("Done");
            }
        };
        worker.execute();
    }
}
