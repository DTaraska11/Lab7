package temple.edu;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageViewerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageViewerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    WebView webView;
    WebViewClient MyWebViewClient;

    public PageViewerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PageViewerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PageViewerFragment newInstance(String param1, String param2) {
        PageViewerFragment fragment = new PageViewerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_page_viewer, container, false);
        webView = (WebView)view.findViewById(R.id.webview);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                ActionBar ab = ((AppCompatActivity) Objects.requireNonNull(getActivity())).getSupportActionBar();
                //ab.setTitle(view.getTitle());
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.com");
        return view;
    }

    public void setWebView(String URL){

        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(MyWebViewClient);
        webView.loadUrl(URL);
        //ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        //ab.setTitle(webView.getTitle());



    }

    public void back(){
        if(webView.canGoBack()){
            webView.goBack();
            //ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
            //ab.setTitle(webView.getTitle());
        }
    }

    public void forward(){
        if(webView.canGoForward()){
            webView.goForward();
            //ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
            //ab.setTitle(webView.getTitle());
        }
    }
}