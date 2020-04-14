@file:Suppress("DEPRECATION")

package com.rookia.android.androidutils.framework.utils.security


import android.content.Context
import android.os.Build
import android.security.KeyPairGeneratorSpec
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import androidx.annotation.RequiresApi
import com.rookia.android.androidutils.utils.security.Encryption
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.interfaces.RSAPublicKey
import java.util.*
import javax.crypto.Cipher
import javax.inject.Inject
import javax.inject.Singleton
import javax.security.auth.x500.X500Principal


@Singleton
class EncryptionImpl @Inject constructor(private val context: Context) : Encryption {

    companion object {
        private const val CIPHER_TYPE = "RSA/ECB/PKCS1Padding"
        private const val KEY_ALGORITHM_RSA = "RSA"

        private const val KEYSTORE_PROVIDER_ANDROID_KEYSTORE = "AndroidKeyStore"
    }

    private fun getKeystoreInstance(): KeyStore {
        val keyStore: KeyStore = KeyStore.getInstance(KEYSTORE_PROVIDER_ANDROID_KEYSTORE)
        keyStore.load(null)
        return keyStore
    }

    private fun createNewKeys(alias: String) {

        val keyStore = getKeystoreInstance()

        try {
            //Create new key if needed
            if (keyStore.containsAlias(alias).not()) {
                val spec = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    val start = Calendar.getInstance()
                    val end = Calendar.getInstance()
                    end.add(Calendar.YEAR, 1)

                    KeyPairGeneratorSpec.Builder(context)
                        .setAlias(alias)
                        .setSubject(X500Principal("CN=$alias, O=Android Authority"))
                        .setSerialNumber(8938.toBigInteger())
                        .setStartDate(start.time)
                        .setEndDate(end.time)
                        .build()

                } else {

                    KeyGenParameterSpec.Builder(
                        alias,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                        .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                        .build()

                }
                val generator = KeyPairGenerator.getInstance(
                    KEY_ALGORITHM_RSA,
                    KEYSTORE_PROVIDER_ANDROID_KEYSTORE
                )

                generator.initialize(spec)
                generator.generateKeyPair()
            }
        } catch (e: Exception) {
            deleteKey(alias)
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Suppress("unused")
    @Throws(KeyStoreException::class)
    private fun deleteKey(alias: String) {
        val keyStore = getKeystoreInstance()
        keyStore.deleteEntry(alias)
    }

    override fun encryptString(text: String?, alias: String): String {

        if (text.isNullOrBlank()) return ""

        val keyStore = getKeystoreInstance()
        var encryptedText = ""
        try {
            createNewKeys(alias)
            val privateKeyEntry = keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry
            val publicKey = privateKeyEntry.certificate.publicKey as RSAPublicKey


            val inCipher = Cipher.getInstance(CIPHER_TYPE)
            inCipher.init(Cipher.ENCRYPT_MODE, publicKey)

            val bytes = inCipher.doFinal(text.toByteArray())
            encryptedText = Base64.encodeToString(bytes, Base64.DEFAULT)


        } catch (e: Exception) {
        }

        return encryptedText
    }

    override fun decryptString(text: String?, alias: String): String {

        if (text.isNullOrBlank()) return ""

        val keyStore = getKeystoreInstance()
        var decryptedText = ""
        try {
            val privateKeyEntry =
                keyStore.getEntry(alias, null) as KeyStore.PrivateKeyEntry? ?: return ""
            //RSAPrivateKey privateKey = (RSAPrivateKey) privateKeyEntry.getPrivateKey();
            val output = Cipher.getInstance(CIPHER_TYPE)
            output.init(Cipher.DECRYPT_MODE, privateKeyEntry.privateKey)

            val encryptedData = Base64.decode(text, Base64.DEFAULT)
            val decodedData = output.doFinal(encryptedData)
            decryptedText = String(decodedData)

        } catch (e: Exception) {
        }

        return decryptedText
    }


}