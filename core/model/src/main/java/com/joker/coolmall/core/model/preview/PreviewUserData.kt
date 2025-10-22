package com.joker.coolmall.core.model.preview

import com.joker.coolmall.core.model.entity.User

/**
 * 预览用户数据
 *
 * @author Joker.X
 */
val previewUserList = listOf(
    User(
        id = 1L,
        unionid = "u123456",
        avatarUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/avatar/default_avatar.png",
        nickName = "酷猫",
        phone = "13800138000",
        gender = 1,
        status = 1,
        loginType = "2",
        createTime = "2024-01-01 12:00:00",
        updateTime = "2024-01-01 12:00:00"
    ),
    User(
        id = 2L,
        unionid = "u234567",
        avatarUrl = "https://game-box-1315168471.cos.ap-guangzhou.myqcloud.com/app/avatar/default_avatar.png",
        nickName = "酷狗",
        phone = "13900139000",
        gender = 2,
        status = 1,
        loginType = "0",
        createTime = "2024-01-02 12:00:00",
        updateTime = "2024-01-02 12:00:00"
    )
)

/**
 * 单个预览用户数据
 *
 * @author Joker.X
 */
val previewUser = previewUserList.first() 