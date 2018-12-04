package com.example.liu.mycam;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.iv_preView);

        Intent intent = getIntent();
        if (intent != null) {
            String dir = intent.getStringExtra("output");

        }
        int a = 1;
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivity(intent);
//        Intent intent = new Intent("android.intent.action.GET_CONTENT");
//        intent.setType("image/*");
//        startActivityForResult(intent, 2);
    }


    public void on1Clicked(View view) {
        choosePhoto();
    }

    public void on2Clicked(View view) {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, 2);
    }

    public void on3Clicked(View view) {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //权限还没有授予，需要在这里写申请权限的代码
            // 第二个参数是一个字符串数组，里面是你需要申请的权限 可以设置申请多个权限
            // 最后一个参数是标志你这次申请的权限，该常量在onRequestPermissionsResult中使用到
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    54456);
        }else { //权限已经被授予，在这里直接写要执行的相应方法即可
             takePhoto();
        }

    }

    private void choosePhoto() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
        startActivityForResult(intentToPickPic, 2);
    }


    private void takePhoto() {

        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        String f = System.currentTimeMillis()+".jpg";
//        Uri fileUri = Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory("").getPath()+f));
//        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); //指定图片存放位置，指定后，在onActivityResult里得到的Data将为null
        startActivityForResult(openCameraIntent, 1234);

        // 跳转到系统的拍照界面
        /*Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
        // File.separator为系统自带的分隔符 是一个固定的常量
        String mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
        *//*imageUri = Uri.fromFile(new File(mTempPhotoPath));*//*
        imageUri = FileProvider.getUriForFile(MainActivity.this,
                MainActivity.this.getApplicationContext().getPackageName() + ".my.provider",
                new File(mTempPhotoPath));
        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto, 1);*/
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            Intent intent = data;
        }
//        fragment4ImageView0 = findViewById(R.id.fragment4ImageView0);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case 1: {
                    // 获得图片
                    try {
                        //该uri就是照片文件夹对应的uri
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        // 给相应的ImageView设置图片 未裁剪
                        imageView.setImageBitmap(bit);
//                        fragment4ImageView0.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    // 获取图片
                    try {
                        //该uri是上一个Activity返回的
                        imageUri = data.getData();
                        if (imageUri != null) {
                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                            Log.i("bit", String.valueOf(bit));
                            imageView.setImageBitmap(bit);
//                            fragment4ImageView0.setImageBitmap(bit);
                        }
//                        Intent intent = new Intent();
//                        intent.setData(imageUri);
                        setResult(RESULT_OK, data);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 3:
                    try {
                        //该uri就是照片文件夹对应的uri
                        Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        // 给相应的ImageView设置图片 未裁剪
                        imageView.setImageBitmap(bit);
//                        fragment4ImageView0.setImageBitmap(bit);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    setResult(RESULT_OK, data);
                    break;
                case 33:
                    try {
                        //该uri是上一个Activity返回的
                        imageUri = data.getData();
                        if (imageUri != null) {
                            Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
//                            Log.i("bit", String.valueOf(bit));
                            imageView.setImageBitmap(bit);
//                            fragment4ImageView0.setImageBitmap(bit);
                        }
//                        Intent intent = new Intent();
//                        intent.setData(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                takePhoto();
            }
        } else {
            // Permission Denied
            Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                choosePhoto();
            } else { // Permission Denied
                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}




