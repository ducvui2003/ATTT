package nlu.fit.leanhduc.controller;


public interface TaskPublisher {
    void addSubscriber(TaskSubscriber subscriber);

    void removeSubscriber(TaskSubscriber subscriber);

    void doTask();

    void notifySubscribers(String status, int progress);
}
