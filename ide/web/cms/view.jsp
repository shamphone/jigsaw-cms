<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles"%>
<%@taglib uri="/WEB-INF/fulong.tld" prefix="fulong"%>
<%@taglib uri="/WEB-INF/fulong-cms.tld" prefix="cms"%>

<object classid="clsid:6BF52A52-394A-11D3-B153-00C04F79FAA6" id="video" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701" width="410" height="320">
  <param name="URL" value='<cms:node name="node" propertyName="courseware-media-file" ignore="true" format="#"/>'/>
  <param name="rate" value="1"/>
  <param name="balance" value="0"/>
  <param name="currentPosition" value="0"/>
  <param name="playCount" value="1"/>
  <param name="autoStart" value="true"/>
  <param name="currentMarker" value="0"/>
  <param name="invokeURLs" value="-1"/>
  <param name="volume" value="50"/>
  <param name="mute" value="0"/>
  <param name="uiMode" value="full"/>
  <param name="stretchToFit" value="0"/>
  <param name="windowLessVideo" value="0"/>
  <param name="enabled" value="true"/>
  <param name="enableContextMenu" value="true"/>
  <param name="fullScreen" value="0"/>
  <param name="enableErrorDialogs" value="0"/>
  <param name='AnimationAtStart' value='false'/>
  <param name='ShowControls' value='0'/>
  <param name='ClickToPlay' value='0'/>
  <param name='ShowStatusBar' value='0'/>
  <param name="EnableFullScreenControls" value="1">

</object>
