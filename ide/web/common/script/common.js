
document.write( '<script type="text/javascript" src="/ide/common/script/contextPath.jsp"></script>' );

if(navigator.userAgent.indexOf("Firefox")>=0){document.write('<script type="text/javascript" language="javascript" src="/ide/common/script/foxcommon.js"></script>');}

/**
 * 兼容各种版本的对话框显示
 */

function showDialog(URL, args, width, height, resizable, scrollbar, status) {
	var version = isVersion();
	var opts = "";
	//对width和height非法数据处理
	if(isNaN(width)){
		if(width.indexOf("px")>=0){
			width = parseInt(width.substr(0,width.indexOf("px")));
		}
		if(isNaN(width)){
			width = parseInt(width);
		}
	}
	if(isNaN(height)){
		if(height.indexOf("px")>=0){
			height = parseInt(height.substr(0,height.indexOf("px")));
		}
		if(isNaN(height)){
			height = parseInt(height);
		}
	}
	if(resizable==null)
		resizable = "yes";
	if(scrollbar == null)
		scrollbar = "no";	
	if (version == 'ie6') {
		opts += "dialogWidth:" + (width) + "px";
		opts += ";dialogHeight:" + (height + 40) + "px";
	}else if(navigator.userAgent.indexOf("Firefox")>=0){
			//模仿实现center:yes功能 add by lkl
			opts += "dialogTop:"+((window.screen.availHeight-height)/2)+"px;dialogLeft:"+((window.screen.availWidth-(width))/2)+"px";
			opts += ";dialogWidth:"+(parseInt(width))+"px"; 
			opts += ";dialogHeight:"+(height)+"px";
	}else{
		opts += "dialogWidth:" + (width) + "px";
		opts += ";dialogHeight:" + (height) + "px";
	}
	opts += ";resizable:" + resizable;
	opts += ";scroolbar:" + scrollbar;
	opts += ";status:" + status;
	opts += ";help:no";
	return window.showModalDialog(URL, args, opts);
};

/**
 * 对Array对象的扩展
 */
if (!Array.prototype.contains) {
	Array.prototype.contains = function(obj) {
		var len = this.length;
		for ( var i = 0; i < len; i++) {
			if (this[i] === obj) {
				return true;
			}
		}
		return false;
	};
};
/**
 * 增加删除方法
 */
if (!Array.prototype.remove) {
	Array.prototype.remove = function(obj) {
		for ( var i = 0, n = 0; i < this.length; i++) {
			if (this[i] != obj) {
				this[n++] = this[i];
			}
		}
		this.length -= 1;
		return obj;
	};
};
/**
 * 对字符串的扩展
 */

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
};
String.prototype.lTrim = function() {
	return this.replace(/(^[\\s]*)/g, "");
};
String.prototype.rTrim = function() {
	return this.replace(/([\\s]*$)/g, "");
};

function LTrim(str) {
	return str.replace(/^[ \t\n\r]+/g, "");
}

function RTrim(str) {
	return str.replace(/[ \t\n\r]+$/g, "");
}

function Trim(str) {
	return RTrim(LTrim(str));
}



function numberToString(number) {
	return String(number);
}
function stringToNumber(str) {
	return Number(str);
}
/**
 * 用来判断浏览器版本号
 */

window["MzBrowser"] = {};
( function() {
	if (MzBrowser.platform)
		return;
	var ua = window.navigator.userAgent;
	MzBrowser.platform = window.navigator.platform;

	MzBrowser.firefox = ua.indexOf("Firefox") > 0;
	MzBrowser.opera = typeof (window.opera) == "object";
	MzBrowser.ie = !MzBrowser.opera && ua.indexOf("MSIE") > 0;
	MzBrowser.mozilla = window.navigator.product == "Gecko";
	MzBrowser.netscape = window.navigator.vendor == "Netscape";
	MzBrowser.safari = ua.indexOf("Safari") > -1;

	if (MzBrowser.firefox)
		var re = /Firefox(\s|\/)(\d+(\.\d+)?)/;
	else if (MzBrowser.ie)
		var re = /MSIE( )(\d+(\.\d+)?)/;
	else if (MzBrowser.opera)
		var re = /Opera(\s|\/)(\d+(\.\d+)?)/;
	else if (MzBrowser.netscape)
		var re = /Netscape(\s|\/)(\d+(\.\d+)?)/;
	else if (MzBrowser.safari)
		var re = /Version(\/)(\d+(\.\d+)?)/;
	else if (MzBrowser.mozilla)
		var re = /rv(\:)(\d+(\.\d+)?)/;

	if ("undefined" != typeof (re) && re.test(ua))
		MzBrowser.version = parseFloat(RegExp.$2);
})();

/**
 * 用来判断浏览器是ie还是firefox
 */
function isVersion() {
	var version;
	if (MzBrowser.ie) {
		version = 'ie';
	}
	if (MzBrowser.firefox) {
		version = 'firefox';
	}
	version += MzBrowser.version;
	return version;
};
// ///////////////////////////////////////////////////////////////////
// /function for limit data input
// /////////////////////////////////////////////////////////////////////
/**
 * 检查输入项是否为小键盘的1-9
 */
function checkInteger() {
	if (event.keyCode < 45 || event.keyCode > 57)
		event.returnValue = false;
	else
		event.returnValue = true;
}

/**
 * 检查输入的页码是否在可选择的范围内
 */
function checkPageNum(oInput, maxPage) {
	var oValue = oInput.value;
	if (oValue != null)
		oValue = oValue.trim();
	if ((oValue == null) || (oValue.length == 0) || isNaN(parseInt(oValue))
			|| (parseInt(oValue) < 1) || (parseInt(oValue) > maxPage)) {
		alert('请输入1到' + maxPage + '之间的数字');
		oInput.focus();
	}

}
/**
 * 跳转到指定页面
 */
function gotoPage(oInput, url) {
	var oValue = oInput.value;
	window.location = url + (parseInt(oValue) - 1);
}

// /////////////////////////////////////////////////////////////////
// functions for cookies
// ////////////////////////////////////////////////////////////////
/**
 * Sets a Cookie with the given name and value.
 * 
 * name Name of the cookie value Value of the cookie [expires] Expiration date
 * of the cookie (default: end of current session) [path] Path where the cookie
 * is valid (default: path of calling document) [domain] Domain where the cookie
 * is valid (default: domain of calling document) [secure] Boolean value
 * indicating if the cookie transmission requires a secure transmission
 */
/**
 * 设置Cookie
 */
function setCookie(name, value, expires, path, domain, secure) {
	var tmp = name
			+ "="
			+ encodeURIComponent(value)
			+ ((expires) ? "; expires=" + expires.toGMTString()
					: "; expires=Fri, 31 Dec 2050 23:59:59 GMT")
			+ ((path) ? "; path=" + path : "; path=/")
			+ ((domain) ? "; domain=" + domain : "")
			+ ((secure) ? "; secure" : "");
	document.cookie = tmp;
}

/**
 * Gets the value of the specified cookie.
 * 
 * name Name of the desired cookie.
 * 
 * Returns a string containing value of specified cookie, or null if cookie does
 * not exist.
 */
/**
 * 获取Cookie
 */
function getCookie(name) {
	var dc = document.cookie;
	var prefix = name + "=";
	var begin = dc.indexOf("; " + prefix);
	if (begin == -1) {
		begin = dc.indexOf(prefix);
		if (begin != 0)
			return '';
	} else {
		begin += 2;
	}
	var end = document.cookie.indexOf(";", begin);
	if (end == -1) {
		end = dc.length;
	}
	var value = dc.substring(begin + prefix.length, end);
	var reg = /\"/g;
	value = value.replace(reg, "");
	return unescape(value);
}

/**
 * Deletes the specified cookie.
 * 
 * name name of the cookie [path] path of the cookie (must be same as path used
 * to create cookie) [domain] domain of the cookie (must be same as domain used
 * to create cookie)
 */
/**
 * 删除Cookie
 */
function deleteCookie(name, path, domain) {
	if (getCookie(name)) {
		var tmp = name + "=" + ((path) ? "; path=" + path : "; path=/")
				+ ((domain) ? "; domain=" + domain : "")
				+ "; expires=Fri, 31 Dec 1999 23:59:59 GMT";
		document.cookie = tmp
	}
}
/**
 * 获取Cookie的值
 */
function getCookieVal(offset) {
	var endstr = document.cookie.indexOf(";", offset);
	if (endstr == -1)
		endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr));
}

// /////////////////////////////////////////////////////////////////
// functions for radio
// ////////////////////////////////////////////////////////////////
/**
 * 获取Radio的值
 */
function GetRadioValue(Object) {
	if (Object.length == null) { // 只有1项
		if (Object.checked == true) {
			return Object.value;
		} else {
			return null;
		}
	} else {

		for (iIndex = 0; iIndex < Object.length; iIndex++) {
			if (Object[iIndex].checked == true) {
				return Object[iIndex].value;
			}
		}
		return null;
	}
}
/**
 * 获取Radio对象
 */
function GetRadioObject(Object) {
	if (Object.length == null) { // 只有1项
		if (Object.checked == true) {
			return Object;
		} else {
			return null;
		}
	} else {
		for (iIndex = 0; iIndex < Object.length; iIndex++) {
			if (Object[iIndex].checked == true) {
				return Object[iIndex];
			}
		}
		return null;
	}
}
/**
 * 设置Radio的值
 */
function setRadioValue(Object, value) {
	for (iIndex = 0; iIndex < Object.length; iIndex++) {
		if (Object[iIndex].value == value) {
			Object[iIndex].checked = true;
		}
	}
}
/**
 * 获取Radio值的所在位置
 */
