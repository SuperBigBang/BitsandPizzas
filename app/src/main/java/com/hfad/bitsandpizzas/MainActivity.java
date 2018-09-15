package com.hfad.bitsandpizzas;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {
    private String[] titles;
    private ListView drawerList;
    private DrawerLayout drawerLayout;
private ActionBarDrawerToggle drawerToggle;
    private ShareActionProvider shareActionProvider;
    private int currentPosition=0;

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }
    private void setActionBarTitle(int position){
        String title;
        if (position==0) {title=getResources().getString(R.string.app_name);}
        else {title=titles[position];}
        getActionBar().setTitle(title);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles)); //simple_list_item_activated_1 этот режим обозначает, что вариано на котором щёлкнул пользователь, выделяется подсветкой
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        if (savedInstanceState==null) {selectItem(0);}
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); //Заполнение меню. Если они есть, то добавляются на панель действий
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {return true;}
        switch (item.getItemId()) {
            case R.id.action_create_order:
                //      System.out.println("Нажата кнопка действия"); //break не нужны т.к. нужно вернуть булево значение
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                //     System.out.println("Нажаты настройки");
                return true;
            default:
                return super.onOptionsItemSelected(item); //если ни один не подходит то выполняется дефолт.
        }
           }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState(); //Синхронизировать состояние выключателя после onRestoreStateInstance
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig); //Чтобы сведния о изменении конфигурации передавались в ActionBarDrawerToggle
    }
}
