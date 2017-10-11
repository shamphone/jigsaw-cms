#/bin/python
# -*- coding: cp936 -*-

def is_jb_build_success(filename):

    import re
    regexp = '^".+\.java": .+$'
    compiled = re.compile(regexp)

    import os
    lines = open(filename).readlines()

    for line in lines:
        line = line.strip()
        result = compiled.match(line)
        if result:
            return 0

    return 1

if __name__ == '__main__':
    import sys
    import os.path
    if len(sys.argv) != 2:
        print('Usage:', os.path.basename(sys.argv[0]), 'buildresultfile')
        sys.exit(0)

    if is_jb_build_success(sys.argv[1]):
        print('Build successful!')

    else:
        print('Build failed!')