function GetRadioIndex(Object, strKey) {
	if (Object.length == null) { // 只有1项
		if (Object.checked == true) {
			return 0;
		} else {
			return -1;
		}
	} else {
		for (iIndex = 0; iIndex < Object.length; iIndex++) {
			if (Object[iIndex].value == strKey) {
				return iIndex;
			}
		}
		return -1;
	}
}
/**
 * 用弹出窗口显示Radio的所有值
 */
function PrintRadioValue(Object) {
	if (Object.length == null) { // 只有1项
		if (Object.checked == true) {
			alert(Object.value);
		}
	} else {
		for (iIndex = 0; iIndex < Object.length; iIndex++) {
			alert(Object[iIndex].value);
		}
	}
}
/**
 * 构建URL地址中的传递参数
 */
function buildURL(name, obj) {
	var ret = '';
	if (obj != null) { // 有效对象
		if (obj.length == null) { // 只有1项
			if (obj.checked) {
				ret = "&" + name + "=" + obj.value;
			}
		} else { // 多项
			for ( var i = 0; i < obj.length; i++) {
				if (obj[i].checked) {
					ret += "&" + name + "=" + obj[i].value;
				}
			}
		}
	}
	return ret;
}
// /////////////////////////////////////////////////////////////////
// functions for number
// ////////////////////////////////////////////////////////////////

/**
 * 求一个最接近它的整数，它的值小于或等于这个浮点数
 */
function cutNumber(number) {
	return Math.floor(number);
}
/**
 * 四舍五入注间大小
 */
function roundNumber(number) {
	return Math.round(number);
}

// /////////////////////////////////////////////////////////////////
// iframe redirect
// ////////////////////////////////////////////////////////////////
/**
 * 不清楚
 */
function MM_goToURL() { // v3.0
	var i, args = MM_goToURL.arguments;
	document.MM_returnValue = false;
	for (i = 0; i < (args.length - 1); i += 2)
		eval(args[i] + ".location='" + args[i + 1] + "'");
}

// /////////////////////////////////////////////////////////////////
// functions for checkBox
// ////////////////////////////////////////////////////////////////
// 判断是否有被checked的项。有，返回true；无，返回false。
function checkNull(field) {
	if (field != null) { // 有效对象
		if (field.length == null) { // 只有1项
			if (field.checked) {
				return true;
			} else {
				return false;
			}
		} else { // 多项
			for ( var i = 0; i < field.length; i++) {
				if (field[i].checked) {
					return true;
				}
			}
		}
	}
	return false;
}

/**
 * 对checkbox进行全选
 */
function selectCheckBoxAll(field, flag) {
	if (field == null)
		return;
	if (flag == null)
		flag = true;

	if (field.length == null) {
		field.checked = flag;
	} else {
		for (i = 0; i < field.length; i++) {
			field[i].checked = flag;
		}
	}
}

/**
 * 返回组件的值，用split进行分隔，split为空则以逗号分隔
 */
function getValue(field, split) {
	if (field == null)
		return;
	var returnvalue = "";
	spilt = split == null ? "," : split;
	if (field.length == null && field.checked) {
		returnvalue = field.value + split;
	} else {
		for (i = 0; i < field.length; i++) {
			if (field[i].checked)
				returnvalue += field[i].value + split;
		}
	}
	return returnvalue;
}

/**
 * 返回组件值的个数
 */
function getValueLength(field) {
	if (field == null)
		return;
	var returnvalue = 0;

	if (field.length == null && field.checked) {
		returnvalue = 1;
	} else {
		for (i = 0; i < field.length; i++) {
			if (field[i].checked)
				returnvalue = returnvalue + 1;
		}
	}
	return returnvalue;
}

// /////////////////////////////////////////////////////////////////·
// functions for button
// ////////////////////////////////////////////////////////////////
/**
 * 设置按钮不可点击
 */
function disableButton() {
	var buttons = document.body.getElementsByTagName("button");
	for (i = 0; i < buttons.length; i++) {
		buttons[i].disabled = "true";
	}
}
/**
 * 设置按钮可点击
 */
function enableButton() {
	var buttons = document.body.getElementsByTagName("button");
	for (i = 0; i < buttons.length; i++) {
		buttons[i].disabled = "";
	}
}
// /////////////////////////////////////////////////////////////////
// functions for open window
// ////////////////////////////////////////////////////////////////
/**
 * 打开一个不可滚动的窗口
 */
function openWin(destJsp, width, height) {
	width = width == null ? '590' : width;
	height = height == null ? '304' : height;
	window.open(destJsp, 'OpenClose', 'toolbar=no,menubar=no,width=' + width
			+ ',height=' + height + ',scrollbars=no');
}
/**
 * 打开一个可以滚动的窗口
 */
function openScrollWin(destJsp, width, height) {
	width = width == null ? '590' : width;
	height = height == null ? '304' : height;
	window.open(destJsp, 'OpenClose', 'toolbar=no,menubar=no,width=' + width
			+ ',height=' + height + ',scrollbars=yes');
}

// /////////////////////////////////////////////////////////////////
// functions for validate
// ////////////////////////////////////////////////////////////////
/**
 * 检查所选择的文件是否是图片文件，即gif,jpg文件
 */
function myValidateImageFile(form, object) {
	if (object == null)
		return true;
	if (object.value == "") {
		return true;
	} else {
		if (/(gif|GIF|jpg|JPG)$/i.test(object.value)) {
			return true;
		} else {
			alert('请选择gif,jpg文件！');
			object.focus();
			return false;
		}
	}
}

/**
 * 检查所选择的文件是否是压缩文件，即rar,zip文件
 */
function myValidateAttFile(form, object) {
	if (object == null)
		return true;
	if (object.value == "") {
		return true;
	} else {
		if (/(rar|zip|RAR|ZIP)$/i.test(object.value)) {
			return true;
		} else {
			alert('附件上传请选择rar或者zip格式的文件！');
			object.focus();
			return false;
		}
	}
}

/**
 * 检查所选择的文件是否是视频文件，即wmv文件
 */
function myValidateWMVFile(form, object) {
	if (object == null)
		return true;
	if (object.value == "") {
		return true;
	} else {
		if (/(wmv|WMV)$/i.test(object.value)) {
			return true;
		} else {
			alert('视频上传请选择wmv格式的文件！');
			object.focus();
			return false;
		}
	}
}

/**
 * 检查所选择的文件是否是word文件，即doc文件
 */
function myValidateWordFile(form, object) {
	if (object == null) {
		alert('附件不能为空！');
		object.focus();
		return false;
	}
	if (object.value == "") {
		return true;
	} else {
		if (/(doc||DOC)$/i.test(object.value)) {
			return true;
		} else {
			alert('附件上传请选择doc或者DOC格式的文件！');
			object.focus();
			return false;
		}
	}
}

/**
 * 检查此项是否为空
 */
function myValidateRequired(form, object) {
	var value = object.value;
	if (value != null && value != "") {
		return true;
	} else {
		alert('此项为必填信息！');
		object.focus();
		return false;
	}
}

// /////////////////////////////////////////////////////////////////
// functions for 汉字转拼音
// ////////////////////////////////////////////////////////////////

