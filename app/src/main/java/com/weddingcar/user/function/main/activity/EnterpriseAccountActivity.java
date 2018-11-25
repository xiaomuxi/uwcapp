package com.weddingcar.user.function.main.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.network.library.controller.NetworkController;
import com.weddingcar.user.R;
import com.weddingcar.user.common.base.BaseActivity;
import com.weddingcar.user.common.bean.UserInfo;
import com.weddingcar.user.common.manager.SPController;
import com.weddingcar.user.common.utils.Base64Utils;
import com.weddingcar.user.common.utils.CheckUtils;
import com.weddingcar.user.common.utils.DrawableUtils;
import com.weddingcar.user.common.utils.FileUtils;
import com.weddingcar.user.common.utils.LogUtils;
import com.weddingcar.user.common.utils.PictureUtils;
import com.weddingcar.user.common.utils.StringUtils;
import com.weddingcar.user.common.utils.UIUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EnterpriseAccountActivity extends BaseActivity implements View.OnClickListener{
    private static final int REQUEST_CODE_PICTURE_CAMERA = 1004;  // 拍照获取头像
    private static final int REQUEST_CODE_PICTURE_ALBUM = 1005;  // 从相册中选择头像

    @BindView(R.id.et_shop)
    EditText et_shop;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_contact)
    EditText et_contact;
    @BindView(R.id.iv_shop)
    ImageView iv_shop;
    @BindView(R.id.iv_license)
    ImageView iv_license;
    @BindView(R.id.btn_submit)
    Button btn_submit;

    TextView tv_take_picture;
    TextView tv_pic_album;
    TextView tv_cancel;
    private String currentSelectedImage;
    private Dialog mPicChooseDialog;
    String imgShop;
    String imgLicense;
    Uri imageUri;
    NetworkController mController;
    UserInfo userInfo;
    @Override
    protected void init() {
        super.init();
        setContentView(R.layout.activity_enterprise_account);
        ButterKnife.bind(this);
        userInfo = SPController.getInstance().getUserInfo();
    }

    @Override
    protected void initActionBar() {
        super.initActionBar();
        setActionBar(R.layout.common_top_bar);
        setTopTitleAndLeft("上传企业信息");
    }

    @Override
    protected void initView() {
        super.initView();
        mController = new NetworkController();

        iv_shop.setOnClickListener(this);
        iv_license.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private void submit() {
        if (checkInput()) {
            // TODO: upload account info
            UIUtils.showToastSafe("上传");
        }
    }

    private boolean checkInput() {
        if (StringUtils.isEmpty(et_shop.getText().toString().trim())) {
            UIUtils.showToastSafe("店名不能为空");
            return false;
        }
        if (StringUtils.isEmpty(et_contact_name.getText().toString().trim())) {
            UIUtils.showToastSafe("请输入联系人");
            return false;
        }
        if (StringUtils.isEmpty(et_contact.getText().toString().trim())) {
            UIUtils.showToastSafe("请输入联系方式");
            return false;
        }
        if (!CheckUtils.checkMobile(et_contact.getText().toString().trim(), false)) {
            UIUtils.showToastSafe("无效的手机号码！");
            return false;
        }
        if (StringUtils.isEmpty(imgShop)) {
            UIUtils.showToastSafe("请上传店铺照片");
            return false;
        }
        if (StringUtils.isEmpty(imgLicense)) {
            UIUtils.showToastSafe("请上传营业执照照片");
            return false;
        }

        return true;
    }

    /**
     * 拍照获取图片
     */
    private void takePicture(int reqCode) {
        imageUri = DrawableUtils.createImageUri(mContext, "store"); // 创建用来存储图片的uri
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, reqCode);
    }

    /**
     * 从相册取
     */
    private void pickImageFromAlbum(int reqCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, reqCode);
    }

    /**
     * 展示底部相册,拍照选择对话框
     */
    public void showCameraChoose() {
        mPicChooseDialog = new Dialog(this, R.style.ActionSheetDialogStyle);
        //填充对话框的布局
        View inflate = LayoutInflater.from(this).inflate(R.layout.layout_dialog_camera_choose, null);
        //初始化控件
        tv_take_picture = (TextView) inflate.findViewById(R.id.tv_take_picture);
        tv_pic_album = (TextView) inflate.findViewById(R.id.tv_pic_album);
        tv_cancel = (TextView) inflate.findViewById(R.id.tv_cancel);

        tv_take_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture(REQUEST_CODE_PICTURE_CAMERA);
                mPicChooseDialog.dismiss();
            }
        });
        tv_pic_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickImageFromAlbum(REQUEST_CODE_PICTURE_ALBUM);
                mPicChooseDialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicChooseDialog.dismiss();
            }
        });
        // 将布局设置给Dialog
        mPicChooseDialog.setContentView(inflate);
        // 获取当前Activity所在的窗体
        Window dialogWindow = mPicChooseDialog.getWindow();
        // 设置Dialog从窗体底部弹出
        dialogWindow.setGravity(Gravity.BOTTOM);
        // 获得窗体的属性
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.y = 20;  // 设置Dialog距离底部的距离
        // 将属性设置给窗体
        dialogWindow.setAttributes(lp);
        mPicChooseDialog.show();    // 显示对话框
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_shop:
                currentSelectedImage = "iv_shop";
                showCameraChoose();
                break;
            case R.id.iv_license:
                currentSelectedImage = "iv_license";
                showCameraChoose();
                break;
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    private void setImage(Bitmap bitmap) {
        String imageStream = Base64Utils.bitmapToBase64(bitmap);
        switch (currentSelectedImage) {
            case "iv_shop":
                imgShop = imageStream;
                iv_shop.setImageBitmap(bitmap);
                break;
            case "iv_license":
                imgLicense = imageStream;
                iv_license.setImageBitmap(bitmap);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_PICTURE_CAMERA:
                if (resultCode == RESULT_OK) {
                    LogUtils.i(TAG, "拍照获取到图片");
                    String imgPath = FileUtils.getRealFilePath(mContext, imageUri);
                    Bitmap bitmap = DrawableUtils.getBitmapFromPath(imgPath, 50, 50);
                    int degree = DrawableUtils.readPictureDegree(imgPath);// 获取图片旋转的角度
                    bitmap = DrawableUtils.rotaingImageView(degree,bitmap);// 将bitmap旋转回来
                    if (bitmap == null) {
                        UIUtils.showToastSafe("图片有问题，不支持上传！");
                        return;
                    }

                    setImage(bitmap);
                }
                break;
            case REQUEST_CODE_PICTURE_ALBUM:
                if (resultCode == RESULT_OK && data != null) {
                    LogUtils.i(TAG, "从相册获取到图片");
                    //图库
                    String imgPath = PictureUtils.getPath(mContext, data.getData());
                    LogUtils.i("==========", imgPath);
                    Bitmap bitmap = DrawableUtils.getBitmapFromPath(imgPath, 50, 50);
                    //保存图片到本地
                    if (bitmap == null) {
                        UIUtils.showToastSafe("图片有问题，不支持上传！");
                        return;
                    }
                    int degree = DrawableUtils.readPictureDegree(imgPath);// 获取图片旋转的角度
                    bitmap = DrawableUtils.rotaingImageView(degree,bitmap);// 将bitmap旋转回来

                    setImage(bitmap);
                }
                break;
        }
    }
}
