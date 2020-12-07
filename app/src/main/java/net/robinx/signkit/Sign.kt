/*
 * Copyright (c) 2020 Huami Inc. All Rights Reserved.
 */

package net.robinx.signkit

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.content.pm.Signature
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


/**
 * MD5: CE:E4:D6:1B:ED:5F:42:23:A1:05:E9:38:3F:F5:B6:F2
 * SHA1: AB:AA:55:F9:3A:AE:16:5C:54:86:15:27:5B:3A:6E:28:78:93:F3:9A
 * SHA-256: 93:4E:37:95:75:C2:01:7C:D3:43:AE:2B:D4:1A:CF:62:68:DA:2A:0C:12:CF:05:BF:1D:55:07:2C:29:5E:21:02
 *
 * 获得以上签名信息 去冒号，字母小写
 *
 * @author wangbin@huami.com <br>
 * @since 2020/12/2
 */
object Sign {
    @SuppressLint("PackageManagerGetSignatures")
    @Throws(PackageManager.NameNotFoundException::class)
    private fun getRawSignature(
        paramContext: Context,
        paramString: String?
    ): Array<Signature>? {
        if (paramString == null || paramString.isEmpty()) {
            throw RuntimeException("获取签名失败，包名为 null")
        }
        val localPackageManager = paramContext.packageManager
        val localPackageInfo: PackageInfo?
        localPackageInfo =
            localPackageManager.getPackageInfo(paramString, PackageManager.GET_SIGNATURES)
        if (localPackageInfo == null) {
            throw RuntimeException("信息为 null, 包名 = $paramString")
        }
        return localPackageInfo.signatures
    }

    /**
     * 开始获得签名
     * @param packageName 报名
     * @return
     */
    fun getSign(context: Context, packageName: String, algorithm: String = Encrypt.MD5): String? {
        val arrayOfSignature =
            kotlin.runCatching { getRawSignature(context, packageName) }.getOrNull()
        if (arrayOfSignature == null || arrayOfSignature.isEmpty()) {
            return null
        }
        return Encrypt.getMessageDigest(arrayOfSignature[0].toByteArray(), algorithm)
    }
}

object Encrypt {
    const val MD5 = "MD5"
    const val SHA1 = "SHA1"
    const val SHA_256 = "SHA-256"

    fun getMessageDigest(byteStr: ByteArray, algorithm: String = MD5): String? {
        try {
            val messageDigest = MessageDigest.getInstance(algorithm)
            messageDigest.run {
                reset()
                update(byteStr)
            }
            val byteArray = messageDigest.digest()

            val result = StringBuffer()
            for (i in byteArray.indices) {
                if (Integer.toHexString(0xFF and byteArray[i].toInt()).length == 1) {
                    result.append("0").append(
                        Integer.toHexString(
                            0xFF and byteArray[i]
                                .toInt()
                        )
                    )
                } else {
                    result.append(Integer.toHexString(0xFF and byteArray[i].toInt()))
                }
            }
            return result.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return null
    }

}