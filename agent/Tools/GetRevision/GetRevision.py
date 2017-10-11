#/bin/python
# -*- coding: cp936 -*-

# This script get the revision number for current svn working directory
# It will save the current directory revision number into the file named
# version.dat

import os
import re

regexpch = '^修订版：\s*(\d+)\s*$'
regexpen = '^Revision:\s*(\d+)\s*$'

def write_file(revision):
    versionfile = open('version.dat', 'w')
    versionfile.write( str(revision) + '\n')
    versionfile.close()

# Run svn executable to get the info
cmd = 'svn.exe info > svn_info.dat'
print(cmd)
os.system(cmd)

file = open('svn_info.dat', 'r')
while 1:
    line = file.readline()
    if not line:
        break

    result = re.compile(regexpch).match(line)
    if(result):
        write_file( result.group(1))
        break

    result = re.compile(regexpen).match(line)
    if(result):
        write_file( result.group(1))
        break

file.close()
os.unlink('svn_info.dat')

