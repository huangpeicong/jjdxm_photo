package com.dou361.photo.compress;

import java.io.File;

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
public class CompressImageImpl implements CompressImage{
    private CompressImageUtil compressImageUtil;
    public CompressImageImpl(CompressConfig config) {
        compressImageUtil=new CompressImageUtil(config);
    }
    @Override
    public void compress(String imagePath, CompressListener listener) {
        File file=new File(imagePath);
        if (file==null||!file.exists()||!file.isFile()){//如果文件不存在，则不做任何处理
            listener.onCompressFailed(imagePath,"要压缩的文件不存在");
            return;
        }
        compressImageUtil.compress(imagePath,listener);
    }
}
