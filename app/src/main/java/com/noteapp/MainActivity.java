package com.noteapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.noteapp.databinding.ActivityMainBinding;
import com.noteapp.security.PasswordManager;

import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PasswordManager.setPasswordCheck()) {
                    Snackbar.make(view, "Please, set application password", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                    showCustomDialog();

                }
                else{

                }
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                                                               .setOpenableLayout(drawer)
                                                               .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    void showCustomDialog() {
        final Dialog dialog = new Dialog(MainActivity.this);
        //We have added a title in the custom layout. So let's disable the default title.
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //The user will be able to cancel the dialog bu clicking anywhere outside the dialog.
        dialog.setCancelable(true);
        //Mention the name of the layout of your custom dialog.
        dialog.setContentView(R.layout.set_pass_dialog);

        //Initializing the views of the dialog.
        final EditText pass_text = dialog.findViewById(R.id.textPass);
        final CheckBox show_pass = dialog.findViewById(R.id.show_pass);
        Button submitButton = dialog.findViewById(R.id.submit_button);

        show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show_pass.isChecked()){
                    pass_text.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else {
                    pass_text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                String password = pass_text.getText().toString();
                try {
                    PasswordManager.createPasswordFile(password);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }




}