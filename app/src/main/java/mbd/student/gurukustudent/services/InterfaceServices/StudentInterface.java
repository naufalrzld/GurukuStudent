package mbd.student.gurukustudent.services.InterfaceServices;

import org.json.JSONObject;

import mbd.student.gurukustudent.model.student.BookingResponse;
import mbd.student.gurukustudent.model.student.LoginResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Naufal on 10/02/2018.
 */

public interface StudentInterface {
    @POST("/student/login")
    Call<LoginResponse> APILogin(@Body JSONObject param);

    @POST("/student/register")
    Call<String> APIRegister(@Body JSONObject param);

    @POST("/student/book")
    Call<BookingResponse> APIBookTeacher(@Body JSONObject param);

    @GET("/student/getBookingStatus/{bookID}")
    Call<String> APIGetBookingStatus(@Path("bookID") int bookID);
}
