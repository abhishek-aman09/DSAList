# Path should be relative to root folder.
file = open("zz. Python/test.txt")
print(file.read(10)) #num of char you want in return
print(file.readline()) # only read a single line

file.close()


#alternate
with open("zz. Python/test.txt") as newFile:
    print(newFile.read())
    newFile.close()

'''
open() takes two params, 1st is file path, 2nd is the operation
you want to perform.
"r" - Read - Default value. Opens a file for reading, error if the file does not exist
"a" - Append - Opens a file for appending, creates the file if it does not exist
"w" - Write - Opens a file for writing, creates the file if it does not exist
"x" - Create - Creates the specified file, returns an error if the file exists

In addition, 2nd param can be appended with another arg
"t" - Text - Default value. Text mode
"b" - Binary - Binary mode (e.g. images)

open("filename.txt") is equivalent of open("filename.txt", "rt") as rt is default for 2nd arg

'''

f = open("zz. Python/created.txt", "x")
f.write("This file is created and written. ")

f.close()

f = open("zz. Python/created.txt", "a")
f.write("This part is appended at the end.")
f.close()

# To delete a file we use os module

import os
if os.path.exists("zz. Python/created.txt"):
  os.remove("zz. Python/created.txt")
else:
  print("The file does not exist")
