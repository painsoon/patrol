package com.psn.patrol.serverfragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.psn.patrol.R;
import com.psn.patrol.viewflipper.PageViewFlipper;

public class BusinessFg extends Fragment implements View.OnClickListener,View.OnTouchListener{
    private Button seekPatrol;
    private Button seekException;
    private Button pointPatrol;
    private Button addPath;
    private Button updataPath;

    private RelativeLayout slide;
    private PageViewFlipper viewFlipper;
    private TranslateAnimation rightInAnim;
    private TranslateAnimation rightOutAnim;
    private TranslateAnimation leftInAnim;
    private TranslateAnimation leftOutAnim;

    public BusinessFg() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_business_fg, container, false);

        init(view);

        return view;
    }

    public void init(View view){

        slide = (RelativeLayout) view.findViewById(R.id.slideView);
        slide.setOnTouchListener(this);
        viewFlipper = (PageViewFlipper) view.findViewById(R.id.viewflipper);

        // 图片从右侧滑入
        rightInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightInAnim.setDuration(500);

        // 图片从右侧滑出
        rightOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        rightOutAnim.setDuration(500);

        // 图片从左侧滑出
        leftOutAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftOutAnim.setDuration(500);

        // 图片从左侧滑入
        leftInAnim = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, -1.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f,
                Animation.RELATIVE_TO_PARENT, 0.0f);
        leftInAnim.setDuration(500);

        // 正常情况左侧划出，右侧滑入
        viewFlipper.setOutAnimation(leftOutAnim);
        viewFlipper.setInAnimation(rightInAnim);

        viewFlipper.setFlipInterval(3000);
        viewFlipper.startFlipping();

        seekPatrol = (Button) view.findViewById(R.id.seekpatrol);
        seekException = (Button) view.findViewById(R.id.exception);
        pointPatrol = (Button) view.findViewById(R.id.patroltest);
        addPath = (Button) view.findViewById(R.id.addpath);
        updataPath = (Button) view.findViewById(R.id.updatapoint);

        seekPatrol.setOnClickListener(this);
        seekException.setOnClickListener(this);
        pointPatrol.setOnClickListener(this);
        addPath.setOnClickListener(this);
        updataPath.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.seekpatrol:
                Intent intent1 = new Intent(getActivity(),PatrolInfoAty.class);
                startActivity(intent1);
                break;
            case R.id.exception:
                Intent intent2 = new Intent(getActivity(),ExceptionInfoAty.class);
                startActivity(intent2);
                break;
            case R.id.patroltest:


                Intent intent4 = new Intent(getActivity(),PatrolTeskAty.class);
                startActivity(intent4);
                break;
            case R.id.addpath:
                Intent intent = new Intent(getActivity(),AddPathListAty.class);
                startActivity(intent);
                break;
            case R.id.updatapoint:
                Intent intent5 = new Intent(getActivity(),ChangeOrderAty.class);
                startActivity(intent5);
                break;
        }
    }

    private static float currentX = 0;
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                viewFlipper.stopFlipping();
                currentX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (event.getX() - currentX > 100) { // 手指向右滑动，左侧滑入，右侧滑出
                    viewFlipper.setInAnimation(leftInAnim);
                    viewFlipper.setOutAnimation(rightOutAnim);
                    viewFlipper.showPrevious();
                    viewFlipper.startFlipping();
                } else if (currentX - event.getX() > 100) {// 手指向左滑动，右侧滑入，左侧滑出
                    viewFlipper.setInAnimation(rightInAnim);
                    viewFlipper.setOutAnimation(leftOutAnim);
                    viewFlipper.showNext();
                    viewFlipper.startFlipping();
                }
                break;
        }
        return true;
    }
}
