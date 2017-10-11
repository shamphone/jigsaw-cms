#
# This script build the all VC projects(Client and MeidaServer), and pack client
# as an install executable, copy mediaserver files to deploy directory
#

# import
import sys
import os
import CheckVCBuildResult

# Configuration data
vs7devenv = '"C:\\Program Files\\Microsoft Visual Studio .NET 2003\\Common7\\IDE\\devenv.com"'
innosetup = '"C:\\Program Files\\Inno Setup 5\\Compil32.exe"'

projectbase = '..\\..\\src\\'
projects = [ \
        'WebLauncher\\WebLauncher.sln', \
        'Video\\Video.sln', \
        'FvsAudioPlay\\FvsAudioPlay.sln', \
        'Client\\Flvcc.sln', \
        'MediaServer\\MediaServer.sln' \
        ]

# Call Visual Stuio .NET IDE to build the solution
cmdtemplate = '"' + vs7devenv + ' /rebuild Release "%s" > build.log 2>&1"'
for project in projects:
    cmd = cmdtemplate % (projectbase + project)
    print(cmd)
    sys.stdout.flush()

    os.system(cmd)

    if not CheckVCBuildResult.is_vc_build_success('build.log'):
        print('Build failed for ', project, '. Check build.log for more information.')
        sys.exit(1)

# Pack the LongConVCS_Setup.exe
cmd = '"' + innosetup + ' /cc ..\\..\\install\\Client\\script\\Flvcc.iss"'
print(cmd)
result = os.system(cmd)

# Copy Media Server file to appropriate location 
deployroot = '..\\..\\deploy\\MediaServer'
os.system('mkdir ' + deployroot + '\\bin')
os.system('mkdir ' + deployroot + '\\conf')
os.system('mkdir ' + deployroot + '\\log')
os.system('copy ..\\..\\src\\MediaServer\\Release\\MediaServer.exe ' + deployroot + '\\bin')
os.system('copy ..\\..\\src\\MediaServer\\conf\\MediaServer.ini.template ' + deployroot + '\\conf\\MediaServer.ini')
os.system('copy ..\\..\\install\\VCRedist\\releaseDll\\msvcr71.dll ' + deployroot + '\\bin')
os.system('copy ..\\..\\install\\VCRedist\\releaseDll\\msvcp71.dll ' + deployroot + '\\bin')

