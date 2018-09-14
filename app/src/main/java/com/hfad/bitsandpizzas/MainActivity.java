package com.hfad.bitsandpizzas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu); //Заполнение меню. Если они есть, то добавляются на панель действий
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                System.out.println("Нажата кнопка действия"); break;
            case R.id.action_settings:
                System.out.println("Нажаты настройки"); break;
        }
        return super.onOptionsItemSelected(item);
    }
}
