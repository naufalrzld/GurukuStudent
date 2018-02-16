package mbd.student.gurukustudent.services.InterfaceServices;

import mbd.student.gurukustudent.model.teacher.TeacherResponse;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Naufal on 13/02/2018.
 */

public interface TeacherInterface {
    @GET("/teacher/all")
    Call<TeacherResponse> APIGetAllGuru();
}
