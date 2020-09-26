package com.example.todobykaustubh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Base64;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class seting extends AppCompatActivity {
    EditText edittext;
    ImageView imgview;
    Bitmap bitmap;
    Switch aSwitch;

    protected void hey() {
        SharedPreferences getshared = getSharedPreferences("info",MODE_PRIVATE);
        String username=getshared.getString("username",null);
        String image = getshared.getString("image",null);
        if(username!=null)
        {
            edittext.setText(username);
        }
        if(image!=null)
        {
            byte[] decodedString = Base64.decode(image,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),decodedByte);
            roundedBitmapDrawable.setCircular(true);
            imgview.setImageDrawable(roundedBitmapDrawable);
        }
        SharedPreferences getsharedswitch = getSharedPreferences("nightMode",MODE_PRIVATE);
        int anInt=getsharedswitch.getInt("is night",-1);
        if(anInt==-1){
            PowerManager powerManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
            int nightModeFlags=getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                aSwitch.setChecked(true);
                aSwitch.setText("Light Mode");
            }
            else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP && powerManager.isPowerSaveMode())
            {
                aSwitch.setChecked(true);
                aSwitch.setText("Light Mode");
            }
        }
        else if(anInt==1){
            aSwitch.setChecked(true);
            aSwitch.setText("Get Your Light mode Back");
        }
        else if(anInt==0){
            aSwitch.setText("Get your Dark Mode Back");
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seting);
        edittext=findViewById(R.id.editText3);
        imgview=findViewById(R.id.imageView4);
        bitmap = ((BitmapDrawable)imgview.getDrawable()).getBitmap();
        aSwitch=findViewById(R.id.switch1);
        hey();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode== RESULT_OK && requestCode ==1)
        {
            try{
                InputStream inputstream = getContentResolver().openInputStream(data.getData());
                bitmap = BitmapFactory.decodeStream(inputstream);
                RoundedBitmapDrawable roundedBitmapDrawable= RoundedBitmapDrawableFactory.create(getResources(),bitmap);
                roundedBitmapDrawable.setCircular(true);
                imgview.setImageDrawable(roundedBitmapDrawable);
                Toast.makeText(this, "File Selected successfully... click on Save and then reopen the app", Toast.LENGTH_LONG).show();
            }
            catch (FileNotFoundException e){
                e.printStackTrace();
                Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
            }
        }
    }
    public void onChanged(View view){
        boolean isChecked=aSwitch.isChecked();
        if(isChecked)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            aSwitch.setText("Get back to Light Mode");
            SharedPreferences shrd = getSharedPreferences("nightMode",MODE_PRIVATE);
            SharedPreferences.Editor editor = shrd.edit();
            editor.putInt("is night",1);
            editor.apply();
            recreate();
        }
        else {
            //int nightModeFlags=getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
            PowerManager powerManager=(PowerManager) getSystemService(Context.POWER_SERVICE);
            /*if (nightModeFlags == Configuration.UI_MODE_NIGHT_YES) {
                AlertDialog alertDialog=new AlertDialog.Builder(seting.this).create();
                alertDialog.setTitle("Device night mode is on");
                alertDialog.setIcon(R.drawable.hook);
                alertDialog.setMessage("your device's night mode is on. So, You can not switch back to light mode");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alertDialog.show();
            }*/
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP && powerManager.isPowerSaveMode())
            {
                showMyDialog();
            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                aSwitch.setText("Get Back to darkmode");
                SharedPreferences shrd = getSharedPreferences("nightMode",MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putInt("is night",0);
                editor.apply();
                recreate();
            }
        }
    }
    private void showMyDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(seting.this);
        builder.setIcon(R.drawable.hook);
        builder.setTitle("Device is on Power saving mode");
        builder.setMessage("Your device is currently running on power saver mode\nIt is highly recommended to use the app in dark mode");
        builder.setPositiveButton("Apply anyway", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                aSwitch.setText("Get Back to darkmode");
                SharedPreferences shrd = getSharedPreferences("nightMode",MODE_PRIVATE);
                SharedPreferences.Editor editor = shrd.edit();
                editor.putInt("is night",0);
                editor.apply();
                recreate();
            }
        });
        builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                aSwitch.setChecked(true);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void editImage(View view){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Pick an image"),1);
    }

    public void changeUser(View view)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] byteArray =byteArrayOutputStream.toByteArray();
        String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
        String msg=edittext.getText().toString();
        SharedPreferences shrd = getSharedPreferences("info",MODE_PRIVATE);
        SharedPreferences.Editor editor = shrd.edit();
        editor.putString("username",msg);
        editor.putString("image",encoded);
        editor.apply();
        edittext.setText(msg);
        Toast.makeText(this, "Saved Succesfully", Toast.LENGTH_LONG).show();
    }
}
