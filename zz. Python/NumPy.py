'''
NumPy is a Python library.

NumPy is used for working with arrays.

NumPy is short for "Numerical Python".
'''

import numpy as np

array = np.array([1, 2, 3, 4, 5])
print(type(array))

# Array dimensions. ndmin defines the size of array

arr = np.array([1, 2, 3, 4], ndmin=5)

print(arr)
print('number of dimensions :', arr.ndim)
print(arr.shape) # gives shape of array

# Indexing is 0 based and similar to java. It also supports negative index starting form -1.
# For 2 or more dimentions, we access elements like arr[0,1] and not arr[0][1]

# Array slicing is defined like arr[start:end:step] or arr[start:end]
# Also supports negative index for slicing
# Slicing 2-D array arr[rowNumber, colStart:colEnd] or arr[rowStart:rowEnd, colNum] or arr[rowStart:rowEnd, colStart:colEnd] 

# Data types. use arr.dtype to get data type. We can specify data type during array creation
arr = np.array([1, 2, 3, 4], dtype='i4') # array of type int of size 4
print(arr)
print(arr.dtype)
# For converting data type e.g. from float to int use arr.astype('i')

# copy vs view. arr.copy() creates a new array while arr.view() created just a new reference to old array.
# We can reshape the array if the dimensions of new shape matches the number of element in current array.
# Reshape return a view. pass arr.reshape(2,3,-1) -1 if dimension is unclear.
# To flatten a higher order array to 1-D array use arr.reshape(-1)
# Iteration
arr = np.array([1, 2, 3, 4, 5, 4, 4])

# for x in arr:
#   for y in x:
#     print(y)

# for advanced iterations like skipping values or changing data types,
# we use nditer() method
# Use ndenumerate(arr) for index based iteration
for idx, x in np.ndenumerate(arr):
  print(idx, x)
# idx returns an array of index, e.g. (0, 1) for a 2d array.

# read array join and split method if required.
# array search, where() return all elements where 4 is present.
# returns an array of arrays.
x = np.where(arr == 4)
print(x[0])
# you can pass expression as well like where(arr % 2 == 0) for even numbers.
# read about np.searchsorted(arr, [el1, el2, el3]) used to search in sorted array.

# read array filtering using boolean array indexing if required.

# NUMPY RANDOM  
from numpy import random
# Generate a floating number between 0 and 1: random.rand()
# Generate a array of floats of size 5: random.rand(5)
# Generate a 1-D array containing 5 random integers from 0 to 100:
x=random.randint(100, size=(5))
print(x)

# choose a random number from an array
y = random.choice(x)
print("Random number ", y)
