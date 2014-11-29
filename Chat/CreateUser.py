import tkMessageBox
from Tkinter import *


def createUser():
    if password.get() == confirm.get():
       print username.get()
        #pass
    else:
        tkMessageBox.showerror('Error', "Password Doesn't Match")
        #print "Error: Password Doesn't Match."
        #print "Please re-enter password."

newUser = Tk()
username = StringVar()
password = StringVar()
confirm = StringVar()
#newUser.geometry('350x100+200+200')
newUser.title("New User")

labelName = Label(newUser, text='Username: ').grid(row = 0, column = 0, sticky = W)
labelPass = Label(newUser, text='Password: ').grid(row = 1, column = 0, sticky = W)
labelConfirm = Label(newUser, text='Confirm Password: ').grid(row = 2, column = 0, sticky = W)

usernameEntry = Entry(newUser, textvariable=username).grid(row = 0, column = 1)
passwordEntry = Entry(newUser, textvariable=password, show="*").grid(row = 1, column = 1)
confirmEntry = Entry(newUser, textvariable=confirm, show="*").grid(row = 2, column = 1)

mbutton = Button(newUser, text = 'OK', command = createUser, fg = 'white', bg = 'blue').grid(row = 3, column = 1, sticky = E)

menubar = Menu(newUser)

newUser.mainloop()