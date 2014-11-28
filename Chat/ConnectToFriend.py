from socket import *
from sys import argv
from threading import Thread

""" Threading method for receiving multiple messages while being able to send simultaneously """
class ReceiveThreadClient(Thread):
    def __init__(self, sock):
        Thread.__init__(self)
        self.sock = sock
        self.shouldstop = False

    def run(self):
        #self.sock.settimeout(10)
        while not self.shouldstop:
            try:
                data = self.sock.recv(1024)
                print data
            except timeout:
                print 'Request timed out!'

    def stop(self):
        self.shouldstop = True 
        
#Expecting 3 arguments: Scriptname, FriendName, IP of friend
if len(argv) == 3:
    friendName = argv[1]
    friendIP = argv[2]
else:
    print "Invalid number of arguments!"

print "friendName: " + friendName
print "friendIP: " + friendIP


print "Attempting to connect to friend: " + friendName

HOST = friendIP
PORT = 9001
s = socket(AF_INET, SOCK_STREAM)
s.connect((HOST, PORT))

r = ReceiveThreadClient(s).start()

while True:
    s.send(raw_input("client: ")) 