var strGB = "啊阿埃挨哎唉哀皑癌蔼矮艾碍爱隘鞍氨安俺按暗岸胺案肮昂盎凹敖熬翱袄傲奥懊澳芭捌扒叭吧笆八疤巴拔跋靶把耙坝霸罢爸白柏百摆佰败拜稗斑班搬扳般颁板版扮拌伴瓣半办绊邦帮梆榜膀绑棒磅蚌镑傍谤苞胞包褒剥薄雹保堡饱宝抱报暴豹鲍爆杯碑悲卑北辈背贝钡倍狈备惫焙被奔苯本笨崩绷甭泵蹦迸逼鼻比鄙笔彼碧蓖蔽毕毙毖币庇痹闭敝弊必辟壁臂避陛鞭边编贬扁便变卞辨辩辫遍标彪膘表鳖憋别瘪彬斌濒滨宾摈兵冰柄丙秉饼炳病并玻菠播拨钵波博勃搏铂箔伯帛舶脖膊渤泊驳捕卜哺补埠不布步簿部怖擦猜裁材才财睬踩采彩菜蔡餐参蚕残惭惨灿苍舱仓沧藏操糙槽曹草厕策侧册测层蹭插叉茬茶查碴搽察岔差诧拆柴豺搀掺蝉馋谗缠铲产阐颤昌猖场尝常长偿肠厂敞畅唱倡超抄钞朝嘲潮巢吵炒车扯撤掣彻澈郴臣辰尘晨忱沉陈趁衬撑称城橙成呈乘程惩澄诚承逞骋秤吃痴持匙池迟弛驰耻齿侈尺赤翅斥炽充冲虫崇宠抽酬畴踌稠愁筹仇绸瞅丑臭初出橱厨躇锄雏滁除楚础储矗搐触处揣川穿椽传船喘串疮窗幢床闯创吹炊捶锤垂春椿醇唇淳纯蠢戳绰疵茨磁雌辞慈瓷词此刺赐次聪葱囱匆从丛凑粗醋簇促蹿篡窜摧崔催脆瘁粹淬翠村存寸磋撮搓措挫错搭达答瘩打大呆歹傣戴带殆代贷袋待逮怠耽担丹单郸掸胆旦氮但惮淡诞弹蛋当挡党荡档刀捣蹈倒岛祷导到稻悼道盗德得的蹬灯登等瞪凳邓堤低滴迪敌笛狄涤翟嫡抵底地蒂第帝弟递缔颠掂滇碘点典靛垫电佃甸店惦奠淀殿碉叼雕凋刁掉吊钓调跌爹碟蝶迭谍叠丁盯叮钉顶鼎锭定订丢东冬董懂动栋侗恫冻洞兜抖斗陡豆逗痘都督毒犊独读堵睹赌杜镀肚度渡妒端短锻段断缎堆兑队对墩吨蹲敦顿囤钝盾遁掇哆多夺垛躲朵跺舵剁惰堕蛾峨鹅俄额讹娥恶厄扼遏鄂饿恩而儿耳尔饵洱二贰发罚筏伐乏阀法珐藩帆番翻樊矾钒繁凡烦反返范贩犯饭泛坊芳方肪房防妨仿访纺放菲非啡飞肥匪诽吠肺废沸费芬酚吩氛分纷坟焚汾粉奋份忿愤粪丰封枫蜂峰锋风疯烽逢冯缝讽奉凤佛否夫敷肤孵扶拂辐幅氟符伏俘服浮涪福袱弗甫抚辅俯釜斧脯腑府腐赴副覆赋复傅付阜父腹负富讣附妇缚咐噶嘎该改概钙盖溉干甘杆柑竿肝赶感秆敢赣冈刚钢缸肛纲岗港杠篙皋高膏羔糕搞镐稿告哥歌搁戈鸽胳疙割革葛格蛤阁隔铬个各给根跟耕更庚羹埂耿梗工攻功恭龚供躬公宫弓巩汞拱贡共钩勾沟苟狗垢构购够辜菇咕箍估沽孤姑鼓古蛊骨谷股故顾固雇刮瓜剐寡挂褂乖拐怪棺关官冠观管馆罐惯灌贯光广逛瑰规圭硅归龟闺轨鬼诡癸桂柜跪贵刽辊滚棍锅郭国果裹过哈骸孩海氦亥害骇酣憨邯韩含涵寒函喊罕翰撼捍旱憾悍焊汗汉夯杭航壕嚎豪毫郝好耗号浩呵喝荷菏核禾和何合盒貉阂河涸赫褐鹤贺嘿黑痕很狠恨哼亨横衡恒轰哄烘虹鸿洪宏弘红喉侯猴吼厚候后呼乎忽瑚壶葫胡蝴狐糊湖弧虎唬护互沪户花哗华猾滑画划化话槐徊怀淮坏欢环桓还缓换患唤痪豢焕涣宦幻荒慌黄磺蝗簧皇凰惶煌晃幌恍谎灰挥辉徽恢蛔回毁悔慧卉惠晦贿秽会烩汇讳诲绘荤昏婚魂浑混豁活伙火获或惑霍货祸击圾基机畸稽积箕肌饥迹激讥鸡姬绩缉吉极棘辑籍集及急疾汲即嫉级挤几脊己蓟技冀季伎祭剂悸济寄寂计记既忌际妓继纪嘉枷夹佳家加荚颊贾甲钾假稼价架驾嫁歼监坚尖笺间煎兼肩艰奸缄茧检柬碱硷拣捡简俭剪减荐槛鉴践贱见键箭件健舰剑饯渐溅涧建僵姜将浆江疆蒋桨奖讲匠酱降蕉椒礁焦胶交郊浇骄娇嚼搅铰矫侥脚狡角饺缴绞剿教酵轿较叫窖揭接皆秸街阶截劫节桔杰捷睫竭洁结解姐戒藉芥界借介疥诫届巾筋斤金今津襟紧锦仅谨进靳晋禁近烬浸尽劲荆兢茎睛晶鲸京惊精粳经井警景颈静境敬镜径痉靖竟竞净炯窘揪究纠玖韭久灸九酒厩救旧臼舅咎就疚鞠拘狙疽居驹菊局咀矩举沮聚拒据巨具距踞锯俱句惧炬剧捐鹃娟倦眷卷绢撅攫抉掘倔爵觉决诀绝均菌钧军君峻俊竣浚郡骏喀咖卡咯开揩楷凯慨刊堪勘坎砍看康慷糠扛抗亢炕考拷烤靠坷苛柯棵磕颗科壳咳可渴克刻客课肯啃垦恳坑吭空恐孔控抠口扣寇枯哭窟苦酷库裤夸垮挎跨胯块筷侩快宽款匡筐狂框矿眶旷况亏盔岿窥葵奎魁傀馈愧溃坤昆捆困括扩廓阔垃拉喇蜡腊辣啦莱来赖蓝婪栏拦篮阑兰澜谰揽览懒缆烂滥琅榔狼廊郎朗浪捞劳牢老佬姥酪烙涝勒乐雷镭蕾磊累儡垒擂肋类泪棱楞冷厘梨犁黎篱狸离漓理李里鲤礼莉荔吏栗丽厉励砾历利傈例俐痢立粒沥隶力璃哩俩联莲连镰廉怜涟帘敛脸链恋炼练粮凉梁粱良两辆量晾亮谅撩聊僚疗燎寥辽潦了撂镣廖料列裂烈劣猎琳林磷霖临邻鳞淋凛赁吝拎玲菱零龄铃伶羚凌灵陵岭领另令溜琉榴硫馏留刘瘤流柳六龙聋咙笼窿隆垄拢陇楼娄搂篓漏陋芦卢颅庐炉掳卤虏鲁麓碌露路赂鹿潞禄录陆戮驴吕铝侣旅履屡缕虑氯律率滤绿峦挛孪滦卵乱掠略抡轮伦仑沦纶论萝螺罗逻锣箩骡裸落洛骆络妈麻玛码蚂马骂嘛吗埋买麦卖迈脉瞒馒蛮满蔓曼慢漫谩芒茫盲氓忙莽猫茅锚毛矛铆卯茂冒帽貌贸么玫枚梅酶霉煤没眉媒镁每美昧寐妹媚门闷们萌蒙檬盟锰猛梦孟眯醚靡糜迷谜弥米秘觅泌蜜密幂棉眠绵冕免勉娩缅面苗描瞄藐秒渺庙妙蔑灭民抿皿敏悯闽明螟鸣铭名命谬摸摹蘑模膜磨摩魔抹末莫墨默沫漠寞陌谋牟某拇牡亩姆母墓暮幕募慕木目睦牧穆拿哪呐钠那娜纳氖乃奶耐奈南男难囊挠脑恼闹淖呢馁内嫩能妮霓倪泥尼拟你匿腻逆溺蔫拈年碾撵捻念娘酿鸟尿捏聂孽啮镊镍涅您柠狞凝宁拧泞牛扭钮纽脓浓农弄奴努怒女暖虐疟挪懦糯诺哦欧鸥殴藕呕偶沤啪趴爬帕怕琶拍排牌徘湃派攀潘盘磐盼畔判叛乓庞旁耪胖抛咆刨炮袍跑泡呸胚培裴赔陪配佩沛喷盆砰抨烹澎彭蓬棚硼篷膨朋鹏捧碰坯砒霹批披劈琵毗啤脾疲皮匹痞僻屁譬篇偏片骗飘漂瓢票撇瞥拼频贫品聘乒坪苹萍平凭瓶评屏坡泼颇婆破魄迫粕剖扑铺仆莆葡菩蒲埔朴圃普浦谱曝瀑期欺栖戚妻七凄漆柒沏其棋奇歧畦崎脐齐旗祈祁骑起岂乞企启契砌器气迄弃汽泣讫掐恰洽牵扦钎铅千迁签仟谦乾黔钱钳前潜遣浅谴堑嵌欠歉枪呛腔羌墙蔷强抢橇锹敲悄桥瞧乔侨巧鞘撬翘峭俏窍切茄且怯窃钦侵亲秦琴勤芹擒禽寝沁青轻氢倾卿清擎晴氰情顷请庆琼穷秋丘邱球求囚酋泅趋区蛆曲躯屈驱渠取娶龋趣去圈颧权醛泉全痊拳犬券劝缺炔瘸却鹊榷确雀裙群然燃冉染瓤壤攘嚷让饶扰绕惹热壬仁人忍韧任认刃妊纫扔仍日戎茸蓉荣融熔溶容绒冗揉柔肉茹蠕儒孺如辱乳汝入褥软阮蕊瑞锐闰润若弱撒洒萨腮鳃塞赛三叁伞散桑嗓丧搔骚扫嫂瑟色涩森僧莎砂杀刹沙纱傻啥煞筛晒珊苫杉山删煽衫闪陕擅赡膳善汕扇缮墒伤商赏晌上尚裳梢捎稍烧芍勺韶少哨邵绍奢赊蛇舌舍赦摄射慑涉社设砷申呻伸身深娠绅神沈审婶甚肾慎渗声生甥牲升绳省盛剩胜圣师失狮施湿诗尸虱十石拾时什食蚀实识史矢使屎驶始式示士世柿事拭誓逝势是嗜噬适仕侍释饰氏市恃室视试收手首守寿授售受瘦兽蔬枢梳殊抒输叔舒淑疏书赎孰熟薯暑曙署蜀黍鼠属术述树束戍竖墅庶数漱恕刷耍摔衰甩帅栓拴霜双爽谁水睡税吮瞬顺舜说硕朔烁斯撕嘶思私司丝死肆寺嗣四伺似饲巳松耸怂颂送宋讼诵搜艘擞嗽苏酥俗素速粟僳塑溯宿诉肃酸蒜算虽隋随绥髓碎岁穗遂隧祟孙损笋蓑梭唆缩琐索锁所塌他它她塔獭挞蹋踏胎苔抬台泰酞太态汰坍摊贪瘫滩坛檀痰潭谭谈坦毯袒碳探叹炭汤塘搪堂棠膛唐糖倘躺淌趟烫掏涛滔绦萄桃逃淘陶讨套特藤腾疼誊梯剔踢锑提题蹄啼体替嚏惕涕剃屉天添填田甜恬舔腆挑条迢眺跳贴铁帖厅听烃汀廷停亭庭挺艇通桐酮瞳同铜彤童桶捅筒统痛偷投头透凸秃突图徒途涂屠土吐兔湍团推颓腿蜕褪退吞屯臀拖托脱鸵陀驮驼椭妥拓唾挖哇蛙洼娃瓦袜歪外豌弯湾玩顽丸烷完碗挽晚皖惋宛婉万腕汪王亡枉网往旺望忘妄威巍微危韦违桅围唯惟为潍维苇萎委伟伪尾纬未蔚味畏胃喂魏位渭谓尉慰卫瘟温蚊文闻纹吻稳紊问嗡翁瓮挝蜗涡窝我斡卧握沃巫呜钨乌污诬屋无芜梧吾吴毋武五捂午舞伍侮坞戊雾晤物勿务悟误昔熙析西硒矽晰嘻吸锡牺稀息希悉膝夕惜熄烯溪汐犀檄袭席习媳喜铣洗系隙戏细瞎虾匣霞辖暇峡侠狭下厦夏吓掀锨先仙鲜纤咸贤衔舷闲涎弦嫌显险现献县腺馅羡宪陷限线相厢镶香箱襄湘乡翔祥详想响享项巷橡像向象萧硝霄削哮嚣销消宵淆晓小孝校肖啸笑效楔些歇蝎鞋协挟携邪斜胁谐写械卸蟹懈泄泻谢屑薪芯锌欣辛新忻心信衅星腥猩惺兴刑型形邢行醒幸杏性姓兄凶胸匈汹雄熊休修羞朽嗅锈秀袖绣墟戌需虚嘘须徐许蓄酗叙旭序畜恤絮婿绪续轩喧宣悬旋玄选癣眩绚靴薛学穴雪血勋熏循旬询寻驯巡殉汛训讯逊迅压押鸦鸭呀丫芽牙蚜崖衙涯雅哑亚讶焉咽阉烟淹盐严研蜒岩延言颜阎炎沿奄掩眼衍演艳堰燕厌砚雁唁彦焰宴谚验殃央鸯秧杨扬佯疡羊洋阳氧仰痒养样漾邀腰妖瑶摇尧遥窑谣姚咬舀药要耀椰噎耶爷野冶也页掖业叶曳腋夜液一壹医揖铱依伊衣颐夷遗移仪胰疑沂宜姨彝椅蚁倚已乙矣以艺抑易邑屹亿役臆逸肄疫亦裔意毅忆义益溢诣议谊译异翼翌绎茵荫因殷音阴姻吟银淫寅饮尹引隐印英樱婴鹰应缨莹萤营荧蝇迎赢盈影颖硬映哟拥佣臃痈庸雍踊蛹咏泳涌永恿勇用幽优悠忧尤由邮铀犹油游酉有友右佑釉诱又幼迂淤于盂榆虞愚舆余俞逾鱼愉渝渔隅予娱雨与屿禹宇语羽玉域芋郁吁遇喻峪御愈欲狱育誉浴寓裕预豫驭鸳渊冤元垣袁原援辕园员圆猿源缘远苑愿怨院曰约越跃钥岳粤月悦阅耘云郧匀陨允运蕴酝晕韵孕匝砸杂栽哉灾宰载再在咱攒暂赞赃脏葬遭糟凿藻枣早澡蚤躁噪造皂灶燥责择则泽贼怎增憎曾赠扎喳渣札轧铡闸眨栅榨咋乍炸诈摘斋宅窄债寨瞻毡詹粘沾盏斩辗崭展蘸栈占战站湛绽樟章彰漳张掌涨杖丈帐账仗胀瘴障招昭找沼赵照罩兆肇召遮折哲蛰辙者锗蔗这浙珍斟真甄砧臻贞针侦枕疹诊震振镇阵蒸挣睁征狰争怔整拯正政帧症郑证芝枝支吱蜘知肢脂汁之织职直植殖执值侄址指止趾只旨纸志挚掷至致置帜峙制智秩稚质炙痔滞治窒中盅忠钟衷终种肿重仲众舟周州洲诌粥轴肘帚咒皱宙昼骤珠株蛛朱猪诸诛逐竹烛煮拄瞩嘱主著柱助蛀贮铸筑住注祝驻抓爪拽专砖转撰赚篆桩庄装妆撞壮状椎锥追赘坠缀谆准捉拙卓桌琢茁酌啄着灼浊兹咨资姿滋淄孜紫仔籽滓子自渍字鬃棕踪宗综总纵邹走奏揍租足卒族祖诅阻组钻纂嘴醉最罪尊遵昨左佐柞做作坐座亍丌兀丐廿卅丕亘丞鬲孬噩丨禺丿匕乇夭爻卮氐囟胤馗毓睾鼗丶亟鼐乜乩亓芈孛啬嘏仄厍厝厣厥厮靥赝匚叵匦匮匾赜卦卣刂刈刎刭刳刿剀剌剞剡剜蒯剽劂劁劐劓冂罔亻仃仉仂仨仡仫仞伛仳伢佤仵伥伧伉伫佞佧攸佚佝佟佗伲伽佶佴侑侉侃侏佾佻侪佼侬侔俦俨俪俅俚俣俜俑俟俸倩偌俳倬倏倮倭俾倜倌倥倨偾偃偕偈偎偬偻傥傧傩傺僖儆僭僬僦僮儇儋仝氽佘佥俎龠汆籴兮巽黉馘冁夔勹匍訇匐凫夙兕亠兖亳衮袤亵脔裒禀嬴蠃羸冫冱冽冼凇冖冢冥讠讦讧讪讴讵讷诂诃诋诏诎诒诓诔诖诘诙诜诟诠诤诨诩诮诰诳诶诹诼诿谀谂谄谇谌谏谑谒谔谕谖谙谛谘谝谟谠谡谥谧谪谫谮谯谲谳谵谶卩卺阝阢阡阱阪阽阼陂陉陔陟陧陬陲陴隈隍隗隰邗邛邝邙邬邡邴邳邶邺邸邰郏郅邾郐郄郇郓郦郢郜郗郛郫郯郾鄄鄢鄞鄣鄱鄯鄹酃酆刍奂劢劬劭劾哿勐勖勰叟燮矍廴凵凼鬯厶弁畚巯坌垩垡塾墼壅壑圩圬圪圳圹圮圯坜圻坂坩垅坫垆坼坻坨坭坶坳垭垤垌垲埏垧垴垓垠埕埘埚埙埒垸埴埯埸埤埝堋堍埽埭堀堞堙塄堠塥塬墁墉墚墀馨鼙懿艹艽艿芏芊芨芄芎芑芗芙芫芸芾芰苈苊苣芘芷芮苋苌苁芩芴芡芪芟苄苎芤苡茉苷苤茏茇苜苴苒苘茌苻苓茑茚茆茔茕苠苕茜荑荛荜茈莒茼茴茱莛荞茯荏荇荃荟荀茗荠茭茺茳荦荥荨茛荩荬荪荭荮莰荸莳莴莠莪莓莜莅荼莶莩荽莸荻莘莞莨莺莼菁萁菥菘堇萘萋菝菽菖萜萸萑萆菔菟萏萃菸菹菪菅菀萦菰菡葜葑葚葙葳蒇蒈葺蒉葸萼葆葩葶蒌蒎萱葭蓁蓍蓐蓦蒽蓓蓊蒿蒺蓠蒡蒹蒴蒗蓥蓣蔌甍蔸蓰蔹蔟蔺蕖蔻蓿蓼蕙蕈蕨蕤蕞蕺瞢蕃蕲蕻薤薨薇薏蕹薮薜薅薹薷薰藓藁藜藿蘧蘅蘩蘖蘼廾弈夼奁耷奕奚奘匏尢尥尬尴扌扪抟抻拊拚拗拮挢拶挹捋捃掭揶捱捺掎掴捭掬掊捩掮掼揲揸揠揿揄揞揎摒揆掾摅摁搋搛搠搌搦搡摞撄摭撖摺撷撸撙撺擀擐擗擤擢攉攥攮弋忒甙弑卟叱叽叩叨叻吒吖吆呋呒呓呔呖呃吡呗呙吣吲咂咔呷呱呤咚咛咄呶呦咝哐咭哂咴哒咧咦哓哔呲咣哕咻咿哌哙哚哜咩咪咤哝哏哞唛哧唠哽唔哳唢唣唏唑唧唪啧喏喵啉啭啁啕唿啐唼唷啖啵啶啷唳唰啜喋嗒喃喱喹喈喁喟啾嗖喑啻嗟喽喾喔喙嗪嗷嗉嘟嗑嗫嗬嗔嗦嗝嗄嗯嗥嗲嗳嗌嗍嗨嗵嗤辔嘞嘈嘌嘁嘤嘣嗾嘀嘧嘭噘嘹噗嘬噍噢噙噜噌噔嚆噤噱噫噻噼嚅嚓嚯囔囗囝囡囵囫囹囿圄圊圉圜帏帙帔帑帱帻帼帷幄幔幛幞幡岌屺岍岐岖岈岘岙岑岚岜岵岢岽岬岫岱岣峁岷峄峒峤峋峥崂崃崧崦崮崤崞崆崛嵘崾崴崽嵬嵛嵯嵝嵫嵋嵊嵩嵴嶂嶙嶝豳嶷巅彳彷徂徇徉後徕徙徜徨徭徵徼衢彡犭犰犴犷犸狃狁狎狍狒狨狯狩狲狴狷猁狳猃狺狻猗猓猡猊猞猝猕猢猹猥猬猸猱獐獍獗獠獬獯獾舛夥飧夤夂饣饧饨饩饪饫饬饴饷饽馀馄馇馊馍馐馑馓馔馕庀庑庋庖庥庠庹庵庾庳赓廒廑廛廨廪膺忄忉忖忏怃忮怄忡忤忾怅怆忪忭忸怙怵怦怛怏怍怩怫怊怿怡恸恹恻恺恂恪恽悖悚悭悝悃悒悌悛惬悻悱惝惘惆惚悴愠愦愕愣惴愀愎愫慊慵憬憔憧憷懔懵忝隳闩闫闱闳闵闶闼闾阃阄阆阈阊阋阌阍阏阒阕阖阗阙阚丬爿戕氵汔汜汊沣沅沐沔沌汨汩汴汶沆沩泐泔沭泷泸泱泗沲泠泖泺泫泮沱泓泯泾洹洧洌浃浈洇洄洙洎洫浍洮洵洚浏浒浔洳涑浯涞涠浞涓涔浜浠浼浣渚淇淅淞渎涿淠渑淦淝淙渖涫渌涮渫湮湎湫溲湟溆湓湔渲渥湄滟溱溘滠漭滢溥溧溽溻溷滗溴滏溏滂溟潢潆潇漤漕滹漯漶潋潴漪漉漩澉澍澌潸潲潼潺濑濉澧澹澶濂濡濮濞濠濯瀚瀣瀛瀹瀵灏灞宀宄宕宓宥宸甯骞搴寤寮褰寰蹇謇辶迓迕迥迮迤迩迦迳迨逅逄逋逦逑逍逖逡逵逶逭逯遄遑遒遐遨遘遢遛暹遴遽邂邈邃邋彐彗彖彘尻咫屐屙孱屣屦羼弪弩弭艴弼鬻屮妁妃妍妩妪妣妗姊妫妞妤姒妲妯姗妾娅娆姝娈姣姘姹娌娉娲娴娑娣娓婀婧婊婕娼婢婵胬媪媛婷婺媾嫫媲嫒嫔媸嫠嫣嫱嫖嫦嫘嫜嬉嬗嬖嬲嬷孀尕尜孚孥孳孑孓孢驵驷驸驺驿驽骀骁骅骈骊骐骒骓骖骘骛骜骝骟骠骢骣骥骧纟纡纣纥纨纩纭纰纾绀绁绂绉绋绌绐绔绗绛绠绡绨绫绮绯绱绲缍绶绺绻绾缁缂缃缇缈缋缌缏缑缒缗缙缜缛缟缡缢缣缤缥缦缧缪缫缬缭缯缰缱缲缳缵幺畿巛甾邕玎玑玮玢玟珏珂珑玷玳珀珉珈珥珙顼琊珩珧珞玺珲琏琪瑛琦琥琨琰琮琬琛琚瑁瑜瑗瑕瑙瑷瑭瑾璜璎璀璁璇璋璞璨璩璐璧瓒璺韪韫韬杌杓杞杈杩枥枇杪杳枘枧杵枨枞枭枋杷杼柰栉柘栊柩枰栌柙枵柚枳柝栀柃枸柢栎柁柽栲栳桠桡桎桢桄桤梃栝桕桦桁桧桀栾桊桉栩梵梏桴桷梓桫棂楮棼椟椠棹椤棰椋椁楗棣椐楱椹楠楂楝榄楫榀榘楸椴槌榇榈槎榉楦楣楹榛榧榻榫榭槔榱槁槊槟榕槠榍槿樯槭樗樘橥槲橄樾檠橐橛樵檎橹樽樨橘橼檑檐檩檗檫猷獒殁殂殇殄殒殓殍殚殛殡殪轫轭轱轲轳轵轶轸轷轹轺轼轾辁辂辄辇辋辍辎辏辘辚軎戋戗戛戟戢戡戥戤戬臧瓯瓴瓿甏甑甓攴旮旯旰昊昙杲昃昕昀炅曷昝昴昱昶昵耆晟晔晁晏晖晡晗晷暄暌暧暝暾曛曜曦曩贲贳贶贻贽赀赅赆赈赉赇赍赕赙觇觊觋觌觎觏觐觑牮犟牝牦牯牾牿犄犋犍犏犒挈挲掰搿擘耄毪毳毽毵毹氅氇氆氍氕氘氙氚氡氩氤氪氲攵敕敫牍牒牖爰虢刖肟肜肓肼朊肽肱肫肭肴肷胧胨胩胪胛胂胄胙胍胗朐胝胫胱胴胭脍脎胲胼朕脒豚脶脞脬脘脲腈腌腓腴腙腚腱腠腩腼腽腭腧塍媵膈膂膑滕膣膪臌朦臊膻臁膦欤欷欹歃歆歙飑飒飓飕飙飚殳彀毂觳斐齑斓於旆旄旃旌旎旒旖炀炜炖炝炻烀炷炫炱烨烊焐焓焖焯焱煳煜煨煅煲煊煸煺熘熳熵熨熠燠燔燧燹爝爨灬焘煦熹戾戽扃扈扉礻祀祆祉祛祜祓祚祢祗祠祯祧祺禅禊禚禧禳忑忐怼恝恚恧恁恙恣悫愆愍慝憩憝懋懑戆肀聿沓泶淼矶矸砀砉砗砘砑斫砭砜砝砹砺砻砟砼砥砬砣砩硎硭硖硗砦硐硇硌硪碛碓碚碇碜碡碣碲碹碥磔磙磉磬磲礅磴礓礤礞礴龛黹黻黼盱眄眍盹眇眈眚眢眙眭眦眵眸睐睑睇睃睚睨睢睥睿瞍睽瞀瞌瞑瞟瞠瞰瞵瞽町畀畎畋畈畛畲畹疃罘罡罟詈罨罴罱罹羁罾盍盥蠲钅钆钇钋钊钌钍钏钐钔钗钕钚钛钜钣钤钫钪钭钬钯钰钲钴钶钷钸钹钺钼钽钿铄铈铉铊铋铌铍铎铐铑铒铕铖铗铙铘铛铞铟铠铢铤铥铧铨铪铩铫铮铯铳铴铵铷铹铼铽铿锃锂锆锇锉锊锍锎锏锒锓锔锕锖锘锛锝锞锟锢锪锫锩锬锱锲锴锶锷锸锼锾锿镂锵镄镅镆镉镌镎镏镒镓镔镖镗镘镙镛镞镟镝镡镢镤镥镦镧镨镩镪镫镬镯镱镲镳锺矧矬雉秕秭秣秫稆嵇稃稂稞稔稹稷穑黏馥穰皈皎皓皙皤瓞瓠甬鸠鸢鸨鸩鸪鸫鸬鸲鸱鸶鸸鸷鸹鸺鸾鹁鹂鹄鹆鹇鹈鹉鹋鹌鹎鹑鹕鹗鹚鹛鹜鹞鹣鹦鹧鹨鹩鹪鹫鹬鹱鹭鹳疒疔疖疠疝疬疣疳疴疸痄疱疰痃痂痖痍痣痨痦痤痫痧瘃痱痼痿瘐瘀瘅瘌瘗瘊瘥瘘瘕瘙瘛瘼瘢瘠癀瘭瘰瘿瘵癃瘾瘳癍癞癔癜癖癫癯翊竦穸穹窀窆窈窕窦窠窬窨窭窳衤衩衲衽衿袂袢裆袷袼裉裢裎裣裥裱褚裼裨裾裰褡褙褓褛褊褴褫褶襁襦襻疋胥皲皴矜耒耔耖耜耠耢耥耦耧耩耨耱耋耵聃聆聍聒聩聱覃顸颀颃颉颌颍颏颔颚颛颞颟颡颢颥颦虍虔虬虮虿虺虼虻蚨蚍蚋蚬蚝蚧蚣蚪蚓蚩蚶蛄蚵蛎蚰蚺蚱蚯蛉蛏蚴蛩蛱蛲蛭蛳蛐蜓蛞蛴蛟蛘蛑蜃蜇蛸蜈蜊蜍蜉蜣蜻蜞蜥蜮蜚蜾蝈蜴蜱蜩蜷蜿螂蜢蝽蝾蝻蝠蝰蝌蝮螋蝓蝣蝼蝤蝙蝥螓螯螨蟒蟆螈螅螭螗螃螫蟥螬螵螳蟋蟓螽蟑蟀蟊蟛蟪蟠蟮蠖蠓蟾蠊蠛蠡蠹蠼缶罂罄罅舐竺竽笈笃笄笕笊笫笏筇笸笪笙笮笱笠笥笤笳笾笞筘筚筅筵筌筝筠筮筻筢筲筱箐箦箧箸箬箝箨箅箪箜箢箫箴篑篁篌篝篚篥篦篪簌篾篼簏簖簋簟簪簦簸籁籀臾舁舂舄臬衄舡舢舣舭舯舨舫舸舻舳舴舾艄艉艋艏艚艟艨衾袅袈裘裟襞羝羟羧羯羰羲籼敉粑粝粜粞粢粲粼粽糁糇糌糍糈糅糗糨艮暨羿翎翕翥翡翦翩翮翳糸絷綦綮繇纛麸麴赳趄趔趑趱赧赭豇豉酊酐酎酏酤酢酡酰酩酯酽酾酲酴酹醌醅醐醍醑醢醣醪醭醮醯醵醴醺豕鹾趸跫踅蹙蹩趵趿趼趺跄跖跗跚跞跎跏跛跆跬跷跸跣跹跻跤踉跽踔踝踟踬踮踣踯踺蹀踹踵踽踱蹉蹁蹂蹑蹒蹊蹰蹶蹼蹯蹴躅躏躔躐躜躞豸貂貊貅貘貔斛觖觞觚觜觥觫觯訾謦靓雩雳雯霆霁霈霏霎霪霭霰霾龀龃龅龆龇龈龉龊龌黾鼋鼍隹隼隽雎雒瞿雠銎銮鋈錾鍪鏊鎏鐾鑫鱿鲂鲅鲆鲇鲈稣鲋鲎鲐鲑鲒鲔鲕鲚鲛鲞鲟鲠鲡鲢鲣鲥鲦鲧鲨鲩鲫鲭鲮鲰鲱鲲鲳鲴鲵鲶鲷鲺鲻鲼鲽鳄鳅鳆鳇鳊鳋鳌鳍鳎鳏鳐鳓鳔鳕鳗鳘鳙鳜鳝鳟鳢靼鞅鞑鞒鞔鞯鞫鞣鞲鞴骱骰骷鹘骶骺骼髁髀髅髂髋髌髑魅魃魇魉魈魍魑飨餍餮饕饔髟髡髦髯髫髻髭髹鬈鬏鬓鬟鬣麽麾縻麂麇麈麋麒鏖麝麟黛黜黝黠黟黢黩黧黥黪黯鼢鼬鼯鼹鼷鼽鼾齄";

