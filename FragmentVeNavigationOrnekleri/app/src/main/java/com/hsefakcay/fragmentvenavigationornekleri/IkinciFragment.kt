package com.hsefakcay.fragmentvenavigationornekleri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hsefakcay.fragmentvenavigationornekleri.databinding.FragmentBirinciBinding
import com.hsefakcay.fragmentvenavigationornekleri.databinding.FragmentIkinciBinding


class IkinciFragment : Fragment() {
    private var _binding: FragmentIkinciBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIkinciBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            //null değilse
            val isim = IkinciFragmentArgs.fromBundle(it).kullaniciAdi
            binding.textView.text = isim
        }
    }
}