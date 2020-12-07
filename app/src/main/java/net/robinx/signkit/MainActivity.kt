package net.robinx.signkit

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import net.robinx.signkit.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvMd5.setOnClickListener {
            val pkgName = binding.etPkgname.text.toString().trim()
            val result = Sign.getSign(this, pkgName)
            binding.tvSignature.text = result

            binding.tvCopy.visibility = View.VISIBLE
        }

        binding.tvSha1.setOnClickListener {
            val pkgName = binding.etPkgname.text.toString().trim()
            val result = Sign.getSign(this, pkgName, Encrypt.SHA1)
            binding.tvSignature.text = result

            binding.tvCopy.visibility = View.VISIBLE

        }

        binding.tvSha256.setOnClickListener {
            val pkgName = binding.etPkgname.text.toString().trim()
            val result = Sign.getSign(this, pkgName, Encrypt.SHA_256)
            binding.tvSignature.text = result

            binding.tvCopy.visibility = View.VISIBLE
        }

        binding.tvCopy.setOnClickListener {
            val cm: ClipboardManager =
                getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("android-signature", binding.tvSignature.text)
            cm.setPrimaryClip(clipData)
        }
    }
}