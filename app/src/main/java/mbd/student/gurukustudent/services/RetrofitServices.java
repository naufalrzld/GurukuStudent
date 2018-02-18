package mbd.student.gurukustudent.services;

import mbd.student.gurukustudent.services.InterfaceServices.TeacherInterface;
import mbd.student.gurukustudent.services.InterfaceServices.StudentInterface;

/**
 * Created by Naufal on 10/02/2018.
 */

public class RetrofitServices {
    public static StudentInterface sendStudentRequest() {
        return RetrofitBaseServices.getApiClient().create(StudentInterface.class);
    }

    public static TeacherInterface sendTeacherRequest() {
        return RetrofitBaseServices.getApiClient().create(TeacherInterface.class);
    }
}
