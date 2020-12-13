package com.example.appbarlayout;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<MyFragment> bodyFragments;
    private ImageView mHeaderIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = findViewById(R.id.community_container_tab_layout);
        viewPager = findViewById(R.id.viewPager);
        mHeaderIcon = findViewById(R.id.person_user_head_icon);
        mHeaderIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                final CustomDialog customDialog = new CustomDialog(this);
//                customDialog.setAwardGetNum(10);
//                customDialog.setmClickListener(new CustomDialog.ViewClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        if (customDialog != null) {
//                            customDialog.dismiss();
//                        }
//                    }
//                });
//
//                customDialog.show();
            }
        });
        bodyFragments = new ArrayList<>();
        MyFragment myFragment1 = new MyFragment();
        myFragment1.setmTitle("ta回答的");
        MyFragment myFragment2 = new MyFragment();
        myFragment2.setmTitle("ta得到的");
        bodyFragments.add(myFragment1);
        bodyFragments.add(myFragment2);

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return bodyFragments.get(position);
            }

            @Override
            public int getCount() {
                return bodyFragments.size();
            }

            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
                return bodyFragments.get(position).getmTitle();
            }
        };
        viewPager.setAdapter(mAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);//将TabLayout和ViewPager关联起来。

    }
}