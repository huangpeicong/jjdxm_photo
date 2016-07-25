package com.dou361.jjdxm_photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.dou361.photo.TakePhoto;
import com.dou361.photo.TakePhotoImpl;
import com.dou361.photo.compress.CompressConfig;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements TakePhoto.TakeResultListener {

    @Bind(R.id.btn_CropFromGallery)
    Button btnCropFromGallery;
    @Bind(R.id.btn_Original)
    Button btnOriginal;
    @Bind(R.id.btn_CropFromTake)
    Button btnCropFromTake;
    @Bind(R.id.btn_TakeOriginal)
    Button btnTakeOriginal;
    @Bind(R.id.iv_imgShow)
    ImageView ivImgShow;
    private TakePhoto takePhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getTakePhoto().onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        getTakePhoto().onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 获取TakePhoto实例
     *
     * @return
     */
    public TakePhoto getTakePhoto() {
        if (takePhoto == null) {
            takePhoto = new TakePhotoImpl(this, this);
        }
        return takePhoto;
    }

    @Override
    public void takeCancel() {

    }

    @Override
    public void takeFail(String msg) {

    }

    @Override
    public void takeSuccess(String imagePath) {
        showImg(imagePath);
    }

    private void showImg(String imagePath) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, option);
        ivImgShow.setImageBitmap(bitmap);
    }

    @OnClick({R.id.btn_CropFromGallery, R.id.btn_Original, R.id.btn_CropFromTake, R.id.btn_TakeOriginal})
    public void onClick(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        Uri imageUri = Uri.fromFile(file);
        switch (view.getId()) {
            case R.id.btn_CropFromGallery://从相册选择照片进行裁剪
                getTakePhoto().onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true).onPicSelectCrop(imageUri);
                break;
            case R.id.btn_Original://从相册选择照片不裁切
                getTakePhoto().onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true).onPicSelectOriginal();
                break;
            case R.id.btn_CropFromTake://从相机拍取照片进行裁剪
                getTakePhoto().onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true).onPicTakeCrop(imageUri);
                break;
            case R.id.btn_TakeOriginal://从相机拍取照片不裁剪
                getTakePhoto().onEnableCompress(new CompressConfig.Builder().setMaxSize(500 * 1024).create(), true).onPicTakeOriginal(imageUri);
                break;
        }
    }
}
