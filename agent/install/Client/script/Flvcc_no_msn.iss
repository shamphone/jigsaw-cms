; FILE:         longCon Vcs.iss
; AUTHOR:       饶上荣
; FOR:          longCon Vcs
; VERSION:      2.0
; DESCRIPTION:  longCon Vcs Win32 安装文件 (My Inno Setup Extensions 3.0.2(beta))
; HISTORY:
;       Sep. 9, 2003  饶上荣    创建此文件
;       Nov. 2004     饶上荣    modify for second version
;       Feb. 2005     张小潘    Modify for second version
;       Jun. 2005     王晓良    modify for second version
; NOTES:
;       版本号出现在[Setup]AppVerName和[Registry]"Version"
;

[Setup]
AppName =CooLink即时通讯模块V2.0
AppVerName =LongCon VCS V2.0
AppPublisher=北京中科辅龙计算机技术股份有限公司
AppPublisherURL=http://www.fulong.com.cn
AppSupportURL=http://www.fulong.com.cn
AppUpdatesURL=http://www.fulong.com.cn
AppCopyright=Copyright 2004 北京中科辅龙计算机技术股份有限公司.
DefaultDirName ={pf}\LongCon VCS V2
DefaultGroupName =LongCon VCS V2
SourceDir=..\..\..
OutputDir=install\Client
OutputBaseFileName =LongConVCS_Setup
UninstallDisplayIcon ={app}\bin\Flvcc.exe
UninstallDisplayName =LongCon VCS V2.0
LicenseFile=install\License\licenseLongCon.txt

[Languages]
Name: chs; MessagesFile: install\language\ChineseSimp-7-lsc-4.2.2.isl

[Tasks]
Name: desktopicon; Description: 创建桌面快捷方式; GroupDescription: 附加快捷方式:
Name: quicklaunchicon; Description: 创建快速启动快捷方式; GroupDescription: 附加快捷方式:
Name: autorun; Description: 登录Windows后自动运行; GroupDescription: 其他:

[Files]
source: src\Client\Release\FvsAudioPlay.exe; DestDir: {app}\bin; Flags: ignoreversion
source: src\Client\Release\Flvcc.exe; DestDir: {app}\bin; Flags: ignoreversion
source: src\Client\Release\wm_hooks.dll; DestDir: {app}\bin; Flags: ignoreversion
source: src\Video\Dump\Release\Dump.ax; DestDir: {app}\bin; Flags: ignoreversion
source: install\VCRedist\Releasedll\MFC71.dll; DestDir: {sys}; Flags: onlyifdoesntexist uninsneveruninstall
source: install\VCRedist\Releasedll\MSVCR71.dll; DestDir: {sys}; Flags: onlyifdoesntexist uninsneveruninstall
source: install\VCRedist\Releasedll\MSVCP71.dll; DestDir: {sys}; Flags: onlyifdoesntexist uninsneveruninstall
source: install\VCRedist\Releasedll\TklSdkAll.dll; DestDir: {app}\bin; Flags: ignoreversion
source: install\VCRedist\Releasedll\TklSdkAve68k.dll; DestDir: {app}\bin; Flags: ignoreversion
source: install\VCRedist\Releasedll\Decoder6ks.dll; DestDir: {app}\bin; Flags: ignoreversion
source: install\VCRedist\Releasedll\TKL_Player.dll; DestDir: {app}\bin; Flags: ignoreversion
source: src\Client\conf\Flvcc.ini.template; DestDir: {app}\conf; DestName: Flvcc.ini; Flags: ignoreversion
source: src\Client\conf\history.ini.template; DestDir: {app}\conf; DestName: history.ini; Flags: ignoreversion
source: src\WebLauncher\WebLauncher\Release\WebLauncher.ocx; DestDir: {sys}; Flags: ignoreversion uninsneveruninstall
source: src\Client\data\confnotice.xsl; DestDir: {app}\data; Flags: ignoreversion
source: src\Client\data\leaveword.xsl; DestDir: {app}\data; Flags: ignoreversion
source: src\Client\data\systemmsg.xsl; DestDir: {app}\data; Flags: ignoreversion
source: src\Client\data\historyconf.xsl; DestDir: {app}\data; Flags: ignoreversion
source: src\Client\data\message.xsl; DestDir: {app}\data; Flags: ignoreversion
source: src\Client\AVClips\WindowsStartup.wav; DestDir: {app}\AVClips; Flags: ignoreversion
source: src\Client\version.dat; DestDir: {app}\; Flags: ignoreversion
source: webmanager3.0\web\Webmonitor\docs\help.htm; DestDir: {app}\help; Flags: ignoreversion
source: webmanager3.0\web\Webmonitor\docs\help.files\*; DestDir: {app}\help\help.files; Flags: ignoreversion

[Dirs]
name: {app}\data\received files

[Registry]
Root: HKLM; Subkey: Software\Fulong; Flags: uninsdeletekeyifempty
Root: HKLM; Subkey: Software\Fulong\FLVCC2.0; Flags: uninsdeletekey
Root: HKLM; Subkey: Software\Fulong\FLVCC2.0; ValueType: string; ValueName: InstallDir; ValueData: {app}\bin
Root: HKLM; Subkey: Software\Fulong\FLVCC2.0; ValueType: string; ValueName: Version; ValueData: 2.0
Root: HKLM; Subkey: SOFTWARE\Microsoft\Windows\CurrentVersion\Run; Flags: uninsdeletevalue; ValueType: string; ValueName: LongCon VCS; ValueData: {code:GetShortName|{app}\bin\Flvcc.exe}; Tasks: autorun

[Icons]
Name: {group}\LongCon VCS V2; FileName: {app}\bin\Flvcc.exe; WorkingDir: {app}\bin\
Name: {group}\卸载LongCon VCS V2.0; Filename: {uninstallexe}
Name: {group}\帮助; FileName: {app}\help\index.html
Name: {userdesktop}\LongCon VCS V2; FileName: {app}\bin\Flvcc.exe; WorkingDir: {app}\bin\; Tasks: desktopicon
Name: {userappdata}\Microsoft\Internet Explorer\Quick Launch\LongCon VCS V2; Filename: {app}\bin\Flvcc.exe; WorkingDir: {app}\bin; Tasks: quicklaunchicon

[Run]
FileName: {sys}\regsvr32.exe; Description: 注册WebLauncher控件; Parameters: /s WebLauncher.ocx; WorkingDir: {sys}
FileName: {sys}\regsvr32.exe; Description: 注册Dump Filter; Parameters: /s Dump.ax; WorkingDir: {app}\bin
FileName: {app}\bin\Flvcc.exe; Description: 安装完成后立即运行; WorkingDir: {app}\bin; Flags: postinstall nowait

[UninstallRun]
FileName: {sys}\regsvr32.exe; Parameters: /u /s Dump.ax; WorkingDir: {app}\bin

[LangOptions]
LanguageName=Simplified Chinese
LanguageID=$0404
DialogFontName=宋体
DialogFontSize=9
TitleFontName=宋体
TitleFontSize=29
WelcomeFontName=宋体
WelcomeFontSize=12
CopyrightFontName=宋体
CopyrightFontSize=9
