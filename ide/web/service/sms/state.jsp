<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%><%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<bean:define id="cstate" name="service" property="state"/><bean:message  key='<%= "Service.SMS.State."+cstate %>' />

