/**
 * 字符串String常用方法列表：
 * （1）String.prototype.trim：删除两边空格
 * （2）String.prototype.ltrim：删除左边空格
 * （3）String.prototype.rtrim：删除右边空格
 * （4）String.prototype.isNum：验证字符串是否以"数值字符"开头
 * （5）String.prototype.isPureNum：验证字符串是否为"纯"数值字符
 * （6）String.prototype.isPureInt：验证字符串是否为"纯整数"型的字符串
 * （7）String.prototype.isPureFloat：验证字符串是否为"纯浮点数"型的字符串
 * （8）String.prototype.isDate：验证字符串是否为"日期"型的字符串
 * （9）String.prototype.isTime：验证字符串是否为"时间"型的字符串
 * （10）String.prototype.isDateTime：验证字符串是否为"日期时间"型的字符串
 * （11）String.prototype.toDateTime：将"日期时间"型的字符串转化为对应的日期Date类型
 */

/**
 * 删除两边空格：
 * 示例：var a=" abc 123 ";var b=a.trim();//b="abc 123";
 */
if(!String.prototype.trim) {
﻿  String.prototype.trim = function() {
﻿  ﻿  return this.replace(/(^\s*)|(\s*$)/g, "");
﻿  };
}

/**
 * 删除左边空格：
 * 示例：var a=" abc 123 ";var b=a.ltrim();//b="abc 123 ";
 */
if(!String.prototype.ltrim) {
﻿  String.prototype.ltrim = function() {
﻿  ﻿  return this.replace(/(^\s*)/g, "");
﻿  };
}

/**
 * 删除右边空格：
 * 示例：var a=" abc 123 ";var b=a.rtrim();//b=" abc 123";
 */
if(!String.prototype.rtrim) {
﻿  String.prototype.rtrim = function() {
﻿  ﻿  return this.replace(/(\s*$)/g, "");
﻿  };
}

/**
 * 统计字符串的长度：
 * flag：可选参数，标识是否将一个非英文字符（如中文等）按两个字符长度来统计，默认为false。
 * 示例：
 * (1)alert("abc中国人123".len());//9
 * (2)alert("abc中国人123".len(1));//12
 */
if(!String.prototype.len) {
﻿  String.prototype.len=function(flag){
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  return this.length;
﻿  ﻿  }else{
﻿  ﻿  ﻿  var s = this.replace(/[^\x00-\xff]/g,"rr");//把非英文字符替换为2个字符
﻿  ﻿  ﻿  return s.length;
﻿  ﻿  }
﻿  };
}

/**
 * 验证字符串是否以"数值字符"开头：
 * 意义：若此判断返回true，则此字符串调用parseInt或parseFolat方法时，将不会等于NaN。
 * flag：可选参数，标识是否清除前后空格，默认为false。
 * 示例：
 * (1)alert("  ".isNum());//false
 * (2)alert("  abc123  ".isNum());//false
 * (3)alert("abc123".isNum());//false
 * (4)alert(" 123 ".isNum());//true
 * (5)alert("123".isNum());//true
 * (6)alert("123abc".isNum());//true
 * (7)alert("123.58abc".isNum());//true
 */
if(!String.prototype.isNum) {
﻿  String.prototype.isNum = function(flag) {
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  return /^\d+/gi.test(this);//正则判断
﻿  ﻿  }else{
﻿  ﻿  ﻿  var s = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  ﻿  return /^\d+/gi.test(s);//正则判断
﻿  ﻿  }
﻿  };
}

/**
 * 验证字符串是否为"纯"数值字符：
 * 意义：若此判断返回true，则可知字符串全部由数字和小数点组成，同时，此字符串调用parseInt或parseFolat方法时，将不会等于NaN。
 * flag：可选参数，标识是否清除前后空格，默认为false。
 * 示例：
 * (1)alert("  ".isPureNum());//false
 * (2)alert("  abc123  ".isPureNum());//false
 * (3)alert("abc123".isPureNum());//false
 * (4)alert("123".isPureNum());//true
 * (5)alert(" 123 ".isPureNum());//true
 * (6)alert(" 12 3 ".isPureNum());//true
 * (7)alert("123abc".isPureNum());//false
 */
