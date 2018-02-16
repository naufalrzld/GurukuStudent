package mbd.student.gurukustudent.model.teacher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Naufal on 13/02/2018.
 */

public class TeacherResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Teacher> teacher = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Teacher> getTeacher() {
        return teacher;
    }

    public void setTeacher(List<Teacher> teacher) {
        this.teacher = teacher;
    }
}
