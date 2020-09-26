package com.example.drawit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import android.Manifest
import android.content.res.Resources
import android.graphics.Color
import android.util.Log
import android.widget.ImageView


class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var paintView: PaintView

    lateinit var colormenu:ImageView
    lateinit var eraser_menu:ImageView
    lateinit var blur_menu:ImageView
    lateinit var undo_menu:ImageView
    lateinit var download_menu:ImageView
    lateinit var brush_menu:ImageView
    lateinit var clear_menu:ImageView

    companion object {
        var selectedColor = "#000000"
    }

    private val TAG = "MainActivity"
    private val STORAGE_PERM = 333

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        colormenu = findViewById(R.id.color_menu)
        brush_menu = findViewById(R.id.brush_menu)
        download_menu = findViewById(R.id.download_menu)
        undo_menu = findViewById(R.id.undo_menu)
        blur_menu = findViewById(R.id.blur_menu)
        eraser_menu = findViewById(R.id.eraser_menu)
        clear_menu = findViewById(R.id.clear_menu)


        paintView = findViewById(R.id.paintView)
        val metrics = Resources.getSystem().displayMetrics
        paintView.init(metrics)
        paintView.normal()

        colormenu.setOnClickListener {
            MaterialColorPickerDialog
                .Builder(this)
                .setColorRes(resources.getStringArray(R.array.themeColorHex).map {
                    Color.parseColor(
                        it
                    )
                }.toList())
                .setDefaultColor(selectedColor)
                .setColorListener(object : ColorListener {
                    override fun onColorSelected(color: Int, colorHex: String) {
                        Log.d("TAG", "color -> : $color")
                        Log.d("TAG", "color -> : $colorHex")
                        selectedColor = colorHex
                        paintView.setColor(colorHex)
                    }
                })
                .showBottomSheet(supportFragmentManager)
        }

        brush_menu.setOnClickListener {
            val brushBottomSheet = BrushBottomSheet(paintView)
            brushBottomSheet.show(supportFragmentManager, brushBottomSheet.tag)
        }

        eraser_menu.setOnClickListener {
            val eraserBottomSheet = EraserBottomSheet(paintView)
            eraserBottomSheet.show(supportFragmentManager, eraserBottomSheet.tag)
        }

        blur_menu.setOnClickListener {
            val blurBottomSheet = BlurBottomSheet(paintView)
            blurBottomSheet.show(supportFragmentManager, blurBottomSheet.tag)
        }

        clear_menu.setOnClickListener {
            MaterialAlertDialogBuilder(this)
                .setTitle("New Sheet")
                .setMessage("Start new drawing?")
                .setPositiveButton("Yes") { _, _ ->
                    paintView.clear()
                }
                .setNegativeButton("Cancel") { dialog, _ ->
                    dialog.cancel()
                }.show()
        }

        undo_menu.setOnClickListener {
            paintView.undo()
        }

        download_menu.setOnClickListener {
            storageTask()
        }
    }

    private fun storageTask() {
        if (hasStoragePermission()) {
            // Have permission, do the thing!
            paintView.download(this)
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.external_storage),
                STORAGE_PERM,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size)

        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size)
        paintView.download(this)
    }


    private fun hasStoragePermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }
}