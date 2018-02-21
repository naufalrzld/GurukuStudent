package mbd.student.gurukustudent.model.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Naufal on 21/02/2018.
 */

public class Transaction {
    @SerializedName("transactioID")
    @Expose
    private Integer transactioID;
    @SerializedName("bookID")
    @Expose
    private Integer bookID;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("paymentMethod")
    @Expose
    private String paymentMethod;
    @SerializedName("total_price")
    @Expose
    private Integer totalPrice;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Transaction(Integer transactioID, Integer bookID, Integer status, String paymentMethod, Integer totalPrice) {
        this.transactioID = transactioID;
        this.bookID = bookID;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.totalPrice = totalPrice;
    }

    public Integer getTransactioID() {
        return transactioID;
    }

    public void setTransactioID(Integer transactioID) {
        this.transactioID = transactioID;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
