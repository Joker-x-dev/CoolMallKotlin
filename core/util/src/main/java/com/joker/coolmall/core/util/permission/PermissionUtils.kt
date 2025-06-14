package com.joker.coolmall.core.util.permission

import android.content.Context
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.joker.coolmall.core.util.toast.ToastUtils

/**
 * 权限工具类，基于 XXPermissions 框架封装
 * 提供常用权限的快捷申请方法
 */
object PermissionUtils {

    /**
     * 申请存储权限（读写外部存储）
     * 用法示例：PermissionUtils.requestStoragePermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestStoragePermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        callback(true)
                    } else {
                        ToastUtils.showWarning("部分存储权限未授予")
                        callback(false)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("存储权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("存储权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请相机权限
     * 用法示例：PermissionUtils.requestCameraPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestCameraPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.CAMERA)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    callback(true)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("相机权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("相机权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请相册权限（读取媒体文件）
     * 用法示例：PermissionUtils.requestGalleryPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestGalleryPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.READ_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        callback(true)
                    } else {
                        ToastUtils.showWarning("部分相册权限未授予")
                        callback(false)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("相册权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("相册权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请通知权限
     * 用法示例：PermissionUtils.requestNotificationPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestNotificationPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.POST_NOTIFICATIONS)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    callback(true)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("通知权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("通知权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请录音权限
     * 用法示例：PermissionUtils.requestAudioPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestAudioPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.RECORD_AUDIO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    callback(true)
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("录音权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("录音权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请位置权限
     * 用法示例：PermissionUtils.requestLocationPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestLocationPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.ACCESS_FINE_LOCATION, Permission.ACCESS_COARSE_LOCATION)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        callback(true)
                    } else {
                        ToastUtils.showWarning("部分位置权限未授予")
                        callback(false)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("位置权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("位置权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请相机和相册权限（组合权限）
     * 用法示例：PermissionUtils.requestCameraAndGalleryPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     */
    fun requestCameraAndGalleryPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(Permission.CAMERA)
            .permission(Permission.READ_EXTERNAL_STORAGE)
            .permission(Permission.READ_MEDIA_IMAGES)
            .permission(Permission.READ_MEDIA_VIDEO)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        callback(true)
                    } else {
                        ToastUtils.showWarning("部分权限未授予")
                        callback(false)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("权限获取失败")
                    }
                    callback(false)
                }
            })
    }

    /**
     * 申请自定义权限组合
     * 用法示例：PermissionUtils.requestCustomPermissions(this, arrayOf(Permission.CAMERA, Permission.RECORD_AUDIO)) { granted, denied -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param permissions 权限数组
     * @param callback 权限申请结果回调，返回是否全部授予和被拒绝的权限列表
     */
    fun requestCustomPermissions(
        context: Context,
        permissions: Array<String>,
        callback: (granted: Boolean, deniedPermissions: List<String>) -> Unit
    ) {
        XXPermissions.with(context)
            .permission(*permissions)
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (allGranted) {
                        callback(true, emptyList())
                    } else {
                        ToastUtils.showWarning("部分权限未授予")
                        callback(false, permissions)
                    }
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        ToastUtils.showError("权限被永久拒绝，请手动授予")
                        XXPermissions.startPermissionActivity(context, *permissions.toTypedArray())
                    } else {
                        ToastUtils.showError("权限获取失败")
                    }
                    callback(false, permissions)
                }
            })
    }

    /**
     * 检查单个权限是否已授予
     * 用法示例：val hasCamera = PermissionUtils.hasPermission(this, Permission.CAMERA)
     *
     * @param context Context
     * @param permission 权限名称
     * @return 是否已授予权限
     */
    fun hasPermission(context: Context, permission: String): Boolean {
        return XXPermissions.isGrantedPermissions(context, permission)
    }

    /**
     * 检查多个权限是否已授予
     * 用法示例：val hasPermissions = PermissionUtils.hasPermissions(this, arrayOf(Permission.CAMERA, Permission.RECORD_AUDIO))
     *
     * @param context Context
     * @param permissions 权限数组
     * @return 是否全部权限都已授予
     */
    fun hasPermissions(context: Context, permissions: Array<String>): Boolean {
        return XXPermissions.isGrantedPermissions(context, *permissions)
    }

    /**
     * 跳转到应用权限设置页面
     * 用法示例：PermissionUtils.openPermissionSettings(this)
     *
     * @param context Context
     */
    fun openPermissionSettings(context: Context) {
        XXPermissions.startPermissionActivity(context)
    }

    /**
     * 跳转到应用权限设置页面（指定权限）
     * 用法示例：PermissionUtils.openPermissionSettings(this, arrayOf(Permission.CAMERA))
     *
     * @param context Context
     * @param permissions 权限数组
     */
    fun openPermissionSettings(context: Context, permissions: Array<String>) {
        XXPermissions.startPermissionActivity(context, *permissions)
    }
}