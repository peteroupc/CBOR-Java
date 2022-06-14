package com.upokecenter.test; import com.upokecenter.util.*;
from fractions import Fraction
import math

def chebtobern(n):
   mat=[[0 for i in range(n + 1)] for j in range(n + 1)]
   for j in range(n + 1): den = Fraction(1, math.comb(n, j))
      for k in range(n + 1): mn = max(0, j + k-n)
         mx = min(j, k) s = sum((-1)**(k-i)*math.comb(2*k, 2*i)*math.comb(n-k, j-i) \
            for i in range(mn, mx + 1)) mat.get(j)[k]=s/den
   return Matrix(mat)
