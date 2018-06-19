package com.example.codex_pc.inventoryapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<NavItem> list;
    NavigationAdapter adapter;
    ListView navList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navList = findViewById(R.id.navList);

        list = new ArrayList<>();
        list.add(new NavItem("Products Store","All the products and goods that are currently in your possession",R.mipmap.store_cart));
        list.add(new NavItem("Suppliers","All the Suppliers that are working with you as of right now",R.mipmap.supplier_icon));
        list.add(new NavItem("Customers","All the Customers that are presently leveraging your services",R.mipmap.customer_icon));
        list.add(new NavItem("Transactions","All the movements of your products organized in one place", R.mipmap.transaction_icon));
        adapter = new NavigationAdapter(this, list);
        navList.setAdapter(adapter);

    }

    //==============================================================================================

        //Menu Details

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sign_out:
                SignOut();
                return true;
            case R.id.sign_out_and_delete_account:
                DeleteAccountAndSignOut();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //==============================================================================================

        //Signing Out or Deleting Account

    public void SignOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Log.d("MainActivity","Signed Out");
                    }
                });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }

    public void DeleteAccountAndSignOut() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        Log.d("LoginPage","Signed Out and Deleted Account");
                    }
                });


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        }, 1000);
    }

    //==============================================================================================
}
