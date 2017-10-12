<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="default_main_frame">
    <tiles:put name="body">
<div class="topNavigation"><span class="navigation">网站统计>>网站数量统计</span></div><br><br><br>
  <!--
      <div align="left" style="height:30px;">
        选择要统计的地区：
        <select name="categoryID"><option value="2329734441353" selected="selected">唐山</option>
          <option value="2304149708875">石家庄</option>
          <option value="2284694169577">秦皇岛</option>
          <option value="2304011057187">保定</option>
          <option value="2304011057187">邯郸</option>
          <option value="2304011057187">廊坊</option>
          <option value="2304011057187">沧州</option>
        </select>
      </div>
  -->
      <div align="left">
        <ul>
            <input checked="checked" type="radio" value="byYear" name="time" />按创建时间统计&nbsp;&nbsp;&nbsp;&nbsp;
            <input type="radio" value="byYear" name="time" />按到期时间统计
        </ul>
        <ul style="list-style:none">
      <li>
        <input type="radio" value="byYear" name="statType" />
        按年统计 从
        <select name="yearFromByYear">
          <option value="2006" selected="selected">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
          <option value="2015">2015</option>
        </select>
        年 到
        <select name="yearToByYear">
          <option value="2006" selected="selected">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
          <option value="2015">2015</option>
        </select>
        年 </li>
      <li>
        <input name="statType" type="radio" value="byMonth" />
        按月统计 从
        <select name="yearFromByMonth">
          <option value="2006" selected="selected">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
          <option value="2015">2015</option>
        </select>
        年
        <select name="monthFromByMonth">
          <option value="1" selected="selected">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
        </select>
        月 到
        <select name="yearToByMonth">
          <option value="2006" selected="selected">2006</option>
          <option value="2007">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
          <option value="2015">2015</option>
        </select>
        年
        <select name="monthToByMonth">
          <option value="1" selected="selected">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
        </select>
        月 </li>
      <li>
        <input type="radio" checked="checked" value="byDay" name="statType" />
        按日统计 从
        <select name="yearFromByDay">
          <option value="2006">2006</option>
          <option value="2007" selected="selected">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
          <option value="2015">2015</option>
        </select>
        年
        <select name="monthFromByDay">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8" selected="selected">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
        </select>
        月
        <select name="dayFromByDay">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
          <option value="13">13</option>
          <option value="14">14</option>
          <option value="15">15</option>
          <option value="16">16</option>
          <option value="17">17</option>
          <option value="18">18</option>
          <option value="19">19</option>
          <option value="20">20</option>
          <option value="21">21</option>
          <option value="22">22</option>
          <option value="23" selected="selected">23</option>
          <option value="24">24</option>
          <option value="25">25</option>
          <option value="26">26</option>
          <option value="27">27</option>
          <option value="28">28</option>
          <option value="29">29</option>
          <option value="30">30</option>
          <option value="31">31</option>
        </select>
        日 到
        <select name="yearToByDay">
          <option value="2006">2006</option>
          <option value="2007" selected="selected">2007</option>
          <option value="2008">2008</option>
          <option value="2009">2009</option>
          <option value="2010">2010</option>
          <option value="2011">2011</option>
          <option value="2012">2012</option>
          <option value="2013">2013</option>
          <option value="2014">2014</option>
          <option value="2015">2015</option>
        </select>
        年
        <select name="monthToByDay">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8" selected="selected">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
        </select>
        月
        <select name="dayToByDay">
          <option value="1">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
          <option value="5">5</option>
          <option value="6">6</option>
          <option value="7">7</option>
          <option value="8">8</option>
          <option value="9">9</option>
          <option value="10">10</option>
          <option value="11">11</option>
          <option value="12">12</option>
          <option value="13">13</option>
          <option value="14">14</option>
          <option value="15">15</option>
          <option value="16">16</option>
          <option value="17">17</option>
          <option value="18">18</option>
          <option value="19">19</option>
          <option value="20">20</option>
          <option value="21">21</option>
          <option value="22">22</option>
          <option value="23">23</option>
          <option value="24">24</option>
          <option value="25">25</option>
          <option value="26">26</option>
          <option value="27">27</option>
          <option value="28">28</option>
          <option value="29" selected="selected">29</option>
          <option value="30">30</option>
          <option value="31">31</option>
        </select>
        日      &nbsp;&nbsp;&nbsp;<input type="button" value="开始统计"/> </li>
        </ul>

      </div>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
              <tr>
                <th></th>
                <th>金卡网站</th>
                <th>银卡网站</th>
                <th>成长卡网站</th>
              </tr>
            <tbody>
                  <tr>
                    <td>2004年</td>
                    <td><a href="siteList.jsp">12</a></td>
                    <td><a href="siteList.jsp">12</a></td>
                    <td><a href="siteList.jsp">12</a></td>
                  </tr>
                  <tr>
                    <td>2005年</td>
                    <td><a href="siteList.jsp">12</a></td>
                    <td><a href="siteList.jsp">12</a></td>
                    <td><a href="siteList.jsp">12</a></td>
                  </tr>
                  <tr>
                    <td>2006年</td>
                    <td><a href="siteList.jsp">12</a></td>
                    <td><a href="siteList.jsp">12</a></td>
                    <td><a href="siteList.jsp">12</a></td>
                  </tr>
            </tbody>
          </table>
    </tiles:put>
</tiles:insert>
