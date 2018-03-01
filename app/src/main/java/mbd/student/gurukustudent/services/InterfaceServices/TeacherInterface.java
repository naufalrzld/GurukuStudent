package mbd.student.gurukustudent.services.InterfaceServices;

import java.util.List;

import mbd.student.gurukustudent.model.teacher.Category;
import mbd.student.gurukustudent.model.teacher.CategoryResponse;
import mbd.student.gurukustudent.model.teacher.TeacherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Naufal on 13/02/2018.
 */

public interface TeacherInterface {
    @GET("/teacher/getCategory")
    Call<CategoryResponse> APIGetAllCategory();
}
