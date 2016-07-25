package com.dou361.photo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.dou361.photo.compress.CompressConfig;

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
 * 创建日期：2016/7/25 11:38
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
public interface TakePhoto {
    /**
     * 处理拍照或从相册选择的照片或裁剪的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    void onActivityResult(int requestCode, int resultCode, Intent data);

    /**
     * 从相册选择原生的照片（不裁切）
     */
    void onPicSelectOriginal();

    /**
     * 从相册选择照片进行裁剪(以默认大小)
     *
     * @param outPutUri 图片保存的路径
     */
    void onPicSelectCrop(Uri outPutUri);

    /**
     * 从相册选择照片进行裁剪
     *
     * @param outPutUri  图片保存的路径
     * @param cropWidth  裁切宽度
     * @param cropHeight 裁切高度
     */
    void onPicSelectCrop(Uri outPutUri, int cropWidth, int cropHeight);

    /**
     * 拍取照片不裁切
     *
     * @param outPutUri 图片保存的路径
     */
    void onPicTakeOriginal(Uri outPutUri);

    /**
     * 从相机拍取照片进行裁剪(以默认大小)
     *
     * @param outPutUri 图片保存的路径
     */
    void onPicTakeCrop(Uri outPutUri);

    /**
     * 从相机拍取照片进行裁剪
     *
     * @param outPutUri  图片保存的路径
     * @param cropWidth  裁切宽度
     * @param cropHeight 裁切高度
     */
    void onPicTakeCrop(Uri outPutUri, int cropWidth, int cropHeight);

    /**
     * 启用照片压缩
     *
     * @param config             压缩照片配置
     * @param showCompressDialog 压缩时是否显示进度对话框
     * @return
     */
    TakePhoto onEnableCompress(CompressConfig config, boolean showCompressDialog);

    /**
     * 裁剪指定uri对应的照片
     *
     * @param imageUri   uri对应的照片
     * @param outPutUri  裁切完成的照片
     * @param cropWidth  裁剪宽度
     * @param cropHeight 裁剪高度
     */
    void onCropImageUri(Uri imageUri, Uri outPutUri, int cropWidth, int cropHeight);

    void onCreate(Bundle savedInstanceState);

    void onSaveInstanceState(Bundle outState);

    /**
     * 拍照结果监听接口
     */
    interface TakeResultListener {

        /**
         * 成功回调
         */
        void takeSuccess(String imagePath);

        /**
         * 失败回调
         */
        void takeFail(String msg);

        /**
         * 取消回调
         */
        void takeCancel();
    }
}