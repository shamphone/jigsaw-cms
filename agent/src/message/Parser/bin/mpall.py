# This script run lyvcMP.bat on all xml file in the current directory

# import
import os
from os.path import join
import sys

for root, dirs, files in os.walk('.', topdown=False):
    for name in files:
        fullname = join(root,name)
        if fullname.endswith('.xml'): 
            cmd = 'mp.bat ' + fullname
            print(cmd);
            os.system(cmd);

