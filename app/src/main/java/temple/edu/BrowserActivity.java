package temple.edu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import static temple.edu.BookmarksActivity.PREFERENCES;
import static temple.edu.BookmarksActivity.WEB_LINKS;
import static temple.edu.BookmarksActivity.WEB_TITLE;

public class BrowserActivity extends AppCompatActivity implements PageControlFragment.OnPageControlSelectedListener, BrowserControlFragment.BrowserControlListener, PageListFragment.OnTitleSelectedListener {


    PageControlFragment pageControlFragment = new PageControlFragment();
    PageViewerFragment pageViewerFragment = new PageViewerFragment();
    PagerFragment pagerFragment = new PagerFragment();
    PageListFragment pageListFragment = new PageListFragment();
    BrowserControlFragment browserControlFragment = new BrowserControlFragment();
    ArrayList<PageViewerFragment> viewerFragments;
    int orientation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        orientation = getResources().getConfiguration().orientation;
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
            ft.replace(R.id.frameLayout, pagerFragment);
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
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (fragment instanceof PageListFragment) {
                PageListFragment pageListFragment = (PageListFragment) fragment;
                pageListFragment.setOnTitleSelectedListener(this);
            }
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
            //ActionBar ab = getSupportActionBar();
            //ab.setTitle(pagerFragment.fragments.get(pagerFragment.viewPager.getCurrentItem()).webView.getTitle());
        }
    }

    @Override
    public void fragmentAdded(String url){
        pagerFragment.addFragment(new PageViewerFragment());
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            refreshList();
        }
        //ActionBar ab = getSupportActionBar();
        //ab.setTitle(pagerFragment.fragments.get(pagerFragment.viewPager.getCurrentItem()).webView.getTitle());
    }

    @Override
    public void bookmarkAdded(String url) {
        String message;
        int index = pagerFragment.viewPager.getCurrentItem();
        SharedPreferences sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        String jsonLink = sharedPreferences.getString(WEB_LINKS, null);
        String jsonTitle = sharedPreferences.getString(WEB_TITLE, null);


        if (jsonLink != null && jsonTitle != null) {

            Gson gson = new Gson();
            ArrayList<String> linkList = gson.fromJson(jsonLink, new TypeToken<ArrayList<String>>() {
            }.getType());

            ArrayList<String> titleList = gson.fromJson(jsonTitle, new TypeToken<ArrayList<String>>() {
            }.getType());

            if (linkList.contains(pagerFragment.fragments.get(index).webView.getUrl())) {
                linkList.remove(pagerFragment.fragments.get(index).webView.getUrl());
                titleList.remove(pagerFragment.fragments.get(index).webView.getTitle().trim());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(WEB_LINKS, new Gson().toJson(linkList));
                editor.putString(WEB_TITLE, new Gson().toJson(titleList));
                editor.apply();


                message = "Bookmark Removed";

            } else {
                linkList.add(pagerFragment.fragments.get(index).webView.getUrl());
                titleList.add(pagerFragment.fragments.get(index).webView.getTitle().trim());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(WEB_LINKS, new Gson().toJson(linkList));
                editor.putString(WEB_TITLE, new Gson().toJson(titleList));
                editor.apply();

                message = "Bookmarked";
            }
        } else {

            ArrayList<String> linkList = new ArrayList<>();
            ArrayList<String> titleList = new ArrayList<>();
            linkList.add(pagerFragment.fragments.get(index).webView.getUrl());
            titleList.add(pagerFragment.fragments.get(index).webView.getTitle());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(WEB_LINKS, new Gson().toJson(linkList));
            editor.putString(WEB_TITLE, new Gson().toJson(titleList));
            editor.apply();

            message = "Bookmarked";
        }
    }

    @Override
    public void bookmarksOpened() {
        startActivity(new Intent(this, BookmarksActivity.class));

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