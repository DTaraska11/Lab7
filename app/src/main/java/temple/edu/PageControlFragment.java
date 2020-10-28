package temple.edu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageControlFragment extends Fragment implements View.OnClickListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    EditText editText;
    WebView webView;

    public PageControlFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PageControlFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PageControlFragment newInstance(String param1, String param2) {
        PageControlFragment fragment = new PageControlFragment();
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

        View view = inflater.inflate(R.layout.fragment_page_control, container, false);
        View view2 = inflater.inflate(R.layout.fragment_page_viewer, container, false);
        ImageButton button3 = (ImageButton) view.findViewById(R.id.imageButton4);
        ImageButton button2 = (ImageButton)view.findViewById(R.id.imageButton3);
        ImageButton button1 = (ImageButton) view.findViewById(R.id.imageButton2);

        editText = view.findViewById(R.id.editText);
        webView = view2.findViewById(R.id.webView);



        button3.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageButton4:
                String url = editText.getText().toString();
                String url2 = "";
                if (!url.contains("http://")){
                    url2 = "http://" + url;
                }
                else{
                    url2 = url;
                }
                webView.loadUrl(url2);
                break;
            case R.id.imageButton2:

                break;
            // Do this for all buttons.
        }
    }
}