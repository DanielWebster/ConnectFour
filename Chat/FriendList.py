import tkMessageBox
from Tkinter import *
from sys import argv

    
def selectFriend():
    print listbox.get(listbox.curselection())
    
friendlist = Tk()
username = StringVar()

#newFriend.geometry('350x100+200+200')
friendlist.title("Friend List")

labelName = Label(friendlist, text="Friend List: ").grid(row = 0, column = 0, sticky = NW)

listbox = Listbox(friendlist, height = 15)
listbox.grid(row=0, column=1)
    
for i in range(len(argv)):
    if(i > 0):
        listbox.insert(END, argv[i])

mbutton = Button(friendlist, text = 'Talk', command = selectFriend, fg = 'white', bg = 'blue').grid(row = 3, column = 1, sticky = E)


friendlist.mainloop()