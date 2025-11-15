package com.joker.coolmall.core.util.permission

import android.content.Context
import com.hjq.permissions.XXPermissions
import com.hjq.permissions.permission.PermissionLists
import com.hjq.permissions.permission.base.IPermission
import com.joker.coolmall.core.util.toast.ToastUtils

/**
 * 权限工具类，基于 XXPermissions 框架封装
 * 提供常用权限的快捷申请方法
 *
 * @author Joker.X
 */
object PermissionUtils {

    /**
     * 处理权限申请结果的公共方法
     * 统一处理权限申请成功/失败的逻辑和用户提示
     *
     * @param activity Activity 实例
     * @param grantedList 已授予的权限列表
     * @param deniedList 被拒绝的权限列表
     * @param permissionName 权限名称（用于错误提示）
     * @param callback 权限申请结果回调
     * @param permissions 申请的权限（可变参数）
     * @author Joker.X
     */
    private fun handlePermissionResult(
        activity: android.app.Activity,
        grantedList: MutableList<IPermission>,
        deniedList: MutableList<IPermission>,
        permissionName: String,
        callback: (granted: Boolean) -> Unit,
        vararg permissions: IPermission
    ) {
        val allGranted = deniedList.isEmpty()
        if (allGranted) {
            // 权限申请成功
            callback(true)
        } else {
            // 权限申请失败
            if (XXPermissions.isDoNotAskAgainPermissions(activity, deniedList)) {
                // 用户勾选了不再询问，引导用户到设置页面
                ToastUtils.showError("${permissionName}被永久拒绝，请手动授予")
                XXPermissions.startPermissionActivity(activity, *permissions)
            } else {
                ToastUtils.showError("${permissionName}获取失败")
            }
            callback(false)
        }
    }

    /**
     * 申请存储权限（读写外部存储）
     * 用法示例：PermissionUtils.requestStoragePermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestStoragePermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getReadExternalStoragePermission())
            .permission(PermissionLists.getWriteExternalStoragePermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "存储权限",
                    callback,
                    PermissionLists.getReadExternalStoragePermission(),
                    PermissionLists.getWriteExternalStoragePermission()
                )
            }
    }


    /**
     * 申请相机权限
     * 用法示例：PermissionUtils.requestCameraPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestCameraPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getCameraPermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "相机权限",
                    callback,
                    PermissionLists.getCameraPermission()
                )
            }
    }


    /**
     * 申请相册权限（读取媒体文件）
     * 用法示例：PermissionUtils.requestGalleryPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestGalleryPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getReadExternalStoragePermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "相册权限",
                    callback,
                    PermissionLists.getReadExternalStoragePermission()
                )
            }
    }


    /**
     * 申请通知权限
     * 用法示例：PermissionUtils.requestNotificationPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestNotificationPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getPostNotificationsPermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "通知权限",
                    callback,
                    PermissionLists.getPostNotificationsPermission()
                )
            }
    }


    /**
     * 申请录音权限
     * 用法示例：PermissionUtils.requestAudioPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestAudioPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getRecordAudioPermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "录音权限",
                    callback,
                    PermissionLists.getRecordAudioPermission()
                )
            }
    }


    /**
     * 申请位置权限
     * 用法示例：PermissionUtils.requestLocationPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestLocationPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getAccessFineLocationPermission())
            .permission(PermissionLists.getAccessCoarseLocationPermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "位置权限",
                    callback,
                    PermissionLists.getAccessFineLocationPermission(),
                    PermissionLists.getAccessCoarseLocationPermission()
                )
            }
    }

    /**
     * 申请相机和相册权限（组合权限）
     * 用法示例：PermissionUtils.requestCameraAndGalleryPermission(this) { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestCameraAndGalleryPermission(
        context: Context,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        XXPermissions.with(activity)
            .permission(PermissionLists.getCameraPermission())
            .permission(PermissionLists.getReadExternalStoragePermission())
            .permission(PermissionLists.getWriteExternalStoragePermission())
            .request { grantedList, deniedList ->
                handlePermissionResult(
                    activity,
                    grantedList,
                    deniedList,
                    "相机和相册权限",
                    callback,
                    PermissionLists.getCameraPermission(),
                    PermissionLists.getReadExternalStoragePermission(),
                    PermissionLists.getWriteExternalStoragePermission()
                )
            }
    }


    /**
     * 申请自定义权限组合
     * 用法示例：PermissionUtils.requestCustomPermissions(this, arrayOf(PermissionLists.getCameraPermission(), PermissionLists.getRecordAudioPermission()), "自定义权限") { granted -> ... }
     *
     * @param context Activity 或 Fragment 的上下文
     * @param permissions 权限数组
     * @param permissionName 权限名称（用于错误提示）
     * @param callback 权限申请结果回调
     * @author Joker.X
     */
    fun requestCustomPermissions(
        context: Context,
        permissions: Array<IPermission>,
        permissionName: String,
        callback: (granted: Boolean) -> Unit
    ) {
        val activity = getActivityFromContext(context)
        if (activity == null) {
            ToastUtils.showError("无法获取Activity实例，权限申请失败")
            callback(false)
            return
        }

        val permissionBuilder = XXPermissions.with(activity)
        permissions.forEach { permission ->
            permissionBuilder.permission(permission)
        }
        permissionBuilder.request { grantedList, deniedList ->
            handlePermissionResult(
                activity,
                grantedList,
                deniedList,
                permissionName,
                callback,
                *permissions
            )
        }
    }


