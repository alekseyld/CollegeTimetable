package com.alekseyld.collegetimetable.view.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.alekseyld.collegetimetable.R;
import com.alekseyld.collegetimetable.internal.di.component.MainComponent;
import com.alekseyld.collegetimetable.presenter.WebViewPresenter;
import com.alekseyld.collegetimetable.service.MusicService;
import com.alekseyld.collegetimetable.utils.Utils;
import com.alekseyld.collegetimetable.view.WebViewView;
import com.alekseyld.collegetimetable.view.fragment.base.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alekseyld on 09.12.2017.
 */

public class WebViewFragment extends BaseFragment<WebViewPresenter> implements WebViewView {

    //document.getElementsByClassName("audio_item ai_has_btn")
    //document.getElementsByClassName("audio_item ai_has_btn")[0].innerHTML += "<div onclick=\"alert(\"1212\");\">+</div>"
    //document.getElementsByClassName("audio_item ai_has_btn")[1].getAttribute("onclick")
    //document.getElementsByClassName("audio_item ai_has_btn")[1].setAttribute("onclick", atr)

    //Название - arr[0].getElementsByClassName('ai_title')[0].innerText
    //Группа -   arr[0].getElementsByClassName('ai_artist')[0].innerText

    //+ "arr[i].setAttribute('onclick', 'alert(\\'' + arr[i].getAttribute('data-id') + '\\');');"

    @Inject
    SharedPreferences sharedPreferences;

    private MusicService mMusicService;
    private boolean serviceBinded = false;
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) iBinder;
            mMusicService = binder.getService(WebViewFragment.this);
            serviceBinded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            serviceBinded = false;
        }
    };

    private final String url = "";
    private final String WEBVIEW_COOKIE_KEY = "WEBVIEW_COOKIE_KEY";
    private final String javaScriptKey = "javascript:";

    private final String fixAudio_js = javaScriptKey + "document.getElementsByClassName('audio_row_content _audio_row_content')[0].click();" +
            "setTimeout(function(){ document.getElementsByClassName('audio_row_content _audio_row_content')[0].click(); ap._impl._currentAudioEl.currentTime = 5; ap._impl._currentAudioEl.currentTime = 10; ap._impl._currentAudioEl.currentTime = 15; getAudioPlayer().pause();}, 2000);";

    private final String onclick_js = javaScriptKey + "var arr = document.getElementsByClassName('audio_item ai_has_btn');"
            + "for (var i = 0; i < arr.length; i++){"
            + "if (arr[i].getAttribute('onclick').toString().includes('audioplayer.playPause(event,')){"
            + "arr[i].setAttribute('onclick', 'JAVA.processAudio(\\'' + arr[i].getAttribute(\"data-id\") + '\\',\\'' + arr[i].getElementsByClassName(\"ai_title\")[0].innerText + '\\',\\'' + arr[i].getElementsByClassName(\"ai_artist\")[0].innerText + '\\');');"
            +"}" +
            "}";

    private final String getAudioUrl_js = javaScriptKey + "getAudioPlayer().play('%s', getAudioPlayer().getPlaylists()[0], '');" +
            "setTimeout(function(){ JAVA.downloadAudio(ap._impl._currentAudioEl.currentSrc, '%s', '%s', '%s'); getAudioPlayer().pause();}, 2000);";

    public static WebViewFragment newInstance(){
        return new WebViewFragment();
    }

    public class JavaScriptInterface
    {
        @JavascriptInterface
        public void processAudio(String id, final String audioTitle, final String audioArtist)
        {
            String[] idConcat = id.split("_");
            final String audioId = idConcat[0] + "_" + idConcat[1];

            Toast.makeText(getContext(), audioArtist + " - " + audioTitle, Toast.LENGTH_SHORT).show();

            //called by javascript
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    pcWebView.loadUrl(
                            String.format(getAudioUrl_js, audioId, audioId, audioTitle, audioArtist)
                    );
                }
            });
        }

        @JavascriptInterface
        public void downloadAudio(String url, String audioId, String audioTitle, String audioArtist) {

            if (mMusicService != null)
                mMusicService.processAudio(url, audioId, audioTitle, audioArtist);
//            Utils.downloadAudioByUrl(url, audioId, audioTitle, audioArtist);
        }
    }

    @BindView(R.id.webview)
    WebView webView;

    @BindView(R.id.webview_pc)
    WebView pcWebView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_webview, container, false);
        ButterKnife.bind(this, v);
        getActivity().setTitle(R.string.webview_title);

        //pc webview
        String newUA= "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
        pcWebView.getSettings().setUserAgentString(newUA);
        pcWebView.getSettings().setJavaScriptEnabled(true);
        pcWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        pcWebView.setWebChromeClient(new WebChromeClient());
        pcWebView.addJavascriptInterface(new JavaScriptInterface(), "JAVA");
        pcWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                pcWebView.loadUrl(javaScriptKey + "window.scrollTo(0,300);");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pcWebView.loadUrl(fixAudio_js);

                    }
                }, 200);
            }
        });

        pcWebView.loadUrl(url + "audio");

        //webview
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.addJavascriptInterface(new JavaScriptInterface(), "JAVA");
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.contains("audio")){
                    sharedPreferences.edit().putString(WEBVIEW_COOKIE_KEY, CookieManager.getInstance().getCookie(url)).apply();
//                    webView.loadUrl("javascript:alert(StaticFiles['audioplayer.js'][\"v\"]);");
//                    view.loadUrl("javascript:alert(ap._impl._currentAudioEl.currentSrc););");

                    webView.loadUrl(onclick_js);
                }
            }
        });

        return v;
    }

    int i = 0;

    @Override
    public void onResume() {
        super.onResume();

        CookieManager.getInstance().setCookie(url, sharedPreferences.getString(WEBVIEW_COOKIE_KEY, ""));//627410

        if (i == 0) {
            webView.loadUrl(url + "audio");
            i++;
        }

    }

    @Override
    public void onStart() {
        super.onStart();

        Intent intent = new Intent(getActivity(), MusicService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();

        if (serviceBinded) {
            getActivity().unbindService(mConnection);
            serviceBinded = false;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    protected void initializeInjections() {
        getComponent(MainComponent.class).inject(this);
    }
}
