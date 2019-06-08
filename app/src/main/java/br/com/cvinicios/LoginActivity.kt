package br.com.cvinicios

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        if (usuarioLogado()){
            proximaTelaLogada()
        }

        botaoEntrar.setOnClickListener{
            validarUsuario()
        }
    }

    private fun usuarioLogado(): Boolean{
        return mAuth.currentUser != null
    }

    private fun proximaTelaLogada(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun validarUsuario(){
        val email = inputEmail.editText?.text.toString()
        val senha = inputSenha.editText?.text.toString()

        if(email.isEmpty()){
            inputEmail.isErrorEnabled = true
            inputEmail.error = "O e-mail deve ser preenchido"
            return
        }else{
            inputEmail.isErrorEnabled = false
        }

        if(senha.isEmpty()){
            inputSenha.isErrorEnabled = true
            inputSenha.error = "A senha deve ser preenchida"
            return
        }else{
            inputSenha.isErrorEnabled = false
        }

        mAuth.signInWithEmailAndPassword(
            inputEmail.editText?.text.toString(),
            inputSenha.editText?.text.toString())
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    proximaTelaLogada()
                }else{
                    Toast.makeText(this, "Falha de autenticação",
                        Toast.LENGTH_LONG).show()
                }
        }
    }
}
