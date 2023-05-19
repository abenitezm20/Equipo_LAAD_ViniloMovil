package com.laad.viniloapp.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.laad.viniloapp.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CollectorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateAlbumFragment : Fragment() {

    private val calendar = Calendar.getInstance()
    private lateinit var releaseDate: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_album, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CollectorFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = CollectorFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Controla para que cuando se le de atras retorne al mainActivity
        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
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
    }

    private fun setRecordLabelSpinner(view: View) {
        var spinner: Spinner = view.findViewById(R.id.recordLabelSpinner)
        val recordLabels = resources.getStringArray(R.array.record_labels)
        val spinnerAdapater =
            ArrayAdapter(requireContext(), R.layout.album_spinner_list, recordLabels)
        spinnerAdapater.setDropDownViewResource(R.layout.album_spinner_list)
        spinner.adapter = spinnerAdapater
    }

    private fun setGenreSpinner(view: View) {
        var spinner: Spinner = view.findViewById(R.id.musicGenreSpinner)
        val musicGenres = resources.getStringArray(R.array.music_genres)
        val spinnerAdapater =
            ArrayAdapter(requireContext(), R.layout.album_spinner_list, musicGenres)
        spinnerAdapater.setDropDownViewResource(R.layout.album_spinner_list)
        spinner.adapter = spinnerAdapater
    }

    private fun showDatePickerDialog() {
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            R.style.CustomDatePickerDialog,
            { _, year, month, dayOfMonth ->
                // Actualizar la fecha seleccionada en el TextView
                calendar.set(year, month, dayOfMonth)
                val selectedDate =
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.time)
                releaseDate.text = selectedDate
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}