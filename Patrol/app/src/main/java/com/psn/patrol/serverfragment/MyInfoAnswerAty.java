package com.psn.patrol.serverfragment;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.psn.patrol.R;
import com.psn.patrol.bean.Question;

import java.util.ArrayList;

public class MyInfoAnswerAty extends ListActivity {

    private GridView noScrollgridview;
    private ArrayList<Question> questions ;
    private TextView tvMessage,ll_date;
    private EditText etComment;
    private GridViewAdapter adapter;
    private String photo[];
    private String ownof,contextor,time,owner;

    private ImageView topButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_info_answer_aty);
        init();
    }

    public void init() {
        Intent intent = getIntent();
        int position = intent.getIntExtra("position",0);
        Question question = QuestionData.getQuestions().get(position);
        photo = question.getPhoto();

        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new GridViewAdapter();
        noScrollgridview = (GridView) findViewById(R.id.noScrollgridview);
        noScrollgridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        noScrollgridview.setAdapter(adapter);

        tvMessage = (TextView) findViewById(R.id.tvMessage);
        ll_date = (TextView) findViewById(R.id.ll_date);
        etComment = (EditText) findViewById(R.id.etComment);

    }

    private class GridViewAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return photo.length;
        }

        @Override
        public Object getItem(int position) {
            return photo[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(MyInfoAnswerAty.this)
                        .inflate(R.layout.item_published_grida, parent, false);
                holder = new ViewHolder();
                holder.image = (ImageView) convertView
                        .findViewById(R.id.item_grida_image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            byte[] bytes = Base64.decode(photo[position], Base64.DEFAULT);
            Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            holder.image.setImageBitmap(bm);

            return convertView;
        }
        private class ViewHolder {
            public ImageView image;
        }
    }

}