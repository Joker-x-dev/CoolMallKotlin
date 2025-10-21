package com.joker.coolmall.feature.order.model

import android.text.TextUtils

/**
 * 支付宝支付结果解析
 *
 * @param rawResult 原始支付结果Map
 * @author Joker.X
 */
class Alipay(rawResult: Map<String, String>) {

    /**
     * @return the resultStatus
     */
    var resultStatus: String? = null

    /**
     * @return the result
     */
    var result: String? = null

    /**
     * @return the memo
     */
    var memo: String? = null

    init {
        if (rawResult.isNotEmpty()) {
            for (key in rawResult.keys) {
                if (TextUtils.equals(key, "resultStatus")) {
                    resultStatus = rawResult[key]
                } else if (TextUtils.equals(key, "result")) {
                    result = rawResult[key]
                } else if (TextUtils.equals(key, "memo")) {
                    memo = rawResult[key]
                }
            }
        }
    }

    override fun toString(): String {
        return ("resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}")
    }

    companion object {
        const val RESULT_STATUS_SUCCESS = "9000"
        const val RESULT_STATUS_CANCEL = "6001"
    }
} 