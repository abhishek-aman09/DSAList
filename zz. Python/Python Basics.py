print("Helllo World")

def myFunction():
    if 3 > 2:
        print("3 is greater than 2")
    else:
        print("What the Fuck")

    print("This is my function")

# Single Line Comment
"""

Multi 
Line 
Comment

"""

# If you use the global keyword, the variable belongs to the global scope:

myFunction()


fruits = ["apple", "banana", "cherry"]
x, y, z = fruits
x = 3
# print(x , y , z)

# complex numbers can't be converted into int or float

import random
randomNumber = random.randint(1, 100)
print(randomNumber)

for x in range(4, 8):
    print(x)

# this has to be the first parameter for all methods which are not static
class Person :
    def __init__(this, name, age):
        this.name = name
        this.age = age
    
    def __str__(this):
        return f"name is :{this.name} and age is ({this.age})"
    
    def defineClass(this):
        print("This is the person class")



obj = Person("Abhishek", 26)

print(obj)

# Inheritance

class Human(Person):
    pass
# Even if the child has no members, we have to pass argumets
# to intantiate parent (kinda like super)
human = Human("Aman", 25)
print(human)
human.defineClass()

# calling super
class Man(Person) :
    def __init__(this, name, age, sex):
        super().__init__(name, age)
        this.sex = sex

    def __str__(this):
        return f"name : {this.name}, age : {this.age}, sex : {this.sex}"


man = Man("AmanBoy", 32, "Male")
print(man)

# Iterable and Iterator (Applicable for lists, tuples, sets and dictionaries as well)

name = "This is My Name"
iterable = iter(name)

print(next(iterable))
print(next(iterable))
print(next(iterable))
print(next(iterable))

# Scoping local, global => naming and global keyword
# If you operate with the same variable name inside and outside of a function, Python will treat them as two separate variables,
# one available in the global scope (outside the function) and one available in the local scope (inside the function):

x = 200
y = 100
def printX() :
    x = 10
    global y
    y = 2000
    print(x, y)

printX()
print(x, y)


# make use of global variable inside method, it will make the variable global

#If you use the nonlocal keyword, the variable will belong to the outer function:
def myfunc1():
  x = "Jane"
  def myfunc2():
    nonlocal x
    x = "hello"
  myfunc2()
  return x

print(myfunc1())

# Python and Json

#convert from JSON to python dictionary
import json
# some JSON:
x =  '{ "name":"John", "age":30, "city":"New York"}'
# parse x:
y = json.loads(x)
# the result is a Python dictionary:
print(y["age"])


# convert from python dict to JSON
# a Python object (dict):
x = {
  "name": "John",
  "age": 30,
  "city": "New York"
}
# convert into JSON:
y = json.dumps(x)
# the result is a JSON string:
print(y)


#Json Formatting

x = {
  "name": "John",
  "age": 30,
  "married": True,
  "divorced": False,
  "children": ("Ann","Billy"),
  "pets": None,
  "cars": [
    {"model": "BMW 230", "mpg": 27.5},
    {"model": "Ford Edge", "mpg": 24.1}
  ]
}
# use . and a space to separate objects, and a space, a = and a space to separate keys from their values:
print(json.dumps(x, indent=4, separators=(". ", " = ")))

# try catch in python
# try is try, catch is except, finally is finally, throws is raise

# String formatting
price = 49
txt = f"It is very {'Expensive' if price>50 else 'Cheap'}"

print(txt)
#You can perform any operation, even mehtod calling indside the {}.

# User Input, inputs are string by default. use int(x) to convert it into integer.

name = input("Enter your Name")
print(f"Your name is {name}")
 