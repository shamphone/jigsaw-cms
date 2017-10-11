import socket
import sys
import time

HOST = '127.0.0.1'
PORT = 2001
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))
addr = s.getsockname()

i = 0
while 1:
    data = s.recv(1024)
    print('Received', 'data')
    s.send(str(i) + ': Server data from ' + str(addr))
    time.sleep(1)
    i = i+1
