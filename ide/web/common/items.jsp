<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
 "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>会员管理系统--使用条款</title>
<META http-equiv=Content-Type content="text/html; charset=UTF-8">
<META HTTP-EQUIV="Expires" CONTENT="Mon, 04 Dec 2999 21:29:02 GMT">
<link rel="stylesheet" type="text/css" href="<html:rewrite module="/common" page="/style/main2.css"/>">
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/common.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/stat.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/ajax.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/area.jsp"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/data.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/industry.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/form.js"/>"></script>
<script language="Javascript" type="text/Javascript" src="<html:rewrite module="/common" page="/script/sortControl.js"/>"></script>
</head>
<body>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="headerBack">
  <tr>
    <td width="21%" rowspan="2"><img width="216" height="75" border="0" src="<html:rewrite module='' page='/images/logo.gif'/>" ></img></td>
    <td width="46%" height="35">&nbsp;</td>
    <td width="33%">&nbsp;</td>
  </tr>
  <tr>
    <td height="40" align="center">

    </td>
    <td valign="bottom">
      <table width="253" height="33" border="0" align="right" cellpadding="0" cellspacing="0" id="headerBackButtom" >
        <tr>
          <td width="28"><img src="../member/images/space.gif" border="0" width="1" height="1"/></td>
          <td width="161" >
              <%
              if(request.getUserPrincipal()!=null)

              {String userID = (String)request.getUserPrincipal().getName();%>
              欢迎您,
              <fulong:name name="principal"></fulong:name>
              <%}%>
          </td>
          <td width="64">
              <%
              if(request.getUserPrincipal()!=null)
              {%>
              <a href="../member/logout.do?fromURL=<%=request.getParameter("fromURL")%>"><img src="../member/images/sme1_041.gif" border=""  width="50" height="22"/></a><img src="../member/images/space.gif" width="1" height="1"/>
               <%}%>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="235" rowspan="2" valign="top" id="leftNav">&nbsp;    </td>
<td height="5" colspan="2"><table width="100%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="7" height="5" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_06.gif"/>" height="5" width="7" alt=""></td>
    <td align="center" id="contentTop" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="1" width="1" alt=""></td>
    <td width="7" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_10.gif"/>" height="5" width="9" alt=""></td>
  </tr>
