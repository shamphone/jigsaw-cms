<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="default_main_frame">
    <tiles:put name="body">
<div class="topNavigation"><span class="navigation">网站统计>>按时间统计</span></div><br /><br /><br>
      <div align="left">
        按创建时间以
        <select name="unit"><option value="年" selected="selected">年</option>
          <option value="月">月</option>
          <option value="周">周</option>
          <option value="日">日</option>
        </select>
        为单位统计自<input type="text" name="year" size="4" value="2004">年起
        <input type="text" name="keywords" size="4" value="3">年内的网站数据
        &nbsp;&nbsp;&nbsp;<input type="button" value="开始统计"/>
      </div>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
              <tr>
                <th></th>
                <th>唐山</th>
                <th>石家庄</th>
                <th>秦皇岛</th>
                <th>保定</th>
                <th>邯郸</th>
                <th>廊坊</th>
                <th>沧州</th>
              </tr>
            <tbody>
                  <tr>
                    <td>金卡网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
                  <tr>
                    <td>银卡网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
                  <tr>
                    <td>成长卡网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
                  <tr>
                    <td>自定义网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
            </tbody>
          </table>
      <div align="left">
        按到期时间以
        <select name="unit"><option value="年" selected="selected">年</option>
          <option value="月">月</option>
          <option value="周">周</option>
          <option value="日">日</option>
        </select>
        为单位统计自<input type="text" name="year" size="4" value="2004">年起
        <input type="text" name="keywords" size="4" value="3">年内的网站数据
        &nbsp;&nbsp;&nbsp;<input type="button" value="开始统计"/>
      </div>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
              <tr>
                <th></th>
                <th>唐山</th>
                <th>石家庄</th>
                <th>秦皇岛</th>
                <th>保定</th>
                <th>邯郸</th>
                <th>廊坊</th>
                <th>沧州</th>
              </tr>
            <tbody>
                  <tr>
                    <td>金卡网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
                  <tr>
                    <td>银卡网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
                  <tr>
                    <td>成长卡网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
                  <tr>
                    <td>自定义网站</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                    <td>12</td>
                  </tr>
            </tbody>
          </table>
    </tiles:put>
</tiles:insert>
