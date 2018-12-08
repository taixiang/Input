package com.input;

import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {


    private LinearLayout container;
    private EditText etName;
    private EditText etPwd;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        container = findViewById(R.id.container);
        etName = findViewById(R.id.et_name);
        etPwd = findViewById(R.id.et_pwd);
        btn = findViewById(R.id.btn);
        //监听键盘弹出/隐藏
        container.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                //getWindowVisibleDisplayFrame 获取当前窗口可视区域大小
                getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
                int screenHeight = getWindow().getDecorView().getHeight();
                //键盘弹出时，可视区域大小改变，屏幕高度 - 窗口可视区域高度 = 键盘弹出高度
                int softHeight = screenHeight - rect.bottom;
                /**
                 * 上移的距离 = 键盘的高度 - 按钮距离屏幕底部的高度(如果手机高度很大，上移的距离会是负数，界面将不会上移)
                 * 按钮距离屏幕底部的高度是用屏幕高度 - 按钮底部距离父布局顶部的高度
                 * 注意这里 btn.getBottom() 是按钮底部距离父布局顶部的高度，这里也就是距离最外层布局顶部高度
                 */
                int scrollDistance = softHeight - (screenHeight - btn.getBottom());
                if (scrollDistance > 0) {
                    //具体移动距离可自行调整
                    container.scrollTo(0, scrollDistance + 60);
                } else {
                    //键盘隐藏，页面复位
                    container.scrollTo(0, 0);
                }
            }
        });

    }
}
