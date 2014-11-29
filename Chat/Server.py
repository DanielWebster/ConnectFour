# import socket
import MySQLdb
from socket import *
import json

from Crypto import *

username = ""
friendsArr = []

def friendsList():
    #Get complete list of all friends + their IPs for logged in user
    print "Getting friends list for " + username
    cur.execute("SELECT friend FROM friends WHERE username=%s", (username))
    for row in cur.fetchall():
        friendsArr.append([row[0], getIP(row[0])])
        
    """ Serializing the list to send over the connection """    
    conn.sendall(json.dumps(friendsArr))
        
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
    cur.execute("UPDATE ips SET ip = %s WHERE username = %s", (addr[0], username))
    db.commit()
#
def createUser():
    print "Creating new user..."
    conn.sendall("NEW USER")
    new_user = conn.recv(1024)
    conn.sendall("NEW PASSWORD")
    new_password = conn.recv(1024)
    
    print "NEW USERNAME: " + new_user
    print "NEW PASSWORD: " + new_password
    
    """ ******************* CHECK IF USERNAME ALREADY EXISTS ******************** """
    cur.execute("SELECT Username FROM users WHERE Username=%s", new_user)
    try:
        stored_username = cur.fetchone()
        stored_username = stored_username[0]
        print "Username already exists!"
    except Exception:
        """ INSERT NEW USER INTO USERS TABLE """
        cur.execute("INSERT INTO users(username, password) VALUES(%s, %s)", (new_user, new_password))
        db.commit()
        print "User created!"
    
def attempt_login():
    conn.sendall("CONNECTED")
    print "Attempting login."
    """FIRST ARGUMENT TO RECEIVE: USERNAME """
    global username 
    username = conn.recv(1024) 
    
    print "Received ", repr(username) 
    """CANT USE SEND ALL"""
    conn.sendall("USERNAME RECEIVED")

    cur.execute("SELECT Password FROM users WHERE Username=%s", username)
    
    try:
        stored_password = cur.fetchone()
        stored_password = stored_password[0]
        #print "Stored password: " + stored_password[0]
    except Exception:
        print "USERNAME does not exist!"
    
   
    """SECOND ARGUMENT TO RECEIVE: PASSWORD """
    received_password = conn.recv(1024) 
    print "Received " + repr(received_password) 
    conn.sendall("PASSWORD RECEIVED")
    
    confirmation = conn.recv(1024)
    
    if confirmation == "OK":
        """ CHECK IF VALID USERNAME AND PASSWORD COMBINATION """
        print "rec_pswd: " + str(received_password) + "\t stored_pswd: " + str(stored_password)
        if str(received_password) == str(stored_password):
            conn.sendall("LOGIN SUCCESSFUL")
            return True
        else:
            conn.sendall("INVALID CREDENTIALS")
            print "invalid credentials"
            return False
        
        
""" CONNECT TO DATABASE """
db = MySQLdb.connect(host="localhost", user="root", passwd="0000", db="chat") 

cur = db.cursor() 

""" SETUP CONNECTION FOR PROCESS """
HOST = 'localhost' 
PORT = 9000
s = socket(AF_INET, SOCK_STREAM)
s.bind((HOST, PORT)) 
s.listen(2) # how many connections it can receive at one time 

while True:
    conn, addr = s.accept() 
    print 'Connected by', addr 
    
    """ While user is NOT logged in"""
    login = False
    while login == False: 
        #conn.sendall("NOT LOGGED IN")
        option = conn.recv(1024)
        if option == "NEW USER":
            createUser()
        elif option == "LOGIN":
            print "User not logged in..."
            login = attempt_login()
    
    #Update IP for user in DB
    updateIP()
    #Get and send friend list + respective IPs to user
    friendsList()

conn.close()
