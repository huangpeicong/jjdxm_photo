package com.dou361.photo.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

/**
 * ========================================
 * <p>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p>
 * 作 者：陈冠明
 * <p>
 * 个人网站：http://www.dou361.com
 * <p>
 * 版 本：1.0
 * <p>
 * 创建日期：2016/7/25 11:40
 * <p>
 * 描 述：Intent工具类用于生成拍照、
 * 从相册选择照片，裁切照片所需的Intent
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class IntentUtils {
    private static final String TAG = IntentUtils.class.getName();

    /**
     * 获取裁切照片的Intent
     * @param targetUri 要裁切的照片
     * @param outPutUri 裁切完成的照片
     * @param cropWidth 裁切之后的宽度
     * @param cropHeight 裁切之后的高度
     * @return
     */
    public static Intent getPhotoCropIntent(Uri targetUri,Uri outPutUri, int cropWidth,int cropHeight) {
        boolean isReturnData = TUtils.isReturnData();
        Log.w(TAG, "getPhotoCropIntent:isReturnData:" + (isReturnData ? "true" : "false"));
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(targetUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", cropWidth);
        intent.putExtra("aspectY", cropHeight);
        intent.putExtra("outputX", cropWidth);
        intent.putExtra("outputY", cropHeight);
        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outPutUri);
        intent.putExtra("return-data", isReturnData);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        return intent;
    }

    /**
     * 获取拍照的Intent
     * @return
     */
    public static Intent getPhotoCaptureIntent(Uri imageUri) {
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        return intent;
    }
    /**
     * 获取选择照片的Intent
     * @return
     */
    public static Intent getPhotoPickIntent() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);//Pick an item from the data
        intent.setType("image/*");//从所有图片中进行选择
        return intent;
    }
}
