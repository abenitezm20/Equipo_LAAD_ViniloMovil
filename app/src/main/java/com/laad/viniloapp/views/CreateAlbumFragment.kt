package com.laad.viniloapp.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil.isValidUrl
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.laad.viniloapp.R
import com.laad.viniloapp.utilities.ALBUM_CREATED
import com.laad.viniloapp.utilities.CREATING_ALBUM
import com.laad.viniloapp.utilities.Utils.Companion.showToast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.Objects


/**
 * A simple [Fragment] subclass.
 * Use the [CollectorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateAlbumFragment : Fragment() {

    private val calendar = Calendar.getInstance()
    private lateinit var releaseDate: TextView
    private val args: CreateAlbumFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_album, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Controla para que cuando se le de atras retorne al mainActivity
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_nav_createalbum_to_mainActivity);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        releaseDate = view.findViewById(R.id.releaseDate)
        releaseDate.setOnClickListener {
            showDatePickerDialog()
        }
        setGenreSpinner(view)
        setRecordLabelSpinner(view)
        setCreateButton(view)
        setEventNetworkError()
    }

    private fun setEventNetworkError() {
        args.albumViewModel.isCreateAlbumError.observe(
            viewLifecycleOwner,
            Observer<Int> { codeError ->
                Log.d("CreateAlbumFragment", "Llego codigo error $codeError")
                when (codeError) {
                    CREATING_ALBUM -> Log.d("CreateAlbumFragment", "En proceso creacion album")
                    ALBUM_CREATED -> findNavController().navigate(R.id.nav_albums)
                    else -> context?.let { showToast(it, "Network Error") }
                }
            })
    }

    private fun setCreateButton(view: View) {
        val createAlbumButton: Button = view.findViewById(R.id.create_album_button)
        createAlbumButton.setOnClickListener(View.OnClickListener {
            Log.d("CreateAlbumFragment", "Creando album")
            processNewAlbum(view)
        })
    }

    private fun processNewAlbum(view: View) {
        val name: EditText = view.findViewById(R.id.createAlbumName)
        if (Objects.isNull(name) || name.text.toString().isEmpty()) {
            context?.let { showToast(it, getString(R.string.invalid_name)) }
            return
        }

        val imageUrl: EditText = view.findViewById(R.id.createAlbumImage)
        if (Objects.isNull(imageUrl) || imageUrl.text.toString()
                .isEmpty() || !isValidUrl(imageUrl.text.toString())
        ) {
            context?.let { showToast(it, getString(R.string.invalid_image_url)) }
            return
        }

        val releaseDate: TextView = view.findViewById(R.id.releaseDate)
        if (Objects.isNull(releaseDate) || releaseDate.text.isEmpty()) {
            context?.let { showToast(it, getString(R.string.invalid_release_date)) }
            return
        }

        val description: EditText = view.findViewById(R.id.createAlbumDesc)
        if (Objects.isNull(description) || description.text.isEmpty()) {
            context?.let { showToast(it, getString(R.string.invalid_description)) }
            return
        }

        val genreSpinner: Spinner = view.findViewById(R.id.musicGenreSpinner)
        if (Objects.isNull(genreSpinner) || Objects.isNull(genreSpinner.selectedItem) || genreSpinner.selectedItem.toString()
                .isEmpty()
        ) {
            context?.let { showToast(it, getString(R.string.invalid_genre)) }
            return
        }

        val recordLabelSpinner: Spinner = view.findViewById(R.id.recordLabelSpinner)
        if (Objects.isNull(recordLabelSpinner) || Objects.isNull(recordLabelSpinner.selectedItem) || recordLabelSpinner.selectedItem.toString()
                .isEmpty()
        ) {
            context?.let { showToast(it, getString(R.string.invalid_record_label)) }
            return
        }

        Log.d("CreateAlbumFragment", "Formulario OK")
        args.albumViewModel.createAlbum(
            name = name.text.toString(),
            imageUrl = imageUrl.text.toString(),
            releaseDate = releaseDate.text.toString(),
            description = description.text.toString(),
            genre = genreSpinner.selectedItem.toString(),
            recordLabel = recordLabelSpinner.selectedItem.toString()
        )
//        findNavController().navigate(R.id.nav_albums)
    }

    private fun setRecordLabelSpinner(view: View) {
        var spinner: Spinner = view.findViewById(R.id.recordLabelSpinner)
        val recordLabels = resources.getStringArray(R.array.record_labels)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.album_spinner_list, recordLabels)
        spinnerAdapter.setDropDownViewResource(R.layout.album_spinner_list)
        spinner.adapter = spinnerAdapter
    }

    private fun setGenreSpinner(view: View) {
        var spinner: Spinner = view.findViewById(R.id.musicGenreSpinner)
        val musicGenres = resources.getStringArray(R.array.music_genres)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.album_spinner_list, musicGenres)
        spinnerAdapter.setDropDownViewResource(R.layout.album_spinner_list)
        spinner.adapter = spinnerAdapter
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                // Actualizar la fecha seleccionada en el TextView
                calendar.set(year, month, dayOfMonth)
                val selectedDate =
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(calendar.time)
                releaseDate.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}