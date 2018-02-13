package mbd.student.gurukustudent.model.guru;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Naufal on 13/02/2018.
 */

public class GuruResponse {
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("data")
    @Expose
    private List<Guru> guru = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Guru> getGuru() {
        return guru;
    }

    public void setGuru(List<Guru> guru) {
        this.guru = guru;
    }
}
