package com.dou361.photo.compress;

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
 * 描 述：压缩照片2.0
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public interface CompressImage {
    void compress(String imagePath, CompressListener listener);

    /**
     * 压缩结果监听器
     */
    interface CompressListener {
        /**
         * 压缩成功
         *
         * @param imgPath 压缩图片的路径
         */
        void onCompressSuccessed(String imgPath);

        /**
         * 压缩失败
         * @param imgPath 压缩失败的图片
         * @param msg 失败的原因
         */
        void onCompressFailed(String imgPath, String msg);
    }
}
