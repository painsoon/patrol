package com.psn.patrol.serveractivity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.psn.patrol.R;
import com.psn.patrol.serverfragment.BusinessFg;
import com.psn.patrol.serverfragment.UserOperateFg;

import java.util.ArrayList;
import java.util.List;

public class OperationAdminAty extends FragmentActivity {
    private RadioGroup radioGroup;
    private RadioButton rdbusiness;
    private RadioButton rduserOperate;
    private ViewPager viewPager;
    private List<Fragment> datas;
    private BusinessFg businessFg;
    private UserOperateFg userOperateFg;

    private FragmentPagerAdapter fpa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_operation_aty);

        init();
        initEvent();

    }

    private void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rd_business:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.rd_userOperate:
                        viewPager.setCurrentItem(1);
                        break;
                }
            }
        });
    }

    private void init() {
        viewPager  = (ViewPager) findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) findViewById(R.id.rgBottom1);
        rdbusiness = (RadioButton) findViewById(R.id.rd_business);
        rduserOperate = (RadioButton) findViewById(R.id.rd_userOperate);

        rdbusiness.setTextColor(Color.parseColor("#049f04"));
        rdbusiness.setChecked(true);

        datas  = new ArrayList<>();
        businessFg  = new BusinessFg();
        userOperateFg = new UserOperateFg();

        datas.add(businessFg);
        datas.add(userOperateFg);

        fpa = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return datas.get(i);
            }

            @Override
            public int getCount() {
                return datas.size();
            }
        };

        viewPager.setAdapter(fpa);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                resetRadioButton();
                switch (i){
                    case 0:
                        rdbusiness.setTextColor(Color.parseColor("#049f04"));
                        rdbusiness.setChecked(true);
                        break;
                    case 1:
                        rduserOperate.setTextColor(Color.parseColor("#049f04"));
                        rduserOperate.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });


    }
    private void resetRadioButton() {
        rdbusiness.setTextColor(Color.BLACK);
        rduserOperate.setTextColor(Color.BLACK);
    }
}
