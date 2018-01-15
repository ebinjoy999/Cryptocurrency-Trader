package com.robotrader.ebinjoy999.robotrader;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.robotrader.ebinjoy999.robotrader.adapter.AdapterRoboLogs;
import com.robotrader.ebinjoy999.robotrader.model.SymbolDetails;
import com.robotrader.ebinjoy999.robotrader.adapter.AdapterNavRecyclerView;
import com.robotrader.ebinjoy999.robotrader.service.MarketTickerWatcher;
import com.robotrader.ebinjoy999.robotrader.service.TraderMainService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import exchange.setup.CustomDialogClass;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.switchEnableTrade) SwitchCompat switchEnableTrade;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.spinner) Spinner spinnerExchange;
    Intent intent;
    TraderReceiver traderReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        traderReceiver = new TraderReceiver();

        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction(MainActivity.TRADE_RECEIVER_LOGS);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MainActivity.TRADE_RECEIVER_PRICE);


        registerReceiver(traderReceiver, intentFilter1);
        registerReceiver(traderReceiver, intentFilter);


        intent = new Intent(this, TraderMainService.class);
        setSupportActionBar(toolbar);
        fab.setVisibility(View.GONE);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        setUPDrawer();
        setListners();
        setUpBottumSheetLog();
    }




    private void setListners() {
        switchEnableTrade.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    startService(intent);
                }else {
                    stopService(intent);
                }
            }
        });

        spinnerExchange.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String spinnerCurrentExchange = adapterView.getSelectedItem().toString();
                String [] exchanges = (getResources().getStringArray(R.array.providers));
                if (spinnerCurrentExchange.equalsIgnoreCase(exchanges[1])){ //Bitfinex

                    CustomDialogClass cdd=new CustomDialogClass(MainActivity.this);
                    cdd.setCancelable(false);
                    cdd.setExchange(spinnerCurrentExchange);
                    cdd.show();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                String spinnerCurrentExchange = adapterView.getSelectedItem().toString();
                int a = 10;
            }
        });


        if(isMyServiceRunning(TraderMainService.class))
            switchEnableTrade.setChecked(true);
    }

    RecyclerView recyclerViewNavigationView;
    AdapterNavRecyclerView adapterNavRecyclerView;
    TextView textViewUpdated;
    private void setUPDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        recyclerViewNavigationView = navigationView.findViewById(R.id.nav_drawer_recycler_view);
        textViewUpdated = navigationView.findViewById(R.id.textView);

        recyclerViewNavigationView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewNavigationView.setLayoutManager(layoutManager);
        adapterNavRecyclerView = new AdapterNavRecyclerView(MainActivity.this);
        recyclerViewNavigationView.setAdapter(adapterNavRecyclerView);
    }

    RecyclerView recyclerViewLogs;
    AdapterRoboLogs adapterRoboLogs;
    Switch switchAutoscroll;
    private void setUpBottumSheetLog() {
        LinearLayout llBottomSheet = findViewById(R.id.bottom_sheet);
        switchAutoscroll = findViewById(R.id.switch2);
        recyclerViewLogs = findViewById(R.id.recyclerViewLogs);
        recyclerViewLogs.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewLogs.setLayoutManager(layoutManager);
        adapterRoboLogs = new AdapterRoboLogs(MainActivity.this);
        recyclerViewLogs.setAdapter(adapterRoboLogs);

//        // change the state of the bottom sheet
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
//        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//        BottomSheetBehavior.BottomSheetCallback() {
//            @Override
//            public void onStateChanged(@NonNull View bottomSheet, int newState) {
//
//            }
//
//            @Override
//            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
//
//            }
//        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    public interface INavigation {

        void onViewClick(int position);

        void onIconClick(int position);
    }


    public static final String TRADE_RECEIVER_PRICE = "TRADE_RECEIVER_PRICE";
    public static final String TRADE_RECEIVER_LOGS = "TRADE_RECEIVER_LOGS";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private class TraderReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context arg0, Intent intentE) {
            // TODO Auto-generated method stub
            switch( intentE.getAction()){
                case TRADE_RECEIVER_PRICE:
                    HashMap<String, SymbolDetails> symbolDetails = new HashMap<>();
                    symbolDetails = (HashMap<String, SymbolDetails>)intentE.getSerializableExtra(MarketTickerWatcher.KEY_SYMBOL_DETAILS);
                    if(symbolDetails!=null && symbolDetails.size()>0 && recyclerViewNavigationView!=null){
                        adapterNavRecyclerView.setSymbolsList(symbolDetails);
                        adapterNavRecyclerView.notifyDataSetChanged();
                        Calendar c = Calendar.getInstance();
                        textViewUpdated.setText(sdf.format(c.getTime()));
                    }
                    break;

                case TRADE_RECEIVER_LOGS:
                    ArrayList<String> logList = new ArrayList<>();
                    logList =  (ArrayList<String>) intentE.getSerializableExtra(MarketTickerWatcher.KEY_LOGS);
                    if(logList!=null && logList.size()>0 && recyclerViewLogs!=null){
                        adapterRoboLogs.setLogList(logList);
                        adapterRoboLogs.notifyDataSetChanged();
                        if(switchAutoscroll.isChecked()){
                            recyclerViewLogs.smoothScrollToPosition(logList.size());
                        }
                    }
                    break;

            }

        }

    }
}
