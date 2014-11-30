from Crypto.PublicKey import RSA

privKeyObj = ("-----BEGIN RSA PRIVATE KEY-----\n" 
"MIICXQIBAAKBgQDDtsvGHhYiJDAHkHRGvpYZ2FAWUOHTV01DCQgluSNb/09XSL/Q\n" 
"3snJlDgUDvWvEyaIW9Gj2efIzn6e5CkG5iKn/3ttRlWDGGcY3k2iNXjSvQAYjSpl\n" 
"t59hHPHCZvPz+0yHs6DVvc+owBxiZAByh1NxK66bzBnEdzU1Sf7aZZ4pMQIDAQAB\n" 
"AoGAPcoxjawkCsVoEItP2qIDW8eKiXEhywquDvMECnzoJ/x0PTdvr+8WwDi2d8a9\n" 
"VHf0W2q5xkRexGxFV77rIQ15dQh3kTEquMKKtmfnKfZbnBxPudn0LlENwKGVnOpC\n" 
"FOkATzH04L4nsjkbxibVuLbniXjmhgHzE3AulIpXr9fN5m0CQQDGQiDjwHlZa+/1\n" 
"VFhcFjrYddi62VFDwDmJHXGXnp86d+cbhPFAgrjUkFU+ELyhiTKJWDfmFgAASSnU\n" 
"p79vJv6vAkEA/Lb4aoBtJMbDRkSaCpu8erHwOH0ZFuQrW/oLstO6C84txhjEGQcz\n" 
"cH1sfuimwh3tg7DVpUsA8DKFqaxZiXROHwJAN3W2N5/XEmG0XX97vD7ntTe6KgKy\n" 
"ze4O6kFXTl+sETILb1JQHoiy5Zt+jP8nlVSI04zfDjknRO0yi29liNVytwJBALdc\n" 
"Dqw/mHFpof/XAKmXy85+Uty5r72TOf6XU2uiAchVBZNJHudF+UWyS0ldhrkru8yk\n" 
"Pq+a1whwr9inS6PW9mMCQQCZSeu14EaTtEpdxPEXstpgQoZyd8ss8NjU+VhO4e0f\n" 
"3Z69TDxC338kLwY+kvfroX81H9BIGpVYMAeHznlEUaZ7\n" 
"-----END RSA PRIVATE KEY-----")
#print privKeyObj
private = RSA.importKey(privKeyObj)
pubKeyObj = ("-----BEGIN PUBLIC KEY-----\n"
"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDtsvGHhYiJDAHkHRGvpYZ2FAW\n"
"UOHTV01DCQgluSNb/09XSL/Q3snJlDgUDvWvEyaIW9Gj2efIzn6e5CkG5iKn/3tt\n"
"RlWDGGcY3k2iNXjSvQAYjSplt59hHPHCZvPz+0yHs6DVvc+owBxiZAByh1NxK66b\n"
"zBnEdzU1Sf7aZZ4pMQIDAQAB\n"
"-----END PUBLIC KEY-----")
public = RSA.importKey(pubKeyObj)

msg = "Poison for your body"
emsg = public.encrypt(msg, "x")[0]
dmsg = private.decrypt(emsg)

print msg
print emsg
print dmsg