package com.twinkle.hfilm;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.loopj.android.image.SmartImageView;
import com.tencent.smtt.sdk.TbsVideo;

import java.util.HashMap;
import java.util.List;

public class InfoActivity extends AppCompatActivity {

    private SmartImageView infosvwpic;
    private TextView infotvwtitle;
    private TextView infotvwtag;
    private TextView infotvwepisode;
    private TextView infotvwabout;
    private android.support.v7.widget.Toolbar infotbr;
    private Spinner spinner1, spinner2;
    private List<HashMap<String, String>> list2;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        init();
        apply();

    }


    private void init() {

        infotbr = (android.support.v7.widget.Toolbar) findViewById(R.id.info_tbr);
        infotvwabout = (TextView) findViewById(R.id.info_tvw_about);
        infotvwepisode = (TextView) findViewById(R.id.info_tvw_episode);
        infotvwtag = (TextView) findViewById(R.id.info_tvw_tag);
        infotvwtitle = (TextView) findViewById(R.id.info_tvw_title);
        infosvwpic = (SmartImageView) findViewById(R.id.info_svw_pic);
        spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner2 = (Spinner) findViewById(R.id.spinner2);
    }

    private void apply() {

        Intent intent = InfoActivity.this.getIntent();
        String name = intent.getStringExtra("name");
        String episode = intent.getStringExtra("episode");
        String tag = intent.getStringExtra("tag");
        String img = intent.getStringExtra("img");
        String info = intent.getStringExtra("info");
        list2 = (List<HashMap<String, String>>) intent.getSerializableExtra("list2");


        infotbr.setTitle(name);
        setSupportActionBar(infotbr);
        infotbr.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        infotvwtitle.setText(name);
        infotvwepisode.setText(episode);
        infotvwtag.setText(tag);
        infotvwabout.setText(info);
        infosvwpic.setScaleType(ImageView.ScaleType.FIT_XY);
        infosvwpic.setImageUrl(img, R.drawable.logo_fir);

        String[] mItems = new String[]{"解析端口一", "解析端口二"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        break;
                    case 1: analyze("screenSize");
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                spinner1.setSelection(1, true);

            }
        });


    }

    private  void  analyze(String str){
        int num = list2.size();
        String[] mItems = new String[num];
        for(int x = 0;x<num;x++){
            mItems[x] = list2.get(x).get(str);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter);
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(flag){
                    TbsVideo.openVideo(InfoActivity.this, list2.get(i).get("m3utx"));
                }
                    flag = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                spinner2.setSelection(0, true);

            }
        });


    }


}
