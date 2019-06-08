package br.com.cvinicios

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_iluminacao.*

class IluminacaoActivity : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private lateinit var ledRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iluminacao)

        database = FirebaseDatabase.getInstance()
        ledRef = database.getReference("Led")

        btnLed.setOnClickListener{
            if (btnLed.displayedChild ==0){
                ledRef.setValue(1)
                btnLed.showNext()
            }else{
                ledRef.setValue(0)
                btnLed.showPrevious()
            }
        }

        ledRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot){
                val value = dataSnapshot.getValue(Int::class.java)
                if(value == 1){
                    if(btnLed.displayedChild != 1){
                        btnLed.showNext()
                    }
                }else{
                    if(btnLed.displayedChild != 0){
                        btnLed.showPrevious()
                    }
                }
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}
