package com.danazone.gomgom.admin.danhsach;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.danazone.gomgom.admin.R;
import com.danazone.gomgom.admin.base.ConnectionUtil;
import com.danazone.gomgom.admin.base.RecyclerViewUtils;
import com.danazone.gomgom.admin.bean.Information;
import com.danazone.gomgom.admin.common.Common;
import com.danazone.gomgom.admin.common.MySingleton;
import com.danazone.gomgom.admin.danhsach.show.ShowInfoActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class InformationActivity extends AppCompatActivity {
    private RecyclerView mRecycleView;
    private EditText mEdtSearch;
    private InformationAdapter mAdapter;
    private List<Information> mUsers;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        mEdtSearch = (EditText) findViewById(R.id.mEdtSearch);
        mRecycleView = (RecyclerView) findViewById(R.id.mRecycleView);
        alertDialog = new SpotsDialog(InformationActivity.this);
        setUpAdapter();
        loadData();
        getTextSearch();
    }

    private void setUpAdapter() {
        RecyclerViewUtils.Create().setUpVertical(this, mRecycleView);
        mUsers = new ArrayList<>();

        mAdapter = new InformationAdapter(this, mUsers, new InformationAdapter.OnUserClickListener() {
            @Override
            public void onClickItem(Information position) {
                Intent intent = new Intent(InformationActivity.this, ShowInfoActivity.class);
                intent.putExtra("user", position);
                startActivity(intent);
            }
        });
        mRecycleView.setAdapter(mAdapter);
    }

    private void loadData() {
        alertDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Common.URL_VIEW_INFORMAITON, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                alertDialog.dismiss();
                try {
                    JSONArray jsonarray = new JSONArray(response);
                    for (int i = 0; i < jsonarray.length(); i++) {
                        Information users = new Information();
                        JSONObject jsonobject = jsonarray.getJSONObject(i);
                        String name = jsonobject.getString("name");
                        String phone = jsonobject.getString("phoneNumber");
                        String typeDriver = jsonobject.getString("typeDriver");
                        String email = jsonobject.getString("email");
                        String birthday = jsonobject.getString("yearOfBirth");
                        String sex = jsonobject.getString("sex");
                        String registerDate = jsonobject.getString("registerDate");
                        String iduser = jsonobject.getString("id");
                        String state = jsonobject.getString("state");
                        String idDeal = jsonobject.getString("idDeal");
                        String licenseNumber = jsonobject.getString("licenseNumber");
                        String expired = jsonobject.getString("expired");
                        String address = jsonobject.getString("address");
                        String identityCard = jsonobject.getString("identityCard");
                        String linkAvatar = jsonobject.getString("linkAvatar");
                        String linkDrivingLicense = jsonobject.getString("linkDrivingLicense");
                        String linkCar = jsonobject.getString("linkCar");
                        String linkVehicleCcertificates = jsonobject.getString("linkVehicleCcertificates");

                        users.setName(name);
                        users.setTypeDriver(typeDriver);
                        users.setPhoneNumber(phone);
                        users.setRegisterDay(registerDate);
                        users.setEmail(email);
                        users.setYearOfBirth(birthday);
                        users.setSex(sex);
                        users.setId(iduser);
                        users.setState(state);
                        users.setIdDeal(idDeal);
                        users.setLicenseNumber(licenseNumber);
                        users.setExpired(expired);
                        users.setIdentityCard(identityCard);
                        users.setAddress(address);
                        users.setLinkCar(linkCar);
                        users.setLinkDrivingLicense(linkDrivingLicense);
                        users.setLinkAvatar(linkAvatar);
                        users.setLinkVehicleCcertificates(linkVehicleCcertificates);
                        mUsers.add(users);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mAdapter.notifyDataSetChanged();
                // mTvView.setText("Thành Viên: " + mUsers.size());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                alertDialog.dismiss();
                Toast.makeText(InformationActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    /**
     * Get text search enter editText
     */
    private void getTextSearch() {
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!ConnectionUtil.isConnected(this)) {
            Toast.makeText(InformationActivity.this, "Vui lòng kiểm tra kết nối internet", Toast.LENGTH_SHORT).show();
            return;
        }
    }
}
