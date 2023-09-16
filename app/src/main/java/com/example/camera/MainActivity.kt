package com.example.camera

import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.camera.databinding.ActivityMainBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    lateinit var photoUri: Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.myImageView.setOnClickListener {
            val file = createImageFile()
            photoUri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID,
                file
            )
            getTakeImageContent.launch(photoUri)
        }
    }
    private val getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()){
            binding.myImageView.setImageURI(photoUri)
        }
    var currentImagePaht = ""
    private fun createImageFile(): File{
        val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val externalFilesDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        println("createImageFile ishlayapti")
        return File.createTempFile("JPEG_$format", ".jpg", externalFilesDir).apply {
            currentImagePaht = absolutePath
        }
    }
}