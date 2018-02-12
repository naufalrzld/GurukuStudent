package mbd.student.gurukustudent.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.activity.LoginActivity;
import mbd.student.gurukustudent.model.member.Member;
import mbd.student.gurukustudent.utils.SessionManager;
import mbd.student.gurukustudent.utils.SharedPreferencesUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountFragment extends Fragment {
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.tvNama)
    TextView tvNama;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvNoTlp)
    TextView tvNoTlp;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvLogout)
    TextView tvLogout;

    private SessionManager session;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
    private TextDrawable mDrawableBuilder;
    private Member member;

    public AccountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind(this, v);

        session = new SessionManager(getContext());
        sharedPreferencesUtils = new SharedPreferencesUtils(getContext(), "DataMember");

        if (sharedPreferencesUtils.checkIfDataExists("profile")) {
            member = sharedPreferencesUtils.getObjectData("profile", Member.class);
            String nama = member.getFirstName() + " " + member.getLastName();
            String username = member.getUsername();
            String noTlp = member.getNoTlp();
            String email = member.getEmail();

            tvNama.setText(nama);
            tvUsername.setText(username);
            tvNoTlp.setText(noTlp);
            tvEmail.setText(email);

            setProfileImage(nama);

            tvLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });
        }

        return v;
    }

    private void setProfileImage(String nama) {
        String letter = "A";

        if(nama != null && !nama.isEmpty()) {
            letter = nama.substring(0, 1);
        }

        int color = mColorGenerator.getRandomColor();
        mDrawableBuilder = TextDrawable.builder().buildRound(letter, color);
        profileImage.setImageDrawable(mDrawableBuilder);
    }

    private void logout() {
        session.setLogin(false);
        sharedPreferencesUtils.clearAllData();
        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }
}
