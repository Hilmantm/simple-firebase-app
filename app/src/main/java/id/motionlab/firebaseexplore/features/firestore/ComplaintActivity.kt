package id.motionlab.firebaseexplore.features.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import id.motionlab.firebaseexplore.R

class ComplaintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complaint)
        val nameField = findViewById<EditText>(R.id.name_field)
        val majorField = findViewById<EditText>(R.id.jurusan_field)
        val complaintField = findViewById<EditText>(R.id.complaint_field)
        val submitBtn = findViewById<Button>(R.id.submit_firestore_btn)

        val db = FirebaseFirestore.getInstance()

        submitBtn.setOnClickListener {
            if (nameField.text.isEmpty()) {
                Toast.makeText(this@ComplaintActivity, "Name Should Not Empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (majorField.text.isEmpty()) {
                Toast.makeText(this@ComplaintActivity, "Jurusan Should Not Empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (complaintField.text.isEmpty()) {
                Toast.makeText(this@ComplaintActivity, "Complaint Should Not Empty", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val complaint = Complaint(name = nameField.text.toString(), major = majorField.text.toString(), complaint = complaintField.text.toString())
            db.collection("complaints").add(complaint.getComplaintHashMap()).addOnSuccessListener {
                Toast.makeText(this@ComplaintActivity, "success add complaint", Toast.LENGTH_SHORT).show()
                // reset field
                nameField.setText("")
                majorField.setText("")
                complaintField.setText("")
            }
        }
    }
}