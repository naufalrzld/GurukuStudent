package mbd.student.gurukustudent.model.teacher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Naufal on 13/02/2018.
 */

public class Teacher {
    @SerializedName("teacherID")
    @Expose
    private Integer teacherID;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("no_tlp")
    @Expose
    private String noTlp;
    @SerializedName("lineAccount")
    @Expose
    private String lineAccount;
    @SerializedName("noWA")
    @Expose
    private String noWA;
    @SerializedName("igAccount")
    @Expose
    private String igAccount;
    @SerializedName("otherAccount")
    @Expose
    private String otherAccount;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;

    public Teacher(Integer teacherID, String username, String firstName, String lastName, String email,
                   String noTlp, String lineAccount, String noWA, String igAccount, String otherAccount,
                   String description, Integer price, String createdAt, String updatedAt) {
        this.teacherID = teacherID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.noTlp = noTlp;
        this.lineAccount = lineAccount;
        this.noWA = noWA;
        this.igAccount = igAccount;
        this.otherAccount = otherAccount;
        this.description = description;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Teacher(Integer teacherID, String firstName, String lastName) {
        this.teacherID = teacherID;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Teacher(Integer teacherID, String username, String firstName, String lastName,
                   String email, String noTlp, String lineAccount, String noWA, String igAccount,
                   String otherAccount, String description, Integer price) {
        this.teacherID = teacherID;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.noTlp = noTlp;
        this.lineAccount = lineAccount;
        this.noWA = noWA;
        this.igAccount = igAccount;
        this.otherAccount = otherAccount;
        this.description = description;
        this.price = price;
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID) {
        this.teacherID = teacherID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNoTlp() {
        return noTlp;
    }

    public void setNoTlp(String noTlp) {
        this.noTlp = noTlp;
    }

    public String getLineAccount() {
        return lineAccount;
    }

    public void setLineAccount(String lineAccount) {
        this.lineAccount = lineAccount;
    }

    public String getNoWA() {
        return noWA;
    }

    public void setNoWA(String noWA) {
        this.noWA = noWA;
    }

    public String getIgAccount() {
        return igAccount;
    }

    public void setIgAccount(String igAccount) {
        this.igAccount = igAccount;
    }

    public String getOtherAccount() {
        return otherAccount;
    }

    public void setOtherAccount(String otherAccount) {
        this.otherAccount = otherAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
