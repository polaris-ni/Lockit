package com.lyni.lockit.ui.authenticate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.lyni.lockit.databinding.FragmentAuthenticateBinding;
import com.lyni.lockit.ui.Config;
import com.lyni.lockit.ui.LockitApplication;
import com.lyni.lockit.ui.MainActivity;
import com.lyni.lockit.utils.BiometricPromptUtil.BiometricPromptUtil;
import com.lyni.lockit.utils.ToastUtil.ToastUtil;

import org.jetbrains.annotations.NotNull;

/**
 * @author Liangyong Ni
 * description 密码验证界面
 * @date 2021/6/13
 */
public class AuthenticateFragment extends Fragment {
    FragmentAuthenticateBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthenticateBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // 检查设备是否开启了加密，是否支持指纹，并且开启了指纹解锁
        if (Config.encrypted && Config.supportFingerprint && Config.useFingerprintEncryption) {
            BiometricPromptUtil.setBiometricPrompt(this, () -> {
                Navigation.findNavController(requireView()).popBackStack();
                LockitApplication.setAuthenticated(true);
            });
            BiometricPromptUtil.show();
            binding.retry.setOnClickListener(v -> BiometricPromptUtil.show());
        } else {
            binding.retry.setVisibility(View.INVISIBLE);
        }
        // 按下返回键程序进入后台
        ((MainActivity) requireActivity()).setOnPressBackListener(mActivity -> mActivity.moveTaskToBack(true));
        // 检查设备是否开启了加密和PIN加密
        if (Config.encrypted && Config.usePasswordEncryption) {
            binding.inputPassword.setOnInputFinishListener(password -> {
                if (password.equals(Config.password)) {
                    Navigation.findNavController(requireView()).popBackStack();
                    LockitApplication.setAuthenticated(true);
                } else {
                    ToastUtil.show("密码错误");
                    binding.inputPassword.cleanPassword();
                }
            });
        } else {
            binding.inputPassword.setVisibility(View.INVISIBLE);
        }

    }
}