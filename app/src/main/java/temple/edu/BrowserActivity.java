package temple.edu;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.OnPageControlSelectedListener, BrowserControlFragment.BrowserControlListener, PageListFragment.OnTitleSelectedListener {


    PageControlFragment pageControlFragment = new PageControlFragment();
    PageViewerFragment pageViewerFragment = new PageViewerFragment();
    PagerFragment pagerFragment = new PagerFragment();
    PageListFragment pageListFragment = new PageListFragment();
    BrowserControlFragment browserControlFragment = new BrowserControlFragment();
    ArrayList<PageViewerFragment> viewerFragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int orientation = getResources().getConfiguration().orientation;
        viewerFragments = new ArrayList<>();
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.activity_main);

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout2, pageControlFragment);
            ft.replace(R.id.frameLayout, pagerFragment);
            ft.replace(R.id.frameLayout5, browserControlFragment);
            ft.replace(R.id.frameLayout4, pageListFragment);
            ft.commit();

        } else {

            setContentView(R.layout.activity_main);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout2, pageControlFragment);
            ft.replace(R.id.frameLayout, pageViewerFragment);
            ft.replace(R.id.frameLayout3, browserControlFragment);
            ft.commit();
        }

    }


    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof PageControlFragment) {
            PageControlFragment pageControlFragment = (PageControlFragment) fragment;
            pageControlFragment.setOnPageConrolSelectedListener(this);
        }
        if (fragment instanceof BrowserControlFragment) {
            BrowserControlFragment browserControlFragment = (BrowserControlFragment) fragment;
            browserControlFragment.setBrowserControlListener(this);
        }
        if (fragment instanceof PageListFragment) {
            PageListFragment pageListFragment = (PageListFragment) fragment;
            pageListFragment.setOnTitleSelectedListener(this);
        }

    }

    @Override
    public void onActionSelected(String url) {

        int index = pagerFragment.viewPager.getCurrentItem();
        if(url.equals("f")){
            pagerFragment.fragments.get(index).forward();
        }
        else if(url.equals("b")){
            pagerFragment.fragments.get(index).back();
        }
        else {
            pagerFragment.fragments.get(index).setWebView(url);
        }
    }

    @Override
    public void fragmentAdded(String url){

        pagerFragment.addFragment(new PageViewerFragment());
        refreshList();


    }

    public ArrayList<PageViewerFragment> getFragments(){
        return pagerFragment.fragments;
    }

    public void refreshList(){
        ArrayList<PageViewerFragment> fragList = pagerFragment.fragments;
        ListView listView = new ListView(this);
        ArrayAdapter<TextView> adapter = new ArrayAdapter<TextView>(this, R.layout.simplerow);
        for (PageViewerFragment fragment:fragList)
        {
            TextView title = new TextView(this);
            title.setText(fragment.webView.getTitle());
            adapter.add(title);
        }

        listView.setAdapter(adapter);
        pageListFragment.refreshList(adapter);

    }


    @Override
    public void OnTitleSelected(int index) {
        pagerFragment.viewPager.setCurrentItem(index);
    }
}