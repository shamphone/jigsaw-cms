package com.fulong.cms.form;

import org.apache.struts.action.ActionForm;
/**
 * 
 *   
 * 
 * Coolink协同工作框架模型 
 *
 * Copyright: 2009-2011 北京中科辅龙计算机技术股份有限公司
 *
 * Company: 北京中科辅龙计算机技术股份有限公司
 *
 * @author lixf,lichengzhao
 *
 * @version 2.0
 *
 */
public class WordArtForm extends ActionForm {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2744908871785780072L;
	private String text;
    private int effect;
    private float fontSize;
    private boolean fontBold;
    private boolean fontItalic;
    private float left;
    private float top;
    private String fontFamily;

    public String getText() {
        return text;
    }

    public int getEffect() {
        return effect;
    }

    public float getFontSize() {
        return fontSize;
    }

    public boolean isFontBold() {
        return fontBold;
    }

    public boolean isFontItalic() {
        return fontItalic;
    }

    public float getLeft() {
        return left;
    }

    public float getTop() {
        return top;
    }

    public String getFontFamily() {
        return fontFamily;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public void setFontSize(float fontSize) {
        this.fontSize = fontSize;
    }

    public void setFontBold(boolean fontBold) {
        this.fontBold = fontBold;
    }

    public void setFontItalic(boolean fontItalic) {
        this.fontItalic = fontItalic;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

}
