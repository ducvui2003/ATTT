package nlu.fit.leanhduc.controller;

public interface TaskOperation {
    void execute() throws Exception; // Allow for exceptions to indicate failure

    void onSuccess();

    void onError(Exception e);
}
