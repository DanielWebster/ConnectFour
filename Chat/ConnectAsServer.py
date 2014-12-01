from socket import *
from threading import Thread
from Crypto.Cipher import AES

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
                #print "Encrypted Data: " + data
                data = decrypt(data, sessionCipher)
                #print "Decrypted Data: " + data
                print data
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
    return dec[:len(dec)-l]

secretkey = "1234567890123456"
setSessionKey(secretkey)

print "Connecting as Server..."    
HOST = gethostbyname(gethostname())
PORT = 9001
s = socket(AF_INET, SOCK_STREAM)
s.bind((HOST, PORT)) 
s.listen(10) # how many connections it can receive at one time 
print "Connected as Server..."

global conn
conn, addr = s.accept() 
print 'Connected by', addr 
r = ReceiveThreadServer(s).start()

while True:
     conn.send(encrypt(raw_input(), sessionCipher))
    
conn.close()
print "connection closed"