</table></td>
</tr>
<tr>
  <td colspan="2" bgcolor="#FFFFFF" valign="top"><table width="100%" height="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="7" align="center" id="contentLeft" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="1" width="1" alt=""></td>
      <td height="510" id="rightArea" align="left" valign="top">
      <div id="maincontent">
                    <table border="0" cellspacing="0" cellpadding="3">
                        <tr>
                            <td valign="top"><img src="<html:rewrite module="/common" page="/images/count-service.gif"/>"></img></td>
                            <td><h2 class="cssHeader"><fulong:config name="system.name"/>会员管理</h2>
                                <span class="tips">请您仔细阅读使用条款。</span>
                            </td>
                        </tr>
                    </table>
                    <ul id="comment">
                        <li class="title">一 特别提示 </li>
                        <li class="text"> 1 <fulong:config name="system.name"/>同意按照本协议的规定及其不时发布的操作规则提供基于互联网以及移动网的相关服务（以下称 &quot; 网络服务 &quot; ），为获得网络服务，服务使用人（以下称 &quot; 用户 &quot; ）应当同意本协议的全部条款并按照页面上的提示完成全部的注册程序。用户在进行注册程序过程中点击  &quot; 同意 &quot; 按钮即表示用户完全接受本协议项下的全部条款。  </li>
                        <li class="text"> 2 用户注册成功后，<fulong:config name="system.name"/>将给予每个用户一个用户帐号及相应的密码，该用户帐号和密码由用户负责保管；用户应当对以其用户帐号进行的所有活动和事件负法律责任。若密码丢失需要找回，企业用户应向<fulong:config name="system.name"/>提供企业营业执照复印件，个人用户应向<fulong:config name="system.name"/>提供身份证明。  </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">二 服务内容 </li>
                        <li class="text"> 1 <fulong:config name="system.name"/>网络服务的具体内容由<fulong:config name="system.name"/>根据实际情况提供，例如自助建站、域名注册、在线支付、搜索、论坛  (BBS) 、电子邮件、发表新闻评论等。  </li>
                        <li class="text"> 2 <fulong:config name="system.name"/>提供的部分网络服务（例如域名注册、自助建站、电子邮件等）为收费的网络服务，用户使用收费网络服务需要向<fulong:config name="system.name"/>支付一定的费用。对于收费的网络服务，<fulong:config name="system.name"/>会在用户使用之前给予用户明确的提示，只有用户根据提示确认其愿意支付相关费用，用户才能使用该等收费网络服务。如用户拒绝支付相关费用，则<fulong:config name="system.name"/>有权不向用户提供该等收费网络服务。 </li>
                        <li class="text"> 3 用户理解，<fulong:config name="system.name"/>仅提供相关的网络服务，除此之外与相关网络服务有关的设备（如个人电脑、手机、及其他与接入互联网或移动网有关的装置）及所需的费用（如为接入互联网而支付的电话费及上网费、为使用移动网而支付的手机费）均应由用户自行负担。</li>
                        <li class="text"> 4 <fulong:config name="system.name"/>交易平台补充说明 </li>
                        <li class="text"> 交易平台仅作为交易地点。 </li>
                        <li class="text"> 本公司网站仅作为用户物色交易对象，就货物和服务的交易进行协商，以及获取各类与贸易相关的服务的地点。但是，本公司不能控制交易所涉及的物品的质量、安全或合法性，商贸信息的真实性或准确性，以及交易方履行其在贸易协议项下的各项义务的能力。本公司不能也不会控制交易各方能否履行协议义务。此外，您应注意到，与外国国民、未成年人或以欺诈手段行事的人进行交易的风险是客观存在的。 </li>
                        <li class="text"> 您的资料和供买卖的物品。 </li>
                        <li class="text"> “您的资料”包括您在注册、交易或列举物品过程中、在任何公开信息场合或通过任何电子邮件形式，向本公司或其他用户提供的任何资料，包括数据、文本、软件、音乐、声响、照片、图画、影像、词句或其他材料。您应对“您的资料”负全部责任，而本公司仅作为您在网上发布和刊登“您的资料”的被动渠道。但是，倘若本公司认为“您的资料”可能使本公司承担任何法律或道义上的责任，或可能使本公司( 全部或部分地 ) 失去本公司的互联网服务供应商或其他供应商的服务，或您未在<fulong:config name="system.name"/>交易平台规定的期限内登录或再次登录网站，则本公司可自行全权决定对“您的资料”采取本公司认为必要或适当的任何行动，包括但不限于删除该类资料。您特此保证，您对提交给<fulong:config name="system.name"/>交易平台的“您的资料”拥有全部权利，包括全部版权。您确认，<fulong:config name="system.name"/>没有责任去认定或决定您提交的资料哪些是应当受到保护的，对享有“服务”的其他用户使用“您的资料”，本公司也不必负责。</li>
                        <li class="text"> 被禁止物品。</li>
                        <li class="text"> 您不得在本公司网站公布或通过本公司网站买卖： </li>
                        <li class="text">    (a) 可能使<fulong:config name="system.name"/>违反任何相关法律、法规、条例或规章的任何物品；</li>
                        <li class="text">(b)  <fulong:config name="system.name"/>认为应禁止或不适合通过本网站买卖的任何物品。 </li></li>
                    </ul>
                    <ul id="comment">
                        <li class="title">三 服务变更、中断或终止</li>
                        <li class="text"> 1 鉴于网络服务的特殊性，用户同意<fulong:config name="system.name"/>有权随时变更、中断或终止部分或全部的网络服务（包括收费网络服务）。如变更、中断或终止的网络服务属于免费网络服务，<fulong:config name="system.name"/>无需通知用户，也无需对任何用户或任何第三方承担任何责任；如变更、中断或终止的网络服务属于收费网络服务，<fulong:config name="system.name"/>应当在变更、中断或终止之前事先通知用户，并应向受影响的用户提供等值的替代性的收费网络服务，如用户不愿意接受替代性的收费网络服务，就该用户已经向<fulong:config name="system.name"/>支付的服务费，<fulong:config name="system.name"/>应当按照该用户实际使用相应收费网络服务的情况扣除相应服务费之后将剩余的服务费退还给该用户。   </li>
                        <li class="text"> 2 用户理解，<fulong:config name="system.name"/>需要定期或不定期地对提供网络服务的平台（如互联网网站、自助建站系统，产品交易系统等）或相关的设备进行检修或者维护，如因此类情况而造成收费网络服务在合理时间内的中断，<fulong:config name="system.name"/>无需为此承担任何责任，但<fulong:config name="system.name"/>应尽可能事先进行通告。</li>
                        <li class="text"> 3 如发生下列任何一种情形，<fulong:config name="system.name"/>有权随时中断或终止向用户提供本协议项下的网络服务（包括收费网络服务）而无需对用户或任何第三方承担任何责任：</li>
                        <li class="text"> 用户提供的企业或个人资料不真实； </li>
                        <li class="text"> 用户违反本协议中规定的使用规则；</li>
                        <li class="text"> 用户在使用收费网络服务时未按规定向<fulong:config name="system.name"/>支付相应的服务费。 </li>
                        <li class="text"> 4 如用户注册的免费网络服务的帐号在任何连续 180日内未实际使用，或者用户注册的收费网络服务的帐号在其订购的收费网络服务的服务期满之后连续 180 日内未实际使用，则<fulong:config name="system.name"/>有权删除该帐号并停止为该用户提供相关的网络服务。  </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">四 使用规则</li>
                        <li class="text"> 1 用户在申请使用<fulong:config name="system.name"/>网络服务时，必须向<fulong:config name="system.name"/>提供准确的个人资料，如个人资料有任何变动，必须及时更新。  </li>
                        <li class="text"> 2 用户不应将其帐号、密码转让或出借予他人使用。如用户发现其帐号遭他人非法使用，应立即通知<fulong:config name="system.name"/>。因黑客行为或用户的保管疏忽导致帐号、密码遭他人非法使用，<fulong:config name="system.name"/>不承担任何责任。  </li>
                        <li class="text"> 3 用户必须同意接受<fulong:config name="system.name"/>通过电子邮件或其他方式向用户发送商品促销或其他相关商业信息。 </li>
                        <li class="text"> 4 对于用户通过<fulong:config name="system.name"/>网络服务（包括但不限于论坛、 BBS  、新闻评论）上传到<fulong:config name="system.name"/>网站上可公开获取区域的任何内容，用户同意<fulong:config name="system.name"/>在全世界范围内具有免费的、永久性的、不可撤销的、非独家的和完全再许可的权利和许可，以使用、复制、修改、改编、出版、翻译、据以创作衍生作品、传播、表演和展示此等内容（整体或部分），和 / 或将此等内容编入当前已知的或以后开发的其他任何形式的作品、媒体或技术中。   </li>
                        <li class="text"> 为维护社会公众的利益；为维护<fulong:config name="system.name"/>的合法权益。</li>
                        <li class="text"> 5 用户在使用<fulong:config name="system.name"/>网络服务过程中，必须遵循以下原则： </li>
                        <li class="text"> 遵守中国有关的法律和法规； </li>
                        <li class="text"> 遵守所有与网络服务有关的网络协议、规定和程序； </li>
                        <li class="text"> 不得为任何非法目的而使用网络服务系统；</li>
                        <li class="text"> 不得利用<fulong:config name="system.name"/>网络服务系统进行任何可能对互联网或移动网正常运转造成不利影响的行为；</li>
                        <li class="text"> 不得利用<fulong:config name="system.name"/>提供的网络服务上传、展示或传播任何虚假的、骚扰性的、中伤他人的、辱骂性的、恐吓性的、庸俗淫秽的或其他任何非法的信息资料 </li>
                        <li class="text"> 不得侵犯其他任何第三方的专利权、著作权、商标权、名誉权或其他任何合法权益； </li>
                        <li class="text"> 不得利用<fulong:config name="system.name"/>网络服务系统进行任何不利于<fulong:config name="system.name"/>的行为； </li>
                        <li class="text"> 如发现任何非法使用用户帐号或帐号出现安全漏洞的情况，应立即通告<fulong:config name="system.name"/>。</li>
                        <li class="text"> 6 如用户在使用网络服务时违反任何上述规定，<fulong:config name="system.name"/>或其授权的人有权要求用户改正或直接采取一切必要的措施（包括但不限于更改或删除用户张贴的内容等、暂停或终止用户使用网络服务的权利）以减轻用户不当行为造成的影响。 </li>
                        <li class="text"> 7 <fulong:config name="system.name"/>针对某些特定的<fulong:config name="system.name"/>网络服务的使用通过各种方式（包括但不限于网页公告、电子邮件、短信提醒等）作出的任何声明、通知、警示等内容视为本协议的一部分，用户如使用该等<fulong:config name="system.name"/>网络服务，视为用户同意该等声明、通知、警示的内容。 </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">五 知识产权 </li>
                        <li class="text"> 1 <fulong:config name="system.name"/>提供的网络服务中包含的任何文本、图片、图形、音频和 / 或视频资料均受版权、商标和 / 或其它财产所有权法律的保护，未经相关权利人同意，上述资料均不得在任何媒体直接或间接发布、播放、出于播放或发布目的而改写或再发行，或者被用于其他任何商业目的。所有这些资料或资料的任何部分仅可作为私人和非商业用途而保存在某台计算机内。<fulong:config name="system.name"/>不就由上述资料产生或在传送或递交全部或部分上述资料过程中产生的延误、不准确、错误和遗漏或从中产生或由此产生的任何损害赔偿，以任何形式，向用户或任何第三方负责。 </li>
                        <li class="text"> 2 <fulong:config name="system.name"/>为提供网络服务而使用的任何软件（包括但不限于软件中所含的任何图象、照片、动画、录像、录音、音乐、文字和附加程序、随附的帮助材料）的一切权利均属于该软件的著作权人，未经该软件的著作权人许可，用户不得对该软件进行反向工程（ reverse engineer ）、反向编译（ decompile ）或反汇编（ disassemble ）。</li>
                    </ul>
                    <ul id="comment">
                        <li class="title">六 隐私保护 </li>
                        <li class="text"> 1 保护用户隐私是<fulong:config name="system.name"/>的一项基本政策，<fulong:config name="system.name"/>保证不对外公开或向第三方提供单个用户的注册资料及用户在使用网络服务时存储在<fulong:config name="system.name"/>的非公开内容，但下列情况除外： </li>
                        <li class="text"> 事先获得用户的明确授权； </li>
                        <li class="text"> 根据有关的法律法规要求； </li>
                        <li class="text"> 按照相关政府主管部门的要求；  </li>
                        <li class="text"> 为维护社会公众的利益；为维护<fulong:config name="system.name"/>的合法权益。</li>
                        <li class="text"> 2 <fulong:config name="system.name"/>可能会与第三方合作向用户提供相关的网络服务，在此情况下，如该第三方同意承担与<fulong:config name="system.name"/>同等的保护用户隐私的责任，则<fulong:config name="system.name"/>有权将用户的注册资料等提供给该第三方。</li>
                        <li class="text"> 3 在不透露单个用户隐私资料的前提下，<fulong:config name="system.name"/>有权对整个用户数据库进行分析并对用户数据库进行商业上的利用。</li>
                    </ul>
                    <ul id="comment">
                        <li class="title">七 免责声明 </li>
                        <li class="text"> 1 用户明确同意其使用<fulong:config name="system.name"/>网络服务所存在的风险将完全由其自己承担；因其使用<fulong:config name="system.name"/>网络服务而产生的一切后果也由其自己承担，<fulong:config name="system.name"/>对用户不承担任何责任。 </li>
                        <li class="text"> 2 <fulong:config name="system.name"/>不担保网络服务一定能满足用户的要求，也不担保网络服务不会中断，对网络服务的及时性、安全性、准确性也都不作担保。 </li>
                        <li class="text"> 3 对于因不可抗力或<fulong:config name="system.name"/>不能控制的原因造成的网络服务中断或其它缺陷，<fulong:config name="system.name"/>不承担任何责任，但将尽力减少因此而给用户造成的损失和影响。 </li>
                        <li class="text"> 4 用户同意，对于<fulong:config name="system.name"/>向用户提供的下列产品或者服务的质量缺陷本身及其引发的任何损失，<fulong:config name="system.name"/>无需承担任何责任：  </li>
                        <li class="text"> <fulong:config name="system.name"/>向用户免费提供的各项网络服务； </li>
                        <li class="text"> <fulong:config name="system.name"/>向用户赠送的任何产品或者服务； </li>
                        <li class="text"> <fulong:config name="system.name"/>向收费网络服务用户附赠的各种产品或者服务。</li>
                    </ul>
                    <ul id="comment">
                        <li class="title">八 违约赔偿</li>
                        <li class="text"> 1 如因<fulong:config name="system.name"/>违反有关法律、法规或本协议项下的任何条款而给用户造成损失，<fulong:config name="system.name"/>同意承担由此造成的损害赔偿责任。  </li>
                        <li class="text"> 2 用户同意保障和维护<fulong:config name="system.name"/>及其他用户的利益，如因用户违反有关法律、法规或本协议项下的任何条款而给<fulong:config name="system.name"/>或任何其他第三人造成损失，用户同意承担由此造成的损害赔偿责任。 </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">九 协议修改</li>
                        <li class="text"> 1 <fulong:config name="system.name"/>有权随时修改本协议的任何条款，一旦本协议的内容发生变动，<fulong:config name="system.name"/>将会通过适当方式向用户提示修改内容。  </li>
                        <li class="text"> 2 如果不同意<fulong:config name="system.name"/>对本协议相关条款所做的修改，用户有权停止使用网络服务。如果用户继续使用网络服务，则视为用户接受<fulong:config name="system.name"/>对本协议相关条款所做的修改。  </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">十 通知送达</li>
                        <li class="text"> 1 本协议项下<fulong:config name="system.name"/>对于用户所有的通知均可通过网页公告、电子邮件、手机短信或常规的信件传送等方式进行；该等通知于发送之日视为已送达收件人。 </li>
                        <li class="text"> 2 用户对于<fulong:config name="system.name"/>的通知应当通过<fulong:config name="system.name"/>对外正式公布的通信地址、传真号码、电子邮件地址等联系信息进行送达。  </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">十一 法律管辖</li>
                        <li class="text"> 1 本协议的订立、执行和解释及争议的解决均应适用中国法律并受中国法院管辖。</li>
                        <li class="text"> 2 如双方就本协议内容或其执行发生任何争议，双方应尽量友好协商解决；协商不成时，任何一方均可向<fulong:config name="system.name"/>所在地的人民法院提起诉讼。  </li>
                    </ul>
                    <ul id="comment">
                        <li class="title">十二 其他规定</li>
                        <li class="text"> 1 本协议构成双方对本协议之约定事项及其他有关事宜的完整协议，除本协议规定的之外，未赋予本协议各方其他权利。</li>
                        <li class="text"> 2 如本协议中的任何条款无论因何种原因完全或部分无效或不具有执行力，本协议的其余条款仍应有效并且有约束力。 </li>
                        <li class="text"> 3 本协议中的标题仅为方便而设，在解释本协议时应被忽略。 </li>
                    </ul>
                </div>
      </td>
      <td width="7" align="center" id="contentRight" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="8" width="8"></td>
    </tr>
  </table>
  <table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
      <td width="7" height="7" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_18.gif"/>/member/images/sme1_18.gif" height="7" width="7" alt=""></td>
      <td align="center" id="contentButtom" ><img src="<html:rewrite module="/common" page="/images/space.gif"/>" height="1" width="1"></td>
      <td width="7" align="center"><img src="<html:rewrite module="/common" page="/images/sme1_22.gif"/>" height="7" width="8" alt=""></td>
    </tr>
  </table></td>
</tr>
</table>
	  <table bgcolor="#ede9d7" width="100%" height="45" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td>
      <table align="center" width="250" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td>
            <a href="/common/items.jsp">使用条款</a>
            |
            <a href="/common/link.jsp">联系方式</a>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</body>
</html>
