package example.com.primaryschoolconnectedsystemapp;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import example.com.primaryschoolconnectedsystemapp.fragment.home;
import example.com.primaryschoolconnectedsystemapp.fragment.me;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Fragment> fgLists;
    private ViewPager mViewPager;
    private BottomNavigationView navigation;
    private long exitTime = 0;

    private final int REQUEST_EXTERNAL_STORAGE = 1;

    public void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            for (int i = 0; i < grantResults.length; i++) {
            }
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.request_read_write_permission), Toast.LENGTH_LONG).show();
                finish();
            } else {
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_me:
                    mViewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyStoragePermissions(this);

        initViewPager();
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.containerViewPager);
        if (mViewPager != null) {
            fgLists = new ArrayList<>(2);
            fgLists.add(new home());
            fgLists.add(new me());
            FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public android.support.v4.app.Fragment getItem(int position) {
                    return fgLists.get(position);
                }

                @Override
                public int getCount() {
                    return fgLists.size();
                }
            };
            mViewPager.setAdapter(mAdapter);

            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    if (position == 0)
                        navigation.setSelectedItemId(R.id.navigation_home);
                    else if (position == 1)
                        navigation.setSelectedItemId(R.id.navigation_me);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // 判断2次点击事件时间
            long curTime = System.currentTimeMillis();
            if ((curTime - exitTime) > 2000) {
                exitTime = curTime;
                Toast.makeText(this, getResources().getString(R.string.exit_again), Toast.LENGTH_SHORT).show();
            } else {
                finish();
            }
        }
        return false;
    }
}
