package com.project.nutriai.ui.camera

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.util.Size
import android.view.LayoutInflater
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.camera.core.AspectRatio
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.project.nutriai.R
import com.project.nutriai.databinding.ActivityCameraBinding
import com.project.nutriai.extensions.cropImage
import com.project.nutriai.extensions.storeImage
import com.project.nutriai.ui.base.BaseActivity
import com.project.nutriai.ui.fruit_identifier.FruitIdentifierActivity
import com.project.nutriai.utils.FileUtils.getImageCacheDir
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

@AndroidEntryPoint
class CameraActivity : BaseActivity<ActivityCameraBinding, CameraViewModel>() {

    override val viewModel: CameraViewModel by viewModels()

    private lateinit var outputDirectory: String

    private lateinit var cameraExecutor: ExecutorService

    private lateinit var backupUri: String

    private lateinit var viewFinderRect: Rect
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null

    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null

    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

    private val startForSingleImage =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { currentUri ->
            if (currentUri != null) {
                startCropImage(currentUri)
            } else {
                showErrorMessage(getString(R.string.failed_to_get_image))
            }
        }

    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            backupUri = result.uriContent.toString()
            startDetect()
        } else {
            showErrorMessage(result.error.toString())
        }
    }

    private val startDetectLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {

        }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityCameraBinding {
        return ActivityCameraBinding.inflate(inflater)
    }

    override fun init(savedInstanceState: Bundle?) {
        initView()
        initListener()
    }

    private fun initView() {
        cameraExecutor = Executors.newSingleThreadExecutor()
        outputDirectory = getImageCacheDir(this)
    }

    private fun initListener() {
        binding.run {
            pvCamera.post {
                setUpCamera()
                viewFinderRect = Rect(
                    cslFinder.left + 12.dp,
                    cslFinder.top + 12.dp,
                    cslFinder.right - 12.dp,
                    cslFinder.bottom - 12.dp
                )
                viewFinderBackground.setViewFinderRect(viewFinderRect)
            }

            imgClose.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }

            ivTakePic.setOnClickListener {
                imageCapture?.let { imageCapture ->
                    val photoFile = createUniqueFile(outputDirectory, FILENAME, PHOTO_EXTENSION)
                    val metadata = ImageCapture.Metadata().apply {
                        isReversedHorizontal = lensFacing == CameraSelector.LENS_FACING_FRONT
                    }
                    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile)
                        .setMetadata(metadata)
                        .build()
                    imageCapture.takePicture(
                        outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                            override fun onError(exc: ImageCaptureException) {
                            }

                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                                ifAtLeastResumed {
                                    val savedUri = output.savedUri ?: Uri.fromFile(photoFile)
                                    setGalleryThumbnail(savedUri)
                                }
                            }
                        })
                }
            }

            imgGallery.setOnClickListener {
                startForSingleImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }
        }
    }

    private val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()
            lensFacing = CameraSelector.LENS_FACING_BACK
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(this))
    }

    private fun bindCameraUseCases() {
        val metrics = DisplayMetrics().also { binding.pvCamera.display.getRealMetrics(it) }
        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        val rotation = binding.pvCamera.display.rotation

        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("")

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        // ImageCapture
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture
            )
            preview?.surfaceProvider = binding.pvCamera.surfaceProvider
        } catch (_: Exception) {
        }
    }

    private fun setGalleryThumbnail(uri: Uri) {
        val bitmap: Bitmap = if (Build.VERSION.SDK_INT < 28) {
            MediaStore.Images.Media.getBitmap(contentResolver, uri)
        } else {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    contentResolver,
                    uri
                )
            )
        }

        val maxWidth = 1024
        val maxHeight = 1024

        val width = bitmap.width
        val height = bitmap.height
        val scaleFactor = max(width / maxWidth, height / maxHeight)

        val resizeBitmap =
            Bitmap.createScaledBitmap(bitmap, width / scaleFactor, height / scaleFactor, false)

        val photoFile = createUniqueFile(outputDirectory, FILENAME_CROP, PHOTO_EXTENSION)

        ifAtLeastResumed {
            val cropped = cropImage(
                resizeBitmap,
                Size(binding.pvCamera.width, binding.pvCamera.height),
                viewFinderRect
            )

            val newUri = storeImage(cropped, photoFile)

            ifAtLeastResumed {
                binding.run {
                    backupUri = newUri.toString()
                    startDetect()
                }
            }
        }
    }

    private fun startDetect() {
        val intent = Intent(this, FruitIdentifierActivity::class.java)
        intent.putExtra(FruitIdentifierActivity.IMAGE_URI, backupUri)
        startDetectLauncher.launch(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_4_VALUE) <= abs(previewRatio - RATIO_16_16_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun startCropImage(uri: Uri?) {
        val cropImageContractOption = CropImageContractOptions(
            uri = uri,
            cropImageOptions = CropImageOptions()
        ).apply {
            aspectRatio(16, 16)
        }

        cropImage.launch(cropImageContractOption)
    }

    private inline fun ifAtLeastResumed(action: () -> Unit) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            action()
        }
    }

    companion object {
        private const val FILENAME = "FRUIT_PICTURES"
        private const val FILENAME_CROP = "FRUIT_PICTURES_CROP"
        private const val PHOTO_EXTENSION = ".jpg"
        private const val RATIO_4_4_VALUE = 4.0 / 4.0
        private const val RATIO_16_16_VALUE = 16.0 / 16.0

        private fun createUniqueFile(directory: String, prefix: String, suffix: String): File {
            var index = 0
            var file: File
            do {
                val filename = "$prefix${if (index == 0) "" else "_$index"}$suffix"
                file = File(directory, filename)
                index++
            } while (file.exists())
            return file
        }
    }
}
