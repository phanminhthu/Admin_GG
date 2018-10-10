package com.danazone.gomgom.admin;


import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.net.URISyntaxException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ArrayList<thuocTinh> arrayList = new ArrayList<>();
    ListAdapter2 arrayAdapter;
    ListView listView;
    Dialog dialog;
    JSONArray jsonArray;
    Socket socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);


        try {
            socket = IO.socket(getString(R.string.sv));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        if (!socket.connected()) {
            startActivity(new Intent(MainActivity.this, mhToken.class));
            finish();
        }
        startService(new Intent(MainActivity.this, MyService.class));

        try {
            jsonArray = new JSONArray(duLieu.dulieu);
            JSONObject jsonObject;
            String type = "";
            arrayList.clear();
            boolean trungLap = false;
            for (int i = 0; i < jsonArray.length(); i++) {
                Log.d("aaa", " " + i);
                jsonObject = jsonArray.getJSONObject(i);
                switch (jsonObject.getInt("code")) {
                    case 33:
                        type = "Ảnh đại diện";
                        break;
                    case 44:
                        type = "Ảnh xe";
                        break;
                    case 55:
                        type = "Giấy đăng ký xe";
                        break;
                    case 66:
                        type = "Giấy phép lái xe";
                        break;
                    case 77:
                        type = "CMND";
                        break;
                }
                for (int o = 0; o < arrayList.size(); o++) {

                    if (arrayList.get(o).SDT.matches(jsonObject.getString("phoneNumber"))) {
                        arrayList.set(o, new thuocTinh(arrayList.get(o).SDT, arrayList.get(o).NoiDung + ", " + type));
                        trungLap = true;
                    }
                }

                if (trungLap == false) {
                    arrayList.add(new thuocTinh(jsonObject.getString("phoneNumber"), type));
                    Log.d("aaa", "ADD ");
                }
                trungLap = false;
            }

            arrayAdapter = new ListAdapter2(MainActivity.this, R.layout.dong_list_view, arrayList);
            listView.setAdapter(arrayAdapter);


        } catch (JSONException e) {
            tb("Lỗi dữ liệu!");
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dialogXemThongTin(arrayList.get(position).SDT);
            }
        });

        socket.on("adminXacNhan", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                if (args[0].toString().matches("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tb("Xong!");
                        }
                    });

                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tb("Server từ chối!");
                        }
                    });

                }
            }
        });


    }

    void tb(String s) {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_LONG).show();
    }


    void dialogXemThongTin(final String sdtIN) {
        Log.d("aaa", sdtIN);
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xem_thong_tin);


        final TextView ten, sdt, namSinh, cmnd, loaiXe, diaChi, bsx;

        final ImageView avatar, xe, gtx, blc;
        TextView avatarX, xeX, gtxX, blcX;
        ten = dialog.findViewById(R.id.ten);
        sdt = dialog.findViewById(R.id.sdt);
        namSinh = dialog.findViewById(R.id.namSinh);
        cmnd = dialog.findViewById(R.id.cmnd);
        loaiXe = dialog.findViewById(R.id.loaiXe);
        diaChi = dialog.findViewById(R.id.diaChi);
        bsx = dialog.findViewById(R.id.bsx);
        avatar = dialog.findViewById(R.id.avatar);
        xe = dialog.findViewById(R.id.xe);
        gtx = dialog.findViewById(R.id.gtx);
        blc = dialog.findViewById(R.id.blc);
        avatarX = dialog.findViewById(R.id.avatarX);
        xeX = dialog.findViewById(R.id.xeX);
        gtxX = dialog.findViewById(R.id.gtxX);
        blcX = dialog.findViewById(R.id.blcX);


        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                if (jsonArray.getJSONObject(i).getString("phoneNumber").matches(sdtIN)) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("name").isEmpty()) {
                        ten.setBackgroundResource(R.color.colorThayDoi);
                    } else
                        ten.setText(jsonObject.getString("name"));
                    if (jsonObject.getString("phoneNumber").isEmpty()) {
                        sdt.setBackgroundResource(R.color.colorThayDoi);
                    } else
                        sdt.setText(jsonObject.getString("phoneNumber"));

                    if (jsonObject.getString("yearOfBirth").isEmpty()) {
                        namSinh.setBackgroundResource(R.color.colorThayDoi);
                    } else

                        namSinh.setText(jsonObject.getString("yearOfBirth"));

                    if (jsonObject.getString("identityCard").isEmpty()) {
                        cmnd.setBackgroundResource(R.color.colorThayDoi);
                    } else
                        cmnd.setText(jsonObject.getString("identityCard"));


                    switch (jsonObject.getInt("typeDriver")) {
                        case 1:
                            loaiXe.setText("Bán tải");
                            break;
                        case 2:
                            loaiXe.setText("0.5 Tấn");
                            break;
                        case 3:
                            loaiXe.setText("1.4 Tấn");
                            break;
                        case 4:
                            loaiXe.setText("2.5 Tấn");
                            break;
                        default:
                            loaiXe.setBackgroundResource(R.color.colorThayDoi);

                    }

                    if (jsonObject.getString("address").isEmpty()) {
                        diaChi.setBackgroundResource(R.color.colorThayDoi);
                    } else
                        diaChi.setText(jsonObject.getString("address"));

                    if (jsonObject.getString("licensePlates").isEmpty()) {
                        bsx.setBackgroundResource(R.color.colorThayDoi);
                    } else
                        bsx.setText(jsonObject.getString("licensePlates"));


                    if (i == 0) {
                        Picasso.with(MainActivity.this).cancelRequest(avatar);
                        if (jsonObject.getString("linkAvatar").isEmpty()) {
                            avatar.setImageResource(R.drawable.ic_close_black_24dp);
                        } else {
                            Picasso.with(MainActivity.this).load(jsonObject.getString("linkAvatar")).into(avatar);
                        }


                        Picasso.with(MainActivity.this).cancelRequest(xe);
                        if (jsonObject.getString("linkCar").isEmpty()) {
                            xe.setImageResource(R.drawable.ic_close_black_24dp);
                        } else {
                            Picasso.with(MainActivity.this).load(jsonObject.getString("linkCar")).into(xe);
                        }


                        Picasso.with(MainActivity.this).cancelRequest(gtx);
                        if (jsonObject.getString("linkVehicleCcertificates").isEmpty()) {
                            gtx.setImageResource(R.drawable.ic_close_black_24dp);
                        } else {
                            Picasso.with(MainActivity.this).load(jsonObject.getString("linkVehicleCcertificates")).into(gtx);
                        }


                        Picasso.with(MainActivity.this).cancelRequest(blc);
                        if (jsonObject.getString("linkDrivingLicense").isEmpty()) {
                            blc.setImageResource(R.drawable.ic_close_black_24dp);
                        } else {
                            Picasso.with(MainActivity.this).load(jsonObject.getString("linkDrivingLicense")).into(blc);
                        }
                    }


                    switch (jsonObject.getInt("code")) {

                        case 33:
                            avatarX.setBackgroundResource(R.color.colorThayDoi);
                            Picasso.with(MainActivity.this).cancelRequest(avatar);
                            if (jsonObject.getString("linkAvatar").isEmpty()) {
                                avatar.setImageResource(R.drawable.ic_close_black_24dp);
                            } else {
                                Picasso.with(MainActivity.this).load(jsonObject.getString("linkAvatar")).into(avatar);
                            }
                            break;
                        case 44:
                            xeX.setBackgroundResource(R.color.colorThayDoi);
                            Picasso.with(MainActivity.this).cancelRequest(xe);
                            if (jsonObject.getString("linkCar").isEmpty()) {
                                xe.setImageResource(R.drawable.ic_close_black_24dp);
                            } else {
                                Picasso.with(MainActivity.this).load(jsonObject.getString("linkCar")).into(xe);
                            }
                            break;
                        case 55:
                            gtxX.setBackgroundResource(R.color.colorThayDoi);
                            Picasso.with(MainActivity.this).cancelRequest(gtx);
                            if (jsonObject.getString("linkVehicleCcertificates").isEmpty()) {
                                gtx.setImageResource(R.drawable.ic_close_black_24dp);
                            } else {
                                Picasso.with(MainActivity.this).load(jsonObject.getString("linkVehicleCcertificates")).into(gtx);
                            }
                            break;
                        case 66:
                            blcX.setBackgroundResource(R.color.colorThayDoi);
                            Picasso.with(MainActivity.this).cancelRequest(blc);
                            if (jsonObject.getString("linkDrivingLicense").isEmpty()) {
                                blc.setImageResource(R.drawable.ic_close_black_24dp);
                            } else {
                                Picasso.with(MainActivity.this).load(jsonObject.getString("linkDrivingLicense")).into(blc);
                            }

                    }
                }


            } catch (Exception e) {
            }
        }


        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog(avatar);
            }
        });
        xe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog(xe);
            }
        });
        gtx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog(gtx);
            }
        });
        blc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaLog(blc);
            }
        });


        Button TC, CN;
        TC = dialog.findViewById(R.id.tc);
        CN = dialog.findViewById(R.id.xn);


        TC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    Log.d("aaa", "Duyệt: " + i);
                    if (arrayList.get(i).SDT.matches(sdtIN)) {
                        arrayList.remove(i);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
                socket.emit("adminXacNhan", duLieu.token, sdtIN, false);
                dialog.cancel();
            }
        });

        CN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = arrayList.size() - 1; i >= 0; i--) {
                    Log.d("aaa", "Duyệt: " + i);
                    if (arrayList.get(i).SDT.matches(sdtIN)) {
                        arrayList.remove(i);
                    }
                }
                arrayAdapter.notifyDataSetChanged();
                socket.emit("adminXacNhan", duLieu.token, sdtIN, true);
                dialog.cancel();
            }
        });


        dialog.show();

    }


    @Override
    protected void onResume() {
        if (arrayList.size() == 0) {
            tb("Đã hết yêu cầu xác minh!");
        }

        super.onResume();
    }

    @Override
    protected void onDestroy() {
        sqlite db2 = new sqlite(this, "tokenAdmin.sqlite", null, 1);
        //tao bang
        db2.QueryData("CREATE TABLE IF NOT EXISTS chayAn(OK VACHAR)");
        //truy van

        db2.QueryData("UPDATE chayAn SET ok = '0'");
        Log.d("aaa", "onDestroy Main");


        super.onDestroy();
    }


    @Override
    public void onBackPressed() {
        Toast.makeText(MainActivity.this, "Dùng nút đa nhiệm để tắt app!", Toast.LENGTH_SHORT).show();
    }

    private void diaLog(ImageView i) {

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_xemhinh);
        dialog.getWindow().setBackgroundDrawableResource(R.color.bgTrongSuot);
        ImageView imageView = dialog.findViewById(R.id.image);
        try {
            imageView.setImageBitmap(((BitmapDrawable) i.getDrawable()).getBitmap());


            dialog.show();
        } catch (Exception e) {
        }


    }


}
