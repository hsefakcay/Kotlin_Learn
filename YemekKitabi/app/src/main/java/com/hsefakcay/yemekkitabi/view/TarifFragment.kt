package com.hsefakcay.yemekkitabi.view
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.hsefakcay.yemekkitabi.databinding.FragmentTarifBinding
import com.hsefakcay.yemekkitabi.model.Tarif
import com.hsefakcay.yemekkitabi.roomdb.TarifDAO
import com.hsefakcay.yemekkitabi.roomdb.TarifDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream


class TarifFragment : Fragment() {

    private var _binding: FragmentTarifBinding? = null
    private val binding get() = _binding!!
    private lateinit var permissionLauncher: ActivityResultLauncher<String> //izin istemek içim
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent> //galeriye gitmek için
    private var secilenGorsel : Uri? = null
    private var secilenBitmap : Bitmap? = null
    private var secilenTarif : Tarif? = null
    private val mDisposable = CompositeDisposable()

    private lateinit var db: TarifDatabase
    private lateinit var tarifDao : TarifDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLancher()

        //initialize
        db = Room.databaseBuilder(requireContext(),TarifDatabase::class.java,"Tarifler").build()
        tarifDao = db.TarifDAO()
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
                secilenTarif = null
                binding.buttonSil.isEnabled = false
                binding.buttonKaydet.isEnabled = true
                binding.editTextIsim.setText("")
                binding.editTextIcindekiler.setText("")
            } else {
                //önceden eklenmiş tarif ise
                binding.buttonSil.isEnabled = true
                binding.buttonKaydet.isEnabled = false
                val id = TarifFragmentArgs.fromBundle(it).id

                mDisposable.add(
                    tarifDao.findById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::handleResponse)
                )
            }
        }

    }
    private fun handleResponse(tarif : Tarif){
        val bitmap = BitmapFactory.decodeByteArray(tarif.gorsel,0,tarif.gorsel.size)
        binding.imageView.setImageBitmap(bitmap)
        binding.editTextIsim.setText(tarif.isim)
        binding.editTextIcindekiler.setText(tarif.malzeme)
        secilenTarif = tarif
    }

    fun kaydet(view: View){
        val isim = binding.editTextIsim.text.toString()
        val malzeme = binding.editTextIcindekiler.text.toString()

        if (secilenBitmap != null){
            val kucukBitmap = kucukBitmapOlustur(secilenBitmap!!,300 )
            val outputStream = ByteArrayOutputStream()
            kucukBitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream)
            val byteDizisi = outputStream.toByteArray()

            val tarif = Tarif(isim,malzeme,byteDizisi)

            //RxJava
            mDisposable.add(
                tarifDao.insert(tarif)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseForInsert)
            )
        }
    }

    private fun handleResponseForInsert(){
        //bir önceki fragment'a dön
        val action = TarifFragmentDirections.actionTarifFragmentToListeFragment()
        Navigation.findNavController(requireView()).navigate(action)
    }

    fun sil(view: View){

        if (secilenTarif != null){
            mDisposable.add(
                tarifDao.delete(tarif = secilenTarif!!)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponseForInsert)

            )
        }
    }

    fun gorselSec(view: View){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED ){
                //izil verilmemiş izin gerekiyor
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_MEDIA_IMAGES)){
                    //snackbar gösterilmeli
                    Snackbar.make(view, "Galeriye ulaşıp görsel seçmemiz lazım!", Snackbar.LENGTH_INDEFINITE).setAction(
                        "İzin ver",
                        View.OnClickListener {
                            //izin isteyeceğiz
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    ).show() //snackbar sonu
                } else {
                    // izin isteyeceğiz
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }
            else{
                //izin verilmiş, galeriye gidilebilir
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }
            //version < TIRAMISU
        }else{
            if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
                //izil verilmemiş izin gerekiyor
                if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //snackbar gösterilmeli
                    Snackbar.make(view, "Galeriye ulaşıp görsel seçmemiz lazım!", Snackbar.LENGTH_INDEFINITE).setAction(
                        "İzin ver",
                        View.OnClickListener {
                            //izin isteyeceğiz
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    ).show() //snackbar sonu
                } else {
                    // izin isteyeceğiz
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
            else{
                //izin verilmiş, galeriye gidilebilir
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)

            }
        }


    }

    private fun registerLancher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK){
                val intentFromResult = result.data
                if (intentFromResult != null){
                    secilenGorsel = intentFromResult.data

                    try {
                        if (Build.VERSION.SDK_INT >= 28){
                            val source = ImageDecoder.createSource(requireActivity().contentResolver, secilenGorsel!!)
                            secilenBitmap = ImageDecoder.decodeBitmap(source)
                            binding.imageView.setImageBitmap(secilenBitmap)
                        }else{
                            //eski yöntem
                            secilenBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,secilenGorsel)
                            binding.imageView.setImageBitmap(secilenBitmap)
                        }
                    }catch (e: Exception){
                        println(e.localizedMessage)
                    }


                }
            }
        }

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){ result->
            if(result){
                //izin verildi galeriye gidebiliriz
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }else{
                //izin verilmedi
                Toast.makeText(requireContext(),"izin verilmedi !", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun kucukBitmapOlustur(kullanicininSectigiBitmap : Bitmap, maximumBoyut : Int) : Bitmap{
        var width = kullanicininSectigiBitmap.width
        var height = kullanicininSectigiBitmap.height

        val bitmapOrani : Double = width.toDouble()/ height.toDouble()

        if(bitmapOrani > 1){
            //gorsel yatay
            width = maximumBoyut
            val kisaltilmisYukseklik = width / bitmapOrani
            height = kisaltilmisYukseklik.toInt()
        }else {
            //gorsel dikey
            height = maximumBoyut
            val kisaltilmisGenislik = height * bitmapOrani
            width = kisaltilmisGenislik.toInt()
        }
        return Bitmap.createScaledBitmap(kullanicininSectigiBitmap, height, width, true)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        mDisposable.clear()
    }
}