var qswhSpell = [ "a", 0, "ai", 2, "an", 15, "ang", 24, "ao", 27, "ba", 36,
		"bai", 54, "ban", 62, "bang", 77, "bao", 89, "bei", 106, "ben", 121,
		"beng", 125, "bi", 131, "bian", 155, "biao", 167, "bie", 171, "bin",
		175, "bing", 181, "bo", 190, "bu", 211, "ca", 220, "cai", 221, "can",
		232, "cang", 239, "cao", 244, "ce", 249, "ceng", 254, "cha", 256,
		"chai", 267, "chan", 270, "chang", 280, "chao", 293, "che", 302,
		"chen", 308, "cheng", 318, "chi", 333, "chong", 349, "chou", 354,
		"chu", 366, "chuai", 382, "chuan", 383, "chuang", 390, "chui", 396,
		"chun", 401, "chuo", 408, "ci", 410, "cong", 422, "cou", 428, "cu",
		429, "cuan", 433, "cui", 436, "cun", 444, "cuo", 447, "da", 453, "dai",
		459, "dan", 471, "dang", 486, "dao", 491, "de", 503, "deng", 506, "di",
		513, "dian", 532, "diao", 548, "die", 557, "ding", 564, "diu", 573,
		"dong", 574, "dou", 584, "du", 591, "duan", 606, "dui", 612, "dun",
		616, "duo", 625, "e", 637, "en", 650, "er", 651, "fa", 659, "fan", 667,
		"fang", 684, "fei", 695, "fen", 707, "feng", 722, "fo", 737, "fou",
		738, "fu", 739, "ga", 784, "gai", 786, "gan", 792, "gang", 803, "gao",
		812, "ge", 822, "gei", 839, "gen", 840, "geng", 842, "gong", 849,
		"gou", 864, "gu", 873, "gua", 891, "guai", 897, "guan", 900, "guang",
		911, "gui", 914, "gun", 930, "guo", 933, "ha", 939, "hai", 940, "han",
		947, "hang", 966, "hao", 969, "he", 978, "hei", 996, "hen", 998,
		"heng", 1002, "hong", 1007, "hou", 1016, "hu", 1023, "hua", 1041,
		"huai", 1050, "huan", 1055, "huang", 1069, "hui", 1083, "hun", 1104,
		"huo", 1110, "ji", 1120, "jia", 1173, "jian", 1190, "jiang", 1230,
		"jiao", 1243, "jie", 1271, "jin", 1298, "jing", 1318, "jiong", 1343,
		"jiu", 1345, "ju", 1362, "juan", 1387, "jue", 1394, "jun", 1404, "ka",
		1415, "kai", 1419, "kan", 1424, "kang", 1430, "kao", 1437, "ke", 1441,
		"ken", 1456, "keng", 1460, "kong", 1462, "kou", 1466, "ku", 1470,
		"kua", 1477, "kuai", 1482, "kuan", 1486, "kuang", 1488, "kui", 1496,
		"kun", 1507, "kuo", 1511, "la", 1515, "lai", 1522, "lan", 1525, "lang",
		1540, "lao", 1547, "le", 1556, "lei", 1558, "leng", 1569, "li", 1572,
		"lia", 1606, "lian", 1607, "liang", 1621, "liao", 1632, "lie", 1645,
		"lin", 1650, "ling", 1662, "liu", 1676, "long", 1687, "lou", 1696,
		"lu", 1702, "lv", 1722, "luan", 1736, "lue", 1742, "lun", 1744, "luo",
		1751, "ma", 1763, "mai", 1772, "man", 1778, "mang", 1787, "mao", 1793,
		"me", 1805, "mei", 1806, "men", 1822, "meng", 1825, "mi", 1833, "mian",
		1847, "miao", 1856, "mie", 1864, "min", 1866, "ming", 1872, "miu",
		1878, "mo", 1879, "mou", 1896, "mu", 1899, "na", 1914, "nai", 1921,
		"nan", 1926, "nang", 1929, "nao", 1930, "ne", 1935, "nei", 1936, "nen",
		1938, "neng", 1939, "ni", 1940, "nian", 1951, "niang", 1958, "niao",
		1960, "nie", 1962, "nin", 1969, "ning", 1970, "niu", 1976, "nong",
		1980, "nu", 1984, "nv", 1987, "nuan", 1988, "nue", 1989, "nuo", 1991,
		"o", 1995, "ou", 1996, "pa", 2003, "pai", 2009, "pan", 2015, "pang",
		2023, "pao", 2028, "pei", 2035, "pen", 2044, "peng", 2046, "pi", 2060,
		"pian", 2077, "piao", 2081, "pie", 2085, "pin", 2087, "ping", 2092,
		"po", 2101, "pu", 2110, "qi", 2125, "qia", 2161, "qian", 2164, "qiang",
		2186, "qiao", 2194, "qie", 2209, "qin", 2214, "qing", 2225, "qiong",
		2238, "qiu", 2240, "qu", 2248, "quan", 2261, "que", 2272, "qun", 2280,
		"ran", 2282, "rang", 2286, "rao", 2291, "re", 2294, "ren", 2296,
		"reng", 2306, "ri", 2308, "rong", 2309, "rou", 2319, "ru", 2322,
		"ruan", 2332, "rui", 2334, "run", 2337, "ruo", 2339, "sa", 2341, "sai",
		2344, "san", 2348, "sang", 2352, "sao", 2355, "se", 2359, "sen", 2362,
		"seng", 2363, "sha", 2364, "shai", 2373, "shan", 2375, "shang", 2391,
		"shao", 2399, "she", 2410, "shen", 2422, "sheng", 2438, "shi", 2449,
		"shou", 2496, "shu", 2506, "shua", 2539, "shuai", 2541, "shuan", 2545,
		"shuang", 2547, "shui", 2550, "shun", 2554, "shuo", 2558, "si", 2562,
		"song", 2578, "sou", 2586, "su", 2589, "suan", 2602, "sui", 2605,
		"sun", 2616, "suo", 2619, "ta", 2627, "tai", 2636, "tan", 2645, "tang",
		2663, "tao", 2676, "te", 2687, "teng", 2688, "ti", 2692, "tian", 2707,
		"tiao", 2715, "tie", 2720, "ting", 2723, "tong", 2733, "tou", 2746,
		"tu", 2750, "tuan", 2761, "tui", 2763, "tun", 2769, "tuo", 2772, "wa",
		2783, "wai", 2790, "wan", 2792, "wang", 2809, "wei", 2819, "wen", 2852,
		"weng", 2862, "wo", 2865, "wu", 2874, "xi", 2903, "xia", 2938, "xian",
		2951, "xiang", 2977, "xiao", 2997, "xie", 3015, "xin", 3036, "xing",
		3046, "xiong", 3061, "xiu", 3068, "xu", 3077, "xuan", 3096, "xue",
		3106, "xun", 3112, "ya", 3126, "yan", 3142, "yang", 3175, "yao", 3192,
		"ye", 3207, "yi", 3222, "yin", 3275, "ying", 3291, "yo", 3309, "yong",
		3310, "you", 3325, "yu", 3346, "yuan", 3390, "yue", 3410, "yun", 3420,
		"za", 3432, "zai", 3435, "zan", 3442, "zang", 3446, "zao", 3449, "ze",
		3463, "zei", 3467, "zen", 3468, "zeng", 3469, "zha", 3473, "zhai",
		3487, "zhan", 3493, "zhang", 3510, "zhao", 3525, "zhe", 3535, "zhen",
		3545, "zheng", 3561, "zhi", 3576, "zhong", 3619, "zhou", 3630, "zhu",
		3644, "zhua", 3670, "zhuai", 3672, "zhuan", 3673, "zhuang", 3679,
		"zhui", 3686, "zhun", 3692, "zhuo", 3694, "zi", 3705, "zong", 3720,
		"zou", 3727, "zu", 3731, "zuan", 3739, "zui", 3741, "zun", 3745, "zuo",
		3747 ];

