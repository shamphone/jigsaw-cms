<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="default_main_frame">
    <tiles:put name="body">
      <div class="topNavigation"><span class="navigation">网站统计>>访问量排名</span></div><br><br><br>

      <div align="left">
        <ul style="list-style:none">
      <li>
        <input type="radio" value="byYear" name="statType" />
        选择要统计的年
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
        年 </li>
      <li>
        <input name="statType" type="radio" value="byMonth" />
        选择要统计的季
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
        年
        <select name="monthFromByMonth">
          <option value="1" selected="selected">1</option>
          <option value="2">2</option>
          <option value="3">3</option>
          <option value="4">4</option>
        </select>
        季 </li>
      <li>
        <input name="statType" type="radio" value="byMonth" />
        选择要统计的月
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
        月 </li>
      <li>
        <input name="statType" type="radio" value="byMonth" />
        选择要统计的周
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
        周 </li>
      <li>
        <input name="statType" type="radio" value="byMonth" />
        选择要统计的日
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
        日
      </li>
      <li>
        <input type="radio" checked="checked" value="byDay" name="statType" />
        按时间段统计 从
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
              <td width="350" height="165" valign="top">
                <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
                  <tr>
                    <td colspan="3" width="226" style="font-size:12px; font-weight:bold; color:#FF0000;">总排名</td>
                  </tr>
                  <tr>
                    <td>1</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>2</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>3</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  </table>
              </td>
              <td width="350" height="165" valign="top">
                <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
                  <tr>
                    <td colspan="3" width="226" style="font-size:12px; font-weight:bold; color:#FF0000;">年排名</td>
                  </tr>
                  <tr>
                    <td>1</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>2</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>3</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  </table>
              </td>
            </tr>
          </table>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
            <tr>
              <td width="350" height="165" valign="top">
                <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
                  <tr>
                    <td colspan="3" width="226" style="font-size:12px; font-weight:bold; color:#FF0000;">季排名</td>
                  </tr>
                  <tr>
                    <td>1</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>2</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>3</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  </table>
              </td>
              <td width="350" height="165" valign="top">
                <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
                  <tr>
                    <td colspan="3" width="226" style="font-size:12px; font-weight:bold; color:#FF0000;">月排名</td>
                  </tr>
                  <tr>
                    <td>1</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>2</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>3</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  </table>
              </td>
            </tr>
          </table>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
            <tr>
              <td width="350" height="165" valign="top">
                <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
                  <tr>
                    <td colspan="3" width="226" style="font-size:12px; font-weight:bold; color:#FF0000;">周排名</td>
                  </tr>
                  <tr>
                    <td>1</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>2</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>3</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  </table>
              </td>
              <td width="350" height="165" valign="top">
                <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
                  <tr>
                    <td colspan="3" width="226" style="font-size:12px; font-weight:bold; color:#FF0000;">日排名</td>
                  </tr>
                  <tr>
                    <td>1</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>2</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  <tr>
                    <td>3</td>
                    <td>
                      <a target="_blank" href="/ecommerce/organization/organizationDetail.do?orgID=2370647951407">
                        中国飞扬股份有限公司　
                      </a>
                      <td>
                        <font color="#666666">访问量:739</font>
                      </td>
                    </tr>
                  </table>
              </td>
            </tr>
          </table>
    </tiles:put>
</tiles:insert>
