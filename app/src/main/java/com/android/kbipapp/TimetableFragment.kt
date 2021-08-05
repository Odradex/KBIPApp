package com.android.kbipapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * A simple [Fragment] subclass.
 * Use the [TimetableFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TimetableFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_timetable, container, false)
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myWebView: WebView = view.findViewById(R.id.webview)
        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                myWebView.evaluateJavascript("document.getElementsByClassName('wrap')[0].style.overflow = 'scroll';", null)

            }
        }
        val isDarkTheme : Boolean = resources.configuration.uiMode.and(Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        val cm = this.activity?.getSystemService(Activity.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cm.getNetworkCapabilities(cm.activeNetwork) != null){
            myWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT
        }
        else{
            Toast.makeText(context,R.string.toast_no_connection, Toast.LENGTH_LONG).show()
            myWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY
        }
        myWebView.loadUrl("https://kbp.by/rasp/timetable/view_beta_kbp/?theme=${if(isDarkTheme) "default" else "classic"}")


        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true
        myWebView.settings.builtInZoomControls = true
        myWebView.settings.displayZoomControls = false

    }

}