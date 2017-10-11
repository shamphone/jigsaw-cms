# This script package the release version of Media Server executables

import zipfile
import datetime

now = datetime.datetime.now()
filename = 'MediaServerRelease-%04d%02d%02d%02d%02d%02d.zip' % \
        (now.year, now.month, now.day, now.hour, now.minute, now.second)

newpackage = zipfile.ZipFile(filename, 'w', zipfile.ZIP_DEFLATED)
newpackage.write('../../../../src/MediaServer/Release/MediaServer.exe', 'bin/MediaServer.exe')
newpackage.write('../../../../src/MediaServer/conf/MediaServer.ini.template', 'conf/MediaServer.ini')
newpackage.write('../../../VCRedist/releaseDll/MFC71.dll', 'bin/MFC71.dll')
newpackage.write('../../../VCRedist/releaseDll/msvcp71.dll', 'bin/msvcp71.dll')
newpackage.write('../../../VCRedist/releaseDll/msvcr71.dll', 'bin/msvcr71.dll')
newpackage.close()

