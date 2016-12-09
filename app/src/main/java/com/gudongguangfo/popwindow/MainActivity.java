package com.gudongguangfo.popwindow;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
private PopupWindow mPopupWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        Button popWindow = (Button) findViewById(R.id.popwindow);
        popWindow.setOnClickListener(this);
        Button dialog = (Button) findViewById(R.id.dialog);
        dialog.setOnClickListener(this);

    }
    //处理点击事件
    @Override
    public void onClick(View v) {
    switch (v.getId()){
        case R.id.popwindow:
            getPopWindow();
            //设置显示的位置
            mPopupWindow.showAtLocation(v, Gravity.CENTER,0,0);
            break;
        case R.id.dialog:
            showDialog();
            break;
    }
    }
    //初始化系统对话框
        private void initDialog() {
        new AlertDialog.Builder(this).setTitle("这是一个牛逼的对话框").
                setMessage("你信吗")
                .setPositiveButton("我信", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this, "不要这么容易相信别人", Toast.LENGTH_SHORT).show();

                    }
                }).setNegativeButton("不信", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "你为什么不相信我", Toast.LENGTH_SHORT).show();

            }
        }).show();
    }
    private void getPopWindow() {
    if (mPopupWindow!=null){
        mPopupWindow.dismiss();
        return;
    }
        else {
        initPopWindow();
    }
    }
    //初始化popwindow
    private void initPopWindow() {
        //获取自定义视图的View
        View popView = getLayoutInflater().inflate(R.layout.mypopwindow, null, false);
        //创建一个popwindow,并设置布局和宽高
        mPopupWindow = new PopupWindow(popView, 400,200, true);
//        mPopupWindow.setAnimationStyle(R.style.AnimationFade);
        //点击其他部分的时候就让对话框消失
        popView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mPopupWindow!=null&&mPopupWindow.isShowing()){
                    mPopupWindow.dismiss();
                    mPopupWindow=null;
                }
                return false;
            }
        });
    }
    //自定义的对话框
    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.mydialog, null);//获取自定义布局
        builder.setView(layout);
        builder.setIcon(R.mipmap.ic_launcher);//设置标题图标
        builder.setTitle(R.string.hello_world);//设置标题内容
        //builder.setMessage("");//显示自定义布局内容
        final AnalogClock clock = (AnalogClock)layout.findViewById(R.id.clock);

        Button button = (Button)layout.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                clock.setBackgroundResource(R.mipmap.ic_launcher);
                Toast.makeText(getApplication(), "button", Toast.LENGTH_SHORT).show();
            }
        });
        //确认按钮
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplication(), "ok", Toast.LENGTH_SHORT).show();
            }
        });
        //取消
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        final AlertDialog dlg = builder.create();
        dlg.show();
    }
}
