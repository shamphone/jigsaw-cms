<%@ page contentType="text/javascript; charset=UTF-8" %>
function Style() {
	this['color']					= "";
	this['font-family'] 			= "";
	this['font-size']				= "";
	this['font-style']				= "";
	this['text-decoration']			= "";
	this['line-height']				= "";
	this['font-weight']				= "";
	this['text-transform']			= "";
	this['background-color']		= "";
	this['background-image']		= "";
	this['background-attachment']	= "";
	this['background-repeat']		= "";
	this['background-position']		= "";
	this['word-spacing']			= "";
	this['letter-spacing']			= "";
	this['text-align']				= "";
	this['vertical-align']			= "";
	this['text-indent']				= "";
	this['white-space']				= "";
	this['display']					= "";
	this['overflow']				= "";
	this['border-color']			= "";
	this['border-style']			= "";
	this['border-size']				= "";
	this['border-top']				= "";
	this['border-bottom']			= "";
	this['border-left']				= "";
	this['border-right']			= "";
	this['margin-top']				= "";
	this['margin-bottom']			= "";
	this['margin-left']				= "";
	this['margin-right']			= "";
	this['padding-top']				= "";
	this['padding-bottom']			= "";
	this['padding-left']			= "";
	this['padding-right']			= "";
	this['width']					= "";
	this['height']					= "";
	this['float']					= "";
	this['visibility']				= "";
	this['position']				= "";
	this['top']						= "";
	this['left']					= "";
	this['z-index']					= "";
	this['clip']					= "";
	this['list-style-type']			= "";
	this['list-style-image']		= "";
	this['list-style-position']		= "";
	this['cursor']					= "";
	this['behavior']				= "";
	this['filter']					= "";
}
Style.prototype.toString = function() {
	var arr = [];
	for (var e in this) {
		if (this[e] && typeof(this[e]) != "function")
			arr.push(e + ": " + this[e]);
	}
	return arr.join("; ");
}
