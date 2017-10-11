#/bin/python
# -*- coding: cp936 -*-

# Create and sign the WebLauncher cab

import os
import sys

cmd = 'cabarc n WebLauncher.cab ..\\..\\WebLauncher\\Release\\WebLauncher.ocx Launcher.inf ..\\..\\..\\..\\install\\VCRedist\\releaseDll\\MFC71.dll ..\\..\\..\\..\\install\\VCRedist\\releaseDll\\msvcr71.dll'
print(cmd)
os.system(cmd)

cmd = 'makecert /sk "fulong" /n "CN=中科辅龙" Fulong.cer'
print(cmd)
os.system(cmd)

cmd = 'cert2spc Fulong.cer Fulong.spc'
print(cmd)
os.system(cmd)

cmd = 'signcode /spc Fulong.spc /k fulong WebLauncher.cab'
print(cmd)
os.system(cmd)
