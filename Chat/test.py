from Crypto.PublicKey import RSA
import random
import sys

int = random.randrange(3,2147483647)
print int
if int%2 == 0:
    int += 1
#print sys.maxint
print int
key = RSA.generate(1024, e=int)


binPrivKey = key.exportKey('PEM')
binPubKey =  key.publickey().exportKey('PEM')

privKeyObj = RSA.importKey(binPrivKey)
pubKeyObj =  RSA.importKey(binPubKey)

msg = "attack at dawn"
emsg = pubKeyObj.encrypt(msg, 'x')[0]
dmsg = privKeyObj.decrypt(emsg)
emsg2 = privKeyObj.decrypt(msg)
dmsg2 = pubKeyObj.encrypt(emsg2, 'x')[0]

#f = open('PrivateKey', 'w')
#f.write(binPrivKey)
#f.close()
#f = open('PublicKey', 'w')
#f.write(binPubKey)
#f.close()
#print key
#print "private key: " + binPrivKey
print "public key: " + binPubKey


#print msg
#print emsg
#print dmsg
#print "emsg2: " + emsg2
#print "dmsg2: " + dmsg2