package com.xuyang.utilcode.util

import com.xuyang.utilcode.constant.RegexConstants
import java.util.regex.Pattern

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object RegexUtils {

    /**
     * 验证手机号（简单）
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isMobileSimple(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_MOBILE_SIMPLE, input)
    }

    /**
     * 验证手机号（精确）
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isMobileExact(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_MOBILE_EXACT, input)
    }

    /**
     * 验证电话号码
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isTel(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_TEL, input)
    }

    /**
     * 验证身份证号码15位
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isIDCard15(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_ID_CARD15, input)
    }

    /**
     * 验证身份证号码18位
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isIDCard18(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_ID_CARD18, input)
    }

    /**
     * 验证邮箱
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isEmail(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_EMAIL, input)
    }

    /**
     * 验证URL
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isURL(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_URL, input)
    }

    /**
     * 验证汉字
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isZh(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_ZH, input)
    }

    /**
     * 验证用户名
     *
     * 取值范围为a-z,A-Z,0-9,"_",汉字，不能以"_"结尾,用户名必须是6-20位
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isUsername(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_USERNAME, input)
    }

    /**
     * 验证yyyy-MM-dd格式的日期校验，已考虑平闰年
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isDate(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_DATE, input)
    }

    /**
     * 验证IP地址
     *
     * @param input 待验证文本
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isIP(input: CharSequence): Boolean {
        return isMatch(RegexConstants.REGEX_IP, input)
    }

    /**
     * 验证6位密码
     *
     * @param password 待验证密码
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isPasswordSix(password: CharSequence): Boolean {
        return isMatch(
            RegexConstants.REGEX_PASSWORD_SIX,
            password
        )
    }

    /**
     * 判断是否匹配正则
     *
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return `true`: 匹配<br></br>`false`: 不匹配
     */
    @JvmStatic
    fun isMatch(regex: String, input: CharSequence?): Boolean {
        return input != null && input.length > 0 && Pattern.matches(regex, input)
    }
}