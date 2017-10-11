# Echo client program
import socket
import sys
import time

HOST = '127.0.0.1'
PORT = 2000
s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.connect((HOST, PORT))
addr = s.getsockname()

i = 0
while 1:
    s.send(str(i) + ': Client data from ' + str(addr))
    data = s.recv(1024)
    print('Received', 'data')
    time.sleep(1)
    i = i+1

