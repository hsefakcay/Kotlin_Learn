package com.hsefakcay.yemekkitabi
import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.hsefakcay.yemekkitabi.databinding.FragmentTarifBinding


class TarifFragment : Fragment() {

    private var _binding: FragmentTarifBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTarifBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imageView.setOnClickListener { gorselSec(it) }
        binding.buttonKaydet.setOnClickListener { kaydet(it) }
        binding.buttonSil.setOnClickListener { sil(it) }

        //argumanları - bu sayfaya gelen parametreleri alma
        arguments?.let {
            val bilgi =TarifFragmentArgs.fromBundle(it).bilgi

            if(bilgi == "yeni"){
                //Yeni tarif eklenecek
                binding.buttonSil.isEnabled = false
                binding.buttonKaydet.isEnabled = true
                binding.editTextIsim.setText("")
                binding.editTextIcindekiler.setText("")
            } else {
                //önceden eklenmiş tarif ise
                binding.buttonSil.isEnabled = true
                binding.buttonKaydet.isEnabled = false
            }
        }

    }

    fun kaydet(view: View){

    }

    fun sil(view: View){

    }

    fun gorselSec(view: View){

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            //izil verilmemiş izin gerekiyor
            if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                //snackbar gösterilmeli
                Snackbar.make(view, "Galeriye ulaşıp görsel seçmemiz lazım!", Snackbar.LENGTH_INDEFINITE).setAction(
                    "İzin ver",
                    View.OnClickListener {
                        //izin isteyeceğiz
                    }
                ).show() //snackbar sonu
            } else {
                // izin isteyeceğiz
            }


        }
        else{
            //izin verilmiş, galeriye gidilebilir
            
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}