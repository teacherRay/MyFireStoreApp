package com.camboray.myfirestoreapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import com.google.firebase.firestore.FirebaseFirestore

import org.w3c.dom.Text

import java.util.HashMap

class MainActivity : AppCompatActivity() {

    private var editTextTitle: EditText? = null
    private var editTextDescription: EditText? = null
    private var textViewData: TextView? = null

    private val db = FirebaseFirestore.getInstance()
    private val noteRef = db.document("Notebook/My First Note")
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
        private val TAG = "MainActivity"

        private val KEY_TITLE = "title"
        private val KEY_DESCRIPTION = "description"
    }
}