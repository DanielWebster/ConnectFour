from Crypto.PublicKey import RSA

key = RSA.generate(1024)

binPrivKey = key.exportKey('DER')
binPubKey =  key.publickey().exportKey('DER')

privKeyObj = RSA.importKey(binPrivKey)
pubKeyObj =  RSA.importKey(binPubKey)

msg = "attack at dawn"
emsg = pubKeyObj.encrypt(msg, 'x')[0]
dmsg = privKeyObj.decrypt(emsg)
emsg2 = privKeyObj.decrypt(msg)
dmsg2 = pubKeyObj.encrypt(emsg2, 'x')[0]


print "private key: " + binPrivKey
print "public key: " + binPubKey

print msg
print emsg
print dmsg
print "emsg2: " + emsg2
print "dmsg2: " + dmsg2