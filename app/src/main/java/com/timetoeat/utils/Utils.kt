package com.timetoeat.utils

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.imperiumlabs.geofirestore.GeoFirestore

class Utils {

    val db = Firebase.firestore
    val geoFirestore = GeoFirestore(db.collection("restaurants"))

}