if(!String.prototype.isPureNum) {
﻿  String.prototype.isPureNum = function(flag) {
﻿  ﻿  if(flag){
﻿  ﻿  ﻿  return /^\d+(\.\d+)*$/gi.test(this);//正则判断
﻿  ﻿  }else{
﻿  ﻿  ﻿  var s = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  ﻿  return /^\d+(\.\d+)*$/gi.test(s);//正则判断
﻿  ﻿  }
﻿  };
}

/**
 * 验证字符串是否为"纯整数"型的字符串：
 * 意义：若此判断返回true，则可知字符串全部由数字组成。
 * flag：可选参数，标识是否清除前后空格，默认为false。
 * 示例：
 * (1)alert("  ".isPureInt());//false
 * (2)alert("  abc123  ".isPureInt());//false
 * (3)alert("123".isPureInt());//true
 * (4)alert(" 123 ".isPureInt());//true
 * (5)alert(" 12 3 ".isPureInt());//false
 * (6)alert("123abc".isPureInt());//false
 */
if(!String.prototype.isPureInt) {
﻿  String.prototype.isPureInt = function(flag) {
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  return /^\d+$/gi.test(this);//正则判断
﻿  ﻿  }else{
﻿  ﻿  ﻿  var s = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  ﻿  return /^\d+$/gi.test(s);//正则判断
﻿  ﻿  }
﻿  };
}

/**
 * 验证字符串是否为"纯浮点数"型的字符串：
 * 意义：若此判断返回true，则可知字符串全部由数字和一个小数点组成。
 * flag：可选参数，标识是否清除前后空格，默认为false。
 * 示例：
 * (1)alert("  ".isPureFloat());//false
 * (2)alert("  abc123  ".isPureFloat());//false
 * (3)alert("123".isPureFloat());//true
 * (4)alert(" 123.4 ".isPureFloat());//true
 * (5)alert(" 123 4 ".isPureFloat());//false
 * (6)alert("123abc".isPureFloat());//false
 * (7)alert("123.4abc".isPureFloat());//false
 */
if(!String.prototype.isPureFloat) {
﻿  String.prototype.isPureFloat = function(flag) {
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  return /^\d+(\.\d+)?$/gi.test(this);//正则判断
﻿  ﻿  }else{
﻿  ﻿  ﻿  var s = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  ﻿  return /^\d+(\.\d+)?$/gi.test(s);//正则判断
﻿  ﻿  }
﻿  };
}

/**
 * 验证字符串是否为"日期"型的字符串：
 * 意义：若此判断返回true，则可知字符串可以转换为日期类型
 * flag：可选参数，标识是否清除前后空格，默认为false。
 * formatStr：可选，格式字符串，可选格式有：(1)yyyy-MM-dd(默认格式)或YYYY-MM-DD (2)yyyy/MM/dd或YYYY/MM/DD (3)MM-dd-yyyy或MM-DD-YYYY (4)MM/dd/yyyy或MM/DD/YYYY
 * 示例：
 * (1)alert("  ".isDate());//false
 * (2)alert("abcd-12-23".isDate());//false
 * (3)alert("  2008-12-23  ".isDate());//false
 * (4)alert("  2008-12-23  ".isDate(1));//true
 * (5)alert("2008-12-23".isDate());//true
 * (6)alert("12-23-2008".isDate(0,"MM-dd-yyyy"));//true
 */
