package com.gusi.ylw;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yhao.floatwindow.FloatWindow;

/**
 * @author Ylw
 * @since 2019/8/5 21:24
 */
public class FloatView extends LinearLayout implements View.OnClickListener {
    public FloatView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        findViewById(R.id.btn_destroy).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.w("Fire", "FloatView:31行:" + v.toString());
        switch (v.getId()) {
            case R.id.btn_destroy:
                FloatWindow.destroy("Float");
                break;
        }
    }
}
