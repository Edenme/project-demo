package com.py.autofillinstall;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

public class InstallerHelperService extends AccessibilityService {
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) return;

        if (event.getPackageName().equals("com.vivo.secime.service")) {
            String password = (String) SharePreferencesUtils.getParam(getApplicationContext(),
                    AppConstants.KEY_PASSWORD, "");
            if (!TextUtils.isEmpty(password)) {
                fillPassword(rootNode, password);
            }
        }
        findAndClickView(rootNode);
    }


    @Override
    public void onInterrupt() {

    }

    /**
     * 自动填充密码
     */
    private void fillPassword(AccessibilityNodeInfo rootNode, String password) {
        AccessibilityNodeInfo editText = rootNode.findFocus(AccessibilityNodeInfo.FOCUS_INPUT);
        if (editText == null) return;

        if (editText.getPackageName().equals("com.bbk.account")
                && editText.getClassName().equals("android.widget.EditText")) {
            Bundle arguments = new Bundle();
            arguments.putCharSequence(AccessibilityNodeInfo
                    .ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE, password);
            editText.performAction(AccessibilityNodeInfo.ACTION_SET_TEXT, arguments);
        }
    }

    /**
     * 查找按钮并点击
     */
    private void findAndClickView(AccessibilityNodeInfo rootNode) {
        List<AccessibilityNodeInfo> nodeInfoList = new ArrayList<>();
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText(getString(R.string.confirm)));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText(getString(R.string.continue_install)));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText(getString(R.string.install)));
//        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("打开"));

        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }

}
