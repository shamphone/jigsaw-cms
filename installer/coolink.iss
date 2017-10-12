; FILE:         coolink.iss
; AUTHOR:       马骊
; FOR:          Coolink 1.0
; VERSION:      1.0
; DESCRIPTION:  Coolink 1.0 Win32 安装文件 (Inno Setup 5.3.3)

#define MyAppName "Coolink协同工作支撑平台"
#define MyAppVerName "Coolink协同工作支撑平台 1.0"
#define MyAppPublisher "北京中科辅龙计算机技术股份有限公司"
#define MyAppURL "http://www.fulong.cn/"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{21206C6C-FB97-4DD0-B214-9AB01C99023D}
AppName={#MyAppName}
AppVerName={#MyAppVerName}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName=C:\coolink
SourceDir =E:\coolink1.0
DefaultGroupName=Coolink
OutputDir=E:\target2
OutputBaseFilename=coolink
Compression=lzma
InternalCompressLevel =max
SolidCompression=yes
ChangesEnvironment=yes
LicenseFile=E:\coolink1.0\license.rtf

[Languages]
;Name: "english"; MessagesFile: "compiler:Default.isl"
Name: cn; MessagesFile: compiler:Languages\ChineseSimp-7-11-lsc-5.1.13.isl

[Dirs]
Name: {app}; Permissions: users-full powerusers-full admins-full authusers-full;
Name: {app}\ORADATA
Name: {app}\ORALOG


[Icons]
;Name: {group}\{cm:ProgramOnTheWeb,{#MyAppName}}; Filename: {#MyAppURL}
Name: {group}\启动Coolink; FileName: {app}\start.bat; WorkingDir: {app}; IconFilename: {app}\icons\run_group.ico
Name: {group}\停止Coolink; FileName: {app}\stop.bat; WorkingDir: {app}; IconFilename: {app}\icons\stop_group.ico
Name: {group}\{cm:UninstallProgram,{#MyAppName}}; Filename: {uninstallexe}
Name: {commondesktop}\启动Coolink; FileName: {app}\start.bat; WorkingDir: {app}; IconFilename: {app}\icons\run_desk.ico
Name: {commondesktop}\停止Coolink; FileName: {app}\stop.bat; WorkingDir: {app}; IconFilename: {app}\icons\stop_desk.ico


[Tasks]
;Name: modifypath; Description: 自动设置环境变量


[Files]
source: apache-tomcat-6.0.18\*.*; DestDir: {app}\apache-tomcat-6.0.18; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: jdk1.6\*.*; DestDir: {app}\jdk1.6; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: thirdparty\*.*; DestDir: {app}\thirdparty; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: icons\*.*; DestDir: {app}\icons; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: version.txt; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: dbinstall.bat; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: perlInstall.bat; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: coolink_database1.0.1.sql; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
;大文本用dmp导入
;source: data.dmp; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: exec.sql; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: start.bat; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: stop.bat; DestDir: {app}; Flags: ignoreversion sortfilesbyextension recursesubdirs
source: templates\*.*; DestDir: {app}\templates; Flags: ignoreversion sortfilesbyextension recursesubdirs
;source: readme.txt; DestDir: {app}; Flags: ignoreversion sortfilesbyextension isreadme

[Run]
;Filename: {app}\apache-tomcat-6.0.18\bin\startup.bat; StatusMsg: 正在安装 apache-tomcat-6.0.18; Description: 安装 apache-tomcat-6.0.18; Flags: shellexec


[Code]
var
  Page: TWizardPage;
  CDKeyPage: TWizardPage;
  DBALabel: TNewStaticText;
  DBServiceNameLabel: TNewStaticText;
  AddressLabel: TNewStaticText;
  UsernameLabel: TNewStaticText;
  PasswordLabel: TNewStaticText;
  installCDKeyLabel: TNewStaticText;
  CDKeySeparator1: TNewStaticText;
  CDKeySeparator2: TNewStaticText;
  CDKeySeparator3: TNewStaticText;
  DomainLabel: TNewStaticText;
  SizeLabel: TNewStaticText;
  UnitLabel: TNewStaticText;
  PortLabel: TNewStaticText;
  DBAEdit: TPasswordEdit;
  DBServiceNameEdit: TNewEdit;
  AddressEdit: TNewEdit;
  UsernameEdit: TNewEdit;
  PasswordEdit: TPasswordEdit;
  installCDKeyEdit1: TNewEdit;
  installCDKeyEdit2: TNewEdit;
  installCDKeyEdit3: TNewEdit;
  installCDKeyEdit4: TNewEdit;
  DomainEdit: TNewEdit;
  SizeEdit: TNewEdit;
  PortEdit: TNewEdit;
  installDB: TCheckBox;
  OKButton, CancelButton: TNewButton;
  ResultCode: Integer;
	newJAVAHOME:	String;




procedure SetEnvironment;
{安装时设置环境变量}
var
  javaHome,classPath,path,tomcatHome : String;
  oldJAVAHOME,oldCLASSPATH,oldTOMCAT_HOME:	String;
begin
  //保存安装前的环境变量，以便卸载时恢复
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'JAVA_HOME', oldJAVAHOME);//保存原有JAVA_HOME的值
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'oldJAVAHOME', oldJAVAHOME);
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'CLASSPATH', oldCLASSPATH);//保存原有CLASSPATH的值
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'oldCLASSPATH', oldCLASSPATH);
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'TOMCAT_HOME', oldTOMCAT_HOME);//保存原有TOMCAT_HOME的值
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'oldTOMCAT_HOME', oldTOMCAT_HOME);

  //设置JAVA_HOME
  javaHome := ExpandConstant('{app}\jdk1.6');
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'JAVA_HOME', javaHome);
  //设置CLASSPATH
  classPath := '%JAVA_HOME%\lib\tools.jar;%JAVA_HOME%\lib\dt.jar';
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'CLASSPATH', classPath);
  //设置Path
  if RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', path) then begin
    path := ExpandConstant('{app}\jdk1.6\bin;') + path;
  end
  else begin
    path := ExpandConstant('{app}\jdk1.6\bin');
  end;
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', path);
  //设置TOMCAT_HOME
  tomcatHome := ExpandConstant('{app}\apache-tomcat-6.0.18');
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'TOMCAT_HOME', tomcatHome);
end;



