from Crypto.PublicKey import RSA

f = open('PrivateKey', 'w')

key = RSA.generate(1024)

binPrivKey = key.exportKey('PEM')
binPubKey =  key.publickey().exportKey('PEM')

privKeyObj = RSA.importKey(binPrivKey)
pubKeyObj =  RSA.importKey(binPubKey)

msg = "attack at dawn"
emsg = pubKeyObj.encrypt(msg, 'x')[0]
dmsg = privKeyObj.decrypt(emsg)
emsg2 = privKeyObj.decrypt(msg)
dmsg2 = pubKeyObj.encrypt(emsg2, 'x')[0]
f.write(binPrivKey)
f.close()
f = open('PublicKey', 'w')
f.write(binPubKey)
f.close()

print "private key: " + binPrivKey
print "public key: " + binPubKey

print msg
print emsg
print dmsg
print "emsg2: " + emsg2
print "dmsg2: " + dmsg2