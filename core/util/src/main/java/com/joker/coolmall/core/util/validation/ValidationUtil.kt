package com.joker.coolmall.core.util.validation

/**
 * 验证工具类
 * 包含常用的验证规则
 *
 * @author Joker.X
 */
object ValidationUtil {

    /**
     * 验证手机号是否有效
     *
     * @param phone 手机号
     * @return 是否有效
     * @author Joker.X
     */
    fun isValidPhone(phone: String): Boolean {
        return phone.isNotEmpty() && phone.length == 11 && phone.matches(Regex("^1[3-9]\\d{9}$"))
    }

    /**
     * 验证短信验证码是否有效
     * 验证码为4位数字
     *
     * @param code 验证码
     * @return 是否有效
     * @author Joker.X
     */
    fun isValidSmsCode(code: String): Boolean {
        return code.length == 4 && code.all { it.isDigit() }
    }

    /**
     * 验证图形验证码是否有效
     * 验证码为4位数字或字母
     *
     * @param code 验证码
     * @return 是否有效
     * @author Joker.X
     */
    fun isValidImageCode(code: String): Boolean {
        return code.length == 4 && code.all { it.isLetterOrDigit() }
    }

    /**
     * 验证邮箱地址是否有效
     *
     * @param email 邮箱地址
     * @return 是否有效
     * @author Joker.X
     */
    fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    }

    /**
     * 验证密码是否有效
     * 密码长度至少为6位
     *
     * @param password 密码
     * @return 是否有效
     * @author Joker.X
     */
    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
} 