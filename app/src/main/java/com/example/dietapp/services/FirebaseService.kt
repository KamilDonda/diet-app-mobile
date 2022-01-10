package com.example.dietapp.services

import android.util.Log
import com.example.dietapp.database.models.User
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseService {

    private val cloud = FirebaseFirestore.getInstance()

    // Create new user
    fun createUser(user: User) {
        cloud.collection(PATH_USER)
            .document(user.uid)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    // Create new user with Google
    fun createUserWithGoogle(user: User) {
        cloud.collection(PATH_USER).whereEqualTo(FIELD_UID, user.uid).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result!!.documents.isEmpty())
                        createUser(user)
                }
            }
    }

    fun updateUser(user: User) {
        cloud.collection(PATH_USER)
            .document(user.uid)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    companion object {
        private const val TAG = "FIREBASE_SERVICE"
        private const val PATH_USER = "users"
        private const val FIELD_UID = "uid"
    }
}