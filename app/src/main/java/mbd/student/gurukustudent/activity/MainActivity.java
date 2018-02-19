package mbd.student.gurukustudent.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import mbd.student.gurukustudent.R;
import mbd.student.gurukustudent.fragment.AccountFragment;
import mbd.student.gurukustudent.fragment.TeacherFragment;
import mbd.student.gurukustudent.fragment.HistoryFragment;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottomNavView)
    BottomNavigationView bottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        bottomNavView.setOnNavigationItemSelectedListener(mOnNavItemSelectedListener);

        loadFragment(new TeacherFragment());
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
            switch (item.getItemId()) {
                case R.id.nav_history:
                    if (!(currentFragment instanceof HistoryFragment)) {
                        fragment = new HistoryFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    break;
                case R.id.nav_guru :
                    if (!(currentFragment instanceof TeacherFragment)) {
                        fragment = new TeacherFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    break;
                case R.id.nav_akun :
                    if (!(currentFragment instanceof AccountFragment)) {
                        fragment = new AccountFragment();
                        loadFragment(fragment);
                        return true;
                    }
                    break;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