/**
 * 不清楚
 */
function UrlEncode(str) {
	/** *******qiushuiwuhen(2002-9-16)******* */
	var i, c, p, q, ret = "", strSpecial = "!\"#$%&'()*+,/:;<=>?@[\]^`{|}~%";
	for (i = 0; i < str.length; i++) {
		if (str.charCodeAt(i) >= 0x4e00) {
			var p = strGB.indexOf(str.charAt(i));
			if (p >= 0) {
				q = p % 94;
				p = (p - q) / 94;
				ret += ("%" + (0xB0 + p).toString(16) + "%" + (0xA1 + q)
						.toString(16)).toUpperCase();
			}
		} else {
			c = str.charAt(i);
			if (c == " ")
				ret += "+";
			else if (strSpecial.indexOf(c) != -1)
				ret += "%" + str.charCodeAt(i).toString(16);
			else
				ret += c;
		}
	}
	return ret;
}

/**
 * 不清楚
 */
function getSpell(str, sp) {
	/** *******qiushuiwuhen(2002-9-16)******* */
	var i, c, t, p, ret = "";
	if (sp == null)
		sp = "";
	for (i = 0; i < str.length; i++) {
		if (i == 0) {
			if (i == 0 && str.charAt(i) != "市") {
				if (str.charCodeAt(i) >= 0x4e00) {
					p = strGB.indexOf(str.charAt(i));

					if (p > -1 && p < 3755) {
						for (t = qswhSpell.length - 1; t > 0; t = t - 2)
							if (qswhSpell[t] <= p)
								break;
						if (t > 0) {
							ret += qswhSpell[t - 1].substr(0, 1) + sp;
						}
					}
				}
			}

		} else {
			if (str.charCodeAt(i) >= 0x4e00) {
				p = strGB.indexOf(str.charAt(i));

				if (p > -1 && p < 3755) {
					for (t = qswhSpell.length - 1; t > 0; t = t - 2)
						if (qswhSpell[t] <= p)
							break;
					if (t > 0) {
						ret += qswhSpell[t - 1].substr(0, 1) + sp;
					}
				}
			}
		}
	}
	return ret.substr(0, ret.length - sp.length);
}

