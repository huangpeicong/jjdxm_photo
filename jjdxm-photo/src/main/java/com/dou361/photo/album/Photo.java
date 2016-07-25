package com.dou361.photo.album;

import java.io.Serializable;

/**
 * ========================================
 * <p/>
 * 版 权：dou361 版权所有 （C） 2015
 * <p/>
 * 作 者：jjdxm
 * <p/>
 * 个人网站：http://www.dou361.com
 * <p/>
 * 版 本：1.0
 * <p/>
 * 创建日期：2016/1/23 10:08
 * <p/>
 * 描 述：一个图片对象
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class Photo implements Serializable {

    /**
     * 图片id
     */
    public String photoId;
    /**
     * 图片缩略图路径
     */
    public String thumbnailPath;
    /**
     * 图片路径
     */
    public String photoPath;
    /**
     * 是否选中
     */
    public boolean isSelected;
}
