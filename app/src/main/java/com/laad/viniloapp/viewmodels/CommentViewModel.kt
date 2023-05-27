package com.laad.viniloapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.laad.viniloapp.data.CommentRepository
import com.laad.viniloapp.data.database.VinylRoomDatabase
import com.laad.viniloapp.models.Comment
import com.laad.viniloapp.utilities.COMMENT_CREATED
import com.laad.viniloapp.utilities.COMMENT_ERROR
import com.laad.viniloapp.utilities.CREATING_COMMENT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class CommentViewModel(application: Application) : AndroidViewModel(application), Serializable {
    companion object {
        @Volatile
        private var instance: CommentViewModel? = null

        fun getInstance(application: Application): CommentViewModel {
            Log.d("CommentViewModel", "Obteniendo instancia")
            return instance ?: synchronized(this) {
                instance ?: CommentViewModel(application).also { instance = it }
            }
        }
    }

    private val commentRepository = CommentRepository(
        application, VinylRoomDatabase.getDatabase(application.applicationContext).commentDao()
    )

    private var _isCreateCommentError = MutableLiveData<Int>(CREATING_COMMENT)
    val isCreateCommentError: LiveData<Int>
        get() = _isCreateCommentError

    fun createComment(
        description: String,
        rating: Int,
        albumId: Int,
        collectorId: Int
    ) {
        try {
            viewModelScope.launch(Dispatchers.Default) {
                withContext(Dispatchers.IO) {
                    commentRepository.createComment(
                        Comment(
                            id = 0,
                            description = description,
                            rating = rating,
                            albumId = albumId,
                            collectorId = collectorId
                        )
                    )
                    _isCreateCommentError.postValue(COMMENT_CREATED)
                }
            }
        } catch (e: Exception) {
            _isCreateCommentError.value = COMMENT_ERROR
        }
    }
}