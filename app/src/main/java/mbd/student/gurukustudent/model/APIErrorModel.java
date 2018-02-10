package mbd.student.gurukustudent.model;

/**
 * Created by Naufal on 10/02/2018.
 */

public class APIErrorModel {
    private int status;
    private String message;

    public APIErrorModel() {

    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
