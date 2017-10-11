# This script count the file lines under a specified directory.

# import
import os
from os.path import join
import sys

# Check parameter
if len(sys.argv) < 3:
    print 'linecount.py dirname pattern...'
    sys.exit()

# Get dir name
top = sys.argv[1]

# Delete old content in backup dir
filecount = 0
sum = 0
for root, dirs, files in os.walk(top, topdown=False):
    for name in files:
        fullname = join(root,name)
        filesum = 0
        for ext in sys.argv[2:]:
            if fullname.endswith('.' + ext): 
                filecount = filecount + 1
                for line in open( join(root, name), 'r'):
                    filesum = filesum + 1
                print(fullname, filesum)
                sum += filesum

print(filecount)
print(' files')
print(sum)
print(' lines')

