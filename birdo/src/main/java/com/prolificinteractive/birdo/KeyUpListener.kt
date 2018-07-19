package com.prolificinteractive.birdo

import android.app.Activity
import android.view.KeyEvent

interface KeyUpListener {

  fun onKeyUp(activity: Activity, keyCode: Int, event: KeyEvent)
}
