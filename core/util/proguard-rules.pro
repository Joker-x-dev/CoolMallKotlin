# xxp权限混淆规则 地址: https://github.com/getActivity/XXPermissions/blob/master/library/proguard-permissions.pro
-keepclassmembers interface com.hjq.permissions.start.IStartActivityDelegate {
    <methods>;
}
-keepclassmembers interface com.hjq.permissions.fragment.IFragmentMethodNative {
    <methods>;
}
-keepclassmembers class androidx.fragment.app.Fragment {
    androidx.fragment.app.FragmentActivity getActivity();
}