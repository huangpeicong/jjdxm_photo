package com.dou361.photo.album;

import java.util.List;

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
 * 创建日期：2016/1/23 10:04
 * <p/>
 * 描 述：一个目录的相册对象
 * <p/>
 * <p/>
 * 修订历史：
 * <p/>
 * ========================================
 */
public class Album {

    /**
     * 相册图片数
     */
    public int count;
    /**
     * 相册名称
     */
    public String albumName;
    /**
     * 相册图片集合
     */
    public List<Photo> photoList;

}
