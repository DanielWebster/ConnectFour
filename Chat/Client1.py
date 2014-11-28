from socket import *
from threading import Thread

import tkMessageBox
import sys
from Tkinter import *

HOST = 'localhost'
PORT = 9000
s = socket(AF_INET, SOCK_STREAM)
s.connect((HOST, PORT)) 

class ReceiveThread(Thread):

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
                #mySocket.close()

    def stop(self):
        self.shouldstop = True 

def login(): 
    
    while True: 
        #print "Waiting for response..."
        response = s.recv(1024) 
        #print response
        
        if response == "CONNECTED":
            s.send(username.get())
        elif response == "USERNAME RECEIVED":
            s.send(password.get()) 
        elif response == "PASSWORD RECEIVED":
            print "Checking credentials..."
            s.send("OK")
        elif response == "LOGIN SUCCESSFUL":
            print "Successfully logged in!"
            friend = "Jake"
            s.send(friend)
            """ AFTER LOGGED IN SETUP AS SERVER ON DIFFERENT PORT """
        elif response == "INVALID CREDENTIALS":
            tkMessageBox.showerror(title="Error",message="Wrong username/password combination!")
           # print "Wrong username/password combination!"
            break
        elif "IP" in str(response):
            #print response
            #connectAsServer()
            """ INITIATE CONNECTION WITH FRIEND """
            connectToFriend(response)
            break
        elif response == "NEW USER":
            s.send(username.get())
        elif response == "NEW PASSWORD":
            s.send(password.get())
        else:
            s.send("LOGIN")
            break
    
    print "I am outside the while loop"
    
def connectToFriend(IP):
    print "IN CONNECT TO FRIEND"
    friendIP = IP.split("IP:")
    friendIP = friendIP[1]
    print "Connect to this IP " + friendIP
    HOST = friendIP
    PORT = 9001
    s = socket(AF_INET, SOCK_STREAM)
    s.connect((HOST, PORT))
    
    r = ReceiveThread(s).start()
    
    while True:
        s.send(raw_input("->"))
        #response = s.recv(1024)
        #print response
    
def connectAsServer():
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
    r = ReceiveThread(s).start()
   
    while True:
        #response = conn.recv(1024)
        #print response
        conn.send(raw_input("->" ))
        
        
    """ BEGIN MESSAGE COMMUNICATION BETWEEN USERS """
    print "after while"
    conn.close()
    print "connection closed"
def newUser():
    s.send("NEW USER")
    mbutton = Button(mGui, text = 'OK', command = something, fg = 'white', bg = 'green').grid(row = 2, column = 1, sticky = E)
    
def something():
    s.send(username.get())
    s.send(password.get()) 
    mbutton = Button(mGui, text = 'OK', command = login, fg = 'yellow', bg = 'red').grid(row = 2, column = 1, sticky = E)
               
""" GUI STUFF """
mGui = Tk()
username = StringVar()
password = StringVar()

mGui.geometry('450x200+200+200')
mGui.title("Networking Chat Project")

pwlabel = Label(mGui, text='Username: ').grid(row = 0, column = 0, sticky = W)
unlabel1 = Label(mGui, text='Password: ').grid(row = 1, column = 0, sticky = W)

unEntry = Entry(mGui, textvariable=username).grid(row = 0, column = 1)
pwEntry = Entry(mGui, textvariable=password).grid(row = 1, column = 1)

mbutton = Button(mGui, text = 'OK', command = login, fg = 'yellow', bg = 'red').grid(row = 2, column = 1, sticky = E)

menubar = Menu(mGui)

filemenu = Menu(menubar, tearoff = 0)
filemenu.add_command(label="New", command = newUser)

menubar.add_cascade(label="File",menu=filemenu)

mGui.config(menu=menubar)

mGui.mainloop()

s.close()