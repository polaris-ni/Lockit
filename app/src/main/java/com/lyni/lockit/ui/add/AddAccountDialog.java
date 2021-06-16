package com.lyni.lockit.ui.add;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.lyni.lockit.R;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

/**
 * @author Liangyong Ni
 * description 显示添加账户的对话框
 * @date 2021/6/15
 */
public class AddAccountDialog {
    private final AlertDialog dialog;
    private final EditText uid, username, notes, password;
    private final CheckBox isMainAccount;

    public AddAccountDialog(Context context, OnEnsureListener onEnsureListener) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(context).inflate(R.layout.dialog_account_add, null, false);
        uid = view.findViewById(R.id.dialog_add_account_uid);
        username = view.findViewById(R.id.dialog_add_account_username);
        password = view.findViewById(R.id.dialog_add_account_password);
        notes = view.findViewById(R.id.dialog_add_account_notes);
        isMainAccount = view.findViewById(R.id.dialog_add_account_is_main_account);
        dialog = new AlertDialog.Builder(context)
                .setTitle("新建账户")
                .setView(view)
                .setPositiveButton("确定", (dialog, which) -> {
                    if (TextUtils.isEmpty(uid.getText()) && TextUtils.isEmpty(username.getText())) {
                        ToastUtil.show("用户id和用户名至少有一个不为空");
                    } else if (TextUtils.isEmpty(password.getText())) {
                        ToastUtil.show("密码不为空");
                    } else {
                        onEnsureListener.onEnsure(uid, username, password, notes, isMainAccount);
                    }
                })
                .setNegativeButton("取消", (dialog, which) -> {

                }).create();
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public interface OnEnsureListener {
        /**
         * 点击确认按钮后执行
         *
         * @param uid           id输入框
         * @param username      用户名输入框
         * @param password      密码输入框
         * @param notes         备注输入框
         * @param isMainAccount 复选框
         */
        void onEnsure(EditText uid, EditText username, EditText password, EditText notes, CheckBox isMainAccount);
    }
}