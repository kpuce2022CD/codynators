package com.example.features.main.data.repository

import android.net.Uri
import com.example.features.main.data.dto.Song
import com.example.features.main.data.dto.Song.Companion.CUSTOM
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class FirebaseRepositoryImpl @Inject constructor(
    private val fbAuth: FirebaseAuth,
    private val fbStorage: FirebaseStorage,
    private val fbRDB: FirebaseDatabase
) : FirebaseRepository {

    override suspend fun uploadOnFirebase(title: String, file: String, uri: Uri) {
        suspendCancellableCoroutine<Unit> { continuation ->
            fbRDB.getReference(fbAuth.uid.toString()).setValue(title)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        fbStorage.reference.child(file).putFile(uri)
                            .addOnSuccessListener {
                                continuation.resume(Unit, {})
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
                    } else {
                        continuation.resumeWithException(IOException("rdb upload error"))
                    }
                }
        }
    }

    override suspend fun deleteOnFirebase(title: String, file: String) {
        suspendCancellableCoroutine<Unit> { continuation ->
            fbRDB.getReference(fbAuth.uid.toString()).child(title).removeValue()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        fbStorage.reference.child(file).delete()
                            .addOnSuccessListener {
                                continuation.resume(Unit, {})
                            }
                            .addOnFailureListener {
                                continuation.resumeWithException(it)
                            }
                    } else {
                        continuation.resumeWithException(IOException("rdb delete error"))
                    }
                }
        }
    }

    override fun getFromRDB() = callbackFlow<List<Song>> {
        fbRDB.getReference(fbAuth.uid.toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    trySend(snapshot.children.map { Song(CUSTOM, it.key.toString()) })
                }

                override fun onCancelled(error: DatabaseError) {
                    cancel()
                }
            })
        awaitClose()
    }.flowOn(Dispatchers.IO)

    override suspend fun signOut() = fbAuth.signOut()
}