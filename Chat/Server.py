# import socket
import MySQLdb
from socket import *
from threading import Thread
import json
import os
from Crypto import *
from Crypto.PublicKey import RSA
from Crypto.Cipher import AES

import uuid

privKeyObj = ("-----BEGIN RSA PRIVATE KEY-----\n" 
"MIICXQIBAAKBgQDDtsvGHhYiJDAHkHRGvpYZ2FAWUOHTV01DCQgluSNb/09XSL/Q\n" 
"3snJlDgUDvWvEyaIW9Gj2efIzn6e5CkG5iKn/3ttRlWDGGcY3k2iNXjSvQAYjSpl\n" 
"t59hHPHCZvPz+0yHs6DVvc+owBxiZAByh1NxK66bzBnEdzU1Sf7aZZ4pMQIDAQAB\n" 
"AoGAPcoxjawkCsVoEItP2qIDW8eKiXEhywquDvMECnzoJ/x0PTdvr+8WwDi2d8a9\n" 
"VHf0W2q5xkRexGxFV77rIQ15dQh3kTEquMKKtmfnKfZbnBxPudn0LlENwKGVnOpC\n" 
"FOkATzH04L4nsjkbxibVuLbniXjmhgHzE3AulIpXr9fN5m0CQQDGQiDjwHlZa+/1\n" 
"VFhcFjrYddi62VFDwDmJHXGXnp86d+cbhPFAgrjUkFU+ELyhiTKJWDfmFgAASSnU\n" 
"p79vJv6vAkEA/Lb4aoBtJMbDRkSaCpu8erHwOH0ZFuQrW/oLstO6C84txhjEGQcz\n" 
"cH1sfuimwh3tg7DVpUsA8DKFqaxZiXROHwJAN3W2N5/XEmG0XX97vD7ntTe6KgKy\n" 
"ze4O6kFXTl+sETILb1JQHoiy5Zt+jP8nlVSI04zfDjknRO0yi29liNVytwJBALdc\n" 
"Dqw/mHFpof/XAKmXy85+Uty5r72TOf6XU2uiAchVBZNJHudF+UWyS0ldhrkru8yk\n" 
"Pq+a1whwr9inS6PW9mMCQQCZSeu14EaTtEpdxPEXstpgQoZyd8ss8NjU+VhO4e0f\n" 
"3Z69TDxC338kLwY+kvfroX81H9BIGpVYMAeHznlEUaZ7\n" 
"-----END RSA PRIVATE KEY-----")

privateKey = RSA.importKey(privKeyObj)
username = ""
friendsArr = []

messageSessionKey = ''

def newSessionKey():
    global messageSessionKey
    # generate a random secret key
    # Will be used for individual messages for users
    # New key per session
    messageSessionKey = str(uuid.uuid4().hex)


""" Threading method for receiving multiple commands while being able to do other actions simultaneously """
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
                #print "data: " + data
                
                if data == "ADD FRIEND":
                    print "Add a friend!!!"
                    user = conn.recv(1024)
                    conn.send("USER RECEIVED")
                    print "user: " + user
                    friend = conn.recv(1024)
                    print "friend: " + friend
                    addFriend(user, friend)
                elif data == "NEW USER":
                    createUser()
                elif data == "LOGIN":
                    """ While user is NOT logged in"""
                    login = False
                    while login == False: 
                            print "User not logged in..."
                            login = attempt_login()
                elif data == "CONNECT TO FRIEND":
                    friend = conn.recv(1024)
                    conn.send(getIP(friend))
                    if conn.recv(1024) == "OK":
                        newSessionKey()
                        print "messageSessionKey: " + messageSessionKey
                        conn.send(messageSessionKey)
                    if conn.recv(1024) == "OK":
                        conn.send(messageSessionKey)
                elif data == "SESSION KEY":
                    sessionKey = conn.recv(1024)
                    #print "Encrypted Key: " + sessionKey
                    sessionKey = privateKey.decrypt(sessionKey)
                    #print "SESSION key: " + sessionKey
                    updateSessionKey(sessionKey)
                elif data == "FRIENDS LIST":
                    #Get and send friend list + respective IPs to user
                    friendsList()
                elif data == "UPDATE IP":
                    #Update IP for user in DB
                    updateIP()
                #print data
            except timeout:
                print 'Request timed out!'

    def stop(self):
        self.shouldstop = True 