// /////////////////////////////////////////////////////////////////
// functions for check image
// ////////////////////////////////////////////////////////////////
/**
 * 选择图片文件
 */
function changeSrc(filePicker, oFileChecker, limit) {
	if (filePicker == null)
		return true;
	if (filePicker.value == "") {
		return true;
	} else {
		if (/(gif|GIF|jpg|JPG)$/i.test(filePicker.value)) {
			oFileChecker.src = filePicker.value;
			if (oFileChecker.fileSize > limit) {
				alert("图片超过大小限制，请裁减。");
				return false;
			} else {
				return true;
			}
		} else {
			alert('请选择gif或者jpg文件！');
			filePicker.focus();
			return false;
		}
	}

}
/**
 * 更改图片宽度和高度
 */
function ImgFormat(w, h, imgId) {
	var img = document.getElementById(imgId);
	alert(img.width)
	var imgW = document.getElementById(imgId).width;
	var imgH = document.getElementById(imgId).height;
	alert(imgW + " " + imgH)
	if (w / h > imgW / imgH) {
		document.getElementById(imgId).height = h;
	} else {
		document.getElementById(imgId).width = w;
	}
}

// /////////////////////////////////////////////////////////////////////////////////////////
// ////下拉框控件的相关方法//////////////////////////////
// ///////////////////////////////////////////////////////////////////////////

