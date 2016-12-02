
# [jjdxm_photo][project] #
## Introduction ##

本项目是基于takephoto项目进行改造的


## Features ##

## Screenshots ##

<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/screenshots/icon01.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/screenshots/icon02.png" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/screenshots/icon01.gif" width="300"> 
<img src="https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/screenshots/icon02.gif" width="300">
 
## download ##

[demo apk下载][downapk]

photo or grab via Maven:

	<dependency>
	  <groupId>com.dou361.photo</groupId>
	  <artifactId>jjdxm-photo</artifactId>
	  <version>x.x.x</version>
	</dependency>

or Gradle:

	compile 'com.dou361.photo:jjdxm-photo:x.x.x'

历史版本

	compile 'com.dou361.photo:jjdxm-photo:1.0.2'
	compile 'com.dou361.photo:jjdxm-photo:1.0.1'
	compile 'com.dou361.photo:jjdxm-photo:1.0.0'


jjdxm-photo requires at minimum Java 9 or Android 2.3.

## Get Started ##

### 有以下两种方式： ###

#### 方式一：通过继承的方式 ####
1. 继承TakePhotoActivity、TakePhotoFragmentActivity、TakePhotoFragment三者之一。
2. 通过getTakePhoto()获取TakePhoto实例进行相关操作。
3. 重写以下方法获取结果

void takeSuccess(String imagePath);  
void takeFail(String msg);
void takeCancel();
此方式使用简单，满足的大部分的使用需求，具体使用详见simple。如果通过继承的方式无法满足实际项目的使用，可以通过下面介绍的方式。

#### 方式二：通过组装的方式 ####
1. 获取TakePhoto实例TakePhoto takePhoto=new TakePhotoImpl(getActivity(),this);
2. 在 onCreate,onActivityResult,onSaveInstanceState方法中调用TakePhoto对用的方法。
3. 调用TakePhoto实例进行相关操作。
4. 在TakeResultListener相关方法中获取结果。

### 关于压缩照片 ###

你可以选择是否对照片进行压缩处理。

 /**
  * 启用照片压缩
  * @param config 压缩照片配置
  * @param showCompressDialog 压缩时是否显示进度对话框
  * @return 
  */
 TakePhoto onEnableCompress(CompressConfig config,boolean showCompressDialog);
eg：
getTakePhoto().onEnableCompress(new CompressConfig.Builder().setMaxSize(50*1024).setMaxPixel(800).create(),true).onPicSelectCrop(imageUri);
如果你启用了照片压缩，TakePhoto会使用CompressImage对照片进行压缩处理，CompressImage目前支持对照片的尺寸以及照片的质量进行压缩。默认情况下，CompressImage开启了尺寸与质量双重压缩， 你可以通过CompressConfig.Builder对照片压缩后的尺寸以及质量进行相关设置。如果你想改变压缩的方式可以通过CompressConfig.Builder进行相关设置。

关于兼容性问题

TakePhoto是基于Android官方标准API编写的，适配了目前市场上主流的Rom。如果你在使用过程中发现了适配问题，可以提交Issues。
1. 为适配部分手机拍照时会回收Activity,TakePhoto在onSaveInstanceState与 onCreate做了相应的恢复处理。
2. 为适配部分手机拍照或从相册选择照片时屏幕方向会发生转变,从而导致拍照失败的问题，可以在AndroidManifest.xml中对使用了TakePhoto的Activity添加android:configChanges="orientation|keyboardHidden|screenSize"配置。
eg:

	<activity
	    android:name=".MainActivity"
	    android:screenOrientation="portrait"
	    android:configChanges="orientation|keyboardHidden|screenSize"
	    android:label="@string/app_name" >
	    <intent-filter>
	        <action android:name="android.intent.action.MAIN" />
	        <category android:name="android.intent.category.LAUNCHER" />
	    </intent-filter>
	</activity>

## More Actions ##

## ChangeLog ##

2016.12.02 1.0.2版本发布，修复部分机型截小图时带黑边和截png图透明内容变黑的问题

## About Author ##

#### 个人网站:[http://www.dou361.com][web] ####
#### GitHub:[jjdxmashl][github] ####
#### QQ:316988670 ####
#### 交流QQ群:548545202 ####


## License ##

    Copyright (C) dou361, The Framework Open Source Project
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
    
     	http://www.apache.org/licenses/LICENSE-2.0
    
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

## (Frequently Asked Questions)FAQ ##
## Bugs Report and Help ##

If you find any bug when using project, please report [here][issues]. Thanks for helping us building a better one.




[web]:http://www.dou361.com
[github]:https://github.com/jjdxmashl/
[project]:https://github.com/jjdxmashl/jjdxm_photo/
[issues]:https://github.com/jjdxmashl/jjdxm_photo/issues/new
[downapk]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/apk/app-debug.apk
[lastaar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/release/jjdxm-photo-1.0.0.aar
[lastjar]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/release/jjdxm-photo-1.0.0.jar
[icon01]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/screenshots/icon01.png
[icon02]:https://raw.githubusercontent.com/jjdxmashl/jjdxm_photo/master/screenshots/icon02.png