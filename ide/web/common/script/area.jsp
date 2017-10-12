<%@ page contentType="text/javascript; charset=UTF-8" %>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
//当前脚本依赖于common.js,ajax.js
/**
 * 选择国家
 */
  function changeCountry(countryS,provinceS){
    var country = document.getElementById( (countryS==null)?"country":countryS );
var province = document.getElementById( (provinceS==null)?"province":provinceS );
var form = country.form;
  countryTemp = country.options[country.selectedIndex].value;
  removeAll(province);
  var newOption=document.createElement("option");
  newOption.value="";
  newOption.text="请选择...";
  province.add(newOption);
  populate_select("<html:rewrite module="/cms" page="/ajaxEntry.do"/>?type=region&parent_code=" + countryTemp,province,false);
      if(form.area!=null){
      changeProvince();
      }
  }
/**
 * 选择省
 */
  function changeProvince(provinceS,cityS){
    var province = document.getElementById( (provinceS==null)?"province":provinceS );
var city = document.getElementById( (cityS==null)?"city":cityS );
var form = province.form;
  provinceTemp = province.options[province.selectedIndex].value;
  removeAll(city);
  var newOption=document.createElement("option");
  newOption.value="";
  newOption.text="请选择...";
  city.add(newOption);
  populate_select("<html:rewrite module="/cms" page="/ajaxEntry.do"/>?type=region&parent_code=" + provinceTemp,city,false);
      if(form.area!=null){
      changeCity();
      }
  }
 /**
 * 选择市
 */
  function changeCity(cityS)
  {
    var city = document.getElementById( (cityS==null)?"city":cityS );
var form = city.form;
  cityTemp = form.city.options[form.city.selectedIndex].value;
  if(form.area!=null){
    removeAll(form.area);
  }else if(form.areaS!=null){
    removeAll(form.areaS);
  }
  var newOption=document.createElement("option");
  newOption.value="";
  newOption.text="请选择...";
  if(form.area!=null){
  form.area.add(newOption);
  populate_select("<html:rewrite module="/cms" page="/ajaxEntry.do"/>?type=region&parent_code=" + cityTemp,form.area,false);
  }
  else if(form.areaS!=null){
  form.areaS.add(newOption);
  populate_select("<html:rewrite module="/cms" page="/ajaxEntry.do"/>?type=region&parent_code=" + cityTemp,form.areaS,false);
      }
  }
 /**
 * 选择地区
 */
function changeArea(areaS)
{
    var area = document.getElementById( (areaS==null)?"area":areaS );
var county = document.getElementById( "county" );
for (i=0; i<area.options.length; i++){
    if(area.options[i].selected){
        if(area.options[i].value!=""){
        county.value=area.options[i].text;
        }else{
        county.value="";
                    }
                }
            }
        }
        
/**
 * 设置省
 */
function setProvince(object,value){
    document.getElementById( object ).options.value=value;
    changeProvince();
}
/**
 * 设置市
 */
function setCity(object,value){
    document.getElementById( object ).options.value=value;
    changeCity();
}
/**
 * 设置地区
 */
function setArea(object,value){
    document.getElementById( object ).options.value=value;
}