    /**
     * 将 Context 转换为 Activity 实例的私有方法
     * 处理 Context 包装类的情况，递归查找真正的 Activity
     *
     * @param context 输入的 Context
     * @return Activity 实例，如果无法获取则返回 null
     * @author Joker.X
     */
    private fun getActivityFromContext(context: Context): android.app.Activity? {
        return when (context) {
            is android.app.Activity -> context
            is android.content.ContextWrapper -> {
                var baseContext = context.baseContext
                while (baseContext is android.content.ContextWrapper && baseContext !is android.app.Activity) {
                    baseContext = baseContext.baseContext
                }
                baseContext as? android.app.Activity
            }

            else -> null
        }
    }


    /**
     * 检查单个权限是否已授予
     * 用法示例：val hasCamera = PermissionUtils.hasPermission(this, PermissionLists.getCameraPermission())
     *
     * @param context Context
     * @param permission 权限对象
     * @return 是否已授予权限
     * @author Joker.X
     */
    fun hasPermission(context: Context, permission: IPermission): Boolean {
        return XXPermissions.isGrantedPermission(context, permission)
    }

    /**
     * 检查多个权限是否已授予
     * 用法示例：val hasPermissions = PermissionUtils.hasPermissions(this, arrayOf(PermissionLists.getCameraPermission(), PermissionLists.getRecordAudioPermission()))
     *
     * @param context Context
     * @param permissions 权限数组
     * @return 是否全部权限都已授予
     * @author Joker.X
     */
    fun hasPermissions(context: Context, permissions: Array<IPermission>): Boolean {
        return XXPermissions.isGrantedPermissions(context, permissions)
    }

    /**
     * 跳转到应用权限设置页面
     * 用法示例：PermissionUtils.openPermissionSettings(this)
     *
     * @param context Context
     * @author Joker.X
     */
    fun openPermissionSettings(context: Context) {
        XXPermissions.startPermissionActivity(context)
    }

    /**
     * 跳转到应用权限设置页面（指定权限）
     * 用法示例：PermissionUtils.openPermissionSettings(this, arrayOf(PermissionLists.getCameraPermission()))
     *
     * @param context Context
     * @param permissions 权限数组
     * @author Joker.X
     */
    fun openPermissionSettings(context: Context, permissions: Array<IPermission>) {
        XXPermissions.startPermissionActivity(context, *permissions)
    }
}