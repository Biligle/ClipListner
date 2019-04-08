package com.bs.finance.manager;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import com.bs.finance.utils.StringUtils;
import java.util.List;

public class ClipManage {

    private ClipManageListener listener;
    private ClipboardManager mClipboardManager;
//    private ClipboardManager.OnPrimaryClipChangedListener mOnPrimaryClipChangedListener;
    private Activity activity;

    public ClipManage(Activity activity, ClipManageListener listener) {
        this.activity = activity;
        this.listener = listener;
        registerClipEvents(activity);
    }

    /**
     * 注册剪切板复制、剪切事件监听
     */
    private void registerClipEvents(Activity activity) {
        mClipboardManager = (ClipboardManager) activity.getSystemService(Context.CLIPBOARD_SERVICE);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ClipData data = mClipboardManager.getPrimaryClip();
        if (isForeground(activity) && null != data) {
            String content = data.getItemAt(0).getText().toString();
            if (null != listener) listener.onResult(content);
            if (null!= content) {
                ClipData clip = ClipData.newPlainText("", "");
                mClipboardManager.setPrimaryClip(clip);
            }
            return;
        }
//        mOnPrimaryClipChangedListener = new ClipboardManager.OnPrimaryClipChangedListener() {
//            @Override
//            public void onPrimaryClipChanged() {
//                ClipData data = mClipboardManager.getPrimaryClip();
//                if (null == data) return;
//                if (mClipboardManager.hasPrimaryClip() && data.getItemCount() > 0) {
//                    // 获取复制、剪切的文本内容
//                    String content = data.getItemAt(0).getText().toString();
//                    if (null != listener) listener.onResult(content);
//                }
//            }
//        };
//        mClipboardManager.addPrimaryClipChangedListener(mOnPrimaryClipChangedListener);
    }

    /**
     * 注销监听
     */
    public void onDestroy(){
        if (mClipboardManager != null/* && mOnPrimaryClipChangedListener != null*/) {
            mClipboardManager = null;
//            mClipboardManager.removePrimaryClipChangedListener(mOnPrimaryClipChangedListener);
        }
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param activity 要判断的Activity
     * @return 是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || StringUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }


    /**
     * 监听剪切板接口
     */
    public interface ClipManageListener {
        void onResult(String content);
    }
}