/**
 * 从指定的url中获取数值来填充oSelect指定的select控件的值。
 */
function populate_select(url, oSelect, remove, selected) {
	var req = getXMLHttpRequest();
	if (remove) {
		removeAll(oSelect);
	}
	req.open("GET", url, false);
	req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	req.send(null);
	if (req.status != 200) {
		alert("无法获取链接：" + url);
		return;
	}
	var resXML = req.responseXML;
	var properties = resXML.getElementsByTagName("parameter");

	for ( var i = 0; i < properties.length; i++) {
	    var name;
		var value;
		if(navigator.userAgent.indexOf("Firefox")>=0){
			name = properties[i].getElementsByTagName("name")[0].textContent;
			value = properties[i].getElementsByTagName("value")[0].textContent;
		}else{
			name = properties[i].getElementsByTagName("name")[0].text;
			value = properties[i].getElementsByTagName("value")[0].text;
		}
		var newOption = document.createElement("option");
		newOption.value = value;
		newOption.text = name;
		if (selected != null && selected == value) {
			newOption.selected = true;
		}
		if(navigator.userAgent.indexOf("Firefox")>=0){
			oSelect.add(newOption,null);
		}else{
			oSelect.add(newOption);
		}
	}
}

/**
 * 填充oSelect指定的select控件的值
 */
function addOption(oSelect, value, text, index) {
	if(optionExists(value,oSelect)||value==null)
		return;
	var newOption = document.createElement("option");
	newOption.value = value;
	newOption.text = text;
	newOption.title = text;
	if (!index) {
		oSelect.add(newOption, index);
		oSelect.selectedIndex = index;
	}
	return newOption;
}
/**
 * 从指定的url中获取数值按照分组来填充oSelect指定的select控件的值。
 */
function populate_fix_select(url, oSelect, remove) {
	var req = getXMLHttpRequest();
	if (remove) {
		removeAll(oSelect);
	}
	req.open("GET", url, false);
	req.setRequestHeader("Content-Type", "text/html; charset=utf-8");
	req.send(null);
	if (req.status != 200) {
		alert("无法获取链接：" + url);
		return;
	}
	var resXML = req.responseXML;
	var properties = resXML.getElementsByTagName("parameter");

	for ( var i = 0; i < properties.length; i++) {
		var name = properties[i].getElementsByTagName("name")[0].text;
		var value = properties[i].getElementsByTagName("value")[0].text;
		var vTemp = new Array();
		vTemp = value.split("*");
		vTemp1 = name.split("#");
		if (vTemp[0] == "fix") {
			var newOptgroup = document.createElement("optgroup");
			newOptgroup.label = name;
			newOptgroup.text = name;
			oSelect.appendChild(newOptgroup);
		} else {
			var newOption = document.createElement("option");
			newOption.value = value;
			newOption.text = vTemp1[0];
			if(navigator.userAgent.indexOf("Firefox")>=0){
				oSelect.add(newOption,null);
			}else{
				oSelect.add(newOption);
			}
		}
	}
}
/**
*火狐中实现swapNode函数
*/
function swapNode(node1,node2)
{
  var parent = node1.parentNode;//父节点
  var t1 = node1.nextSibling;//两节点的相对位置
  var t2 = node2.nextSibling;
  
  //如果是插入到最后就用appendChild
  if(t1) parent.insertBefore(node2,t1);
  else parent.appendChild(node2);
  if(t2) parent.insertBefore(node1,t2);
  else parent.appendChild(node1);
}    

/**
 * 上移
 */
function upperShift(contentSelector) {
	var oOptions = contentSelector.options;
	for ( var i = 1; i < oOptions.length; i++) {
		if (oOptions[i].selected)
			if(navigator.userAgent.indexOf("Firefox")>=0){
			   swapNode(oOptions[i],oOptions[i-1]);
			}else{
			     oOptions[i].swapNode(oOptions[i - 1]);
			}
	}
	if(document.all){
		contentSelector.fireEvent("onchange");
	}else{
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent("change",true,true);
		contentSelector.dispatchEvent(evt);
	}
}

/**
 * 下移
 */
function lowerShift(contentSelector) {
	var oOptions = contentSelector.options;
	for ( var i = oOptions.length - 2; i > -1; i--) {
		if (oOptions[i].selected){
			if(navigator.userAgent.indexOf("Firefox")>=0){
			   swapNode(oOptions[i],oOptions[i+1]);
			}else{
			     oOptions[i].swapNode(oOptions[i + 1]);
			}}
	}
	if(document.all){
		contentSelector.fireEvent("onchange");
	}else{
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent("change",true,true);
		contentSelector.dispatchEvent(evt);
	}
}

/**
 * 删除选中
 */
function deleteOption(contentSelector) {
	var oOptions = contentSelector.options;
	for ( var i = 0; i < oOptions.length;) {
		var oOption = oOptions[i];
		if (oOption.selected)
			oOption.parentNode.remove(i);
		else
			i++;
	}
	if(document.all){
		contentSelector.fireEvent("onchange");
	}else{
		var evt = document.createEvent("HTMLEvents");
		evt.initEvent("change",true,true);
		contentSelector.dispatchEvent(evt);
	}
}

/**
 * 删除select控件的全部options和optgroup.
 */
function removeAll(oSelect) {
	var obj = oSelect.options;
	var oOptGroup = oSelect.children;
	for ( var i = 0; i < obj.length; i++) {
		obj.options.remove(i);
		i--;
	}
	if (oOptGroup != null) {
		if (oOptGroup.length != null) {
			var optGroupLength = oOptGroup.length;
			for ( var j = 0; j < optGroupLength; j++) {
				oSelect.removeChild(oOptGroup[0]);
			}
		}
	}
}
/**
 * 全选
 */
function selectAll(field) {
	var u = field.options;
	for (i = 0; i < u.length; i++) {
		u[i].selected = "true";
	}
}
/**
 * 返回选中项
 */
function getSelectedItem(oSelect) {
	var u = oSelect.options;
	for ( var i = 0; i < u.length; i++) {
		if (u[i].selected == true) {
			return u[i];
		}
	}
	return false;
}

/**
 * 返回选中项（多选）
 */
function getSelectedItems(oSelect) {
	var u = oSelect.options;
	var ret = [];
	for ( var i = 0; i < u.length; i++) {
		if (u[i].selected) {
			ret.push(u[i]);
		}
	}
	return ret.length > 0 ? ret : false;
}

/**
 * 返回值等于value的项
 */
