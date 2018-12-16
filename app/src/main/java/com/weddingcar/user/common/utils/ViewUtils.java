package com.weddingcar.user.common.utils;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.FrameLayout;

public class ViewUtils {
	/** 把自身从父View中移除 */
	public static void removeSelfFromParent(View view) {
		if (view != null) {
			ViewParent parent = view.getParent();
			if (parent != null && parent instanceof ViewGroup) {
				ViewGroup group = (ViewGroup) parent;
				group.removeView(view);
			}
		}
	}

	/** 请求View树重新布局，用于解决中层View有布局状态而导致上层View状态断裂 */
	public static void requestLayoutParent(View view, boolean isAll) {
		ViewParent parent = view.getParent();
		while (parent != null && parent instanceof View) {
			if (!parent.isLayoutRequested()) {
				parent.requestLayout();
				if (!isAll) {
					break;
				}
			}
			parent = parent.getParent();
		}
	}

	/** 判断触点是否落在该View上 */
	public static boolean isTouchInView(MotionEvent ev, View v) {
		int[] vLoc = new int[2];
		v.getLocationOnScreen(vLoc);
		float motionX = ev.getRawX();
		float motionY = ev.getRawY();
		return motionX >= vLoc[0] && motionX <= (vLoc[0] + v.getWidth()) && motionY >= vLoc[1] && motionY <= (vLoc[1] + v.getHeight());
	}

	/** FindViewById的泛型封装，减少强转代码 */
	public static <T extends View> T findViewById(View layout, int id) {
		return (T) layout.findViewById(id);
	}

	/** webview内存释放方法,李聪聪 */
	public static void releaseWebView(FrameLayout webContainer , WebView webView) {
		try {
			if (webContainer != null){
				webContainer.removeAllViews();
			}
			if(webView != null) {
				webView.clearHistory();
				webView.clearCache(true);
				webView.loadUrl("about:blank"); // clearView() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
				webView.freeMemory();
				/**这个方法千万不能用:这个影响销毁后的webview重新加载该webview时不能再次加载出该webview和不响应webview的其它事件*/
				//			webView.pauseTimers();
				//			webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
