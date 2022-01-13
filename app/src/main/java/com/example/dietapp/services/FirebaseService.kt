package com.example.dietapp.services

import android.util.Log
import com.example.dietapp.database.models.User
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

class FirebaseService {

    private val cloud = FirebaseFirestore.getInstance()

    // Create new user
    fun createUser(user: User) {
        cloud.collection(PATH_USERS)
            .document(user.uid)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    // Create new user with Google
    fun createUserWithGoogle(user: User) {
        cloud.collection(PATH_USERS).whereEqualTo(FIELD_UID, user.uid).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    if (it.result!!.documents.isEmpty())
                        createUser(user)
                }
            }
    }

    fun updateUser(user: User) {
        cloud.collection(PATH_USERS)
            .document(user.uid)
            .set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }

    fun getUserData(uid: String): User {
        return Tasks.await(
            cloud.collection(PATH_USERS)
                .document(uid)
                .get()
        ).toObject<User>() ?: User(uid)
    }

    companion object {
        private const val TAG = "FIREBASE_SERVICE"
        private const val PATH_USERS = "users"
        private const val FIELD_UID = "uid"
    }
}