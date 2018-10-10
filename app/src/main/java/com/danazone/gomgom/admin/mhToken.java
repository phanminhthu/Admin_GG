package com.danazone.gomgom.admin;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.danazone.gomgom.admin.danhsach.InformationActivity;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

public class mhToken extends AppCompatActivity {
    private Socket socket;
    private sqlite db;
    private EditText editText;
    private Button button;
    private String s = "";
    private Button mBtDanhSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mh_token);
        editText = findViewById(R.id.token);
        button = findViewById(R.id.ok);
        mBtDanhSach = findViewById(R.id.mBtDanhSach);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();

        final Notification.Builder notif = new Notification.Builder(getApplicationContext())
                .setContentTitle("ÁDsá")
                .setContentText("dfgsg")
                .setSmallIcon(R.drawable.ic_logo_mini) //ok
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_mini));
        notif.setDefaults(Notification.DEFAULT_VIBRATE);
        notificationManager.notify(2, notif.build());

        try {
            socket = IO.socket(getString(R.string.sv));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (!socket.connected()) {
            socket.connect();
        }
        //data base
        db = new sqlite(this, "tokenAdmin.sqlite", null, 1);
        //tao bang
        db.QueryData("CREATE TABLE IF NOT EXISTS token(TK VACHAR)");
        //truy van
        Cursor contro = db.GetData("SELECT * FROM token");

        while (contro.moveToNext()) {
            editText.setText(contro.getString(0));
        }

        sqlite db2 = new sqlite(this, "tokenAdmin.sqlite", null, 1);
        //tao bang
        db2.QueryData("CREATE TABLE IF NOT EXISTS chayAn(OK VACHAR)");
        //truy van

        Cursor contro2 = db2.GetData("SELECT * FROM chayAn");
        if (contro2.getCount() == 0) {
            db2.QueryData("INSERT INTO chayAn (ok) VALUES ('1')");
        } else {
            db2.QueryData("UPDATE chayAn SET ok = '1'");
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().isEmpty()) {
                    Toast.makeText(mhToken.this, "Nhập gì đó đi bạn ơi!", Toast.LENGTH_LONG).show();
                } else {
                    button.setVisibility(View.INVISIBLE);
                    s = editText.getText().toString();
                    socket.emit("adminDangNhap", editText.getText().toString());
                }
            }
        });

        mBtDanhSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mhToken.this, InformationActivity.class));
                finish();
            }
        });

        socket.on("adminDangNhap", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args[0].toString().matches("true")) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mhToken.this, "Xin chào admin!", Toast.LENGTH_LONG).show();
                        }
                    });

                    duLieu.dulieu = args[1].toString();
                    Log.d("aaa", duLieu.dulieu);
                    db.QueryData("DELETE FROM token");
                    db.QueryData("INSERT INTO token (TK) VALUES ('" + s + "')");
                    duLieu.token = s;
                    startActivity(new Intent(mhToken.this, MainActivity.class));
                    finish();
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mhToken.this, "Sai token rồi bạn ơi!", Toast.LENGTH_LONG).show();

                            button.setVisibility(View.VISIBLE);
                        }
                    });

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Toast.makeText(mhToken.this, "Dùng nút đa nhiệm để tắt app!", Toast.LENGTH_SHORT).show();
    }
}
