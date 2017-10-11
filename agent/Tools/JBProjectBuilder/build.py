#
# This script build the JBuilder Project, send the result to specified
# email address.
#

# import
import sys
import time
import os
import smtplib
import CheckJBBuildResult
from email.MIMEText import MIMEText

# read configuration data from many configuration files
conf = {}
for conf_file_name in sys.argv[1:]:
    for line in open(conf_file_name, 'r'):
        if line[0] != '#':
            equal_sign_pos = line.find('=')
            if equal_sign_pos != -1:
                key   = line[0:equal_sign_pos].strip()
                value = line[equal_sign_pos+1:].strip()
                conf[key] = value

# Call JBuilder executable
exe = '"' + conf['jbuilder.prog'] + '" '
cmd = '"' + exe + '-build "' + conf['JBProject'] + '" > build.log 2>&1"'
print(cmd)
os.system(cmd)

# Get the date, form the proper version string
import time
now  = time.localtime();
date = str(now.tm_year) + '-' + str(now.tm_mon) + '-' + str(now.tm_mday) + '-' + str(now.tm_hour) + '-' + str(now.tm_min)

# Make mail
file = open('build.log', 'r')
msg = MIMEText(file.read())
file.close()
if CheckJBBuildResult.is_jb_build_success('build.log'):
    msg['Subject'] = conf['JBProject'] + " Build Log" + date
else:
    msg['Subject'] = 'Failed! ' + conf['JBProject'] + " Build Log" + date

msg['From'] = conf['mail.from']
msg['To'] = conf['mail.to']

# Send mail
s = smtplib.SMTP()
s.connect(conf['mail.server'])
s.login(conf['mail.user'], conf['mail.password'])
s.sendmail(conf['mail.from'], conf['mail.to'].split(), msg.as_string())
s.close()

