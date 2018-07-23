package com.camboray.myfirestoreapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.firestore.FirebaseFirestore
import java.util.HashMap

class MainActivity : AppCompatActivity() {

    var editTextTitle: EditText? = null
    var editTextDescription: EditText? = null
    var textViewData: TextView? = null

   val db = FirebaseFirestore.getInstance()
   val noteRef = db.document("Notebook/My First Note")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextTitle = findViewById(R.id.edit_text_title)
        editTextDescription = findViewById(R.id.edit_text_description)
        textViewData = findViewById(R.id.text_view_data)
    }

    fun saveNote(v: View) {
        val title = editTextTitle!!.text.toString()
        val description = editTextDescription!!.text.toString()

        val note = HashMap<String, Any>()
        note[KEY_TITLE] = title
        note[KEY_DESCRIPTION] = description

        noteRef.set(note)
                .addOnSuccessListener { Toast.makeText(this@MainActivity, "Note saved", Toast.LENGTH_SHORT).show() }
                .addOnFailureListener { e ->
                    Toast.makeText(this@MainActivity, "Error!", Toast.LENGTH_SHORT).show()
                    Log.d(TAG, e.toString())
                }
    }

    fun loadNote(v: View) {

        noteRef.get()
                .addOnSuccessListener { documentSnapshot ->
                    if (documentSnapshot.exists()) {
                        val title = documentSnapshot.getString(KEY_TITLE)
                        val description = documentSnapshot.getString(KEY_DESCRIPTION)
                        textViewData!!.text = "Title: $title\nDescription: $description"
                        Toast.makeText(this@MainActivity, "Note Found", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@MainActivity, "Document Not Found", Toast.LENGTH_SHORT).show()
                    }
                }


    }

    companion object {
      val TAG = "MainActivity"
      val KEY_TITLE = "title"
      val KEY_DESCRIPTION = "description"
    }
}