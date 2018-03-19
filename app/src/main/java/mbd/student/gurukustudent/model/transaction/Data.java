package mbd.student.gurukustudent.model.transaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import mbd.student.gurukustudent.model.teacher.Teacher;

/**
 * Created by Naufal on 21/02/2018.
 */

public class Data {
    @SerializedName("bookID")
    @Expose
    private Integer bookID;
    @SerializedName("teacherID")
    @Expose
    private Integer teacherID;
    @SerializedName("studentID")
    @Expose
    private Integer studentID;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("duration")
    @Expose
    private Integer duration;
    @SerializedName("finish")
    @Expose
    private Integer finish;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("note")
    @Expose
    private String note;
    @SerializedName("Teacher")
    @Expose
    private Teacher teacher;
    @SerializedName("Transaction")
    @Expose
    private Transaction transaction;

    public Data(Integer bookID, Integer status, Integer duration, String location, String date,
                String time, String note, Teacher teacher, Transaction transaction) {
        this.bookID = bookID;
        this.status = status;
        this.duration = duration;
        this.location = location;
        this.date = date;
        this.time = time;
        this.note = note;
        this.teacher = teacher;
        this.transaction = transaction;
    }

    public Data(Integer bookID, Integer status, Integer duration, String location, String date,
                String time, String note, Teacher teacher) {
        this.bookID = bookID;
        this.status = status;
        this.duration = duration;
        this.location = location;
        this.date = date;
        this.time = time;
        this.note = note;
        this.teacher = teacher;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public Integer getTeacherID() {
        return teacherID;
    }

    public void setTeacherID(Integer teacherID) {
        this.teacherID = teacherID;
    }

    public Integer getStudentID() {
        return studentID;
    }

    public void setStudentID(Integer studentID) {
        this.studentID = studentID;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
