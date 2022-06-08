package com.example.codrum

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.codrum.databinding.ActivityRegisterBinding
import com.example.codrum.dialog.LoadingDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.jetbrains.anko.toast

class RegisterActivity : AppCompatActivity() {

    private var _binding: ActivityRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.btnRegister.setOnClickListener {
            val email: String = binding.tilEmail.editText?.text.toString()
            val passwd: String = binding.tilPasswd.editText?.text.toString()
            val name: String = binding.tilName.editText?.text.toString()

            if (isValidate()) {
                register(email, passwd, name)
//                val dialog = LoadingDaialog(this)
//
//                CoroutineScope(Dispatchers.Main).launch {
//                    dialog.show()
//                    var fbRegister = withContext(Dispatchers.IO){
//                        register(email,passwd,name)
//                    }
//                    delay(1000L)
//                    dialog.dismiss()
//                    finish()
//                }
            } else {
                return@setOnClickListener
            }
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

    }

    // 형식 유효성 검사
    private fun isValidate(): Boolean {
        val email: String = binding.tilEmail.editText?.text.toString()
        val pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val passwd: String = binding.tilPasswd.editText?.text.toString()
        val name: String = binding.tilName.editText?.text.toString()

        var count = 0
        // 이메일 유효성
        if (email.isEmpty()) {
            binding.tilEmail.error = "이메일을 입력해 주세요."
        } else if (!email.matches(pattern.toRegex())) {
            binding.tilEmail.error = "이메일 형식을 맞추어주세요."
        } else {
            binding.tilEmail.error = null
            binding.tilEmail.isErrorEnabled = false
            count++
        }

        //비밀번호 유효성 검사
        if (passwd.isEmpty()) {
            binding.tilPasswd.error = "비밀번호를 입력해 주세요."
        } else if (passwd.length < 6) {
            binding.tilPasswd.error = "최소 6자리 이상입니다."
        } else {
            binding.tilPasswd.error = null
            binding.tilPasswd.isErrorEnabled = false
            count++
        }
        //이름 유효성 검사
        if (name.isEmpty()) {
            binding.tilName.error = "이름을 입력해 주세요."
        } else if (name.length > 10) {
            binding.tilName.error = "최대 10자리 입니다."
        } else {
            binding.tilName.error = null
            binding.tilName.isErrorEnabled = false
            count++
        }
        if (count == 3) return true

        return false
    }

    private fun register(email: String, password: String, name: String) {
        val dialog = LoadingDialog(this)
        dialog.show()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val data = hashMapOf(
                        "name" to name,
                        "uid" to user?.uid.toString()
                    )
                    db.collection("User").document(user?.uid.toString())
                        .set(data)
                        .addOnSuccessListener { Log.d("Firestore", "Success to writing data") }
                        .addOnFailureListener { e -> Log.w("Firestore", "Fail to writing data", e) }
                    dialog.dismiss()
                    toast("계정 생성 완료")
                    finish()
                    Log.d("auth", "createUserWithEmail:success")
                } else {
                    // If sign in fails, display a message to the user.
                    dialog.dismiss()
                    Log.w("auth", "createUserWithEmail:failure", task.exception)
                }
            }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}