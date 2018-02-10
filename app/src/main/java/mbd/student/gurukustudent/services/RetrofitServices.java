package mbd.student.gurukustudent.services;

import mbd.student.gurukustudent.services.InterfaceServices.MemberInterface;

/**
 * Created by Naufal on 10/02/2018.
 */

public class RetrofitServices {
    public static MemberInterface sendMemberRequest() {
        return RetrofitBaseServices.getApiClient().create(MemberInterface.class);
    }
}
