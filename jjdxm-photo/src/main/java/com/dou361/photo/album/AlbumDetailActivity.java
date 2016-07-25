package com.dou361.photo.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dou361.photo.utils.AlbumParams;
import com.dou361.photo.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ========================================
 * <p>
 * 版 权：dou361 版权所有 （C） 2015
 * <p>
 * 作 者：jjdxm
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/1/26 10:46
 * <p>
 * 描 述：单个相册
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class AlbumDetailActivity extends Activity implements OnClickListener {
    /**
     * 返回
     */
    LinearLayout ll_back;
    /**
     * 返回
     */
    TextView tv_title;
    /**
     * 标题
     */
    TextView tv_center;
    /**
     * 编辑
     */
    LinearLayout ll_edit;
    /**
     * 编辑
     */
    TextView tv_edit;
    /**
     * 完成
     */
    Button btn_complie;
    /**
     * 相册view
     */
    GridView gridView;
    /**
     * 相片缩略图集合
     */
    private List<Photo> dataList;
    /**
     * 当前选择的集合
     */
    private Map<String, String> map = new HashMap<String, String>();
    /**
     * 相片缩略图适配器
     */
    private AlbumDetailAdapter adapter;
    /**
     * 用户操作动作0为返回，1为完成
     */
    private int action;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Toast.makeText(AlbumDetailActivity.this, "最多选择9张图片", Toast.LENGTH_LONG).show();
                    break;

                default:
                    break;
            }
        }
    };

    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = this;

        setContentView(ResourceUtils.getResourceIdByName(mContext, "layout", "jjdxm_photo_albumdetail"));
        Intent intent = getIntent();
        String albumName = intent.getStringExtra(AlbumParams.ALBUMNAME);
        ll_back = (LinearLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ll_back"));
        ll_edit = (LinearLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ll_edit"));
        tv_title = (TextView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_title"));
        tv_center = (TextView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_center"));
        tv_edit = (TextView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_edit"));
        btn_complie = (Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "btn_complie"));
        gridView = (GridView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "gridview"));
        ll_back.setOnClickListener(this);
        ll_edit.setOnClickListener(this);
        tv_title.setText("返回");
        tv_center.setText(albumName);
        tv_edit.setText("取消");
        dataList = (List<Photo>) getIntent().getSerializableExtra(
                AlbumParams.EXTRA_IMAGE_LIST);
        gridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter = new AlbumDetailAdapter(this, dataList, map,
                mHandler);
        gridView.setAdapter(adapter);
        /** 监听当前选择图片数量 */
        adapter.setTextCallback(new AlbumDetailAdapter.TextCallback() {
            public void onListen(int count) {
                btn_complie.setText("完成" + "(" + count + ")");
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
//                Intent intent = new Intent(AlbumDetailActivity.this, PhotoPreviewActivity.class);
//                intent.putExtra(AlbumParams.PHOTO_ID, position);
//                startActivityForResult(intent, AlbumParams.ALBUMDETAIL_REQUESTCODE);
            }

        });
        btn_complie.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {
                ArrayList<String> list = new ArrayList<String>();
                Collection<String> c = map.values();
                Iterator<String> it = c.iterator();
                for (; it.hasNext(); ) {
                    list.add(it.next());
                }
                for (int i = 0; i < list.size(); i++) {
                    if (AlbumHelper.getHelper().drr.size() < 9) {
                        AlbumHelper.getHelper().drr.add(list.get(i));
                    }
                }
                action = 1;
                onBackPressed();
            }

        });
    }


    @Override
    public void onBackPressed() {
        /** 关闭该Activity之前回传值 */
        Intent intent = new Intent();
        intent.putExtra(AlbumParams.USERACTION, action);
        setResult(AlbumParams.ALBUMDETAIL_RESULT, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AlbumParams.ALBUMDETAIL_REQUESTCODE && resultCode == AlbumParams.PHOTOPREVIEW_RESULT) {
            int action = data.getIntExtra(AlbumParams.USERACTION, 0);
            if (action == 1) {
                onBackPressed();
            } else {
                //TODO 刷新
            }
        } else if (requestCode == AlbumParams.ALBUMDETAIL_REQUESTCODE && resultCode == AlbumParams.SELECTPHOTOPREVIEW_RESULT) {
            int action = data.getIntExtra(AlbumParams.USERACTION, 0);
            if (action == 1) {
                onBackPressed();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "ll_back")) {
            action = 0;
            onBackPressed();

        } else if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "ll_edit")) {
            if (adapter != null) {
                adapter.cancelSelect();
            }
            btn_complie.setText("完成" + "(" + 0 + ")");
            adapter.notifyDataSetChanged();
        }
    }
}
