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
                print "Encrypted Data: " + data
                data = decrypt(data, sessionCipher)
                print "Decrypted Data: " + data
            except timeout:
                print 'Request timed out!'

    def stop(self):
        self.shouldstop = True 
        


sessionCipher =''

def setSessionKey(secretkey):
    global sessionCipher
    sessionCipher = AES.new(secretkey)
    
def pad(s):
    return s + ((16-len(s) % 16) * "{")

def encrypt(plaintext, cipher):
    return cipher.encrypt(pad(plaintext))

def decrypt(ciphertext, cipher):
    dec = cipher.decrypt(ciphertext).decode("utf-8")
    l = dec.count("{")
    return dec[:len(dec)-1]

secretkey = "1234567890123456"
setSessionKey(secretkey)

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
    s.send(encrypt(raw_input("client: "), sessionCipher)) 
