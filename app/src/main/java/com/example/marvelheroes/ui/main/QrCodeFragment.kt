package com.example.marvelheroes.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.example.marvelheroes.R
import kotlin.system.exitProcess


class QrCodeFragment : Fragment() {

    private var codeScanner: CodeScanner? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_qr_code, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val scannerView = view.findViewById<CodeScannerView>(R.id.scanner_view)
        val activity = requireActivity()
        if (verifyStoragePermissions(requireContext())) {
            codeScanner = CodeScanner(activity, scannerView)
            codeScanner!!.decodeCallback = DecodeCallback {
                activity.runOnUiThread {
                    showDialog()
                }
            }
            scannerView.setOnClickListener {
                codeScanner!!.startPreview()
            }
        }
    }

    private fun showDialog() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setMessage("O QRCode foi lido com sucesso!")
            .setCancelable(false)
            .setPositiveButton(
                "Ok"
            ) { dialog, id -> dialog.cancel() }
        builder.create()?.show()
    }

    private fun showDialogError() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(context)
        builder.setMessage("O Aplicativo precisa de sua permissão para acessar a camera!")
            .setCancelable(false)
            .setPositiveButton(
                "Ok"
            ) { dialog, id ->
                activity?.finish()
                exitProcess(0)
            }
        builder.create()?.show()
    }

    private fun verifyStoragePermissions(context: Context): Boolean {

        val perimssaoGarantida: Int = PackageManager.PERMISSION_GRANTED
        val permissaoAcessarCamera: Int =
            ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)

        if (perimssaoGarantida != permissaoAcessarCamera) {
            showDialogError()
            return false
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        codeScanner?.startPreview()
    }

    override fun onPause() {
        codeScanner?.releaseResources()
        super.onPause()
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): QrCodeFragment {
            return QrCodeFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}