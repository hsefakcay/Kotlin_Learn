package com.hsefakcay.fragmentvenavigationornekleri

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hsefakcay.fragmentvenavigationornekleri.databinding.FragmentBirinciBinding


class BirinciFragment : Fragment() {

    private var _binding: FragmentBirinciBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBirinciBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

//viewlar oluşturulduğunda
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.editText.setText("")
        binding.button.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun sonraki(view: View){

    }
}