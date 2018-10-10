package com.danazone.gomgom.admin.danhsach.show;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.danazone.gomgom.admin.R;
import com.danazone.gomgom.admin.bean.Information;
import com.squareup.picasso.Picasso;

public class ShowInfoActivity extends AppCompatActivity {
    private TextView mTvName, mTvPhone, mTvEmail, mTvBirthDay, mTvSex, mTvType, mTvDate, licenseNumber, address, identityCard, expired;
    private Information users;
    private ImageView mImgAvatar, mImgGDK, mImgBLX, mImgLX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);
        mTvName = (TextView) findViewById(R.id.mTvName);
        mTvPhone = (TextView) findViewById(R.id.mTvPhone);
        mTvEmail = (TextView) findViewById(R.id.mTvEmail);
        mTvBirthDay = (TextView) findViewById(R.id.mTvBirthDay);
        mTvSex = (TextView) findViewById(R.id.mTvSex);
        mTvType = (TextView) findViewById(R.id.mTvType);
        mTvDate = (TextView) findViewById(R.id.mTvDate);
        licenseNumber = (TextView) findViewById(R.id.licenseNumber);
        address = (TextView) findViewById(R.id.address);
        identityCard = (TextView) findViewById(R.id.identityCard);
        expired = (TextView) findViewById(R.id.expired);
        mImgBLX = (ImageView) findViewById(R.id.mImgBLX);
        mImgGDK = (ImageView) findViewById(R.id.mImgGDK);
        mImgAvatar = (ImageView) findViewById(R.id.mImgAvatar);
        mImgLX = (ImageView) findViewById(R.id.mImgLX);

        Intent intent = getIntent();
        users = (Information) intent.getSerializableExtra("user");
        mTvName.setText(users.getName());
        mTvPhone.setText(users.getPhoneNumber());
        mTvEmail.setText(users.getEmail());
        mTvBirthDay.setText(users.getYearOfBirth());
        mTvSex.setText(users.getSex());
        mTvType.setText(users.getTypeDriver());
        mTvDate.setText(users.getRegisterDay());
        identityCard.setText(users.getIdentityCard());
        licenseNumber.setText(users.getLicenseNumber());
        expired.setText(users.getExpired());
        address.setText(users.getAddress());
        System.out.println("222222222222222222222" + users.getLinkAvatar());

        Picasso.with(getBaseContext())
                .load(users.getLinkAvatar())
                .placeholder(R.drawable.unknown_avatar)
                .error(R.drawable.unknown_avatar)
                .into(mImgAvatar);


        Picasso.with(getBaseContext())
                .load(users.getLinkVehicleCcertificates())
                .placeholder(R.drawable.unknown_avatar)
                .error(R.drawable.unknown_avatar)
                .into(mImgGDK);


        Picasso.with(getBaseContext())
                .load(users.getLinkDrivingLicense())
                .placeholder(R.drawable.unknown_avatar)
                .error(R.drawable.unknown_avatar)
                .into(mImgBLX);

        Picasso.with(getBaseContext())
                .load(users.getLinkCar())
                .placeholder(R.drawable.unknown_avatar)
                .error(R.drawable.unknown_avatar)
                .into(mImgLX);


    }
}
