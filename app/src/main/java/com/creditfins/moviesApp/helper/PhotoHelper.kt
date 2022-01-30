package com.creditfins.moviesApp.helper

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

import java.io.File
import java.io.IOException

/**
 * Created by µðšţãƒâ ™ on 1/23/2020.
 * ->
 */
class PhotoHelper : ActivityCompat.OnRequestPermissionsResultCallback {
    private var mActivity: AppCompatActivity? = null
    private var mFragment: Fragment? = null
    private var mCurrentPhotoPath: String? = null
    private var mPhotoReadyCallback: PhotoReadyCallback? = null

    constructor(activity: AppCompatActivity) {
        mActivity = activity
    }

    constructor(fragment: Fragment) {
        mFragment = fragment
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                if (null != mPhotoReadyCallback) {
                    mPhotoReadyCallback!!.onPhotoReady(mCurrentPhotoPath)
                }
            } else if (requestCode == GALLERY_REQUEST) {
                if (null != mPhotoReadyCallback) {
                    getImagePathFormGallery(data!!)
                }
            }
        }
    }

    fun getmCurrentPhotoPath(): String? {
        return mCurrentPhotoPath
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicIntent()
            }
        } else if (requestCode == REQUEST_GALLERY_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePicIntentForGallery()
            }
        }
    }

    private fun takePicIntentForGallery() {
        val i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        mActivity?.startActivityForResult(i, GALLERY_REQUEST)
        mFragment?.startActivityForResult(i, GALLERY_REQUEST)
    }

    private fun takePicIntent() {
        val takePicIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePicIntent.resolveActivity(
                (mActivity ?: mFragment?.context!!).packageManager
            ) != null
        ) {
            val photoFile: File
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                return
            }

            if (photoFile != null) {
                mCurrentPhotoPath = photoFile.absolutePath
                val photoUri: Uri = FileProvider.getUriForFile(
                    mActivity ?: mFragment?.context!!,
                    "com.yisweb.re3aty.fileprovider",
                    photoFile
                )
                takePicIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                mActivity?.startActivityForResult(takePicIntent, CAMERA_REQUEST)
                mFragment?.startActivityForResult(takePicIntent, CAMERA_REQUEST)
            }
        }
    }

    fun base64Image(imagePath: String, base: (photoBase: String) -> Unit) {
        if (Build.VERSION.SDK_INT >= 29) {
            val pfd =
                (mActivity ?: mFragment?.context!!).contentResolver.openFileDescriptor(
                    imagePath.toUri(),
                    "r"
                )
            if (pfd != null) {
                convertImage(
                    BitmapFactory.decodeFileDescriptor(pfd.fileDescriptor),
                    base
                )
            }
        } else {
            convertImage(BitmapFactory.decodeFile(imagePath), base)
        }
    }

    fun base64ImageFromUrl(imageUrl: String, base: (photoBase: String) -> Unit) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val bm = Glide.with(mActivity ?: mFragment?.context!!)
                    .asBitmap()
                    .load(imageUrl)
                    .submit(200, 200)
                    .get()
                val byteArrayOutputStream = ByteArrayOutputStream()
                bm.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                val byteArray = byteArrayOutputStream.toByteArray()
                val encoded = "" + Base64.encodeToString(byteArray, Base64.DEFAULT)
                base(encoded)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun convertImage(bitmap: Bitmap?, base: (photoBase: String) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            val byteArrayOS = ByteArrayOutputStream()
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOS)
            val base64 = Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT)
            base("data:image/png;base64,$base64")
        }
    }

    fun getScaledBitmap(callback: BitmapReadyCallback, width: Int, height: Int) {
        if (TextUtils.isEmpty(mCurrentPhotoPath)) {
            callback.onError()
            return
        }

        Thread(Runnable {
            val bmOptions = BitmapFactory.Options()
            val photoW = bmOptions.outWidth
            val photoH = bmOptions.outHeight
            //
            //        // Determine how much to scale down the image
            //        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);
            //        // Decode the image file into a Bitmap sized to fill the View
            //        bmOptions.inJustDecodeBounds = false;
            //        bmOptions.inSampleSize = scaleFactor;
            //        bmOptions.inPurgeable = true;

            val bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions)
            callback.onBitmapReady(bitmap)
        }).start()
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val imageFileName = "MetrashkomPic_" + System.currentTimeMillis() + "_"
        val storageDir =
            (mActivity ?: mFragment?.context!!).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        mCurrentPhotoPath = image.absolutePath
        return image
    }

    fun capturePhoto(callback: PhotoReadyCallback) {
        mPhotoReadyCallback = callback
        if (ActivityCompat.checkSelfPermission(
                mActivity ?: mFragment?.context!!,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePicIntent()
        } else {
            ActivityCompat.requestPermissions(
                mActivity ?: mFragment?.context!! as Activity,
                arrayOf(Manifest.permission.CAMERA),
                REQUEST_CAMERA_PERMISSION
            )
        }
    }

    fun openGallery(callback: PhotoReadyCallback) {
        mPhotoReadyCallback = callback
        if (ActivityCompat.checkSelfPermission(
                mActivity ?: mFragment?.context!!,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            takePicIntentForGallery()
        } else {
            ActivityCompat.requestPermissions(
                mActivity ?: mFragment?.context!! as Activity,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_GALLERY_PERMISSION
            )
        }
    }

    private fun getImagePathFormGallery(data: Intent) {
        val cursor: Cursor?
        if (Build.VERSION.SDK_INT >= 29) {
            cursor = (mActivity ?: mFragment?.context!!).contentResolver.query(
                data.data!!,
                arrayOf(MediaStore.Images.Media._ID),
                null,
                null,
                null
            )

            if (cursor != null) {
                val columnIndexID = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                cursor.moveToFirst()
                val imageUri = ContentUris.withAppendedId(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    cursor.getLong(columnIndexID)
                )
                mCurrentPhotoPath = imageUri.toString()
            }
        } else {
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            cursor = (mActivity ?: mFragment?.context!!).contentResolver.query(
                data.data!!,
                filePathColumn, null, null, null
            )
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                mCurrentPhotoPath = cursor.getString(columnIndex)
            }
        }

        if (cursor != null) {
            mPhotoReadyCallback!!.onPhotoReady(mCurrentPhotoPath)
            cursor.close()
        }
    }

    fun deleteFile(fileName: String) {
        val image =
            (mActivity ?: mFragment?.context!!).getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val filePath = image?.absolutePath + "/$fileName"
        File(filePath).delete()
    }

    interface BitmapReadyCallback {
        fun onBitmapReady(bitmap: Bitmap)

        fun onError()
    }

    interface PhotoReadyCallback {
        fun onPhotoReady(filePath: String?)
    }

    companion object {
        val CAMERA_REQUEST = View.generateViewId()
        val GALLERY_REQUEST = View.generateViewId()
        private val REQUEST_CAMERA_PERMISSION = View.generateViewId()
        private val REQUEST_GALLERY_PERMISSION = View.generateViewId()
    }
}

