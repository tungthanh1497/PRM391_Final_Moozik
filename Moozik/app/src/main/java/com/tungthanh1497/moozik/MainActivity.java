package com.tungthanh1497.moozik;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding2.support.v7.widget.RxSearchView;
import com.squareup.picasso.Picasso;
import com.tungthanh1497.moozik.adapters.TabPagerMainAdapter;
import com.tungthanh1497.moozik.fragments.MusicTypeFragment;
import com.tungthanh1497.moozik.fragments.OfflineFragment;
import com.tungthanh1497.moozik.fragments.PlayListFragment;
import com.tungthanh1497.moozik.fragments.SearchFragment;
import com.tungthanh1497.moozik.fragments.SongDetailFragment;
import com.tungthanh1497.moozik.managers.MusicManager;
import com.tungthanh1497.moozik.managers.ScreenManager;
import com.tungthanh1497.moozik.models.SongModel;
import com.victor.loading.newton.NewtonCradleLoading;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity {

    public static List<SongModel> songModelList = new ArrayList<>();
    public static FloatingActionButton fabPlayPause;
    public static ImageView ivAvaMini;
    static TextView tvNameMini;
    public static SeekBar sbMini;
    static RelativeLayout rlMini;
    static RotateAnimation animation;

    public static NewtonCradleLoading nclLoading;
    public static ImageView ivLoading;

    boolean doubleBackToExitPressedOnce = false;

    public static boolean isMain = true;
    boolean internetIsOn = true;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savePlayListToSP(getBaseContext());
    }

    @Override
    public void onBackPressed() {
        Log.d("14971497", "onBackPressed: " + getFragmentManager().getBackStackEntryCount());
        Log.d("14971497", "onBackPressed: " + isMain);
        Log.d("14971497", "onBackPressed: " + doubleBackToExitPressedOnce);

        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
//            if (isMain) {
//                if (doubleBackToExitPressedOnce) {
////                if(MusicManager.mediaPlayer!=null){
////                    MusicManager.mediaPlayer.release();
////                    MusicManager.mediaPlayer=null;
////                }
//                    super.onBackPressed();
//                    return;
//                }
//
//                this.doubleBackToExitPressedOnce = true;
//                Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
//
//                new Handler().postDelayed(new Runnable() {
//
//                                              @Override
//                                              public void run() {
//                                                  doubleBackToExitPressedOnce = false;
//                                              }
//                                          }, 2000
//                );
//            } else {
            super.onBackPressed();
            isMain = true;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission granted!", Toast.LENGTH_LONG).show();
                        checkPermission = true;
                    } else {
                        Toast.makeText(this, "No permission granted!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    return;
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        CheckPermission();
//        ActionBar actionBar = getSupportActionBar();
//        // Set below attributes to add logo in ActionBar.
//        actionBar.setDisplayShowHomeEnabled(true);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setLogo(R.drawable.icon_moozik);
//        actionBar.setTitle("Moozik");


        Log.d("14971497", "onCreate: songModelList" + songModelList);
//        clearSP(getBaseContext());
        songModelList = loadSongListFromSP();
        Log.d("14971497", "onCreate: songModelList" + songModelList);

        nclLoading = findViewById(R.id.ncl_loading);
        nclLoading.setLoadingColor(Color.parseColor("#ef4f91"));
        ivLoading = findViewById(R.id.iv_loading);

        rlMini = findViewById(R.id.rl_mini_player);
        rlMini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScreenManager.openFragmentMoveUp(getSupportFragmentManager(), new SongDetailFragment(), R.id.rl_huge_container, getBaseContext());

            }
        });
        ivAvaMini = findViewById(R.id.iv_ava_song_mini);
        tvNameMini = findViewById(R.id.tv_name_song_mini);
        sbMini = findViewById(R.id.sb_seekbar_mini);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fabPlayPause = (FloatingActionButton) findViewById(R.id.fab_play_pause);


        fabPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MusicManager.playPause();
            }
        });
        configureTabLayout();
        checkInternet();
    }

    public void CheckPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            checkPermission = true;
        }
    }

    static public boolean checkPermission = false;
    private static final int MY_PERMISSION_REQUEST = 1;

    private void checkInternet() {
        ReactiveNetwork
                .observeInternetConnectivity()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        Log.d("14971497", "accept: " + aBoolean);
                        if (aBoolean) {
                            if (!internetIsOn) {
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                            }
                        } else {
                            internetIsOn = false;
                            Toast.makeText(MainActivity.this, "Moozik need internet for best performance", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private List<SongModel> loadSongListFromSP() {

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getBaseContext());
        Gson gson = new Gson();
        String json = appSharedPrefs.getString("MyPlayList", "");
//        Student mStudentObject = gson.fromJson(json, Student.class);


        Type type = new TypeToken<List<SongModel>>() {
        }.getType();
        List<SongModel> listTemp = new ArrayList<>();
        if (json != "") {
            listTemp = gson.fromJson(json, type);
        }
        return listTemp;
    }

    private void clearSP(Context context) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        prefsEditor.clear();
        prefsEditor.commit();
    }

    public static void savePlayListToSP(Context context) {
        Type type = new TypeToken<List<SongModel>>() {
        }.getType();
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(songModelList, type);
        prefsEditor.putString("MyPlayList", json);
        prefsEditor.commit();
    }


    public static void updateMiniPlayer(SongModel songModel, Context context) {
        if (songModel.getImgId() != 0) {
            Picasso.with(context).load(songModel.getImgId()).transform(new CropCircleTransformation()).into(ivAvaMini);
        } else {
            Picasso.with(context).load(songModel.getImage()).transform(new CropCircleTransformation()).into(ivAvaMini);
        }
        setRotateAnimation(ivAvaMini);
        tvNameMini.setText(songModel.getTitle() + " - " + songModel.getAuthor());
        rlMini.setVisibility(View.VISIBLE);

    }

    public static void setRotateAnimation(ImageView imageView) {
//        Animation moveUpIn = AnimationUtils.loadAnimation(this, R.anim.anim_move_up_in);
//
//        // set animation when start this activity
//        mediaPlayMusic.startAnimation(moveUpIn);
//        meidaPlayNextMusic.startAnimation(moveUpIn);
//        meidaPlayPreviousMusic.startAnimation(moveUpIn);

        // rotation circle imageview
        animation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(60000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);

        imageView.setAnimation(animation);
    }

    public static void setRotateAnimationSlow(ImageView imageView) {
//        Animation moveUpIn = AnimationUtils.loadAnimation(this, R.anim.anim_move_up_in);
//
//        // set animation when start this activity
//        mediaPlayMusic.startAnimation(moveUpIn);
//        meidaPlayNextMusic.startAnimation(moveUpIn);
//        meidaPlayPreviousMusic.startAnimation(moveUpIn);

        // rotation circle imageview
        animation = new RotateAnimation(
                0, 360,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(120000);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);

        imageView.setAnimation(animation);
    }


    int oldTabPositon = -1;
    TabLayout tabLayout;

    private void configureTabLayout() {
        tabLayout = (TabLayout) findViewById(R.id.tl_main);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_type));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_search));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_star_border));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_lib));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.icon_play_list));


        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager_main);
        final PagerAdapter adapter = new TabPagerMainAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

