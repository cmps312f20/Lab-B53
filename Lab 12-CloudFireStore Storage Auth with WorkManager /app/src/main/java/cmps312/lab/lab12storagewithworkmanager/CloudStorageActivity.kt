package cmps312.lab.lab12storagewithworkmanager

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.FirebaseStorageKtxRegistrar
import kotlinx.android.synthetic.main.activity_cloud_storage.*
import java.text.SimpleDateFormat
import java.util.*

class CloudStorageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cloud_storage)

        uploadImageBtn.setOnClickListener {
           //uploadImage();
        }

        pickImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 101)
        }
    }

    fun uploadImage(photoUri : Uri){
        var timeStamp = SimpleDateFormat("yyyyMMmdd").format(Date())
        var fileName = "IMAGE_$timeStamp"+"_.png"

        var storageRef = FirebaseStorage.getInstance().reference
            .child("/images").child(fileName)

        storageRef.putFile(photoUri).addOnSuccessListener {
            Toast.makeText(this, "Uploded successfully", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 101){
            val photoUri = data?.data!!
            imageView.setImageURI(photoUri)
            uploadImage(photoUri)
        }
    }
}