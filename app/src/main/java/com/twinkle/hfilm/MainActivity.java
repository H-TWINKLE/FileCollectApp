package com.twinkle.hfilm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.futuremind.recyclerviewfastscroll.FastScroller;
import com.loopj.android.image.SmartImageView;
import com.twinkle.hfilm.inter.Spider;
import com.twinkle.hfilm.java.NetworkImageHolderView;
import com.twinkle.hfilm.java.MyApp;
import com.yalantis.guillotine.animation.GuillotineAnimation;
import com.yalantis.jellytoolbar.listener.JellyListener;
import com.yalantis.jellytoolbar.widget.JellyToolbar;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper;
import com.zhy.adapter.recyclerview.wrapper.LoadMoreWrapper;

import org.xutils.DbManager;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.table.DbModel;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener, View.OnClickListener {


    private JellyToolbar jbrmain;

    private FrameLayout lltmain;
    private MyApp myApp;
    private DbManager db;
    private AppCompatEditText editText;
    private ImageView ivw_main_logo;
    private TextView tvw_main_title;
    private RecyclerView mRecyclerView;
    private HeaderAndFooterWrapper mHeaderAndFooterWrapper;
    private LoadMoreWrapper mLoadMoreWrapper;
    private FastScroller fastScroller;
    private List<DbModel> list;
    private CommonAdapter mAdapter;
    private List<String> lists = new ArrayList<>();
    private String url_xpath = "http://www.85105052.com/admin.php?url=";
    private int flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            init();
            apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void init() {

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        myApp = new MyApp();
        ivw_main_logo = (ImageView) findViewById(R.id.ivw_main_logo);
        tvw_main_title = (TextView) findViewById(R.id.tvw_main_title);
        lltmain = (FrameLayout) findViewById(R.id.activity_main);
        jbrmain = (JellyToolbar) findViewById(R.id.jbr_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.rvw_main);
        fastScroller = (FastScroller) findViewById(R.id.fastscroll);


    }

    private void apply() throws Exception {


        value("tv");
        set_toolbar();
        set_recyclerview();
        set_header();
        load();

    }


    private void set_toolbar() {
        editText = (AppCompatEditText) LayoutInflater.from(this).inflate(R.layout.content_main_edit, null);
        editText.setBackgroundResource(R.color.colorTransparent);
        editText.setOnKeyListener(this);

        jbrmain.setContentView(editText);
        jbrmain.setJellyListener(jellyListener);


        View layou_Menu = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_menu, null);
        lltmain.addView(layou_Menu);

        new GuillotineAnimation.GuillotineBuilder(layou_Menu, layou_Menu.findViewById(R.id.ivw_menu_logo), ivw_main_logo)
                .setStartDelay(250)
                .setActionBarViewForAnimation(jbrmain)
                .setClosedOnStart(true)
                .build();
    }

    private void set_recyclerview() {

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        mAdapter = new CommonAdapter<DbModel>(MainActivity.this, R.layout.recyclerview_content, list) {
            @Override
            protected void convert(ViewHolder holder, DbModel s, int position) {
                SmartImageView svw = holder.getView(R.id.svw_rvw);
                holder.setText(R.id.tvw_title_rvw, list.get(position - 2).getString("title"));
                holder.setText(R.id.tvw_info_rvw, list.get(position - 2).getString("episode"));
                svw.setImageUrl(list.get(position - 2).getString("href_img"));
            }
        };

    }

    private void set_header() throws Exception {
        mHeaderAndFooterWrapper = new HeaderAndFooterWrapper(mAdapter);

        ConvenientBanner convenientBanner = new ConvenientBanner(MainActivity.this);
        convenientBanner.setPages(
                new CBViewHolderCreator<NetworkImageHolderView>() {
                    @Override
                    public NetworkImageHolderView createHolder() {
                        return new NetworkImageHolderView();
                    }
                }, lists)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Toast.makeText(MainActivity.this, "where = " + position, Toast.LENGTH_SHORT).show();
                    }
                })
                .setMinimumHeight(800);

        convenientBanner.startTurning(3000);
        TabLayout tabLayout = new TabLayout(MainActivity.this);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tv)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.film)));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        value("tv");
                        flag = 0;
                        mLoadMoreWrapper.notifyDataSetChanged();
                        break;
                    case 1:
                        value("film");
                        flag = 1;
                        mLoadMoreWrapper.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mHeaderAndFooterWrapper.addHeaderView(convenientBanner);
        mHeaderAndFooterWrapper.addHeaderView(tabLayout);

        mRecyclerView.setAdapter(mHeaderAndFooterWrapper);
        mHeaderAndFooterWrapper.notifyDataSetChanged();

    }

    private void load() {
        mLoadMoreWrapper = new LoadMoreWrapper(mHeaderAndFooterWrapper);
        /*mLoadMoreWrapper.setLoadMoreView(R.layout.default_loading);
        mLoadMoreWrapper.setOnLoadMoreListener(new LoadMoreWrapper.OnLoadMoreListener()
        {
            @Override
            public void onLoadMoreRequested()
            {
              new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {

                      value("tv");
                      mLoadMoreWrapper.notifyDataSetChanged();

                  }
              },2000);

            }
        });*/
        mRecyclerView.setAdapter(mLoadMoreWrapper);
        fastScroller.setRecyclerView(mRecyclerView);
        mAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                try {
                    String url;

                    if (flag == 0) {
                        url = list.get(position - 2).getString("href_url");
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);
                    } else {
                     /*   url = url_xpath + list.get(position - 2).getString("href_url");
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra("url", url);
                        startActivity(intent);*/
                        new Spider().iface2(MainActivity.this,list.get(position-2).getString("href_url"));
                    }


                    // new Spider().xpath_url(list.get(position-2).getString("href_url"),MainActivity.this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    private JellyListener jellyListener = new JellyListener() {

        @Override
        public void onToolbarExpandingStarted() {
            super.onToolbarExpandingStarted();
            ivw_main_logo.setVisibility(View.GONE);
            tvw_main_title.setVisibility(View.GONE);
        }

        @Override
        public void onToolbarCollapsingStarted() {
            super.onToolbarCollapsingStarted();
            ivw_main_logo.setVisibility(View.VISIBLE);
            tvw_main_title.setVisibility(View.VISIBLE);
        }

        @Override
        public void onCancelIconClicked() {
            if (TextUtils.isEmpty(editText.getText())) {
                jbrmain.collapse();
            } else {
                editText.getText().clear();
            }
        }

    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.ivw_main_logo:
                break;
            default:
                break;
        }
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
                Toast.makeText(MainActivity.this, "输入null", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(MainActivity.this, "输入" + editText.getText().toString(), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public void value(String str) {
        try {
            lists.clear();
            db = x.getDb(myApp.daoConfig);
            list = db.findDbModelAll(new SqlInfo("select * from " + str));
            for (int y = 0; y < 4; y++) {
                lists.add(list.get(y).getString("href_img"));
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
