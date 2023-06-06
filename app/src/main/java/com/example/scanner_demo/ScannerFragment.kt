package com.example.scanner_demo

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.scanner_demo.databinding.FragmentScannerBinding

class ScannerFragment : Fragment() {
    private val viewModel by viewModels<ScannerViewModel>()
    private var _binding: FragmentScannerBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScannerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initScanner()
        initBinding()

    }
    private fun initBinding() {
        binding.apply {
            scanButton.setOnClickListener {
                viewModel.startScan()
                croppedImageView.visibility = View.GONE
            }
            flipButton.setOnClickListener { viewModel.flipCard() }
        }
    }
    private fun initScanner(){
        viewModel.setScanner(requireActivity())
        viewModel.cardImage.observe(viewLifecycleOwner) {
            binding.croppedImageView.setImageBitmap(it)
            binding.croppedImageView.visibility = View.VISIBLE
        }
        viewModel.errorMessage.observe(viewLifecycleOwner) {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
        viewModel.isFlippable.observe(viewLifecycleOwner) {
            binding.flipButton.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}