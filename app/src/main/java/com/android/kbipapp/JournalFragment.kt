package com.android.kbipapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment


class JournalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val paramSurname : String
        val paramGroup : String
        val paramBirthdate : String

        val preferences = requireActivity().getSharedPreferences("User", Context.MODE_PRIVATE)
        paramSurname = preferences.getString("surname", null).toString()
        paramGroup = preferences.getString("group", null).toString()
        paramBirthdate = preferences.getString("birthdate", null).toString()

        val js: String =
            "document.getElementById(\"student_name\").value = '$paramSurname';" +
                    "document.getElementById(\"birth_day\").value = '$paramBirthdate';" +
                    "document.getElementById(\"group_id\").value = getGroup(document.getElementsByTagName('option'));" +
                    "check_login();"

        val js_GetGroupID = "var group = '$paramGroup'\n;" +
                "function getGroup(options) {\n" +
                "    for (i=0; i<options.length; i++) {\n" +
                "        if (options[i].text == group)\n" +
                "        {\n" +
                "            return options[i].value\n" +
                "        }\n" +
                "    }\n" +
                "};"

        val js_RemoveBar = "document.body.removeChild(document.getElementsByClassName('navbar navbar-inverse navbar-fixed-top')[0]); document.body.style.padding = '0'"

        val myWebView: WebView = view.findViewById(R.id.webview)
        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, weburl: String) {
                myWebView.evaluateJavascript(js_GetGroupID, null)
                myWebView.evaluateJavascript(" var FunctionOne = function () {$js}; FunctionOne();", null)
                myWebView.evaluateJavascript(js_RemoveBar, null)
            }
        }
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true
        myWebView.settings.builtInZoomControls = true
        myWebView.settings.displayZoomControls = false
        myWebView.loadUrl("http://nehai.by/ej/index.php")

//        myWebView.evaluateJavascript(js, null)
    }
}