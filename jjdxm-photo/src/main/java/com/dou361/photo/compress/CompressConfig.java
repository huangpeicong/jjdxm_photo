package com.dou361.photo.compress;


import java.io.Serializable;

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
 * 描 述：压缩配置类
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ========================================
 */
public class CompressConfig implements Serializable {

    /**
     * 长或宽不超过的最大像素,单位px
     */
    private int maxPixel=1200;
    /**
     * 压缩到的最大大小，单位B
     */
    private int maxSize=100*1024;

    /**
     * 是否启用像素压缩
     */
    private boolean enablePixelCompress=true;
    /**
     * 是否启用质量压缩
     */
    private boolean enableQualityCompress=true;
    public static CompressConfig getDefaultConfig(){
        return new CompressConfig();
    }
    public int getMaxPixel() {
        return maxPixel;
    }
    public CompressConfig setMaxPixel(int maxPixel) {
        this.maxPixel = maxPixel;
        return this;
    }
    public int getMaxSize() {
        return maxSize;
    }
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isEnablePixelCompress() {
        return enablePixelCompress;
    }

    public void enablePixelCompress(boolean enablePixelCompress) {
        this.enablePixelCompress = enablePixelCompress;
    }

    public boolean isEnableQualityCompress() {
        return enableQualityCompress;
    }

    public void enableQualityCompress(boolean enableQualityCompress) {
        this.enableQualityCompress = enableQualityCompress;
    }
    public static class Builder{
        private CompressConfig config;
        public Builder() {
            config=new CompressConfig();
        }
        public Builder setMaxSize(int maxSize) {
            config.setMaxSize( maxSize);
            return this;
        }
        public Builder setMaxPixel(int maxPixel) {
            config.setMaxPixel(maxPixel);
            return this;
        }
        public Builder enablePixelCompress(boolean enablePixelCompress) {
            config.enablePixelCompress(enablePixelCompress);
            return this;
        }
        public Builder enableQualityCompress(boolean enableQualityCompress) {
            config.enableQualityCompress(enableQualityCompress);
            return this;
        }
        public CompressConfig create(){
            return config;
        }
    }
}

