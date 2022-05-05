package id.whynot.submission3.View.Signup

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
import id.whynot.submission3.R
import id.whynot.submission3.View.Login.LoginActivity
import id.whynot.submission3.databinding.ActivitySignUpBinding
import id.whynot.submission3.request.RequestSignup
import id.whynot.submission3.response.SignupResponse
import id.whynot.submission3.retrofitAndAPI.API
import id.whynot.submission3.retrofitAndAPI.retrofitclient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playanimation()
        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
        }
        binding.SignupBTN.setOnClickListener {
            signUp()
        }
    }

    private fun signUp(){
        showloading(true)
        val request = RequestSignup()
        request.name = binding.inputname.text.toString().trim()
        request.email = binding.inputemail.text.toString().trim()
        request.password =binding.inputpasword.text.toString().trim()

        val retro = retrofitclient().getRetroClientInstance().create(API::class.java)
        Log.e("signup: ", "udah sampe sini ")
        retro.register(request).enqueue(object : Callback<SignupResponse> {
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                showloading(false)
                val user = response.body()
                if(user != null ){
                    Log.e("onResponse: berhasil ", user.error.toString())
                    Log.e("onResponse: berhasil ", user.message.toString())

                    Toast.makeText(this@SignUpActivity, R.string.successsignup, Toast.LENGTH_SHORT).show()
                    Handler((Looper.getMainLooper())).postDelayed({
                        val intent = Intent(this@SignUpActivity,LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    },1000)
                }
                else{
                    showloading(false)
                    Toast.makeText(this@SignUpActivity, R.string.failedsignup2, Toast.LENGTH_SHORT).show()
                    Log.e("onResponse: gagaldong ", "${request.name}")
                    Log.e("onResponse: gagaldong ", "${request.email}")
                    Log.e("onResponse: gagaldong ", "${request.password}")
                    binding.inputname.text?.clear()
                    binding.inputemail.text?.clear()
                    binding.inputpasword.text?.clear()
                }

            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                showloading(false)
                Log.e("onFailure:", "${t.message}")
                Toast.makeText(this@SignUpActivity, R.string.failedsignup, Toast.LENGTH_SHORT).show()


            }


        })
    }


    private fun playanimation() {
        ObjectAnimator.ofFloat(binding.Logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
        val singuptext = ObjectAnimator.ofFloat(binding.singuptext, View.ALPHA, 1f).setDuration(300)
        val nametext = ObjectAnimator.ofFloat(binding.nametext, View.ALPHA, 1f).setDuration(300)
        val inputname = ObjectAnimator.ofFloat(binding.inputname, View.ALPHA, 1f).setDuration(300)
        val emailtext = ObjectAnimator.ofFloat(binding.emailtext, View.ALPHA, 1f).setDuration(300)
        val inputemail = ObjectAnimator.ofFloat(binding.inputemail, View.ALPHA, 1f).setDuration(300)
        val passwordtext =
            ObjectAnimator.ofFloat(binding.passwordtext, View.ALPHA, 1f).setDuration(300)
        val inputpasword =
            ObjectAnimator.ofFloat(binding.inputpasword, View.ALPHA, 1f).setDuration(300)
        val signupBTN = ObjectAnimator.ofFloat(binding.SignupBTN, View.ALPHA, 1f).setDuration(300)
        val bottomLinearLayout =
            ObjectAnimator.ofFloat(binding.BottomLinearLayout, View.ALPHA, 1f).setDuration(300)
        AnimatorSet().apply {
            playSequentially(
                singuptext,
                nametext,
                inputname,
                emailtext,
                inputemail,
                passwordtext,
                inputpasword,
                signupBTN,
                bottomLinearLayout,
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