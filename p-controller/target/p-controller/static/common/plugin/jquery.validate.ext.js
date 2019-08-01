/**
 * 验证扩展
 * Created by hardy on 2017/6/22.
 */

// jQuery.validator增加时间验证
jQuery.validator.addMethod("isTime", function (value, element) {
    if (this.optional(element)) return true;
    try {
        var strArray = value.split(" ");
        var strDate = strArray[0].split("-");
        var strTime = strArray[1].split(":");
        var a = new Date(strDate[0], (strDate[1] - parseInt(1)), strDate[2], strTime[0], strTime[1], strTime[2]);
        return a.getFullYear() > 0 && a.getMonth() > 0 && a.getDay() > 0;
    } catch (e) { }
    return false;
}, "请输入正确的时间！");

// jQuery.validator增加账号验证
jQuery.validator.addMethod("isAccount", function (value, element) {
    return this.optional(element) || (/^\w+$/.test(value));
}, "请输入英文、数字、下划线");

// 判断浮点型(只能包含数字、小数点等字符)
jQuery.validator.addMethod("isFloat", function (value, element) {
    return this.optional(element) || (/^[-\+]?\d+(\.\d+)?$/.test(value));
}, "只能包含数字、小数点等字符");

// jQuery.validator增加匹配Integer
jQuery.validator.addMethod("isInteger", function (value, element) {
    return this.optional(element) || ((/^[-\+]?\d+$/.test(value) && parseInt(value)>=0));
}, "只能输入大于零的整数");

//判断数值类型，包括整数和浮点数
jQuery.validator.addMethod("isNumber", function (value, element) {
    return this.optional(element) || ((/^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value)));
}, "只能输入整数和浮点数");

//判断数字 (只能输入[0-9]数字)
jQuery.validator.addMethod("isDigits", function (value, element) {
    return this.optional(element) || (/^\d+$/.test(value));
}, "只能输入[0-9]数字");

//判断中文字符 (只能包含中文字符)
jQuery.validator.addMethod("isChineseChar", function (value, element) {
    return this.optional(element) || (/^[\u0391-\uFFE5]+$/.test(value));
}, "只能包含中文字符");

//判断英文字符
jQuery.validator.addMethod("isEnglish", function (value, element) {
    return this.optional(element) || (/^[A-Za-z]+$/.test(value));
}, "只能英文字符");

// 手机号码验证
jQuery.validator.addMethod("isMobile", function (value, element) {
    return this.optional(element) || (/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));
}, "手机号码格式有误");

// 电话号码验证
jQuery.validator.addMethod("isPhone", function (value, element) {
    return this.optional(element) || (/^(\d{3,4}-?)?\d{7,9}$/g.test(value));
}, "电话码格式有误");

//联系电话(手机/电话皆可)验证
jQuery.validator.addMethod("isTel", function (value, element) {
    return this.optional(element) || (/^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value) || (/^(\d{3,4}-?)?\d{7,9}$/g.test(value) && length==11));
}, "手机或电话格式有误");

//匹配qq
jQuery.validator.addMethod("isQq", function (value, element) {
    return this.optional(element) || (/^[1-9]\d{4,12}$/.test(value));
}, "qq格式有误");

// 邮政编码验证
jQuery.validator.addMethod("isZipCode", function (value, element) {
    return this.optional(element) || (/^[0-9]{6}$/.test(value));
}, "邮政编码格式有误");

//匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。
jQuery.validator.addMethod("isPwd", function (value, element) {
    return this.optional(element) || (/^[a-zA-Z]\\w{6,12}$/.test(value));
}, "密码以字母开头，长度在6-12之间，只能包含字符、数字和下划线");

// IP地址验证
jQuery.validator.addMethod("isIp", function (value, element) {
    return this.optional(element) || (/^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value));
}, "IP地址格式不正确");

//email
jQuery.validator.addMethod("isEmail", function (value, element) {
    return this.optional(element) || (/^[a-z0-9]+@([a-z0-9]+\.)+[a-z]{2,4}$/i.test(value));
}, "Email地址格式不正确");

