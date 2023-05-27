package com.laad.viniloapp.data

import android.app.Application
import android.util.Log
import com.laad.viniloapp.data.database.CachedCommentDao
import com.laad.viniloapp.data.network.ViniloServiceAdapter
import com.laad.viniloapp.models.Collector
import com.laad.viniloapp.models.Comment
import com.laad.viniloapp.models.CommentRequest

class CommentRepository (val application: Application, private val cachedCommentDao: CachedCommentDao){

    suspend fun createComment(comment: Comment): Comment {
        Log.d("CommentRepository", "Creando Comentario en repositorio")
        val collectorTemp = Collector(1, "Colecctor Test","57300856236","col@test.com")
        val req = CommentRequest(
            description = comment.description,
            rating = comment.rating,
            collector = collectorTemp
        )
        val newComment = ViniloServiceAdapter.getInstance(application).createComment(comment.albumId, req)
        cachedCommentDao.insert(newComment)
        return newComment
    }
}