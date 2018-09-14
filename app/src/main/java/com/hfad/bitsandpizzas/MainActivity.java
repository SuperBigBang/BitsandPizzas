package com.hfad.bitsandpizzas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu); //Заполнение меню. Если они есть, то добавляются на панель действий
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider= (ShareActionProvider) menuItem.getActionProvider();
        setIntent("This is example text");
        return super.onCreateOptionsMenu(menu);
    }

    private void setIntent(String text){
        Intent intent= new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
          //      System.out.println("Нажата кнопка действия"); //break не нужны т.к. нужно вернуть булево значение
                Intent intent = new Intent(this,OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
           //     System.out.println("Нажаты настройки");
                return true;
                default: return super.onOptionsItemSelected(item); //если ни один не подходит то выполняется дефолт.
        }
    }
}
