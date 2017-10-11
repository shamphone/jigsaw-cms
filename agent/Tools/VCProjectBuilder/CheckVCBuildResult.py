#/bin/python
# -*- coding: cp936 -*-

def is_vc_build_success(filename):

    import re
    regexp = '^.*生成: \d+ 已成功, (\d+) 已失败, \d+ 已跳过\s*$'
    compiled = re.compile(regexp)

    import os
    lines = open(filename).readlines()

    for line in lines:
        line = line.strip()
        result = compiled.match(line)
        if result:
            if result.group(1) == '0':
                return 1
            else:
                return 0

if __name__ == '__main__':
    import sys
    import os.path
    if len(sys.argv) != 2:
        print('Usage:', os.path.basename(sys.argv[0]), 'buildresultfile')
        sys.exit(0)

    if is_vc_build_success(sys.argv[1]):
        print('Build successful!')

    else:
        print('Build failed!')

