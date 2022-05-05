package id.whynot.submission3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import id.whynot.submission3.View.Addpost.AddActivity
import id.whynot.submission3.View.Welcome.WellcomeActivity
import id.whynot.submission3.adapter.PostAdapter
import id.whynot.submission3.databinding.ActivityMainBinding
import id.whynot.submission3.model.modelUserPreferences
import id.whynot.submission3.model.post
import id.whynot.submission3.preference.userprefreference
import id.whynot.submission3.response.PostResponse
import id.whynot.submission3.retrofitAndAPI.API
import id.whynot.submission3.retrofitAndAPI.retrofitclient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val postItem = MutableLiveData<List<post>>()
    private lateinit var adapterList: PostAdapter
    private lateinit var UserPreference: userprefreference
    private lateinit var binding: ActivityMainBinding
    private lateinit var modelUserPreferences: modelUserPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        UserPreference = userprefreference(this)
        adapterList = PostAdapter()

        refreshApp()

        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = adapterList
            rvList.setHasFixedSize(true)

            floatingbtn.setOnClickListener {
                val intent = Intent(this@MainActivity,AddActivity::class.java)
                intent.putExtra(AddActivity.EXTRA_TOKEN, modelUserPreferences.token)
                startActivity(intent)
            }
        }

        checkUserexist()

        postItem.observe(this) {
            Log.d("bisa ayo ", "cek api $it")
            adapterList.setData(it)
            binding.rvList.visibility = View.VISIBLE
        }

    }

    private fun checkUserexist() {
        modelUserPreferences = UserPreference.getUser()
        if (modelUserPreferences.name.toString().isEmpty()) {

            val intent = Intent(this@MainActivity, WellcomeActivity::class.java)
            startActivity(intent)
        } else {
            listPost("Bearer ${modelUserPreferences.token}")
            Log.e("checkUserexist: ", "${modelUserPreferences.name}")
            Log.e("checkUserexist: ", "${modelUserPreferences.token}")
        }

    }

    private fun hapus() {
        val user = modelUserPreferences(
            "",
            ""
        )
        UserPreference.setUser(user)
        Log.e("saveuser:", "kosong")
        val intent = Intent(this@MainActivity, WellcomeActivity::class.java)
        startActivity(intent)
    }

    private fun listPost(token: String) {
        showloading(true)
        val retro = retrofitclient().getRetroClientInstance().create(API::class.java)
        Log.e("main activity: ", "udah sampe sini main $token ")
        retro.getpost(token).enqueue(object : Callback<PostResponse> {
            override fun onResponse(
                call: Call<PostResponse>,
                response: Response<PostResponse>
            ) {
                showloading(false)
                val listpost = arrayListOf<post>()
                val postFromApi = response.body()
                if (postFromApi != null) {
                    Log.e("panjang : ", postFromApi.listStory?.size.toString())

                    for (posisition in 0 until postFromApi.listStory?.size!!) {
                        val postingan = post(
                            postFromApi.listStory!![posisition].name,
                            postFromApi.listStory!![posisition].description,
                            postFromApi.listStory!![posisition].photoUrl,
                        )
                        listpost.add(postingan)
                    }
                }
                postItem.postValue(listpost)
                Log.e("onResponse: suskes ", "$postItem")

            }

            override fun onFailure(call: Call<PostResponse>, t: Throwable) {
                showloading(false)
                Toast.makeText(this@MainActivity, R.string.failedgetdata, Toast.LENGTH_SHORT).show()
                Log.e("onFailure: ", t.message.toString())

            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.setting -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.quit -> {
                hapus()
                true
            }
            else -> {
                false
            }
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
    private fun refreshApp(){
        binding.swipeToRefresh.setOnRefreshListener {
            listPost("Bearer ${modelUserPreferences.token}")
            binding.swipeToRefresh.isRefreshing = false
        }
    }
}