def updateSessionKey(sessionKey):
    print "Updating sessionKey for user: " + username
    print "New User SessionKey: " + sessionKey
    try:
        cur.execute("UPDATE users SET PublicKey = %s WHERE username = %s", (sessionKey, username))
        db.commit()
    except Exception:
        print "Username does not exist!"
    
    print "SessionKey updated..."

def addFriend(user, friend):
    print "Adding friend \'" + friend + "\' to user \'" + user + "\'"
    cur.execute("INSERT INTO friends(username, friend) VALUES (%s, %s)", (user, friend))
    db.commit()

def friendsList():
    #Get complete list of all friends + their IPs for logged in user
    print "Getting friends list for " + username
    cur.execute("SELECT friend FROM friends WHERE username=%s", (username))
    friendsArr = []
    for row in cur.fetchall():
        friendsArr.append([row[0]])
        
    """ Serializing the list to send over the connection """    
    conn.send(json.dumps(friendsArr))
        
def getIP(user):
    #print "Getting IP for " + user
    # Get IP for a user
    cur.execute("SELECT ip FROM ips WHERE username=%s", (user))
    try:
        IP = cur.fetchone()
        IP = IP[0]
        print "IP for " + user + ": " + IP
        return IP
    except Exception:
        print "IP does not exist for user: " + user
        return "N/A"
    

def updateIP():
    print "Updating IP for user: " + username
    print "New IP: " + addr[0]
    try:
        cur.execute("INSERT INTO ips(username, IP) VALUES(%s,%s)", (username, addr[0]))
        db.commit()
    except Exception:
        cur.execute("UPDATE ips SET ip = %s WHERE username = %s", (addr[0], username))
        db.commit()
        
    print "IP updated..."
#
def createUser():
    print "Creating new user..."

    new_user = conn.recv(1024)
    new_password = conn.recv(1024)
    
    print "NEW USERNAME: " + new_user
    print "NEW PASSWORD: " + repr(new_password)
    
    """ ******************* CHECK IF USERNAME ALREADY EXISTS ******************** """
    cur.execute("SELECT Username FROM users WHERE Username=%s", new_user)
    try:
        stored_username = cur.fetchone()
        stored_username = stored_username[0]
        print "Username already exists!"
    except Exception:
        """ INSERT NEW USER INTO USERS TABLE """
        cur.execute("INSERT INTO users(username, password) VALUES(%s, %s)", (new_user, repr(new_password)))
        db.commit()
        print "User created!"
    
def attempt_login():
    conn.send("CONNECTED")
    print "Attempting login."
    """FIRST ARGUMENT TO RECEIVE: USERNAME """
    global username 
    username = conn.recv(1024) 
    print "username: " + username
    
    #print "Received ", repr(username) 
    """CANT USE SEND ALL"""
    conn.send("USERNAME RECEIVED")

    cur.execute("SELECT Password FROM users WHERE Username=%s", username)
    
    try:
        stored_password = cur.fetchone()
        stored_password = stored_password[0]
        #print "Stored password: " + stored_password[0]
    except Exception:
        print "USERNAME does not exist!"
    
   
    """SECOND ARGUMENT TO RECEIVE: PASSWORD """
    received_password = conn.recv(1024) 
    #print "Received " + repr(received_password) 
    conn.send("PASSWORD RECEIVED")
    
    confirmation = conn.recv(1024)
    
    if confirmation == "OK":
        """ CHECK IF VALID USERNAME AND PASSWORD COMBINATION """
        #print "rec_pswd: " + repr(received_password) + "\t stored_pswd: " + (stored_password)
        if repr(received_password) == (stored_password):
            conn.send("LOGIN SUCCESSFUL")
            return True
        else:
            conn.send("INVALID CREDENTIALS")
            print "invalid credentials"
            return False
        
        
""" CONNECT TO DATABASE """
db = MySQLdb.connect(host="localhost", user="root", passwd="0000", db="chat") 

cur = db.cursor() 

""" SETUP CONNECTION FOR PROCESS """
HOST = '172.18.44.108' 
PORT = 9000
s = socket(AF_INET, SOCK_STREAM)
s.bind((HOST, PORT)) 
s.listen(100) # how many connections it can receive at one time 

while True:
    global conn
    conn, addr = s.accept() 
    print 'Connected by', addr 
    
    r = ReceiveThreadServer(s).start()
    


conn.close()