procedure ClearEnvironment;
{卸载时时清除环境变量}
var
  path,tomcatHome : String;
  Position : Integer;
  oldJAVAHOME,oldCLASSPATH,oldTOMCAT_HOME:	String;
begin
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'oldJAVAHOME', oldJAVAHOME);
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'JAVA_HOME', oldJAVAHOME);
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'oldCLASSPATH', oldCLASSPATH);
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'CLASSPATH', oldCLASSPATH);
  if RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', path) then begin
    Position := Pos(ExpandConstant('{app}\jdk1.6\bin;'),path);
    if Position = 1 then begin
      path := Copy(path,Length(ExpandConstant('{app}\jdk1.6\bin;'))+1,Length(path));
    end;
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', path);
  end;
  RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'oldTOMCAT_HOME', oldTOMCAT_HOME);
  RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'TOMCAT_HOME', oldTOMCAT_HOME);
end;



{//////////////////////////////////////////////////////////////////////////////////////}

{在这里编写NextButtonClick当中用到的功能函数}

procedure setRegValue();
{往注册表中写入Coolink程序安装信息}
var
  versionInfo: TStringList;
begin
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'domain', Trim(DomainEdit.Text));
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'sn', installCDKeyEdit1.Text+'-'+installCDKeyEdit2.Text+'-'+installCDKeyEdit3.Text+'-'+installCDKeyEdit4.Text);
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'installedDate', GetDateTimeString('dd/mm/yyyy hh:nn:ss', '-', ':'));
    try
      versionInfo := TStringList.Create;
      if FileExists(ExpandConstant('{app}\version.txt')) then begin
        versionInfo.LoadFromFile(ExpandConstant('{app}\version.txt'));
        RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'version', versionInfo[0]);
      end;
    finally
      versionInfo.Free;
    end;
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'path', ExpandConstant('{app}'));

    //保存数据库的配置信息，方便升级版程序使用
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'DBSystemPW', DBAEdit.Text);   //保存数据库system用户的密码
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'DBSID', Trim(DBServiceNameEdit.Text));    //保存数据库服务名SID
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'newDBName', Trim(UsernameEdit.Text));     //保存程序所使用的数据库表空间名
    RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK', 'newDBPW', PasswordEdit.Text);      //保存程序所使用的数据库密码
end;



function checkWinVersion(): Boolean;
{检查操作系统版本，若为Windows 2003 或 XP则返回true，否则返回false}
var
  Version: TWindowsVersion;
begin
  GetWindowsVersionEx(Version);
  if Version.NTPlatform and (Version.Major = 5) and ((Version.Minor = 1) or (Version.Minor = 2)) then begin
    Result := True;
  end
  else begin
    MsgBox('此安装程序只支持Windows 2003 或 XP 版本!', mbError, MB_OK);
		Result := false;
  end;
end;


function checkCoolinkInstalled(): Boolean;
{检查是否已经安装Coolink，若已经安装则返回true，否则返回false}
var
  domainStr: String;
begin
  if RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK','domain', domainStr) then begin
    MsgBox('本机上已经安装了Coolink程序！', mbError, MB_OK);
		Result := false;
  end
  else begin
    Result := True;
  end;
end;




function checkPort(): Boolean;
{检查80端口是否已经被占用，若未被占用则返回true，否则返回false}
var
  portInfos,AStrings: TStringList;
  ErrorCode,i,j,Acount: Integer;
  thePID: String;
