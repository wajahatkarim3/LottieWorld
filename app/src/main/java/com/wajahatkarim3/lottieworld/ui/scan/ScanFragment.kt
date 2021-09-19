package com.wajahatkarim3.lottieworld.ui.scan

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.google.zxing.Result
import com.wajahatkarim3.lottieworld.R
import com.wajahatkarim3.lottieworld.base.BaseFragment
import com.wajahatkarim3.lottieworld.data.model.AnimationModel
import com.wajahatkarim3.lottieworld.utils.showToast
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanFragment: BaseFragment(), ZXingScannerView.ResultHandler {

    private val REQUIRED_PERMISSION = Manifest.permission.CAMERA

    private lateinit var scannerView: ZXingScannerView

    // Single Permission Contract
    private val askCameraPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
        if (result) {
            // Granted
            startScanning()
        } else {
            // Denied
            activity?.showToast(getString(R.string.permissions_not_granted_str))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scannerView = ZXingScannerView(requireContext())   // Programmatically initialize the scanner view
        return scannerView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startScanning()
        }
    }

    override fun handleResult(rawResult: Result) {
        val lottieUrl = rawResult.text
        if (lottieUrl != null) {
            val bundle = bundleOf(AnimationModel.LOTTIE_URL_KEY to lottieUrl)
            findNavController().navigate(R.id.action_scanFragment_to_animationDetailsFragment, bundle)
        } else {
            scannerView.resumeCameraPreview(this)
        }

    }

    override fun onResume() {
        super.onResume()
        if (!allPermissionsGranted()) {
            askCameraPermission.launch(REQUIRED_PERMISSION)
        }
    }

    fun startScanning() {
        scannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        scannerView.startCamera();          // Start camera on resume
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera() // Stop camera on pause
    }

    private fun allPermissionsGranted() =
        context?.let { ctx ->
            ContextCompat.checkSelfPermission(ctx, REQUIRED_PERMISSION)
        } == PackageManager.PERMISSION_GRANTED
}