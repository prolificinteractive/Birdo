package com.prolificinteractive.sample

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.prolificinteractive.birdo.BirdoInitializer
import com.prolificinteractive.birdo.KeyUpDetector
import com.prolificinteractive.birdo.VolumeDownDetector
import com.prolificinteractive.birdo.utils.MockMode
import com.prolificinteractive.sample.api.responses.Characters
import kotlinx.android.synthetic.main.activity_home.characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity(), Callback<Characters> {
  private var charactersAdapter: CharactersAdapter = CharactersAdapter()

  private lateinit var keyUpDetector: KeyUpDetector
  private lateinit var mockModeFetcher: MockMode

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home)

    characters.layoutManager = GridLayoutManager(this, 3)
    characters.adapter = charactersAdapter

    val app = application as App

    app.client.getCharacters().enqueue(this)

    val birdoInitializer = BirdoInitializer(this, MyBirdoActivity::class.java)

    keyUpDetector = VolumeDownDetector(birdoInitializer)
    mockModeFetcher = MockMode(this)
  }

  override fun onResponse(call: Call<Characters>?, response: Response<Characters>?) {
    if (response != null && response.isSuccessful && response.body() != null) {
      charactersAdapter.data = response.body()!!.results
      charactersAdapter.notifyDataSetChanged()
    }
  }

  override fun onFailure(call: Call<Characters>?, t: Throwable?) {
    Log.e("HomeActivity", t?.message, t)
  }

  /**
   * This is used to open the debug activity in debug builds: [KeyUpDetector]
   */
  override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
    keyUpDetector.onKeyUp(this, keyCode, event)
    return super.onKeyUp(keyCode, event)
  }
}
