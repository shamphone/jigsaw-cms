# This script package the debug version of Media Server executables

import zipfile
import datetime

now = datetime.datetime.now()
filename = 'MediaServerDebug-%04d%02d%02d%02d%02d%02d.zip' % \
        (now.year, now.month, now.day, now.hour, now.minute, now.second)

newpackage = zipfile.ZipFile(filename, 'w', zipfile.ZIP_DEFLATED)
newpackage.write('../../../../src/MediaServer/Debug/MediaServer.exe', 'bin/MediaServer.exe')
newpackage.write('../../../../src/MediaServer/conf/MediaServer.ini.template', 'conf/MediaServer.ini')
newpackage.write('../../../VCRedist/debugDll/MFC71D.dll', 'bin/MFC71D.dll')
newpackage.write('../../../VCRedist/debugDll/msvcp71D.dll', 'bin/msvcp71D.dll')
newpackage.write('../../../VCRedist/debugDll/msvcr71D.dll', 'bin/msvcr71D.dll')
newpackage.close()

