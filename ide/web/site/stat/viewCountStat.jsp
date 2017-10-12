<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="default_main_frame">
    <tiles:put name="body">
      <div class="topNavigation"><span class="navigation">网站统计>>访问量统计</span></div><br><br><br>
<p><font size="3">网站访问量统计</p>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
  <tr>
    <th scope="row">网站范围</th>
    <td><ul>
      <li>
        <input type="radio" name="radiobutton" value="radiobutton" />
        自选网站：
        <select name="select" size="5">
          <option>中国产业安全指南网</option>
        </select>
        <input type="button" name="Submit" value="添加..." onclick="window.open('addCountSite.jsp','添加自选网站','height=220,width=450,toolbar=no,scrollbars=no,menubar=no')" />
        <input type="button" name="Submit2" value="移除" />
      </li>
    </ul>    </td>
  </tr>
  <tr>
    <th scope="row">时间范围</th>
    <td><ul>
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
        按天统计 从
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
        日      </li>
    </ul>    </td>
  </tr>
<tr>
    <th scope="row">&nbsp;</th>
    <td align="center"><input name="submit" type="button" value="统计" /></td>
  </tr></table>
<p>统计结果</p>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
  <thead>
    <tr>
      <td>网站 </td>
      <td align="middle">2007-08-23 </td>
      <td align="middle">2007-08-24 </td>
      <td align="middle">2007-08-25 </td>
      <td align="middle">2007-08-26 </td>
      <td align="middle">2007-08-27 </td>
      <td align="middle">2007-08-28 </td>
      <td align="middle">2007-08-29 </td>
      <td align="middle">总计 </td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <td>安徽省商务厅</td>
      <td align="right">0 </td>
      <td align="right">7 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">1 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">8 </td>
    </tr>
    <tr>
      <td>北京某机构</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>海南省商务厅</td>
      <td align="right">0 </td>
      <td align="right">1 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">1 </td>
    </tr>
    <tr>
      <td>河南省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国氮肥工业协会</td>
      <td align="right">0 </td>
      <td align="right">3 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">3 </td>
    </tr>
    <tr>
      <td>中国皮革工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国淀粉工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国深圳市世贸组织事务中心</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国磷肥工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国机床工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国汽车工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国钢铁工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国纺织工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国机械工业联合会</td>
      <td align="right">0 </td>
      <td align="right">2 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">2 </td>
    </tr>
    <tr>
      <td>中国石油和化学工业协会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>浙江省经贸委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>云南省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>厦门经发局</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>新疆建设兵团商务局</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>天津市经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>上海经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>深圳市贸易工业局</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>陕西省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>山西省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>山东省经贸委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>四川省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>青海省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>青岛市经济委员会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>宁夏回族自治区商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>宁波市经济委员会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>内蒙古商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>辽宁省经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>吉林省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>江苏省经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>江西省经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>湖南省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>湖北省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>黑龙江省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>河北省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>贵州省经贸委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>广西壮族自治区商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>甘肃省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>福建省外经贸厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>大连市对外贸易经济合作局</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>重庆市经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>北京商务局</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>广东省外经贸厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>广东省经委</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>新疆维吾尔自治区经济贸易委员会</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>安徽省商务厅</td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
      <td align="right">0 </td>
    </tr>
    <tr>
      <td>中国产业安全指南网</td>
      <td align="right">8,371 </td>
      <td align="right">6,569 </td>
      <td align="right">4,020 </td>
      <td align="right">3,010 </td>
      <td align="right">7,280 </td>
      <td align="right">8,494 </td>
      <td align="right">5,548 </td>
      <td align="right">43,292 </td>
    </tr>
    <tr>
      <td>总计 </td>
      <td align="right">8,371 </td>
      <td align="right">6,582 </td>
      <td align="right">4,020 </td>
      <td align="right">3,010 </td>
      <td align="right">7,281 </td>
      <td align="right">8,494 </td>
      <td align="right">5,548 </td>
      <td align="right">43,306 </td>
    </tr>
  </tbody>
</table>
<!--endprint-->
<p>
  <input type="button" value="打印" name="print" />
  <input type="button" value="转存为excel" name="download" />
</p>
    </tiles:put>
</tiles:insert>
