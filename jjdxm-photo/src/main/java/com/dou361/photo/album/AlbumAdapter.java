package com.dou361.photo.album;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.photo.utils.ResourceUtils;

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
 * 创建日期：2016/1/26 10:20
 * <p>
 * 描 述：相册列表适配器
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class AlbumAdapter extends BaseAdapter {


    /**
     * 日志输出标识
     */
    private final String TAG = getClass().getSimpleName();
    private final Context mContext;
    /**
     * 所在的Activity对象
     */
    private Activity act;
    /**
     * 图片缩略图集列表
     */
    private List<Album> dataList;
    /**
     * bitmap缓存帮助工具
     */
    private BitmapCache cache;
    /**
     * 图片显示加载回调
     */
    private BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
        @Override
        public void imageLoad(ImageView imageView, Bitmap bitmap,
                              Object... params) {
            if (imageView != null && bitmap != null) {
                String url = (String) params[0];
                if (url != null && url.equals((String) imageView.getTag())) {
                    ((ImageView) imageView).setImageBitmap(bitmap);
                } else {
                    Log.e(TAG, "callback, bmp1 not match");
                }
            } else {
                Log.e(TAG, "callback, bmp1 null");
            }
        }
    };

    public AlbumAdapter(Activity act, List<Album> list) {
        this.act = act;
        this.mContext = act;
        dataList = list;
        cache = new BitmapCache();
    }

    @Override
    public int getCount() {
        int count = 0;
        if (dataList != null) {
            count = dataList.size();
        }
        return count;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        Holder holder;
        if (arg1 == null) {
            holder = new Holder();
            arg1 = View.inflate(act, ResourceUtils.getResourceIdByName(mContext, "layout", "jjdxm_photo_item_album"), null);
            holder.iv = (ImageView) arg1.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "image"));
            holder.selected = (ImageView) arg1.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "isselected"));
            holder.name = (TextView) arg1.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "name"));
            holder.count = (TextView) arg1.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "count"));
            arg1.setTag(holder);
        } else {
            holder = (Holder) arg1.getTag();
        }
        Album item = dataList.get(arg0);
        holder.count.setText("" + item.count);
        holder.name.setText(item.albumName);
        holder.selected.setVisibility(View.GONE);
        if (item.photoList != null && item.photoList.size() > 0) {
            String thumbPath = item.photoList.get(0).thumbnailPath;
            String sourcePath = item.photoList.get(0).photoPath;
            holder.iv.setTag(sourcePath);
            /** 异步加载缩略图并显示 */
            cache.displayBmp(holder.iv, thumbPath, sourcePath, callback);
        } else {
            holder.iv.setImageBitmap(null);
            Log.e(TAG, "no images in bucket " + item.albumName);
        }
        return arg1;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView name;
        private TextView count;
    }

}
