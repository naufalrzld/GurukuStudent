package mbd.student.gurukustudent.model.teacher;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Naufal on 26/02/2018.
 */

public class Category {
    @SerializedName("categoryID")
    @Expose
    private Integer categoryID;
    @SerializedName("categoryName")
    @Expose
    private String categoryName;

    public Integer getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(Integer categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
