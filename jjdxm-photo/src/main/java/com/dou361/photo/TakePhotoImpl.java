package com.dou361.photo;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.dou361.photo.compress.CompressConfig;
import com.dou361.photo.compress.CompressImage;
import com.dou361.photo.compress.CompressImageImpl;
import com.dou361.photo.utils.IntentUtils;
import com.dou361.photo.utils.TConstant;
import com.dou361.photo.utils.TUtils;

/**
 * ========================================
 * <p/>
 * 版 权：dou361.com 版权所有 （C） 2015
 * <p/>
 * 作 者：陈冠明
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/7/25 11:40
 * <p/>
 * 描 述：拍照及从图库选择照片框架
 * 从相册选择照片进行裁剪，从相机拍取照片进行裁剪<br>
 * 从相册选择照片（不裁切），并获取照片的路径<br>
 * 拍取照片（不裁切），并获取照片路径
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class TakePhotoImpl implements TakePhoto {

    private Activity activity;
    private TakeResultListener listener;
    private Uri outPutUri;
    private int cropHeight;
    private int cropWidth;
    private CompressConfig compressConfig;
    /**
     * 是否显示压缩对话框
     */
    private boolean showCompressDialog;
    private ProgressDialog wailLoadDialog;

    public TakePhotoImpl(Activity activity, TakeResultListener listener) {
        this.activity = activity;
        this.listener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            cropHeight = savedInstanceState.getInt("cropHeight");
            cropWidth = savedInstanceState.getInt("cropWidth");
            showCompressDialog = savedInstanceState.getBoolean("showCompressDialog");
            outPutUri = savedInstanceState.getParcelable("outPutUri");
            compressConfig = (CompressConfig) savedInstanceState.getSerializable("compressConfig");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("cropHeight", cropHeight);
        outState.putInt("cropWidth", cropWidth);
        outState.putBoolean("showCompressDialog", showCompressDialog);
        outState.putParcelable("outPutUri", outPutUri);
        outState.putSerializable("compressConfig", compressConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TConstant.PIC_SELECT_CROP:
                if (resultCode == Activity.RESULT_OK && data != null) {//从相册选择照片并裁切
                    cropImageUri(data.getData());
                } else {
                    listener.takeCancel();
                }
                break;
            case TConstant.PIC_SELECT_ORIGINAL://从相册选择照片不裁切
                if (resultCode == Activity.RESULT_OK) {
                    String picturePath = TUtils.getFilePathWithUri(data.getData(), activity);
                    if (!TextUtils.isEmpty(picturePath)) {
                        takeSuccess(picturePath);
                    } else {
                        takeFail("文件没找到");
                    }
                } else {
                    listener.takeCancel();
                }
                break;
            case TConstant.PIC_TAKE_CROP://拍取照片,并裁切
                if (resultCode == Activity.RESULT_OK) {
                    cropImageUri(outPutUri);
                }
                break;
            case TConstant.PIC_TAKE_ORIGINAL://拍取照片
                if (resultCode == Activity.RESULT_OK) {
                    takeSuccess(TUtils.getFilePathWithUri(outPutUri, activity));
                } else {
                    listener.takeCancel();
                }
                break;
            case TConstant.PIC_CROP://裁剪照片
                if (resultCode == Activity.RESULT_OK) {
                    takeSuccess(TUtils.getFilePathWithUri(outPutUri, activity));
                } else if (resultCode == Activity.RESULT_CANCELED) {//裁切的照片没有保存
                    if (data != null) {
                        Bitmap bitmap = data.getParcelableExtra("data");//获取裁切的结果数据
                        TUtils.writeToFile(bitmap, outPutUri);//将裁切的结果写入到文件
                        takeSuccess(TUtils.getFilePathWithUri(outPutUri, activity));
                        Log.w("info", bitmap == null ? "null" : "not null");
                    } else {
                        takeFail("没有获取到裁剪结果");
                    }
                } else {
                    listener.takeCancel();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onCropImageUri(Uri imageUri, Uri outPutUri, int cropWidth, int cropHeight) {
        activity.startActivityForResult(IntentUtils.getPhotoCropIntent(imageUri, outPutUri, cropWidth, cropHeight), TConstant.PIC_CROP);
    }

    @Override
    public void onPicSelectOriginal() {
        activity.startActivityForResult(IntentUtils.getPhotoPickIntent(), TConstant.PIC_SELECT_ORIGINAL);
    }

    @Override
    public void onPicSelectCrop(Uri outPutUri) {
        onPicSelectCrop(outPutUri, TConstant.outputX, TConstant.outputY);
    }

    @Override
    public void onPicSelectCrop(Uri outPutUri, int cropWidth, int cropHeight) {
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
        this.outPutUri = outPutUri;
        activity.startActivityForResult(IntentUtils.getPhotoPickIntent(), TConstant.PIC_SELECT_CROP);
    }

    @Override
    public void onPicTakeOriginal(Uri outPutUri) {
        this.outPutUri = outPutUri;
        activity.startActivityForResult(IntentUtils.getPhotoCaptureIntent(this.outPutUri), TConstant.PIC_TAKE_ORIGINAL);
    }

    @Override
    public void onPicTakeCrop(Uri outPutUri) {
        onPicTakeCrop(outPutUri, TConstant.outputX, TConstant.outputY);
    }

    @Override
    public void onPicTakeCrop(Uri outPutUri, int cropWidth, int cropHeight) {
        this.cropWidth = cropWidth;
        this.cropHeight = cropHeight;
        this.outPutUri = outPutUri;
        activity.startActivityForResult(IntentUtils.getPhotoCaptureIntent(this.outPutUri), TConstant.PIC_TAKE_CROP);
    }

    @Override
    public TakePhoto onEnableCompress(CompressConfig config, boolean showCompressDialog) {
        this.compressConfig = config;
        this.showCompressDialog = showCompressDialog;
        return this;
    }

    private void cropImageUri(Uri imageUri) {
        onCropImageUri(imageUri, outPutUri, cropWidth, cropHeight);
    }

    private void takeSuccess(final String picturePath) {
        if (null == compressConfig) {
            listener.takeSuccess(picturePath);
        } else {
            if (showCompressDialog)
                wailLoadDialog = TUtils.showProgressDialog(activity, "正在压缩照片...");
            new CompressImageImpl(compressConfig).compress(picturePath, new CompressImage.CompressListener() {
                @Override
                public void onCompressSuccessed(String imgPath) {
                    listener.takeSuccess(imgPath);
                    if (wailLoadDialog != null && !activity.isFinishing()) wailLoadDialog.dismiss();
                }

                @Override
                public void onCompressFailed(String imagePath, String msg) {
                    listener.takeFail(String.format("图片压缩失败:%s,picturePath:%s", msg, picturePath));
                    if (wailLoadDialog != null && !activity.isFinishing()) wailLoadDialog.dismiss();
                }
            });
        }
    }

    private void takeFail(String message) {
        listener.takeFail(message);
    }
}