if(!String.prototype.isDate) {
﻿  String.prototype.isDate = function(flag, formatStr) {
﻿  ﻿  var dateStr = this;
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  dateStr = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  }
﻿  ﻿  if(!formatStr){
﻿  ﻿  ﻿  formatStr = "yyyy-MM-dd";//默认格式：yyyy-MM-dd
﻿  ﻿  }
﻿  ﻿  if(dateStr.length!=formatStr.length){
﻿  ﻿  ﻿  return false;
﻿  ﻿  }else{
﻿  ﻿  ﻿  if(formatStr=="yyyy-MM-dd"||formatStr=="YYYY-MM-DD"){
﻿  ﻿  ﻿  ﻿  var r1=/^(((((([02468][048])|([13579][26]))(00))|(\d{2}(([02468][48])|([13579][26]))))\-((((0[13578])|(1[02]))\-(([0-2][0-9])|(3[01])))|(((0[469])|(11))\-(([0-2][0-9])|(30)))|(02\-([0-2][0-9]))))|(\d{2}(([02468][1235679])|([13579][01345789]))\-((((0[13578])|(1[02]))\-(([0-2][0-9])|(3[01])))|(((0[469])|(11))\-(([0-2][0-9])|(30)))|(02\-(([0-1][0-9])|(2[0-8]))))))$/;
﻿  ﻿  ﻿  ﻿  return r1.test(dateStr);
﻿  ﻿  ﻿  }else if(formatStr=="yyyy/MM/dd"||formatStr=="YYYY/MM/DD"){
﻿  ﻿  ﻿  ﻿  var r2=/^(((((([02468][048])|([13579][26]))(00))|(\d{2}(([02468][48])|([13579][26]))))\/((((0[13578])|(1[02]))\/(([0-2][0-9])|(3[01])))|(((0[469])|(11))\/(([0-2][0-9])|(30)))|(02\/([0-2][0-9]))))|(\d{2}(([02468][1235679])|([13579][01345789]))\/((((0[13578])|(1[02]))\/(([0-2][0-9])|(3[01])))|(((0[469])|(11))\/(([0-2][0-9])|(30)))|(02\/(([0-1][0-9])|(2[0-8]))))))$/;
﻿  ﻿  ﻿  ﻿  return r2.test(dateStr);
﻿  ﻿  ﻿  }else if(formatStr=="MM-dd-yyyy"||formatStr=="MM-DD-YYYY"){
﻿  ﻿  ﻿  ﻿  var r3=/^((((((0[13578])|(1[02]))\-(([0-2][0-9])|(3[01])))|(((0[469])|(11))\-(([0-2][0-9])|(30)))|(02\-([0-2][0-9])))\-(((([02468][048])|([13579][26]))(00))|(\d{2}(([02468][48])|([13579][26])))))|(((((0[13578])|(1[02]))\-(([0-2][0-9])|(3[01])))|(((0[469])|(11))\-(([0-2][0-9])|(30)))|(02\-(([0-1][0-9])|(2[0-8])))))\-\d{2}(([02468][1235679])|([13579][01345789])))$/;
﻿  ﻿  ﻿  ﻿  return r3.test(dateStr);
﻿  ﻿  ﻿  }else if(formatStr=="MM/dd/yyyy"||formatStr=="MM/DD/YYYY"){
﻿  ﻿  ﻿  ﻿  var r4=/^((((((0[13578])|(1[02]))\/(([0-2][0-9])|(3[01])))|(((0[469])|(11))\/(([0-2][0-9])|(30)))|(02\/([0-2][0-9])))\/(((([02468][048])|([13579][26]))(00))|(\d{2}(([02468][48])|([13579][26])))))|(((((0[13578])|(1[02]))\/(([0-2][0-9])|(3[01])))|(((0[469])|(11))\/(([0-2][0-9])|(30)))|(02\/(([0-1][0-9])|(2[0-8])))))\/\d{2}(([02468][1235679])|([13579][01345789])))$/;
﻿  ﻿  ﻿  ﻿  return r4.test(dateStr);
﻿  ﻿  ﻿  }else{
﻿  ﻿  ﻿  ﻿  alert("日期格式不正确！");
﻿  ﻿  ﻿  ﻿  return false;
﻿  ﻿  ﻿  }
﻿  ﻿  }
﻿  ﻿  return false;
﻿  };
}

/**
 * 验证字符串是否为"时间"型的字符串：
 * flag：可选参数，标识是否清除前后空格，默认为false。
 * formatStr：可选，格式字符串，可选格式有：(1)hh:mm:ss(默认格式) (2)hh-mm-ss (3)hh/mm/ss
 */
