#
# This script get the lastest source from the SVN
#

# import
import sys
import os

# read configuration data from configuration file
conf = {}
if len(sys.argv) == 1:
    script_file_name = sys.argv[0]
    conf_file_name = script_file_name[0: len(script_file_name)-2] + "conf"
else:
    conf_file_name = sys.argv[1]

for line in open(conf_file_name, 'r'):
    if line[0] != '#':
        equal_sign_pos = line.find('=')
        if equal_sign_pos != -1:
            key   = line[0:equal_sign_pos].strip()
            value = line[equal_sign_pos+1:].strip()
            conf[key] = value

# cleanup the source tree
exe = '"' + conf['svn'] + '"'
cmd = '"' + exe + ' cleanup ' + conf['updatepath'] + '"'
print(cmd)
os.system(cmd)

# update the source tree
cmd = '"' + exe + ' --username ' + conf['username'] + \
        ' --password ' + conf['password'] + \
        ' update ' + conf['updatepath'] + '"'
print(cmd)
os.system(cmd)

