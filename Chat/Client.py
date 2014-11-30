from socket import *
from threading import Thread

import tkMessageBox
import sys
from Tkinter import *

import json
import os
import subprocess
from Crypto.Hash import *

friends = []
hash = SHA512.new()

HOST = 'localhost'
PORT = 9000
s = socket(AF_INET, SOCK_STREAM)
s.connect((HOST, PORT))

def displayFriends():
    for friend in friends:
        print "Friend: " + friend[0]

def login(): 
    s.send("LOGIN")
    while True: 
        #print "Waiting for response..."
        response = s.recv(1024) 
        #print response
        
        if response == "CONNECTED":
            s.send(username.get())
        elif response == "USERNAME RECEIVED":
             hash.update(password.get())
             s.send(hash.digest()) 
        elif response == "PASSWORD RECEIVED":
            print "Checking credentials..."
            s.send("OK")
        elif response == "LOGIN SUCCESSFUL":
            print "Successfully logged in!"
            response = s.recv(1024)
            global friends
            friends = json.loads(response)
            displayFriends()
            break
        elif response == "INVALID CREDENTIALS":
            tkMessageBox.showerror(title="Error",message="Wrong username/password combination!")
            break
        else:
            break
    
    os.system("start python ConnectAsServer.py")
    mGui.destroy()
    
    while True:
        friendName = raw_input("Connect to friend: ")
        connectToFriend(friendName)
    
def connectToFriend(friendName):
    print "Attempting to connect to friend: " + friendName
    
    #Get IP from friend name
    for friend in friends:
        if friend[0] == friendName:
            friendIP = friend[1]
    
    os.system("start python ConnectToFriend.py " + friendName + " " + friendIP)
    
    
def newUser():
    s.send("NEW USER")
    proc = subprocess.Popen(['python', 'CreateUser.py'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    input = proc.communicate()[0]
    inputs = input.split("||")
    
    s.send(inputs[0])
    hash.update(inputs[1])
    s.send(hash.digest()) 


               
""" GUI STUFF """
mGui = Tk()
username = StringVar()
password = StringVar()

mGui.geometry('450x200+200+200')
mGui.title("Networking Chat Project")

pwlabel = Label(mGui, text='Username: ').grid(row = 0, column = 0, sticky = W)
unlabel1 = Label(mGui, text='Password: ').grid(row = 1, column = 0, sticky = W)

unEntry = Entry(mGui, textvariable=username).grid(row = 0, column = 1)
pwEntry = Entry(mGui, textvariable=password, show="*").grid(row = 1, column = 1)

mbutton = Button(mGui, text = 'OK', command = login, fg = 'yellow', bg = 'red').grid(row = 2, column = 1, sticky = E)

menubar = Menu(mGui)

filemenu = Menu(menubar, tearoff = 0)
filemenu.add_command(label="New", command = newUser)

menubar.add_cascade(label="File",menu=filemenu)

mGui.config(menu=menubar)

mGui.mainloop()

s.close()
