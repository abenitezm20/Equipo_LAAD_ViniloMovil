package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.addCallback
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.laad.viniloapp.R
import com.laad.viniloapp.utilities.ALBUM_CREATED
import com.laad.viniloapp.utilities.COMMENT_CREATED
import com.laad.viniloapp.utilities.CREATING_ALBUM
import com.laad.viniloapp.utilities.CREATING_COMMENT
import com.laad.viniloapp.utilities.Utils
import com.laad.viniloapp.viewmodels.AlbumViewModel
import com.laad.viniloapp.viewmodels.CommentViewModel
import androidx.lifecycle.Observer
import java.util.Objects


/**
 * A simple [Fragment] subclass.
 * Use the [CommentAlbumFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentAlbumFragment : Fragment() {

    private lateinit var commentViewModel: CommentViewModel
    private var albumIdparam: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Controla para que cuando se le de atras retorne al mainActivity
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.action_nav_commentalbum_to_mainActivity);
        }

        //recibe el parametro del otro fragment
        setFragmentResultListener("requestKey") { key, bundle ->
            albumIdparam = Integer.parseInt(bundle.getString("albumId"))
            Log.d("CommentAlbumFragment", "Recibiendo parametro albumId:"+albumIdparam)
            val activity = requireNotNull(this.activity)
            commentViewModel = CommentViewModel.getInstance(activity.application)
            commentViewModel.resetCreateCommentFlag()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.comment_album_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireNotNull(this.activity)
        commentViewModel = CommentViewModel.getInstance(activity.application)

        setCalificaSpinner(view)
        setCreateButton(view)
        setEventNetworkError()
    }

    private fun setEventNetworkError() {
        commentViewModel.isCreateCommentError.observe(viewLifecycleOwner, Observer<Int> { codeError ->
            Log.d("CommentAlbumFragment", "Llego codigo error $codeError")
            when (codeError) {
                CREATING_COMMENT -> Log.d("CommentAlbumFragment", "En proceso creacion comentario")
                COMMENT_CREATED -> {
                    findNavController().navigate(R.id.nav_albums)
                }
                else -> context?.let { Utils.showToast(it, "Network Error") }
            }
        })
    }

    private fun setCreateButton(view: View) {
        val createCommentButton: Button = view.findViewById(R.id.save_comment_button)
        createCommentButton.setOnClickListener(View.OnClickListener {
            processNewComment(view)
        })
    }

    private fun processNewComment(view: View) {
        val descripcion: EditText = view.findViewById(R.id.descCommentAlbum)
        if (Objects.isNull(descripcion) || descripcion.text.toString().isEmpty()) {
            context?.let { Utils.showToast(it, getString(R.string.invalid_comment_desc)) }
            return
        }

        val calificaSpinner: Spinner = view.findViewById(R.id.calificaSpinner)
        if (Objects.isNull(calificaSpinner) || Objects.isNull(calificaSpinner.selectedItem) || calificaSpinner.selectedItem.toString()
                .isEmpty()
        ) {
            context?.let { Utils.showToast(it, "Debe ingresar una calificación valida") }
            return
        }

        Log.d("CommentAlbumFragment", "Formulario OK")
        commentViewModel.createComment(
            description = descripcion.text.toString(),
            rating = Integer.parseInt(calificaSpinner.selectedItem.toString()),
            albumId = albumIdparam,
            collectorId = 100
        )
    }

    private fun setCalificaSpinner(view: View) {
        var spinner: Spinner = view.findViewById(R.id.calificaSpinner)
        val calificaciones = resources.getStringArray(R.array.calificaciones)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.album_spinner_list, calificaciones)
        spinnerAdapter.setDropDownViewResource(R.layout.album_spinner_list)
        spinner.adapter = spinnerAdapter
    }


}