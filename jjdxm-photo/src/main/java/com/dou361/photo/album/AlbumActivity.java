package com.dou361.photo.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dou361.photo.utils.AlbumParams;
import com.dou361.photo.utils.ResourceUtils;

import java.io.Serializable;
import java.util.List;


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
 * 创建日期：2016/1/21 15:02
 * <p>
 * 描 述：相册首页
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class AlbumActivity extends Activity implements View.OnClickListener {


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
     * 相册缩略图列表
     */
    private List<Album> dataList;
    /**
     * 相册列表view
     */
    GridView gridView;
    /**
     * 自定义的适配器
     */
    private AlbumAdapter adapter;
    /**
     * 相册帮助工具
     */
    private AlbumHelper helper;
    /**
     * 当前Activity对象
     */
    private Activity activity;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        mContext = this;
        setContentView(ResourceUtils.getResourceIdByName(mContext, "layout", "jjdxm_photo_album"));
        ll_back = (LinearLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "ll_back"));
        tv_title = (TextView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_title"));
        tv_center = (TextView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "tv_center"));
        gridView = (GridView) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "gridview"));
        ll_back.setOnClickListener(this);
        tv_title.setText("返回");
        tv_center.setText("相册");
        activity = this;
        helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        dataList = helper.getImagesBucketList(false);
        adapter = new AlbumAdapter(activity, dataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                /**
                 * 通知适配器，绑定的数据发生了改变，应当刷新视图
                 */
                Intent intent = new Intent(activity,
                        AlbumDetailActivity.class);
                intent.putExtra(AlbumParams.ALBUMNAME, dataList.get(position).albumName);
                intent.putExtra(AlbumParams.EXTRA_IMAGE_LIST,
                        (Serializable) dataList.get(position).photoList);
                startActivityForResult(intent, AlbumParams.ALBUM_REQUESTCODE);
            }

        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == ResourceUtils.getResourceIdByName(mContext, "id", "ll_back")) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        /** 关闭该Activity之前回传值 */
        Intent intent = new Intent();
        setResult(AlbumParams.ALBUM_RESULT, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AlbumParams.ALBUM_REQUESTCODE && resultCode == AlbumParams.ALBUMDETAIL_RESULT) {
            int action = data.getIntExtra(AlbumParams.USERACTION, 0);
            if (action == 1) {
                onBackPressed();
            }
        }
    }
}
