package com.joker.coolmall.feature.market

class NativeLib {

    /**
     * A native method that is implemented by the 'market' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'market' library on application startup.
        init {
            System.loadLibrary("market")
        }
    }
}