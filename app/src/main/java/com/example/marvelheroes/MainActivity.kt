package com.example.marvelheroes

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.marvelheroes.ui.main.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST_CODE = 200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
        verifyStoragePermissions(this)
    }

    private fun verifyStoragePermissions(activity: Activity) {

        val perimssaoGarantida: Int = PackageManager.PERMISSION_GRANTED
        val permissaoAcessarCamera: Int =
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)

        when {
            perimssaoGarantida != permissaoAcessarCamera -> {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        activity,
                        Manifest.permission.CAMERA
                    )
                ) {
                    Toast.makeText(
                        this,
                        getString(R.string.alert_camera_permission),
                        Toast.LENGTH_LONG
                    ).show()
                    requestPermissions(activity)
                } else {
                    requestPermissions(activity)
                }
            }
            else -> {

            }
        }
    }

    private fun requestPermissions(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.CAMERA),
            PERMISSION_REQUEST_CODE
        )
    }
}