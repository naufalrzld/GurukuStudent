package mbd.student.gurukustudent.services.InterfaceServices;

import org.json.JSONObject;

import mbd.student.gurukustudent.model.member.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by Naufal on 10/02/2018.
 */

public interface MemberInterface {
    @POST("/akun/login")
    Call<LoginResponse> APILogin(@Body JSONObject param);
}
