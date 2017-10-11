# When JBuilder clean web project, the tmp directory and all the 
# sub-dirs under the .svn directory will be removed. This behaviour
# confused the TortoiseSVN and cause us much trouble.
# This script recursively re-create the tmp and its sub-dirs.

# import
import os
import os.path
import sys

# Print hello
print('For the Stupidity of JBuilder and Bigotry of TortoiseSVN!')

# Find the configuration file which specifies the project directory
# name, if no configuration file specified in the command line, the same
# name file ends with .conf which resides under the same directory with
# this script is used.
if len(sys.argv) < 2:
    script_file_name = sys.argv[0]
    conf_file_name = script_file_name[0: len(script_file_name)-2] + "conf"
else:
    conf_file_name = sys.argv[1]
print('Using configuration from file ' + conf_file_name)

# Open the configuration file, travell every line
file = open(conf_file_name, 'r')
while 1:
    line = file.readline()
    if not line:
        break

    # Omit the comment
    if line[0] == '#':
        continue

    # Skip the newline character
    if line.endswith('\n'):
        top = line[0: len(line)-1]
    else:
        top = line

    # take line as a directory name, recursively go down it
    print('Fix directory ' + top)
    for root, dirs, files in os.walk(top, topdown=False):
        for name in dirs:
            fullname = os.path.join(root,name)
            if fullname.endswith('.svn'): 
                tmpname = fullname + '\\' + 'tmp'
                if not os.access( tmpname, os.F_OK ):
                    os.mkdir(tmpname)
                    os.mkdir(tmpname + '\\' + 'prop-base')
                    os.mkdir(tmpname + '\\' + 'props')
                    os.mkdir(tmpname + '\\' + 'text-base')
                    os.mkdir(tmpname + '\\' + 'wcprops')

