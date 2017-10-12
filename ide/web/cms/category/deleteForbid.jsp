<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<tiles:insert definition="dialog_frame">
  <tiles:put name="javascript">
  </tiles:put>
  <tiles:put name="dialog">
    <script type="text/Javascript" language="Javascript">
    var status=<%=request.getParameter("status")%>;
    if(status==0)
    document.write("该分类系系统级分类，不支持删除。");
    if(status==1)
    document.write("该分类下有子分类，不能删除。");
    if(status==2)
    document.write("该分类下有内容节点，不能删除。");
    if(status==3)
    document.write("该分类被其他分类属性引用，不支持删除。");
  </script>
</tiles:put>
</tiles:insert>
