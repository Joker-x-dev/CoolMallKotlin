package com.joker.coolmall.core.ui.htmltext.util

/**
 * 验证URL，确保URL格式正确
 */
internal fun validateUrl(
    url: String,
    domain: String? = null
): String? {
    if (domain != null && url.startsWith("/")) return domain + url.replace(" ", "%20")
    if (!url.startsWith("http")) return null
    return url.replace(" ", "%20")
} 