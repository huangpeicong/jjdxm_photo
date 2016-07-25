package com.dou361.photo.album;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.dou361.photo.utils.AlbumParams;
import com.dou361.photo.utils.FileUtils;
import com.dou361.photo.utils.ResourceUtils;

import java.util.ArrayList;
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
 * 创建日期：2016/1/26 10:14
 * <p>
 * 描 述：对已经选择的图片进行预览操作
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class SelectPhotoPreviewActivity extends Activity {

    private ArrayList<View> listViews = null;
    private ViewPager pager;
    private MyPageAdapter adapter;
    private int count;
    public List<Bitmap> bmp;
    public List<String> drr;
    public List<String> del = new ArrayList<String>();
    public int max;

    RelativeLayout photo_relativeLayout;
    private Context mContext;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        setContentView(ResourceUtils.getResourceIdByName(mContext, "layout", "jjdxm_photo_selectphotopreview"));

        photo_relativeLayout = (RelativeLayout) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "photo_relativeLayout"));
        photo_relativeLayout.setBackgroundColor(0x70000000);

        bmp = AlbumHelper.getHelper().bmp;
        drr = AlbumHelper.getHelper().drr;
        max = AlbumHelper.getHelper().currentSelectNum;

        Button photo_bt_exit = (Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "photo_bt_exit"));
        photo_bt_exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlbumHelper.getHelper().bmp.clear();
                AlbumHelper.getHelper().drr.clear();
                AlbumHelper.getHelper().currentSelectNum = 0;
                onBackPressed();
            }
        });
        Button photo_bt_del = (Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "photo_bt_del"));
        photo_bt_del.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (listViews.size() == 1) {
                    AlbumHelper.getHelper().bmp.clear();
                    AlbumHelper.getHelper().drr.clear();
                    AlbumHelper.getHelper().currentSelectNum = 0;
                    FileUtils.deleteDir();
                    onBackPressed();
                } else {
                    String newStr = drr.get(count).substring(
                            drr.get(count).lastIndexOf("/") + 1,
                            drr.get(count).lastIndexOf("."));
                    bmp.remove(count);
                    drr.remove(count);
                    del.add(newStr);
                    max--;
                    pager.removeAllViews();
                    listViews.remove(count);
                    adapter.setListViews(listViews);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        Button photo_bt_enter = (Button) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "photo_bt_enter"));
        photo_bt_enter.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                AlbumHelper.getHelper().bmp = bmp;
                AlbumHelper.getHelper().drr = drr;
                AlbumHelper.getHelper().currentSelectNum = max;
                for (int i = 0; i < del.size(); i++) {
                    FileUtils.delFile(del.get(i) + ".jpeg");
                }
                onBackPressed();
            }
        });

        pager = (ViewPager) findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "viewpager"));
        pager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < bmp.size(); i++) {
            initListViews(bmp.get(i));//
        }

        adapter = new MyPageAdapter(listViews);// 构造adapter
        pager.setAdapter(adapter);// 设置适配器
        Intent intent = getIntent();
        int id = intent.getIntExtra(AlbumParams.PHOTO_ID, 0);
        pager.setCurrentItem(id);
    }

    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        ImageView img = new ImageView(this);// 构造textView对象
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        listViews.add(img);// 添加view
    }

    @Override
    public void onBackPressed() {
        /** 关闭该Activity之前回传值 */
        Intent intent = new Intent();
        intent.putExtra(AlbumParams.USERACTION, 1);
        setResult(AlbumParams.SELECTPHOTOPREVIEW_RESULT, intent);
        super.onBackPressed();
    }

    private OnPageChangeListener pageChangeListener = new OnPageChangeListener() {

        public void onPageSelected(int arg0) {// 页面选择响应函数
            count = arg0;
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {// 滑动中。。。

        }

        public void onPageScrollStateChanged(int arg0) {// 滑动状态改变

        }
    };

    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;// content

        private int size;// 页数

        public MyPageAdapter(ArrayList<View> listViews) {// 构造函数
            // 初始化viewpager的时候给的一个页面
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {// 自己写的一个方法用来添加数据
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {// 返回数量
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {// 销毁view对象
            ((ViewPager) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {// 返回view对象
            try {
                ((ViewPager) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }


    }
}
