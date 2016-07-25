package com.dou361.photo.album;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dou361.photo.utils.ResourceUtils;

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
 * 创建日期：2016/1/26 11:17
 * <p>
 * 描 述：单个相册的适配器
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class AlbumDetailAdapter extends BaseAdapter {

    private final Context mContext;
    private TextCallback textcallback = null;
    private final String TAG = getClass().getSimpleName();
    private Activity act;
    private List<Photo> dataList;
    private Map<String, String> map;
    private BitmapCache cache;
    private Handler mHandler;
    private int selectTotal = 0;
    BitmapCache.ImageCallback callback = new BitmapCache.ImageCallback() {
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

    /**
     * 取消所以选择
     */
    public void cancelSelect() {
        map.clear();
        selectTotal = 0;
        for (int i = 0; i < dataList.size(); i++) {
            dataList.get(i).isSelected = false;
        }
        AlbumHelper.getHelper().clearSelect();
    }

    public static interface TextCallback {
        public void onListen(int count);
    }

    public void setTextCallback(TextCallback listener) {
        textcallback = listener;
    }

    public AlbumDetailAdapter(Activity act, List<Photo> list, Map<String, String> map, Handler mHandler) {
        this.act = act;
        this.mContext = act;
        dataList = list;
        this.map = map;
        cache = new BitmapCache();
        this.mHandler = mHandler;
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
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Holder {
        private ImageView iv;
        private ImageView selected;
        private TextView text;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Holder holder;

        if (convertView == null) {
            holder = new Holder();
            convertView = View.inflate(act, ResourceUtils.getResourceIdByName(mContext, "layout", "jjdxm_photo_item_albumdetail"), null);
            holder.iv = (ImageView) convertView.findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "image"));
            holder.selected = (ImageView) convertView
                    .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "isselected"));
            holder.text = (TextView) convertView
                    .findViewById(ResourceUtils.getResourceIdByName(mContext, "id", "item_image_grid_text"));
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        final Photo item = dataList.get(position);

        holder.iv.setTag(item.photoPath);
        cache.displayBmp(holder.iv, item.thumbnailPath, item.photoPath,
                callback);
        if (item.isSelected) {
            holder.selected.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "jjdxm_photo_select"));
            holder.text.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "jjdxm_photo_bgd_relatly_line"));
        } else {
            holder.selected.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "jjdxm_photo_unselect"));
            holder.text.setBackgroundColor(0x00000000);
        }
        holder.selected.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                String path = dataList.get(position).photoPath;

                if ((AlbumHelper.getHelper().drr.size() + selectTotal) < 9) {
                    item.isSelected = !item.isSelected;
                    if (item.isSelected) {
                        holder.selected
                                .setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "jjdxm_photo_select"));
                        holder.text.setBackgroundResource(ResourceUtils.getResourceIdByName(mContext, "drawable", "jjdxm_photo_bgd_relatly_line"));
                        selectTotal++;
                        if (textcallback != null)
                            textcallback.onListen(selectTotal);
                        map.put(path, path);

                    } else if (!item.isSelected) {
                        holder.selected.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "jjdxm_photo_unselect"));
                        holder.text.setBackgroundColor(0x00000000);
                        selectTotal--;
                        if (textcallback != null)
                            textcallback.onListen(selectTotal);
                        map.remove(path);
                    }
                } else if ((AlbumHelper.getHelper().drr.size() + selectTotal) >= 9) {
                    if (item.isSelected == true) {
                        item.isSelected = !item.isSelected;
                        holder.selected.setImageResource(ResourceUtils.getResourceIdByName(mContext, "mipmap", "jjdxm_photo_unselect"));
                        selectTotal--;
                        map.remove(path);

                    } else {
                        Message message = Message.obtain(mHandler, 0);
                        message.sendToTarget();
                    }
                }
            }

        });

        return convertView;
    }
}
