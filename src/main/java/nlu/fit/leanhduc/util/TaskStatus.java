package nlu.fit.leanhduc.util;

public enum TaskStatus {
    PENDING("Pending"),
    RUNNING("Running"),
    COMPLETED("Completed"),
    FAILED("Failed");

    private final String status;

    TaskStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
