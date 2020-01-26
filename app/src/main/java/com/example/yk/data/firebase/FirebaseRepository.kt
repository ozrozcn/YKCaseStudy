package com.example.yk.data.firebase

import android.net.Uri
import com.example.yk.data.model.User
import com.example.yk.data.repository.UserRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseRepository : UserRepository {

    val auth = FirebaseAuth.getInstance()
    private val firestoreRef = FirebaseFirestore.getInstance()
    private val storageRf = FirebaseStorage.getInstance().reference.child(USER_PATH_KEY)


    companion object {
        const val USER_PATH_KEY = "users"
        const val PHOTO_URL = "photoUrl"
    }

    override suspend fun registerNewUser(user: User, pass: String) {
        return suspendCoroutine { continuation ->

            auth.createUserWithEmailAndPassword(user.mail, pass)
                .continueWith { task ->
                    if (task.isSuccessful) {
                        saveUserDetail(user)
                    } else {
                        continuation.resumeWithException(RuntimeException(task.exception?.localizedMessage))
                    }
                }.addOnCompleteListener {
                    continuation.resume(Unit)
                }
        }
    }

    private fun saveUserDetail(user: User): Task<Void> {
        val collection = firestoreRef.collection(USER_PATH_KEY)
        val saveTask = collection.document(user.mail).set(user)

        saveTask
            .continueWith { task ->
                if (task.isSuccessful && user.photoUri != null) {
                    saveUserPhoto(user)
                }
            }

        return saveTask
    }

    override suspend fun signIn(email: String, password: String) {
        return suspendCoroutine { continuation ->
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(RuntimeException(it.exception?.localizedMessage))
                }
            }
        }
    }

    @ExperimentalCoroutinesApi
    override fun getUserDetail(email: String): Flow<User> {
        return channelFlow {
            val userRef = firestoreRef.collection(USER_PATH_KEY).document(email)
                .addSnapshotListener { doc, e ->
                    if (doc != null) {
                        val user = doc.toObject(User::class.java)
                        user?.let {
                            channel.offer(it)
                        }
                    } else {
                        channel.close(e)
                    }
                }

            awaitClose { userRef.remove() }
        }
    }

    private fun saveUserPhoto(user: User) {
        uploadPhoto(user).continueWithTask { urlTask ->
            user.photoUrl = urlTask.result.toString()
            firestoreRef.collection(USER_PATH_KEY)
                .document(user.mail)
                .update(PHOTO_URL, user.photoUrl)
        }.addOnFailureListener {
            throw RuntimeException("User Image Upload Fail!")
        }
    }

    private fun uploadPhoto(user: User): Task<Uri> {
        val storageRef = storageRf.child(user.mail)
        return storageRef.putFile(Uri.parse(user.photoUri))
            .continueWithTask { uploadTask ->
                uploadTask.result?.storage?.downloadUrl
            }
    }

    override suspend fun deleteUser(email: String) {
        return suspendCoroutine { continuation ->
            val user = auth.currentUser
            user?.delete()?.addOnCompleteListener {
                if (it.isSuccessful) {
                    continuation.resume(Unit)
                } else {
                    continuation.resumeWithException(RuntimeException(it.exception?.localizedMessage))
                }
            }?.continueWith { deleteUserImage(email) }
        }
    }

    private fun deleteUserImage(email: String) {
        storageRf.child(email).delete()
    }
}