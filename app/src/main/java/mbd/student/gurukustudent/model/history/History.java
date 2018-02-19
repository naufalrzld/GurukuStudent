package mbd.student.gurukustudent.model.history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import mbd.student.gurukustudent.model.student.Student;
import mbd.student.gurukustudent.model.teacher.Teacher;

/**
 * Created by Naufal on 19/02/2018.
 */

public class History {
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
    @SerializedName("Student")
    @Expose
    private Student student;
    @SerializedName("Teacher")
    @Expose
    private Teacher teacher;

    public History(int bookID, int status, Teacher teacher) {
        this.bookID = bookID;
        this.status = status;
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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