//        viewPager.setOffscreenPageLimit(5);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                    if (fragment instanceof MusicTypeFragment
                            || fragment instanceof SearchFragment
                            || fragment instanceof OfflineFragment
                            || fragment instanceof PlayListFragment)
                        continue;
                    getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }


                int newTabPositon = tab.getPosition();
                if (newTabPositon == 2) {
                    if (oldTabPositon > newTabPositon) {
                        newTabPositon--;
                    } else {
                        newTabPositon++;
                    }
                }
                viewPager.setCurrentItem(newTabPositon);
                oldTabPositon = newTabPositon;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        tabLayout.getTabAt(4).select();
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        tabStrip.getChildAt(2).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    //    PublishSubject publishSubject;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);


////        SearchView searchView = (SearchView) menuItem.getActionView();
//        MenuItem searchMenu = menu.findItem(R.id.search_view);
//        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchMenu);
//
//        final SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
//        searchAutoComplete.setBackgroundColor(Color.parseColor("#673888"));
//        searchAutoComplete.setTextColor(Color.parseColor("#f3f3f3"));
//        searchAutoComplete.setDropDownBackgroundResource(android.R.color.holo_blue_light);
//
//        String dataArr[] = {"Apple" , "Amazon" , "Amd", "Microsoft", "Microwave", "MicroNews", "Intel", "Intelligence"};
//        ArrayAdapter<String> newsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, dataArr);
//        searchAutoComplete.setAdapter(newsAdapter);
//
//        searchAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long id) {
//                String queryString=(String)adapterView.getItemAtPosition(itemIndex);
//                searchAutoComplete.setText("" + queryString);
//                Toast.makeText(MainActivity.this, "you clicked " + queryString, Toast.LENGTH_LONG).show();
//            }
//        });
//
//        // Below event is triggered when submit search query.
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
//                alertDialog.setMessage("Search keyword is " + query);
//                alertDialog.show();
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//
////        RxSearchView.queryTextChanges(searchView)

        return true;
    }

//    private void setupSearch() {
//        ConnectableObservable<SearchViewQueryTextEvent> searchObs = RxSearchView.queryTextChangeEvents(searchView).publish();
//        searchObs.skip(1)
//                .throttleLast(100, TimeUnit.MILLISECONDS)
//                .debounce(200, TimeUnit.MILLISECONDS)
//                .onBackpressureLatest()
//                .observeOn(AndroidSchedulers.mainThread())
//                .filter(new Func1<SearchViewQueryTextEvent, Boolean>() {
//                    @Override
//                    public Boolean call(SearchViewQueryTextEvent searchViewQueryTextEvent) {
//                        final boolean empty = TextUtils.isEmpty(searchViewQueryTextEvent.queryText());
//                        if (empty) {
//                            //Dont show anything  clear adapter
//                        }
//                        return !empty;
//                    }
//                }).subscribe(new Subscriber<SearchViewQueryTextEvent>() {
//
//            @Override
//            public void onNext(SearchViewQueryTextEvent searchViewQueryTextEvent) {
//                String searchTerm = searchViewQueryTextEvent.queryText().toString();
//                if (!searchViewQueryTextEvent.isSubmitted()) {
//                    submitRecommendationsSearch(searchTerm);
//                }
//            }
//
//            @Override
//            public void onCompleted() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//            }
//        });
//
//        searchObs.subscribe(new Subscriber<SearchViewQueryTextEvent>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(SearchViewQueryTextEvent searchViewQueryTextEvent) {
//                if (searchViewQueryTextEvent.isSubmitted()) {
//                    submitFullSearch(searchTerm);
//                }
//            }
//        });
//
//        Subscription searchSub = searchObs.connect();
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.search_view) {
//            Toast.makeText(this, "searching here", Toast.LENGTH_SHORT).show();
////            ScreenManager.openFragment(getSupportFragmentManager(), new SearchFragment(), R.id.rl_layout_container);
//
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
