#
# This script backup the development database of Lyvc
# 

import os
import sys

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

# Call pg_dumpall
os.environ['PATH'] = os.environ['PATH'] + ';' + conf['pgbin']
os.environ['PGHOST'] = conf['pghost']
os.environ['PGUSER'] = conf['pguser']
os.environ['PGPASSWORD'] = conf['pgpassword']

exe = '"' + conf['pgbin'] + '\pg_dumpall.exe"'
command = '"' + exe + ' -c > d:\\lyvc\\backup\\backup.sql "'
print command
os.system(command)

