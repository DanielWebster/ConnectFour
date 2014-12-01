import tkMessageBox
from Tkinter import *
from sys import argv

    
def selectFriend():
    print listbox.get(listbox.curselection())
    friendlist.destroy()
    
friendlist = Tk()
username = StringVar()

friendlist.geometry('+200+100')
friendlist.title("Friend List")

labelName = Label(friendlist, text="Friend List: ").grid(row = 0, column = 0, sticky = NW)

listbox = Listbox(friendlist, height = 15)
listbox.grid(row=0, column=1)
    

friend = argv[1].split(" ")
for i in friend:
    listbox.insert(END, i)

mbutton = Button(friendlist, text = 'Talk', command = selectFriend, fg = 'white', bg = 'blue').grid(row = 3, column = 1, sticky = E)


friendlist.mainloop()