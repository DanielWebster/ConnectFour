import tkMessageBox
from Tkinter import *


def addFriend():
    print username.get()
    newFriend.destroy()


newFriend = Tk()
username = StringVar()

#newFriend.geometry('350x100+200+200')
newFriend.title("New Friend")

labelName = Label(newFriend, text="Friend's Username: ").grid(row = 0, column = 0, sticky = W)

usernameEntry = Entry(newFriend, textvariable=username).grid(row = 0, column = 1)

mbutton = Button(newFriend, text = 'Add', command = addFriend, fg = 'white', bg = 'blue').grid(row = 3, column = 1, sticky = E)


newFriend.mainloop()