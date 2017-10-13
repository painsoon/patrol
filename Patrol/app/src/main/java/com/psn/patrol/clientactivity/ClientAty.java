package com.psn.patrol.clientactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;

import com.psn.patrol.R;
import com.psn.patrol.db.SqlManager;
import com.psn.patrol.db.TeskSqliteOpenHelper;

public class ClientAty extends Activity implements View.OnClickListener,View.OnTouchListener{
    private Button btsign;
    private Button btspport;
    private Button btgps;
    private Button btpath;
    private RelativeLayout slide;
    private ViewFlipper viewFlipper;
    private TranslateAnimation rightInAnim;
    private TranslateAnimation rightOutAnim;
    private TranslateAnimation leftInAnim;
    private TranslateAnimation leftOutAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 无标题
        setContentView(R.layout.activity_client_aty);
        init();
    }

    @Override
    protected void onDestroy() {
        SqlManager sqlmanager = new SqlManager(new TeskSqliteOpenHelper(this,"tesk.db",null,1));
        sqlmanager.Delete("delete from testpath");
        super.onDestroy();
    }

    public void init(){

        slide = (RelativeLayout) findViewById(R.id.slideView);
        slide.setOnTouchListener(this);
        viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper);

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

        btsign = (Button) findViewById(R.id.client_sign);
        btspport = (Button) findViewById(R.id.client_support);
        btgps = (Button) findViewById(R.id.client_gps);
        btpath = (Button) findViewById(R.id.client_seekpath);

        btsign.setOnClickListener(this);
        btspport.setOnClickListener(this);
        btgps.setOnClickListener(this);
        btpath.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.client_sign:
                btsign.setText("已签到");
                btsign.setEnabled(false);
                break;
            case R.id.client_support:
                intent  = new Intent(this,RescueAty.class);
                startActivity(intent);
                break;
            case R.id.client_gps:
                intent  = new Intent(this,BaiDuMapAty.class);
                startActivity(intent);
                break;
            case R.id.client_seekpath:
                intent = new Intent(this,SelectPathAty.class);
                startActivity(intent);
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