begin
  //通过命令行命令来获取所有与端口80相关的进程
  ShellExec('open' , ExpandConstant('{cmd}'), '/c netstat -aon|findstr ":80" >c:/tempInfo.txt', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
	portInfos := TStringList.Create;
	try
    portInfos.LoadFromFile('c:/tempInfo.txt');
    for i:=0 to portInfos.count-1 do begin
      Acount := Pos(':80',portInfos[i]);
      if (Acount < 30) and (Copy(portInfos[i],Acount+3,1) = ' ') then begin
        thePID := Copy(portInfos[i],Length(portInfos[i])-3,4)
        //通过命令行命令根据进程ID来获取占用80端口的进程名称
        ShellExec('open' , ExpandConstant('{cmd}'), '/c tasklist /fi "PID eq '+thePID+'" >c:/tempInfo.txt', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
        break;
      end;
    end;
  finally
    portInfos.Free;
  end;
  AStrings := TStringList.Create;
  try
    AStrings.LoadFromFile('c:/tempInfo.txt');
    if AStrings.count>3 then begin
      if Length(AStrings[0]) < 1 then begin
        Acount := Pos(' ',AStrings[3]);
        MsgBox('本机的80端口已经被进程 "'+Copy(AStrings[3],0,Acount)+'"所占用,请先关闭此进程再继续安装!', mbError, MB_OK);
        Result := false;
      end
      else begin
        Result := True;
      end;
    end
    else begin
      Result := True;
    end;
  finally
    AStrings.Free;
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/tempInfo.txt"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  end;
end;




function checkOraInstalled(): Boolean;
{检查是否已经安装了Oracle客户端，若已经安装则返回true，否则返回false}
begin
  if RegValueExists(HKEY_LOCAL_MACHINE, 'SOFTWARE\ORACLE', 'inst_loc') then begin
    Result := True;
  end
  else begin
    MsgBox('请先在本机服务器上安装Oracle客户端！', mbError, MB_OK);
    Result := false;
  end;
end;



function checkOraVersion(DBAPassword, DBServiceName, newDBName: String; isCreateTab: Boolean): Boolean;
{检查数据库服务器中Oracle版本,并且检查用户需要创建的新数据库是否存在，若为9i以上版本并且新数据库不存在则返回true，否则返回false}
var
  versionBat,versionSQL,versionInfo,dbExistInfo: TStringList;
  ErrorCode: Integer;
  dbVersion,newDBCountSr: String;
begin
  //创建查看Oracle版本的批处理文件和SQL文件
  ShellExec('open' , ExpandConstant('{cmd}'), '/c netstat -aon|findstr ":123456" >c:/oracleVersion.bat', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  ShellExec('open' , ExpandConstant('{cmd}'), '/c netstat -aon|findstr ":123456" >c:/oracleVersion.sql', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  try
    versionBat := TStringList.Create;
    versionSQL := TStringList.Create;
    versionBat.Add('sqlplus -L "system/'+DBAPassword+'@'+DBServiceName+'" @oracleVersion.sql || exit');
    versionBat.Add('exit');
    versionBat.SaveToFile('c:/oracleVersion.bat');
    //判断Oracle版本的SQL脚本
    versionSQL.Add('spool c:\version.log;');
    versionSQL.Add('select version from v$instance;');
    versionSQL.Add('spool off;');
    //判断新数据库是否已经存在的SQL脚本
    versionSQL.Add('spool c:\tab.log;');
    versionSQL.Add('select sum(no) from (');
    versionSQL.Add('select  count(*)  no  from dba_tablespaces where lower(tablespace_name)='''+newDBName+'''');
    versionSQL.Add('union all');
    versionSQL.Add('select  count(*)  no  from dba_users where lower(username) ='''+newDBName+''');');
    versionSQL.Add('spool off;');
    //通过数据库网络服务名例如orcl81获取数据库服务器中服务名SID
    versionSQL.Add('spool c:\orcl.log;');
    versionSQL.Add('select  instance_name  from v$instance;');
    versionSQL.Add('spool off;');
    //
    versionSQL.Add('spool c:\oraFilePath.log;');
    versionSQL.Add('select  SUBSTR(FILE_NAME,1,INSTR(FILE_NAME,'''+'\'+''',-1)-1) from dba_data_files  where tablespace_name ='''+'SYSTEM'+''' AND ROWNUM=1;');
    versionSQL.Add('spool off;');
    //
    versionSQL.Add('exit;');
    versionSQL.SaveToFile('c:/oracleVersion.sql');
  finally
    versionBat.Free;
    versionSQL.Free;
  end;
  //执行批处理文件获取Oracle连接信息和版本信息
  ShellExec('open', ExpandConstant('c:/oracleVersion.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  try
    versionInfo := TStringList.Create;
    if FileExists('c:/version.log') then begin
      versionInfo.LoadFromFile('c:/version.log');
      if versionInfo.count=5 then begin
        dbVersion := versionInfo[3];
        //支持9.2.0.1.0及以上版本
        if (StrToInt(Copy(dbVersion,7,1))>=1) or ((StrToInt(Copy(dbVersion,1,1))=1)) then begin
          //当用户勾选了“安装新数据库”判断新数据库是否已经存在
          if isCreateTab then begin
            dbExistInfo := TStringList.Create;
            dbExistInfo.LoadFromFile('c:/tab.log');
            newDBCountSr := dbExistInfo[3];
            if StrToInt(Trim(newDBCountSr))>0 then begin
              MsgBox('此数据库已经存在，请重新输入新数据库用户名!', mbError, MB_OK);
              Result := false;
            end
            else begin
              Result := True;
            end;
            dbExistInfo.Free;
          end
          else begin
            Result := True;
          end;
        end
        else begin
          MsgBox('数据库版本过低，必须是Oracle 9.2.0.1.0以上版本!', mbError, MB_OK);
          Result := false;
        end;
      end
      else begin
        MsgBox('数据库启动有误，请先检查数据库运行状态!', mbError, MB_OK);
        Result := false;
      end;
      ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/version.log"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
      ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/tab.log"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    end
    else begin
      MsgBox('数据库连接出错，请重新输入!', mbError, MB_OK);
      Result := false;
    end;
  finally
    versionInfo.Free;
  end;
  ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/oracleVersion.bat"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/oracleVersion.sql"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
end;



Function EncrypKey (Src:String; Key:String):string;
{加密算法，生成安装程序过程中输入的校验码CDKey}
{此安装程序中暂未使用}
var
  idx :integer;
  KeyLen :Integer;
  KeyPos :Integer;
  offset :Integer;
  dest :string;
  SrcPos :Integer;
  SrcAsc :Integer;
  TmpSrcAsc :Integer;
  Range :Integer;

begin
    KeyLen:=Length(Key);
    if KeyLen = 0 then key:='Think Space';
    KeyPos:=0;
    SrcPos:=0;
    SrcAsc:=0;
    Range:=256;

    offset:=Random(Range);
    dest:=format('%1.2x',[offset]);
    for SrcPos := 1 to Length(Src) do
    begin
      SrcAsc:=(Ord(Src[SrcPos]) + offset) MOD 255;
      if KeyPos < KeyLen then KeyPos:= KeyPos + 1 else KeyPos:=1;
      SrcAsc:= SrcAsc xor Ord(Key[KeyPos]);
      dest:=dest + format('%1.2x',[SrcAsc]);
      offset:=SrcAsc;
    end;
    Result:=Dest;
end;



function checkCDKey(domainStr ,key ,inputCDKey: String): Boolean;
{检查输入的安装校验码CDKey，若正确则返回true，否则返回false}
var
  encrypStr : string;
begin
  encrypStr := Copy(GetMD5OfString(domainStr),1,16);
  if Uppercase(inputCDKey) = Uppercase(encrypStr) then begin
    Result := True;
  end
  else begin
    MsgBox('您输入的序列号不正确，请重新输入！', mbError, MB_OK);
		Result := false;
  end;
end;

{NextButtonClick中所用到的所有函数结束}
{//////////////////////////////////////////////////////////////////////////////////////}



procedure Replace(oldstring, newstring, inifile: String);
{这个函数将inifile这个文件中的oldstring，全部替换成newstring}
var
  IniFileLines: TArrayOfString;
  i: Integer;
begin
  if FileExists(inifile) then begin
    LoadStringsFromFile(inifile, IniFileLines);
      for i:= 0 to GetArrayLength(IniFileLines)-1 do begin
        if (Pos(oldstring, IniFileLines[i]) > 0) then
          StringChange(IniFileLines[i], oldstring, newstring);
      end;
    SaveStringsToFile(inifile, IniFileLines, False);
  end;
{Replace函数结束}
end;



function getDBSID(): String;
{获取数据库服务器中服务名SID}
var
  dbSIDInfo: TStringList;
  ErrorCode,spacePos: Integer;
  dbSIDSr: String;
begin
  try
    dbSIDInfo := TStringList.Create;
    if FileExists('c:/orcl.log') then begin
      dbSIDInfo.LoadFromFile('c:/orcl.log');
      if dbSIDInfo.count=5 then begin
        dbSIDSr := Trim(dbSIDInfo[3]);
        if dbSIDSr<>'' then begin
          Result := dbSIDSr;
        end
        else begin
          Result := 'undefinedSID';
        end;
      end
      else begin
        Result := 'undefinedSID';
      end;
      ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/orcl.log"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    end
    else begin
      Result := 'undefinedSID';
    end;
  finally
    dbSIDInfo.Free;
  end;
end;




function Dircopy(SourceFolderPath, DestinyFolderPath, Copytype: String):Boolean;
{这个函数执行复SourceFolderPath路径的文件到DestinyFolderPath}
{Copytype有2种类型, dir和files}
{dir 用于复制整个文件夹}
{files 用于复制单个文件，或者*.xxx类型的复制}
var
  ResultCode: Integer;
  Cmd: String;
begin
  //将文件路径转为绝对路径
  SourceFolderPath  := ExpandConstant('{app}') + SourceFolderPath;
  DestinyFolderPath := ExpandConstant('{app}') + DestinyFolderPath;

  case Copytype of

  'dir':
  //文件夹复制
    begin
      //创建目标文件夹
      if not DirExists(SourceFolderPath) then begin
        MsgBox(SourceFolderPath + '路径不存在。', mbInformation, mb_Ok);
        Result := false;
      end
      else begin
        if not ForceDirectories(DestinyFolderPath) then begin
          MsgBox(DestinyFolderPath + '创建不成功。', mbInformation, mb_Ok);
          Result := false;
        end
        else begin
          //建立批处理文件
          Cmd := 'xcopy ' + SourceFolderPath + ' /E/Q/R/K/C/Y ' + DestinyFolderPath;
          SaveStringToFile(ExpandConstant('{app}\tempfile.bat'), #13#10 + Cmd + #13#10, True);
          //执行复制文件操作
          if Exec(ExpandConstant('{app}\tempfile.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ResultCode) then begin
            if ResultCode = 0 then begin
              Result := true;
              end
            else if ResultCode = 1 then begin
              MsgBox('文件复制发生错误，请重新安装。', mbInformation, mb_Ok);
              Result := false;
            end
            else begin
              MsgBox('文件复制发生错误，请重新安装。', mbInformation, mb_Ok);
              Result := false;
            end;
          end;
        //删除批处理文件
        DeleteFile(ExpandConstant('{app}\tempfile.bat'));
        end;
      end;
    end;

  'files':
  //*.txt类型的文件复制
    begin
      //建立批处理文件
      Cmd := 'xcopy ' + SourceFolderPath + ' /Q/R/K/C/Y ' + DestinyFolderPath;
      SaveStringToFile(ExpandConstant('{app}\tempfile.bat'), #13#10 + Cmd + #13#10, True);
      //执行复制文件操作
      if Exec(ExpandConstant('{app}\tempfile.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ResultCode) then begin
        if ResultCode = 0 then begin
          Result := true;
          end
        else if ResultCode = 1 then begin
          MsgBox('文件复制发生错误，请重新安装。', mbInformation, mb_Ok);
          Result := false;
        end
        else begin
          MsgBox('文件复制发生错误，请重新安装。', mbInformation, mb_Ok);
          Result := false;
        end;
      end;
      //删除批处理文件
      DeleteFile(ExpandConstant('{app}\tempfile.bat'));
    end;

  end;
end;



function getORAFilePath(): String;
var
  ORAFilePathInfo: TStringList;
  infoStr: String;
  ErrorCode: Integer;
begin
  if FileExists('c:/oraFilePath.log') then begin
    try
      ORAFilePathInfo := TStringList.Create;
      ORAFilePathInfo.LoadFromFile('c:/oraFilePath.log');
      infoStr := Trim(ORAFilePathInfo[3]);
      if Pos(':\', infoStr) <> 0 then begin
        Result := infoStr;
      end
      else begin
        Result := 'c:\';
      end;
    finally
      ORAFilePathInfo.Free;
    end;
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "c:/oraFilePath.log"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
  end
  else begin
    Result := 'c:\';
  end;
end;



procedure installTomcat;
{在Windows系统服务中注册Tomcat服务}
var
  ErrorCode: Integer;
  tomcatInstallStrs: TStringList;
begin
  try
    //创建临时批处理文件，用于存放执行注册服务所需命令
    Replace('%JAVA_HOME%', ExpandConstant('{app}\jdk1.6'), ExpandConstant('{app}\apache-tomcat-6.0.18\bin\service.bat'));
    Replace('%theCATALINA_HOME%', ExpandConstant('{app}\apache-tomcat-6.0.18'), ExpandConstant('{app}\apache-tomcat-6.0.18\bin\service.bat'));
		ShellExec('open' , ExpandConstant('{cmd}'), '/c netstat -aon|findstr ":123456" >'+ExpandConstant('{app}\tempfile.bat'), '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
		tomcatInstallStrs := TStringList.Create;
    tomcatInstallStrs.Add('cd '+ExpandConstant('{app}\apache-tomcat-6.0.18\bin'));
    tomcatInstallStrs.Add('service install Coolink');
    tomcatInstallStrs.Add('exit');
    tomcatInstallStrs.SaveToFile(ExpandConstant('{app}\tempfile.bat'));
	finally
		tomcatInstallStrs.Free;
	end;
  //执行服务注册操作，完成之后删除临时批处理文件
  Exec(ExpandConstant('{app}\tempfile.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
	DeleteFile(ExpandConstant('{app}\tempfile.bat'));
end;



procedure unInstallTomcat;
{卸载程序时，在Windows系统服务中卸载Tomcat服务}
var
  ErrorCode: Integer;
  tomcatUnInstallStrs: TStringList;
begin
  try
    //创建临时批处理文件，用于存放执行注册服务所需命令
		ShellExec('open' , ExpandConstant('{cmd}'), '/c netstat -aon|findstr ":123456" >'+ExpandConstant('{app}\tempfile.bat'), '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
		tomcatUnInstallStrs := TStringList.Create;
    tomcatUnInstallStrs.Add('cd '+ExpandConstant('{app}\apache-tomcat-6.0.18\bin'));
    tomcatUnInstallStrs.Add('service remove Coolink');
    tomcatUnInstallStrs.Add('exit');
    tomcatUnInstallStrs.SaveToFile(ExpandConstant('{app}\tempfile.bat'));
	finally
		tomcatUnInstallStrs.Free;
	end;
  //执行服务卸载操作，完成之后删除临时批处理文件
  Exec(ExpandConstant('{app}\tempfile.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
	DeleteFile(ExpandConstant('{app}\tempfile.bat'));
end;


procedure CreateTheWizardPages;

	  begin
			Page := CreateCustomPage(wpLicense, '数据库配置', '输入数据库配置');

			DBALabel := TNewStaticText.Create(Page);
      DBALabel.Caption := '数据库管理员密码：';
      DBALabel.Parent := Page.Surface;

			DBAEdit := TPasswordEdit.Create(Page);
			//DBAEdit.Top := CheckBox.Top + CheckBox.Height + ScaleY(8);
			DBAEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			DBAEdit.Left := DBALabel.Width+ScaleX(8);
			DBAEdit.Text := '';
			DBAEdit.Parent := Page.Surface;


			DBServiceNameLabel := TNewStaticText.Create(Page);
			DBServiceNameLabel.Top := DBAEdit.Top + DBAEdit.Height + ScaleY(8);
      DBServiceNameLabel.Caption := '        数据库服务名：';
      DBServiceNameLabel.Parent := Page.Surface;

			DBServiceNameEdit := TNewEdit.Create(Page);
			DBServiceNameEdit.Top := DBAEdit.Top + DBAEdit.Height + ScaleY(8);
			DBServiceNameEdit.Left := DBServiceNameLabel.Width+ScaleX(8);
			DBServiceNameEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			DBServiceNameEdit.Text := 'orcl';
			DBServiceNameEdit.Parent := Page.Surface;


			AddressLabel := TNewStaticText.Create(Page);
			AddressLabel.Top := DBServiceNameEdit.Top + DBServiceNameEdit.Height + ScaleY(8);
      AddressLabel.Caption := '数据库服务器地址：';
      AddressLabel.Parent := Page.Surface;

			AddressEdit := TNewEdit.Create(Page);
			AddressEdit.Top := DBServiceNameEdit.Top + DBServiceNameEdit.Height + ScaleY(8);
			AddressEdit.Left := AddressLabel.Width+ScaleX(8);
			AddressEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			AddressEdit.Text := '127.0.0.1';
			AddressEdit.Parent := Page.Surface;


			UsernameLabel := TNewStaticText.Create(Page);
			UsernameLabel.Top := AddressEdit.Top + AddressEdit.Height + ScaleY(8);
      UsernameLabel.Caption := '    新数据库用户名：';
      UsernameLabel.Parent := Page.Surface;

			UsernameEdit := TNewEdit.Create(Page);
			UsernameEdit.Top := AddressEdit.Top + AddressEdit.Height + ScaleY(8);
			UsernameEdit.Left := UsernameLabel.Width+ScaleX(8);
			UsernameEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			UsernameEdit.Text := 'coolink';
			UsernameEdit.Parent := Page.Surface;


			PasswordLabel := TNewStaticText.Create(Page);
			PasswordLabel.Top := UsernameEdit.Top + UsernameEdit.Height + ScaleY(8);
      PasswordLabel.Caption := '        新数据库密码：';
      PasswordLabel.Parent := Page.Surface;

			PasswordEdit := TPasswordEdit.Create(Page);
			PasswordEdit.Top := UsernameEdit.Top + UsernameEdit.Height + ScaleY(8);
			PasswordEdit.Left := PasswordLabel.Width+ScaleX(8);
			PasswordEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			PasswordEdit.Text := '';
			PasswordEdit.Parent := Page.Surface;


			SizeLabel := TNewStaticText.Create(Page);
			SizeLabel.Top := PasswordEdit.Top + PasswordEdit.Height + ScaleY(8);
      SizeLabel.Caption := '            数据库大小：';
      SizeLabel.Parent := Page.Surface;

			SizeEdit := TNewEdit.Create(Page);
			SizeEdit.Top := PasswordEdit.Top + PasswordEdit.Height + ScaleY(8);
			SizeEdit.Left := SizeLabel.Width+ScaleX(8);
			SizeEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			SizeEdit.Text := '2048';
			SizeEdit.Parent := Page.Surface;

			UnitLabel := TNewStaticText.Create(Page);
			UnitLabel.Top := PasswordEdit.Top + PasswordEdit.Height + ScaleY(8);
			UnitLabel.Left := SizeLabel.Width+SizeEdit.Width+ScaleX(12);
      UnitLabel.Caption := 'M（兆）';
      UnitLabel.Parent := Page.Surface;


      PortLabel := TNewStaticText.Create(Page);
			PortLabel.Top := SizeEdit.Top + SizeEdit.Height + ScaleY(8);
      PortLabel.Caption := '            数据库端口：';
      PortLabel.Parent := Page.Surface;

			PortEdit := TNewEdit.Create(Page);
			PortEdit.Top := SizeEdit.Top + SizeEdit.Height + ScaleY(8);
			PortEdit.Left := PortLabel.Width+ScaleX(8);
			PortEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			PortEdit.Text := '1521';
			PortEdit.Parent := Page.Surface;


			DomainLabel := TNewStaticText.Create(Page);
			DomainLabel.Top := PortEdit.Top + PortEdit.Height + ScaleY(8);
      DomainLabel.Caption := '                网站域名：';
      DomainLabel.Parent := Page.Surface;

			DomainEdit := TNewEdit.Create(Page);
			DomainEdit.Top := PortEdit.Top + PortEdit.Height + ScaleY(8);
			DomainEdit.Left := DomainLabel.Width+ScaleX(8);
			DomainEdit.Width := Page.SurfaceWidth div 2 - ScaleX(8);
			DomainEdit.Text := 'www.coolink.cn';
			DomainEdit.Parent := Page.Surface;

			installDB := TCheckBox.Create(Page);
			installDB.Top := AddressEdit.Top + AddressEdit.Height + ScaleY(8);
			installDB.Left := UsernameLabel.Width+UsernameEdit.Width+ScaleX(16);
			installDB.Caption := '安装新数据库';
			installDB.Parent := Page.Surface;

			CDKeyPage := CreateCustomPage(Page.ID, '安装验证', '输入安装序列号');


end;



procedure CreateTheCDKeyPages;
begin
  installCDKeyLabel := TNewStaticText.Create(CDKeyPage);
  installCDKeyLabel.Caption := '安装序列号：';
  installCDKeyLabel.Parent := CDKeyPage.Surface;
  installCDKeyLabel.Top := CDKeyPage.SurfaceHeight div 4 ;

  installCDKeyEdit1 := TNewEdit.Create(CDKeyPage);
  installCDKeyEdit1.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(8);
  installCDKeyEdit1.Width := CDKeyPage.SurfaceWidth div 6 ;
  installCDKeyEdit1.MaxLength := 4;
  installCDKeyEdit1.Text := '';
  installCDKeyEdit1.Parent := CDKeyPage.Surface;

  CDKeySeparator1 := TNewStaticText.Create(CDKeyPage);
  CDKeySeparator1.Caption := '-';
  CDKeySeparator1.Font.Size := 20;
  CDKeySeparator1.Parent := CDKeyPage.Surface;
  CDKeySeparator1.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(2);
  CDKeySeparator1.Left := installCDKeyEdit1.Left+installCDKeyEdit1.Width+ScaleX(8);

  installCDKeyEdit2 := TNewEdit.Create(CDKeyPage);
  installCDKeyEdit2.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(8);
  installCDKeyEdit2.Left := CDKeySeparator1.Left+CDKeySeparator1.Width+ScaleX(8);
  installCDKeyEdit2.Width := CDKeyPage.SurfaceWidth div 6 ;
  installCDKeyEdit2.MaxLength := 4;
  installCDKeyEdit2.Text := '';
  installCDKeyEdit2.Parent := CDKeyPage.Surface;

  CDKeySeparator2 := TNewStaticText.Create(CDKeyPage);
  CDKeySeparator2.Caption := '-';
  CDKeySeparator2.Font.Size := 20;
  CDKeySeparator2.Parent := CDKeyPage.Surface;
  CDKeySeparator2.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(2);
  CDKeySeparator2.Left := installCDKeyEdit2.Left+installCDKeyEdit2.Width+ScaleX(8);

  installCDKeyEdit3 := TNewEdit.Create(CDKeyPage);
  installCDKeyEdit3.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(8);
  installCDKeyEdit3.Left := CDKeySeparator2.Left+CDKeySeparator2.Width+ScaleX(8);
  installCDKeyEdit3.Width := CDKeyPage.SurfaceWidth div 6 ;
  installCDKeyEdit3.MaxLength := 4;
  installCDKeyEdit3.Text := '';
  installCDKeyEdit3.Parent := CDKeyPage.Surface;

  CDKeySeparator3 := TNewStaticText.Create(CDKeyPage);
  CDKeySeparator3.Caption := '-';
  CDKeySeparator3.Font.Size := 20;
  CDKeySeparator3.Parent := CDKeyPage.Surface;
  CDKeySeparator3.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(2);
  CDKeySeparator3.Left := installCDKeyEdit3.Left+installCDKeyEdit3.Width+ScaleX(8);

  installCDKeyEdit4 := TNewEdit.Create(CDKeyPage);
  installCDKeyEdit4.Top := installCDKeyLabel.Top + installCDKeyLabel.Height + ScaleY(8);
  installCDKeyEdit4.Left := CDKeySeparator3.Left+CDKeySeparator3.Width+ScaleX(8);
  installCDKeyEdit4.Width := CDKeyPage.SurfaceWidth div 6 ;
  installCDKeyEdit4.MaxLength := 4;
  installCDKeyEdit4.Text := '';
  installCDKeyEdit4.Parent := CDKeyPage.Surface;
end;



function NextButtonClick(CurPageID: Integer): Boolean;
begin
  case CurPageID of
    wpWelcome:
        begin
          Result := checkWinVersion() and checkCoolinkInstalled() and checkPort() and checkOraInstalled();
        end;
    Page.ID:
        begin
          if checkOraVersion(DBAEdit.Text, Trim(DBServiceNameEdit.Text), Trim(UsernameEdit.Text), installDB.Checked) then begin
          //if 1 = 1 then begin
            CreateTheCDKeyPages;
            Result := true;
          end
          else begin
            Result := false;
          end;
        end;
    CDKeyPage.ID:
        begin
          Result := checkCDKey(DomainEdit.Text ,'Coolink' ,installCDKeyEdit1.Text+installCDKeyEdit2.Text+installCDKeyEdit3.Text+installCDKeyEdit4.Text);
        end;
  else
    Result := True;
  end;
end;



procedure CurUninstallStepChanged(CurUninstallStep: TUninstallStep);
var
  ErrorCode: Integer;
begin
  if CurUninstallStep = usUninstall then begin //执行卸载前先卸载系统服务中的Tomcat服务
    unInstallTomcat;
  end;
	if CurUninstallStep = usPostUninstall then begin //执行卸载后清除注册表中关于本软件的信息
    ClearEnvironment;
    RegDeleteKeyIncludingSubkeys(HKEY_LOCAL_MACHINE, 'SOFTWARE\COOLINK');
  end;
end;

function NeedRestart(): Boolean;
begin
		Result := False;
end;


procedure CurStepChanged(CurStep: TSetupStep);
var
  fileName,fileNameNew,perlDir,perlPath,ORAFilePath,ORAFileName: String;
  ErrorCode: Integer;
begin
  if CurStep = ssPostInstall then begin
    //判断是否覆盖templates目录，若第一次安装则直接生成此目录，若已存在此目录，询问用户是否覆盖
    if not DirExists(ExpandConstant('{app}\apache-tomcat-6.0.18\webapps\template001')) then begin
      Dircopy('\templates', '\apache-tomcat-6.0.18\webapps\', 'dir');
    end
    else begin
      if MsgBox('是否要覆盖现有的template001目 录？', mbConfirmation, MB_YESNO) = IDYES then begin
        Dircopy('\templates', '\apache-tomcat-6.0.18\webapps\', 'dir');
      end;
    end;
    DelTree(ExpandConstant('{app}\templates'),true,true,true);

    //修改安装数据库的批处理文件dbinstall.bat
    fileName := ExpandConstant('{app}\dbinstall.bat');
    Replace('%SystemPassword%', DBAEdit.Text, fileName);
    Replace('%ServiceName%', Trim(DBServiceNameEdit.Text), fileName);
    Replace('%username%', Trim(UsernameEdit.Text), fileName);
    Replace('%password%', PasswordEdit.Text, fileName);

    //修改数据库脚本文件exec.sql
    fileName := ExpandConstant('{app}\exec.sql');
    Replace('%Systempassword%', DBAEdit.Text, fileName);
    ORAFilePath := getORAFilePath()+'\';
    ORAFileName := Trim(UsernameEdit.Text)+IntToStr(Random(99999));
    while FileExists(ORAFilePath+ORAFileName+'.ORA') do begin   //如果要创建的ORA文件已存在，则重新生成一个文件名，直到不重复为止
      ORAFileName := Trim(UsernameEdit.Text)+IntToStr(Random(99999));
    end;
    Replace('%oraFileName%', ORAFilePath+ORAFileName+'.ORA', fileName);
    Replace('%size%', Trim(SizeEdit.Text), fileName);
    Replace('%serviceName%', Trim(DBServiceNameEdit.Text), fileName);
    Replace('%username%', Trim(UsernameEdit.Text), fileName);
    Replace('%password%', PasswordEdit.Text, fileName);
    Replace('%localhost%', Trim(DomainEdit.Text), fileName);

    //如果用户勾选了“安装新数据库”，则执行数据库安装脚本
    if installDB.Checked then begin
      ShellExec('open', ExpandConstant('{app}\dbinstall.bat'), '', '', SW_SHOW, ewWaitUntilTerminated, ErrorCode);
    end;

    //复制并创建cgi\awstats.domain.conf文件中的log日志路径
    fileName := ExpandConstant('{app}\apache-tomcat-6.0.18\webapps\monitor\WEB-INF\cgi\awstats.conf');
    fileNameNew := ExpandConstant('{app}\apache-tomcat-6.0.18\webapps\monitor\WEB-INF\cgi\awstats.'+ Trim(DomainEdit.Text) +'.conf');
    FileCopy(fileName,fileNameNew,false);
    Replace('%localhost%', Trim(DomainEdit.Text), fileNameNew);
    Replace('%TomcatHome%', ExpandConstant('{app}\apache-tomcat-6.0.18'), fileNameNew);

    //修改配置文件config.xml中的数据库配置
    //fileName := ExpandConstant('{app}\apache-tomcat-6.0.18\webapps\coolink\WEB-INF\config.xml');
    //Replace('%DBAddress%', Trim(AddressEdit.Text), fileName);
    //Replace('%DBPort%', Trim(PortEdit.Text), fileName);
    //Replace('%DBServiceName%', getDBSID(), fileName);
    //Replace('%username%', Trim(UsernameEdit.Text), fileName);
    //Replace('%password%', PasswordEdit.Text, fileName);

    //修改tomcat中server.xml文件的配置
    fileName := ExpandConstant('{app}\apache-tomcat-6.0.18\conf\server.xml');
    //Replace('%coolinkPath%', ExpandConstant('{app}\apache-tomcat-6.0.18\webapps\coolink'), fileName);
    Replace('%DBAddress%', Trim(AddressEdit.Text), fileName);
    Replace('%DBPort%', Trim(PortEdit.Text), fileName);
    Replace('%DBServiceName%', getDBSID(), fileName);
    Replace('%username%', Trim(UsernameEdit.Text), fileName);
    Replace('%password%', PasswordEdit.Text, fileName);

    //修改安装Perl所需的批处理文件perlInstall.bat
    fileName := ExpandConstant('{app}\perlInstall.bat');
    Replace('%path%', ExpandConstant('{app}'), fileName);

    //讲程序有关信息写入注册表
    setRegValue();

    //设置环境变量
		SetEnvironment;

		//将tomcat服务注册成系统服务
    installTomcat;

    //如果服务器中未安装Perl，则提示安装
    if not RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SOFTWARE\Perl', 'BinDir', perlDir) then begin
      ShellExec('open', ExpandConstant('{app}\perlInstall.bat'), '', '', SW_HIDE, ewWaitUntilTerminated, ErrorCode);
      RegQueryStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', perlPath);
      perlPath := perlPath + ';C:\Perl\bin';
      RegWriteStringValue(HKEY_LOCAL_MACHINE, 'SYSTEM\CurrentControlSet\Control\Session Manager\Environment', 'Path', perlPath);
    end;
  end;

  if CurStep = ssDone then begin
    //如果需要创建新数据库，则在新数据库创建之后启动tomcat服务，否则直接启动tomcat服务
    if ((installDB.Checked) and (FileExists(ExpandConstant('{app}\dbInstalling.log'))))then begin
      while not FileExists(ExpandConstant('{app}\dbInstalled.log')) do begin
        Sleep(5000);
      end;
      ShellExec('open' , ExpandConstant('{cmd}'), '/c Net start "Coolink"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
      ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\dbInstalling.log')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
      ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\dbInstalled.log')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    end
    else begin
      ShellExec('open' , ExpandConstant('{cmd}'), '/c Net start "Coolink"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    end;
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\coolink_database1.0.1.sql')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\dbinstall.bat')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\exec.sql')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    //ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\data.dmp')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\log.log')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);
    ShellExec('open' , ExpandConstant('{cmd}'), '/c del "'+ExpandConstant('{app}\perlInstall.bat')+'"', '',SW_HIDE, ewWaitUntilTerminated, ErrorCode);

    //tomcat启动完毕之后打开访问页面
    ShellExec('open', 'http://localhost/ide/cms', '', '', SW_HIDE, ewNoWait, ErrorCode);
  end;
end;



procedure InitializeWizard();

begin
  CreateTheWizardPages;
end;
