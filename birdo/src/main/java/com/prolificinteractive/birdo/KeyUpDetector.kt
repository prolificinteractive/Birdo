package com.prolificinteractive.birdo

import android.app.Activity
import android.view.KeyEvent

interface KeyUpDetector {

  fun onKeyUp(activity: Activity, keyCode: Int, event: KeyEvent)
}
