from socket import *
from threading import Thread

""" Threading method for receiving multiple messages while being able to send simultaneously """
class ReceiveThreadServer(Thread):

    def __init__(self, sock):
        Thread.__init__(self)
        self.sock = sock
        self.shouldstop = False

    def run(self):
        #self.sock.settimeout(10)
        while not self.shouldstop:
            try:
                data = conn.recv(1024)
                print data
            except timeout:
                print 'Request timed out!'

    def stop(self):
        self.shouldstop = True 

print "Connecting as Server..."    
HOST = 'localhost'
PORT = 9001
s = socket(AF_INET, SOCK_STREAM)
s.bind((HOST, PORT)) 
s.listen(1) # how many connections it can receive at one time 
print "Connected as Server..."

global conn
conn, addr = s.accept() 
print 'Connected by', addr 
r = ReceiveThreadServer(s).start()

while True:
    conn.send(raw_input("server: " ))
    
conn.close()
print "connection closed"