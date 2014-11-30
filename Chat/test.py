from Crypto.Hash import *


hash = SHA512.new()
hash.update('message')
print hash.digest()

