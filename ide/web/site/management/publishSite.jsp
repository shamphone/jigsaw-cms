<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<tiles:insert definition="default_main_frame">
    <tiles:put name="body">
      <div class="topNavigation"><span class="navigation">静态发布>>待发布网站</span></div><br><br><br>
          <div align="left">
          <form name="searchForm" method="GET" action="/cms/admin/site/search.do">
            <fieldset style="height:45px;">
              <legend>模糊搜索</legend>
              关键字：<input type="text" name="keywords" size="25" value="">&nbsp;&nbsp;<input type="button" value="搜索"/>
                （在网站名称，二级域名，建站机构和卡号中搜索）
            </fieldset>
            网站名称：<input type="text" name="keywords" size="25" value="">&nbsp;&nbsp;
            二级域名：<input type="text" name="keywords" size="25" value="">&nbsp;&nbsp;<br>
            建站机构：<input type="text" name="keywords" size="25" value="">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            卡号：<input type="text" name="keywords" size="25" value="">
          <br>  网站模板：<select name="select">
          <option selected="selected">全部模板</option>
          <option>金卡A模板</option>
          <option>金卡B模板</option>
          <option>金卡C模板</option>
          <option>金卡E模板</option></select>&nbsp;&nbsp;&nbsp;&nbsp;
            状态：<select name="select">
          <option selected="selected">全部</option>
          <option>正常</option>
          <option>停用</option>
          <option>到期</option>
        </select>
        &nbsp;&nbsp;
        到期时间：从<input type="text" name="keywords" size="12" value="">到<input type="text" name="keywords" size="12" value="">
              &nbsp;&nbsp;&nbsp;&nbsp;
              <input type="button" value="搜索"/>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value="自定义列"/>
            <input type="hidden" name="category" value="2329734441353"/>
          </form>
          </div>
      <table width="100%" class="tableClass" cellpadding="0" cellspacing="1">
              <tr>
          <th scope="col"><input type="checkbox" name="checkbox" value="checkbox" /></th>
                <th>网站名称</th>
                <th>二级域名</th>
                <th>建站机构</th>
                <th>卡号</th>
                <th>创建时间</th>
                <th>到期时间</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
            <tbody>
              <form action="delete.do" method="POST">
                <input type="hidden" name="address" value="/admin/site/category.do?category=2329734441353"/>

                  <tr>
                    <td><input type="checkbox" name="checkbox" value="checkbox" /></td>
                    <td><a target="_blank" href="http://answt.acs.gov.cn">安徽省商务厅</a></td>
                    <td>answt</td>
                    <td>

                        <a target="_blank" href="http://www.acs.gov.cn/member/2329734441906.card">
                        安徽省商务厅
                      </a>
                    &nbsp;
                    </td>
                    <td><a target="_blank" href="http://answt.acs.gov.cn">1234134134</a></td>
                    <td>2007年05月18日</td>
                    <td>2007年05月18日</td>
                    <td>正常</td>
                    <td nowrap="nowrap">
                      <a target="_blank" href="http://answt.acs.gov.cn">重新发布</a>
                    </td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" name="checkbox" value="checkbox" /></td>
                    <td><a target="_blank" href="http://answt.acs.gov.cn">安徽省商务厅</a></td>
                    <td>answt</td>
                    <td>

                        <a target="_blank" href="http://www.acs.gov.cn/member/2329734441906.card">
                        安徽省商务厅
                      </a>
                    &nbsp;
                    </td>
                    <td><a target="_blank" href="http://answt.acs.gov.cn">1234134134</a></td>
                    <td>2007年05月18日</td>
                    <td>2007年05月18日</td>
                    <td>停用</td>
                    <td nowrap="nowrap">
                      <a target="_blank" href="http://answt.acs.gov.cn">重新发布</a>
                    </td>
                  </tr>
                  <tr>
                    <td><input type="checkbox" name="checkbox" value="checkbox" /></td>
                    <td><a target="_blank" href="http://answt.acs.gov.cn">安徽省商务厅</a></td>
                    <td>answt</td>
                    <td>

                        <a target="_blank" href="http://www.acs.gov.cn/member/2329734441906.card">
                        安徽省商务厅
                      </a>
                    &nbsp;
                    </td>
                    <td><a target="_blank" href="http://answt.acs.gov.cn">1234134134</a></td>
                    <td>2007年05月18日</td>
                    <td>2007年05月18日</td>
                    <td>到期</td>
                    <td nowrap="nowrap">
                      <a target="_blank" href="http://answt.acs.gov.cn">重新发布</a>
                    </td>
                  </tr>
                  <tfoot>
                    <td colspan="9">
                       每页显示<select name="select">
          <option selected="selected">10</option>
          <option>20</option>
          <option>50</option></select>个，总共20个，当前是第1/3页&nbsp;<a href='' onclick="return false">下一页</a>&nbsp;<a href='' onclick="return false">尾页</a>
                      跳转到 <input size="1" name="pagenum" /><button onclick="goto(this.form)">跳转</button>
                    </td>
                  </tfoot>
              </form>
            </tbody>
          </table>
          <div align="right" class="pager">

          </div>

<script type="text/javascript" language="javascript">
  function doDelete(siteID){
    if(confirm("删除之后无法恢复，确实要删除这些网站？")){
      var address=encodeURI("/admin/site/category.do?category=2329734441353");
      //window.location="delete.do?sites="+siteID+"&address="+address;
    }
  }
  function promptChange(submitter){
    if(confirm("确认修改这些网站的类型?")){
    }else
    {
      submitter.form.category.selectedIndex=0;
    }
  }
  </script>
    </tiles:put>
</tiles:insert>
