from socket import *
from threading import Thread

import tkMessageBox
from Tkinter import *

import sys
import json
import os
import subprocess

from Crypto.Hash import *
from Crypto.PublicKey import RSA

import uuid


pubKeyObj = ("-----BEGIN PUBLIC KEY-----\n"
"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDtsvGHhYiJDAHkHRGvpYZ2FAW\n"
"UOHTV01DCQgluSNb/09XSL/Q3snJlDgUDvWvEyaIW9Gj2efIzn6e5CkG5iKn/3tt\n"
"RlWDGGcY3k2iNXjSvQAYjSplt59hHPHCZvPz+0yHs6DVvc+owBxiZAByh1NxK66b\n"
"zBnEdzU1Sf7aZZ4pMQIDAQAB\n"
"-----END PUBLIC KEY-----")
publicKey = RSA.importKey(pubKeyObj)

sessionKey = ''


friends = []

HOST = 'localhost'
PORT = 9000
s = socket(AF_INET, SOCK_STREAM)
s.connect((HOST, PORT))


def displayFriends():
    friendlist = ''
    for friend in friends:
        friendlist += (friend[0] + " ")
    #print friendlist
    #return friendlist
    proc = subprocess.Popen([sys.executable, 'Friendlist.py', friendlist], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    input = proc.communicate()[0]
    contact = (str(input).strip())
    return contact

def addFriend():
    proc = subprocess.Popen(['python', 'AddFriend.py'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    input = proc.communicate()[0]
    friend=(str(input).strip())
    
    print "Adding friend: " + friend
    print "To User: " + username.get()
    s.send("ADD FRIEND")
    s.send(username.get())
    if s.recv(1024) == "USER RECEIVED":
        s.send(friend)

def login(): 
    s.send("LOGIN")
    while True: 
        #print "Waiting for response..."
        response = s.recv(1024) 
        #print response
        
        if response == "CONNECTED":
            s.send(username.get())
        elif response == "USERNAME RECEIVED":
            s.send(SHA512.new(password.get()).digest()) 
        elif response == "PASSWORD RECEIVED":
            print "Checking credentials..."
            s.send("OK")
        elif response == "LOGIN SUCCESSFUL":
            print "Successfully logged in!"
            s.send("UPDATE IP")
            s.send("FRIENDS LIST")
            response = s.recv(1024)
            global friends
            friends = json.loads(response)
            os.system("start python ConnectAsServer.py")
            mGui.destroy()
            newSessionKey()
            #Before communication channel is open, send the secret key
            s.send("SESSION KEY")
            global sessionKey
            s.send(sessionKey)
            #print "Try to add a friend..."
            #addFriend()
            while True:
                #friendName = raw_input("Connect to friend: ")
                connectToFriend(displayFriends())
            break
        elif response == "INVALID CREDENTIALS":
            tkMessageBox.showerror(title="Error",message="Wrong username/password combination!")
            break
        else:
            break 


def newSessionKey():
    global sessionKey
    # generate a random secret key
    # Will be used for individual messages for users
    # New key per session
    sessionKey = str(uuid.uuid4().hex)
    
    
def connectToFriend(friendName):
    print "Getting friend IP from server..."
    print "Attempting to connect to friend: " + friendName
    s.send("CONNECT TO FRIEND")
    s.send(friendName)
    friendIP = s.recv(1024)
    messageSessionKey = s.recv(1024)
    friendKey = s.recv(1024)
    print "MessageSessionKey: " + messageSessionKey
    print "FriendKey: " + friendKey
    os.system("start python ConnectToFriend.py " + friendName + " " + friendIP + " " + str(messageSessionKey) + " " + str(friendKey))
    
    
def newUser():
    s.send("NEW USER")
    proc = subprocess.Popen(['python', 'CreateUser.py'], stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    input = proc.communicate()[0]
    inputs = input.split("||")
    
    s.send(inputs[0])
    s.send(SHA512.new(str(inputs[1]).strip()).digest()) 

               
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
