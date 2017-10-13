package com.psn.patrol.serverfragment;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.psn.patrol.R;
import com.psn.patrol.db.SqlManager;
import com.psn.patrol.db.TeskSqliteOpenHelper;

import java.util.ArrayList;

public class AddPathListAty extends ListActivity implements View.OnClickListener {

    private AddPathAdapter addPathAdapter;
    private Button addPath;

    private SqlManager sqlManager;
    private TeskSqliteOpenHelper sqlHelper;
    private ArrayList<String[]> list = new ArrayList<>();
    private ImageView topButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_path_lsit_aty);

        init();
        addPathAdapter = new AddPathAdapter(this, list);
        setListAdapter(addPathAdapter);
        addPathAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1) {
            String s = data.getStringExtra("results");
            String ss[] = s.split(";");
            list.add(ss);
            addPathAdapter.notifyDataSetChanged();
        }
    }

    public void init() {
        addPath = (Button) findViewById(R.id.add_path_bt);
        addPath.setOnClickListener(this);
        topButton = (ImageView) findViewById(R.id.topButton);
        topButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_path_bt:
                Intent intent = new Intent(AddPathListAty.this, AddPathInfoAty.class);
                startActivityForResult(intent, 1);
            case R.id.topButton:
                finish();
                break;
        }

    }
}
