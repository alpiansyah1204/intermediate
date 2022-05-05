package id.whynot.submission3.View.Login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import id.whynot.submission3.MainActivity
import id.whynot.submission3.R
import id.whynot.submission3.View.Signup.SignUpActivity
import id.whynot.submission3.databinding.ActivityLoginBinding
import id.whynot.submission3.model.modelUserPreferences
import id.whynot.submission3.preference.userprefreference
import id.whynot.submission3.request.RequestLogin
import id.whynot.submission3.response.loginResponse
import id.whynot.submission3.retrofitAndAPI.API
import id.whynot.submission3.retrofitAndAPI.retrofitclient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userprefreference: userprefreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playanimation()
        userprefreference = userprefreference(this)
        binding.apply {
            btnSignup.setOnClickListener {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
            }
            LoginBTN.setOnClickListener {
                login()
            }
        }

    }

    private fun login() {
        showloading(true)
        val request = RequestLogin()
        request.email = binding.inputemail.text.toString()
        request.password = binding.inputpasword.text.toString()

        val retro = retrofitclient().getRetroClientInstance().create(API::class.java)
        Log.e("login: ", "udah sampe sini ")
        retro.login(request).enqueue(object : Callback<loginResponse> {
            override fun onResponse(call: Call<loginResponse>, response: Response<loginResponse>) {

                showloading(false)
                val user = response.body()
                if (user != null) {
                    Log.e("onResponse: berhasil ", user.loginResult?.name.toString())
                    Log.e("onResponse: berhasil ", user.loginResult?.token.toString())
                    Toast.makeText(this@LoginActivity, R.string.successlogin, Toast.LENGTH_SHORT).show()
                    saveuser(user.loginResult?.name.toString(), user.loginResult?.token.toString())
                    Handler(Looper.getMainLooper()).postDelayed({
                        val intent = Intent(this@LoginActivity,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    },1000)
                }
                else {
                    showloading(false)
                    Log.e("onResponse: gagal ", user?.loginResult?.name.toString())
                    Log.e("onResponse: gagal ", user?.loginResult?.token.toString())
                    Toast.makeText(this@LoginActivity, R.string.failedlogin2, Toast.LENGTH_SHORT).show()
                    binding.inputemail.text?.clear()
                    binding.inputpasword.text?.clear()
                }
            }
            override fun onFailure(call: Call<loginResponse>, t: Throwable) {
                showloading(false)
                Log.e("onFailure:", "${t.message}")
                Toast.makeText(this@LoginActivity, R.string.failedlogin, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveuser(name: String, token: String) {
        val user = modelUserPreferences(
            name,
            token
        )
        userprefreference.setUser(user)
        Log.e("saveuser:", " $name  $token")
    }


    private fun playanimation() {
        ObjectAnimator.ofFloat(binding.Logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val loginText = ObjectAnimator.ofFloat(binding.LoginText, View.ALPHA, 1f).setDuration(300)
        val emailText = ObjectAnimator.ofFloat(binding.EmailText, View.ALPHA, 1f).setDuration(300)
        val inputemail = ObjectAnimator.ofFloat(binding.inputemail, View.ALPHA, 1f).setDuration(300)
        val passwordText =
            ObjectAnimator.ofFloat(binding.passwordText, View.ALPHA, 1f).setDuration(300)
        val inputpasword =
            ObjectAnimator.ofFloat(binding.inputpasword, View.ALPHA, 1f).setDuration(300)
        val loginBTN = ObjectAnimator.ofFloat(binding.LoginBTN, View.ALPHA, 1f).setDuration(300)
        val bottomLinearLayout =
            ObjectAnimator.ofFloat(binding.BottomLinearLayout, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(
                loginText,
                emailText,
                inputemail,
                passwordText,
                inputpasword,
                loginBTN,
                bottomLinearLayout
            )
            start()
        }
    }

    private fun showloading(istrue:Boolean){
        if(istrue){
            binding.blackscreen.visibility = View.VISIBLE
            binding.progressbar.visibility = View.VISIBLE
        }
        else{
            binding.blackscreen.visibility = View.GONE
            binding.progressbar.visibility = View.GONE
        }
    }
}