if(!String.prototype.isTime) {
﻿  String.prototype.isTime = function(flag, formatStr) {
﻿  ﻿  var timeStr = this;
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  timeStr = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  }
﻿  ﻿  if(!formatStr){
﻿  ﻿  ﻿  formatStr = "hh:mm:ss";//默认格式：hh:mm:ss
﻿  ﻿  }
﻿  ﻿  if(timeStr.length!=formatStr.length){
﻿  ﻿  ﻿  return false;
﻿  ﻿  }else{
﻿  ﻿  ﻿  if(formatStr=="hh:mm:ss"){
﻿  ﻿  ﻿  ﻿  var r1=/^(([0-1][0-9])|(2[0-3]))\:([0-5][0-9])\:([0-5][0-9])$/;
﻿  ﻿  ﻿  ﻿  return r1.test(timeStr);
﻿  ﻿  ﻿  }else if(formatStr=="hh-mm-ss"){
﻿  ﻿  ﻿  ﻿  var r2=/^(([0-1][0-9])|(2[0-3]))\-([0-5][0-9])\-([0-5][0-9])$/;
﻿  ﻿  ﻿  ﻿  return r2.test(timeStr);
﻿  ﻿  ﻿  }else if(formatStr=="hh/mm/ss"){
﻿  ﻿  ﻿  ﻿  var r3=/^(([0-1][0-9])|(2[0-3]))\/([0-5][0-9])\/([0-5][0-9])$/;
﻿  ﻿  ﻿  ﻿  return r3.test(timeStr);
﻿  ﻿  ﻿  }else{
﻿  ﻿  ﻿  ﻿  alert("时间格式不正确！");
﻿  ﻿  ﻿  ﻿  return false;
﻿  ﻿  ﻿  }
﻿  ﻿  }
﻿  ﻿  return false;
﻿  };
}

/**
 * 验证字符串是否为"日期时间"型的字符串：
 * 意义：若此判断返回true，则可知字符串可以转换为Date类型的实例
 * flag：可选参数，标识是否清除前后空格，默认为false。
 */
if(!String.prototype.isDateTime) {
﻿  String.prototype.isDateTime = function(flag) {
﻿  ﻿  var dateTimeStr = this;
﻿  ﻿  if(!flag){
﻿  ﻿  ﻿  dateTimeStr = this.replace(/(^\s*)|(\s*$)/g, "");//清除前后空格
﻿  ﻿  }
﻿  ﻿  //格式：yyyy-MM-dd hh:mm:ss
﻿  ﻿  var dateTimeReg=/^(((((([02468][048])|([13579][26]))(00))|(\d{2}(([02468][48])|([13579][26]))))\-((((0[13578])|(1[02]))\-(([0-2][0-9])|(3[01])))|(((0[469])|(11))\-(([0-2][0-9])|(30)))|(02\-([0-2][0-9]))))|(\d{2}(([02468][1235679])|([13579][01345789]))\-((((0[13578])|(1[02]))\-(([0-2][0-9])|(3[01])))|(((0[469])|(11))\-(([0-2][0-9])|(30)))|(02\-(([0-1][0-9])|(2[0-8]))))))(\s{1}(([0-1][0-9])|(2[0-3]))\:([0-5][0-9])\:([0-5][0-9]))?$/;
﻿  ﻿  return dateTimeReg.test(dateTimeStr);
﻿  };
}


/**
 * 将字符串格式转化为日期Date类型：基于上面的String.prototype.isDateTime验证通过后再转化。
 * 格式：yyyy-MM-dd hh:mm:ss
 * 注意：无法转化时（即验证不通过或JS异常），返回null。
 */
if(!String.prototype.toDateTime) {
﻿  String.prototype.toDateTime = function()
﻿  {
﻿      try{
﻿          if(!(this.isDateTime())) {
﻿              return null;
﻿          }
﻿          var subdate = this.substring(0,10);
﻿          var subtime = this.substring(11,19);
﻿          var ds = subdate.split("-");
﻿          var dt = subtime.split(":");
﻿          var yyyy = parseInt(ds[0],10);
﻿          var MM = parseInt(ds[1]-1,10);
﻿          var dd = parseInt(ds[2],10);
﻿          var hh = parseInt(dt[0],10);
﻿          var mm = parseInt(dt[1],10);
﻿          var ss = parseInt(dt[2],10);
﻿          return new Date(yyyy, MM, dd, hh, mm, ss);
﻿      }catch(ee){
﻿          return null;
﻿      }
﻿  };
}
