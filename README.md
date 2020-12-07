# SignKit
微信签名工具增强,根据包名获取MD5/SHA1/SHA-256，可复制

# Usage

```kotlin
val result = Sign.getSign(this, pkgName, Encrypt.MD5)
val result = Sign.getSign(this, pkgName, Encrypt.SHA1)
val result = Sign.getSign(this, pkgName, Encrypt.SHA_256)
```
