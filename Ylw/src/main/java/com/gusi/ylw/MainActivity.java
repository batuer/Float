package com.gusi.ylw;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.widget.Toast;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.IFloatWindow;
import com.yhao.floatwindow.MoveType;
import com.yhao.floatwindow.PermissionUtil;

/**
 * @author ylw   2019/8/5 19:42
 * @Des
 */
public class MainActivity extends AppCompatActivity {
    private static final int REQ_CODE = 831;

    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            boolean hasPermissionOnActivityResult = PermissionUtil.hasPermissionOnActivityResult(this);
            showMsg(hasPermissionOnActivityResult ? "授权成功" : "授权失败!");
        }
    }

    public void check(View view) {
        boolean hasPermission = PermissionUtil.hasPermission(this);
        showMsg(hasPermission ? "具有悬浮权限" : "没有悬浮权限");
    }

    public void show(View v) {
        boolean hasPermission = PermissionUtil.hasPermission(this);
        if (!hasPermission) {
            showMsg("请先申请权限!");
            request(null);
            return;
        }
        IFloatWindow window = FloatWindow.get("Float");
        if (window != null) {
            showMsg("已经显示!");
            window.show();
            return;
        }
        View view = LayoutInflater.from(this).inflate(R.layout.layout_float, null);
        //效果图1
        FloatWindow.with(getApplicationContext())
                .setTag("Float")
                .setView(view)
                .setWidth(getScreenWidth())
//                .setX(Screen.width, 0.8f)
//                .setY(Screen.height, 0f)
                .setMoveType(MoveType.inactive)
                .setMoveStyle(500, new BounceInterpolator())
                .setDesktopShow(true)
                .build();
        window = FloatWindow.get("Float");
        if (window != null) {
            window.show();
        }
    }

    public void request(View view) {
        if (PermissionUtil.hasPermission(this)) {
            showMsg("已具有悬浮权限!");
        } else {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            startActivityForResult(intent, REQ_CODE);
        }
    }

    private void showMsg(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public void show_30(View view) {
        boolean hasPermission = PermissionUtil.hasPermission(this);
        if (!hasPermission) {
            showMsg("请先申请权限!");
            request(null);
            return;
        }
        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                show(null);
            }
        }, 5000);
    }

    public void destroy(View view) {
        IFloatWindow window = FloatWindow.get("Float");
        if (window != null) {
            FloatWindow.destroy("Float");
        } else {
            showMsg("未发现显示的浮窗");
        }
    }

    /**
     * Return the width of screen, in pixel.
     *
     * @return the width of screen, in pixel
     */
    public int getScreenWidth() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.x;
    }

    /**
     * Return the height of screen, in pixel.
     *
     * @return the height of screen, in pixel
     */
    public int getScreenHeight() {
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) return -1;
        Point point = new Point();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealSize(point);
        } else {
            wm.getDefaultDisplay().getSize(point);
        }
        return point.y;
    }


}
