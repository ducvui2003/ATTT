package nlu.fit.leanhduc.controller;

public interface TaskSubscriber {
    void onTaskUpdate(String status, int progress);
}