function findSelectItem(oSelect, value) {
	var u = oSelect.options;
	for ( var i = 0; i < u.length; i++) {
		if (u[i].value == value) {
			return u[i];
		}
	}
	return false;
}

/**
 * 设置值等于value的项为选中
 */
function setSelectValue(oSelect, value) {
	var u = oSelect.options;
	for ( var i = 0; i < u.length; i++) {
		if (u[i].value == value) {
			u[i].selected = true;
		}
	}
}

/**
 * 判断是否存在值等于value的项
 */
function optionExists(value, oSelect) {
	if (oSelect.options != null)
		for ( var j = 0; j < oSelect.options.length; j++) {
			if (oSelect.options[j].value == value)
				return true;
		}
	return false;
}

/**
 * 从指定的url中获得返回字符串来填充指定的控件。
 */
function populate_text(url, target, remove) {
	var req = getXMLHttpRequest();
	var inner = '';
	if (!remove) {
		inner = target.innerHTML;
	}
	var callback = function() {
		if ((req.readyState == 4) && (req.status == 200)) {
			var data = req.responseText;
			target.innerHTML = inner + data;
		}
	}
	sendRequest(req, url, callback);
}

/**
 * selected所对应的控件不可选，deselected所对应的控件可选。
 */
function toggleSelect(selected, deselected) {
	selected.disabled = false;
	deselected.disabled = true;
}

/**
 * 相对地址转成绝对地址
 */
function toAbsoluteURL(s) {
	var l = location, h, p, f, i;
	if (/^\w+:/.test(s)) {
		return s;
	}

	h = l.protocol + '//' + l.host;
	if (s.indexOf('/') == 0) {
		return h + s;
	}

	p = l.pathname.replace(/\/[^\/]*$/, '');
	f = s.match(/\.\.\//g);
	if (f) {
		s = s.substring(f.length * 3);
		for (i = f.length; i--;i>=0) {
			p = p.substring(0, p.lastIndexOf('/'));
		}
	}

	return h + p + '/' + s;
}

function acs(char) {
	document.write('&#' + char);
}

/**
 * 获取url请求地址的参数
 */
function getURLParam(name) {
	String.prototype.getQueryString = function(name) {
		var reg = new RegExp("(^|&|\\?)" + name + "=([^&]*)(&|$)"), r;
		if (r = this.match(reg))
			return unescape(r[2]);
		return null;
	};
	return window.location.href.getQueryString(name);
}

/**
 * 调整图片大小
 */
function resizeImg(sImg, oWidth, oHeight) {
	if (oWidth == null || oWidth == "") {
		oWidth = 80;
	}
	if (oHeight == null || oHeight == "") {
		oHeight = 80;
	}
	sImg.style.display = "";
	if (sImg.width > oWidth && sImg.height > oHeight) {
		if (sImg.width < sImg.height) {
			sImg.height = oHeight;
			sImg.width = sImg.width * oHeight / sImg.height;
		} else if (sImg.width > 0) {
			sImg.width = oWidth;
			sImg.height = sImg.height * oWidth / sImg.width;
		}
		sImg.enlarge = true;
	} else if (sImg.width > oWidth) {
		sImg.width = oWidth;
		sImg.height = sImg.height * oWidth / sImg.width;
		sImg.enlarge = true;
	} else if (sImg.height > oHeight) {
		sImg.height = oHeight;
		sImg.width = sImg.width * oHeight / sImg.height;
		sImg.enlarge = true;
	}
}

/**
 * 显示原始图片
 **/
function showEnlargeImage(sDiv) {
	var sImg = sDiv.children.tags("img")[0];
	var enlargeDiv = document.getElementById("enlargeDiv");
	if (enlargeDiv != null) {
		if ((enlargeDiv.currentId != sImg.id) && (sImg.enlarge)) {
			var enlargeImg = enlargeDiv.children.tags("img")[0];
			enlargeImg.src = sImg.src;
			var fileList = sDiv;
			while (fileList.offsetParent) {
				fileList = fileList.offsetParent;
			}
			if (sDiv.parentNode.offsetTop - fileList.scrollTop + 120
					+ enlargeImg.width < fileList.clientHeight) {
				enlargeDiv.style.left = sDiv.parentNode.offsetLeft;
				enlargeDiv.style.top = sDiv.parentNode.offsetTop;
			} else {
				enlargeDiv.style.left = sDiv.parentNode.offsetLeft + 260
						- enlargeImg.width;
				enlargeDiv.style.top = sDiv.parentNode.offsetTop
						- fileList.scrollTop + 80 - enlargeImg.height;
			}
			enlargeDiv.style.display = "block";
			enlargeDiv.currentId = sImg.id;
		}
	}
}

/**
 * 隐藏原始图片
 **/
function hideEnlargeImage(sDiv) {
	var sImg = sDiv.children.tags("img")[0];
	var enlargeDiv = document.getElementById("enlargeDiv");
	if (enlargeDiv != null) {
		enlargeDiv.style.display = "none";
		enlargeDiv.currentId = null;
	}
}

/**
 * 检查是否是正整数
 **/
function validatorInteger(object) {
	if (object != null && object.value != undefined) {
		var s = object.value;
		if (s != "") {
			var regu = "^[0-9]+$";
			var re = new RegExp(regu);
			if (s.search(re) == -1) {
				alert("请输入正整数");
				return false;
			}
		}
		return true;
	} else {
		alert("控件不存在，校验失败");
		return false;
	}
}

/**
 * 检查是否为空
 **/
function validatorRequired(object, name, form) {
	var temp = true;
	for(var j = 0; j < object.length; j++ ) {
	if (object[j] != null && object[j].value != undefined) {
		var s = object[j].value;
		if (s.trim() == "") {
			temp = false;
			alert(name[j] + "不能为空");
			return;
		}
	} else {
		temp = false;
		alert(name[j] + "控件不存在，校验失败");
	}
	}
	if (temp == true) {
        form.submit();
	}
}
/**
 * 覆盖validator-rules.xml中的trim方法，为了验证全角空格, \u3000即全角空格
 */
function trim (s) {
	return s.replace(/(^[\s\u3000]*)|([\s\u3000]*$)/g, "");
};

/**
 * 字符串转换成日期，字符串格式必须是"yyyy-MM-dd HH:mm:SS"
 */
function strToDate(dateStr){
	var arr1 = dateStr.split(" ");
	if(arr1.length!=null&&arr1.length>1){
		var dateArr = arr1[0].split("-");
		var timeArr = arr1[1].split(":");
		if(timeArr.length>2){
			return new Date(dateArr[0],dateArr[1],dateArr[2],timeArr[0],timeArr[1],timeArr[2]); 
		}else if(dateArr.length>2){
			return new Date(dateArr[0],dateArr[1],dateArr[2]);
		}else{
			return null;
		}
	}else{
		var dateArr = arr1[0].split("-");
		if(dateArr.length>2){
			return new Date(dateArr[0],dateArr[1],dateArr[2]);
		}else{
			return null;
		}
	}

}

var HTMLTools = new Object();

HTMLTools.HTMLEncode = function( text )
{
	if ( !text )
		return '' ;

	text = text.replace( /&/g, '&amp;' ) ;
	text = text.replace( /</g, '&lt;' ) ;
	text = text.replace( />/g, '&gt;' ) ;
	text = text.replace( /\"/g, '&quot;' ) ;
	text = text.replace( /\'/g, '&#39;' ) ;

	return text ;
}

HTMLTools.HTMLDecode = function( text )
{
	if ( !text )
		return '' ;

	text = text.replace( /&gt;/g, '>' ) ;
	text = text.replace( /&lt;/g, '<' ) ;
	text = text.replace( /&amp;/g, '&' ) ;
	text = text.replace( /&quot;/g, '\"' ) ;
	text = text.replace( /&#39;/g, '\'' ) ;

	return text ;
}

/**
*用于获取一个element的第一个子元素 
*/
function getFirstElement(eparent){
	if(eparent.nodeType != 3){
		for(var i=0;i<eparent.childNodes.length;i++){
			if(eparent.childNodes[i].nodeType == 1){
				return eparent.childNodes[i];
			}
		}
	}
}


/**
 * 将选中的选项从源转到目标
 * @param oFrom
 * @param oTo
 * @return
 */
function moveOptions(oFrom, oTo){
	if(oFrom.selectedIndex<0)
		return false;
	var opt = oFrom.options[oFrom.selectedIndex];
	if(optionExists(opt.value,oTo)){
		return;
	}
	var newOpt = document.createElement("option");
	newOpt.text = opt.text;
	newOpt.value = opt.value;
	oTo.options.add(newOpt);
//	oFrom.options.remove(opt);
	return true;
};

/**
 * 
 */
function Hilight(obj)
{
     obj.runtimeStyle.backgroundColor  = "#c0c0c0";
}

/**
 * 
 * @return
 */
function Restore(obj)
{
     obj.runtimeStyle.backgroundColor  = "#f0f0f0";
}

/**
 * 检查是否为空 object中放置各元素 若有一个不为空 怎么表单可以正常提交
 **/
function validatorSub(object, name, form){
	var temFlag = false;
	var warning = "";
	for(var j = 0; j < object.length; j++ ) {
		if (object[j] != null && object[j].value != undefined && object[j].type != "checkbox" && object[j].value.trim()) {
			temFlag = true;
		}else if( object[j].type == "checkbox" && object[j].checked){
			temFlag = true;
		}else if(j ==(object.length-1)){
			warning = warning+name[j];
		}else{
			warning = warning+name[j]+"或者";
		}
	}
	if (temFlag) {
        form.submit();
	}else{
		alert(warning+"不能同时为空，请重新配置")
		return false;
	}
}