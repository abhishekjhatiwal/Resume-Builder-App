package com.example.resumebuilderapp.data

import android.graphics.Bitmap
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.lang.System.out

class ResumeReposetiry {
    private val firebaseDatabase: DatabaseReference = Firebase.database.reference
    private val firebaseStorage: StorageReference = Firebase.storage.reference
    private val userId: String get() = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"

    suspend fun createOrUpdate(resume: ResumeData, locationPhotoBitmap: Bitmap?): ResumeData {
        val id = resume.id.ifEmpty {
            firebaseDatabase.child("users").child(userId).push().key
                ?: throw Exception("Cannot generate id")
        }
        var photoUrl = resume.personal.photoUrl

        if (locationPhotoBitmap != null) {
            val byte = ByteArrayOutputStream().use { out->
                locationPhotoBitmap.compress(Bitmap.CompressFormat.JPEG, 92, out)
                out.toByteArray()
            }
            val photoPath = "user/$userId/$id/photo.jpeg"
            val photoStorage = firebaseStorage.child(photoPath)
            photoStorage.putBytes(byte).await()
            photoUrl = photoStorage.downloadUrl.await().toString()
        }
        val updated = resume.copy(
            id = id,
            personal = resume.personal.copy(photoUrl = photoUrl)
        )
        firebaseDatabase.child("users").child(userId).child(id).setValue(updated).await()
        return updated
    }

    suspend fun fetch(id: String): ResumeData? {
        return firebaseDatabase.child("users").child(userId).child(id).get().await()
            .getValue(ResumeData::class.java)
    }

    suspend fun delete(id: String){
        firebaseDatabase.child("users").child(userId).child(id).removeValue().await()
//        firebaseStorage.child("user/$userId/$id/photo.jpeg").delete().await()
        runCatching {
            firebaseStorage.child("user/$userId/$id/photo.jpeg").delete().await()
        }
    }
}