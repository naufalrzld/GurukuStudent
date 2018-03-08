package mbd.student.gurukustudent.services.InterfaceServices;

import org.json.JSONObject;

import mbd.student.gurukustudent.model.history.HistoryResponse;
import mbd.student.gurukustudent.model.student.LoginResponse;
import mbd.student.gurukustudent.model.student.UpdateProfileResponse;
import mbd.student.gurukustudent.model.teacher.TeacherResponse;
import mbd.student.gurukustudent.model.transaction.TransactionResponse;
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

    @POST("/student/editProfile")
    Call<UpdateProfileResponse> APIEditProfile(@Body JSONObject param);

    @GET("/student/findTeacher/{category}")
    Call<TeacherResponse> APIGetAllGuru(@Path("category") String category);

    @POST("/student/book")
    Call<String> APIBookTeacher(@Body JSONObject param);

    @GET("/student/getBookingStatus/{bookID}")
    Call<String> APIGetBookingStatus(@Path("bookID") int bookID);

    @GET("/student/getBookingHistory/{studentID}")
    Call<HistoryResponse> APIGetBookingHistory(@Path("studentID") int studentID);

    @GET("/student/transaction/{studentID}")
    Call<TransactionResponse> APIGetTransaction(@Path("studentID") int studentID);

    @POST("/student/payment/cash")
    Call<String> APIPaymentCash(@Body JSONObject param);
}
