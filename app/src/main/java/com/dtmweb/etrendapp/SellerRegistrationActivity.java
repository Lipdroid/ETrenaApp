package com.dtmweb.etrendapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.dtmweb.etrendapp.apis.ApiClient;
import com.dtmweb.etrendapp.apis.ApiService;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.customViews.CircleImageView;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.SellerObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.ImageUtils;

import org.apache.http.entity.mime.content.FileBody;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SellerRegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    private CorrectSizeUtil mCorrectSize = null;
    private ImageView btn_cross = null;
    private CircleImageView btn_image_selection = null;
    private Button btn_go = null;
    private EditText et_store_name = null;
    private EditText et_password = null;
    private EditText et_password_retype = null;
    private EditText et_bank_name = null;
    private EditText et_bank_account_name = null;
    private EditText et_bank_account_number = null;
    private EditText et_country = null;
    private EditText et_city = null;
    private EditText et_address = null;
    private EditText et_store_owner_name = null;
    private EditText et_store_owner_contact = null;
    private EditText et_mail = null;
    private EditText et_instagram = null;
    private Context mContext = null;
    private static final String TAG = "SellerRegistrationActivity";


    String store_name = null;
    String password = null;
    String password_retype = null;
    String bank_name = null;
    String bank_account_name = null;
    String bank_account_number = null;
    String country = null;
    String city = null;
    String address = null;
    String store_owner_name = null;
    String store_owner_contact = null;
    String email = null;
    String instagram = null;

    private Bitmap pro_img = null;

    public static final int TYPE_UPLOAD_PHOTO = 999;
    private int MY_REQUEST_CODE = 111;
    private SellerObject mUserObj = null;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        afterClickBack();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_registration);
        mContext = this;
        findViews();
        initListenersForViews();
        mCorrectSize = CorrectSizeUtil.getInstance(this);
        mCorrectSize.correctSize();
    }

    private void findViews() {
        btn_cross = (ImageView) findViewById(R.id.btn_cross);
        btn_image_selection = (CircleImageView) findViewById(R.id.btn_image_selection);
        btn_go = (Button) findViewById(R.id.btn_go);
        et_store_name = (EditText) findViewById(R.id.et_store_name);
        et_password = (EditText) findViewById(R.id.et_password);
        et_password_retype = (EditText) findViewById(R.id.et_password_retype);
        et_bank_name = (EditText) findViewById(R.id.et_bank_name);
        et_bank_account_name = (EditText) findViewById(R.id.et_bank_account_name);
        et_bank_account_number = (EditText) findViewById(R.id.et_bank_account_number);
        et_country = (EditText) findViewById(R.id.et_country);
        et_city = (EditText) findViewById(R.id.et_city);
        et_address = (EditText) findViewById(R.id.et_address);
        et_store_owner_name = (EditText) findViewById(R.id.et_store_owner_name);
        et_store_owner_contact = (EditText) findViewById(R.id.et_store_owner_contact);
        et_mail = (EditText) findViewById(R.id.et_mail);
        et_instagram = (EditText) findViewById(R.id.et_instagram);

    }

    private void initListenersForViews() {
        btn_go.setOnClickListener(this);
        btn_cross.setOnClickListener(this);
        btn_image_selection.setOnClickListener(this);
        et_country.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) afterClickCountry();
                return false;
            }
        });
        et_city.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) afterClickCity();
                return false;
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_go:
                afterClickSumbit();
                break;
            case R.id.btn_cross:
                afterClickBack();
                break;
            case R.id.btn_image_selection:
                afterClickImageSelection();
                break;

        }
    }

    private void afterClickCity() {
        moveToChoosenActivity(Constants.TYPE_CITY);
    }

    private void afterClickCountry() {
        moveToChoosenActivity(Constants.TYPE_COUNTRY);
    }

    private void afterClickImageSelection() {
        afterClickImage();
    }

    private void afterClickIntaConnect() {
    }

    private void afterClickBack() {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }

    private void afterClickSumbit() {
        //go to main after checking validity and api call
        checkFieldValidation();
    }

    @SuppressLint("LongLogTag")
    private void checkFieldValidation() {
        store_name = et_store_name.getText().toString();
        password = et_password.getText().toString();
        password_retype = et_password_retype.getText().toString();
        bank_name = et_bank_name.getText().toString();
        bank_account_name = et_bank_account_name.getText().toString();
        bank_account_number = et_bank_account_number.getText().toString();
        country = et_country.getText().toString();
        city = et_city.getText().toString();
        address = et_address.getText().toString();
        store_owner_name = et_store_owner_name.getText().toString();
        store_owner_contact = et_store_owner_contact.getText().toString();
        email = et_mail.getText().toString();
        instagram = et_instagram.getText().toString();

//        if (store_name == null || store_name.equals("")) {
//            Log.e(TAG, "store name input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "store name input is empty", "OK", null);
//            return;
//        } else if (password == null || password.equals("")) {
//            Log.e(TAG, "password input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "password input is empty", "OK", null);
//            return;
//        } else if (password.length() < 8 && !GlobalUtils.isValidPassword(password)) {
//            Log.e(TAG, "password is not Valid(Minimum 8 digit)");
//            GlobalUtils.showInfoDialog(mContext, "Error", "password is not Valid(Minimum 8 digit)", "OK", null);
//            return;
//        } else if (password_retype == null || password_retype.equals("")) {
//            Log.e(TAG, "password retype input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "password retype input is empty", "OK", null);
//            return;
//        } else if (bank_name == null || bank_name.equals("")) {
//            Log.e(TAG, "bank name input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "bank name input is empty", "OK", null);
//            return;
//        } else if (bank_account_name == null || bank_account_name.equals("")) {
//            Log.e(TAG, "bank account name input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "bank account name input is empty", "OK", null);
//            return;
//        } else if (bank_account_number == null || bank_account_number.equals("")) {
//            Log.e(TAG, "bank account number input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "bank account number input is empty", "OK", null);
//            return;
//        } else if (country == null || country.equals("")) {
//            Log.e(TAG, "country input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "country name input is empty", "OK", null);
//            return;
//        } else if (city == null || store_owner_name.equals("")) {
//            Log.e(TAG, "city input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "city name input is empty", "OK", null);
//            return;
//        } else if (store_owner_name == null || address.equals("")) {
//            Log.e(TAG, "store owner name input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "store owner name input is empty", "OK", null);
//            return;
//        } else if (store_owner_contact == null || store_owner_contact.equals("")) {
//            Log.e(TAG, "store owner contact input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "store owner contact input is empty", "OK", null);
//            return;
//        } else if (email == null || email.equals("")) {
//            Log.e(TAG, "email input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "email input is empty", "OK", null);
//            return;
//        } else if (instagram == null || instagram.equals("")) {
//            Log.e(TAG, "instagram input is empty");
//            GlobalUtils.showInfoDialog(mContext, "Error", "instagram input is empty", "OK", null);
//            return;
//        } else if (!password.equals(password_retype)) {
//            Log.e(TAG, "confirm password do not match");
//            GlobalUtils.showInfoDialog(mContext, "Error", "confirm password do not match", "OK", null);
//            return;
//        } else if (pro_img == null) {
//            Log.e(TAG, "missing profile image");
//            GlobalUtils.showInfoDialog(mContext, "Error", "profile picture is missing", "OK", null);
//            return;
//        }

        requestToSighUp();

    }

    private void requestToSighUp() {
        GlobalUtils.showLoadingProgress(mContext);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        try {
            final Map<String, String> requestBody = new HashMap<>();
            requestBody.put("email", "test78668@gmail.com");
            requestBody.put("password1", "456qwert");
            requestBody.put("password2", "456qwert");
            requestBody.put("phone", "dfdsfds");
            requestBody.put("name", "dfdsfsd");
            requestBody.put("instagram", "dfdsfds");
            requestBody.put("is_buyer", "false");
            requestBody.put("is_seller", "true");
            File file = null;
            try {
                Bitmap bm = pro_img;
                file = ImageUtils.bitmapToFile(bm);
            } catch (Exception e) {

            }

            Call<SellerObject> registration = apiService.registerUser(requestBody, file);
            registration.enqueue(new Callback<SellerObject>() {
                @Override
                public void onResponse(Call<SellerObject> call, Response<SellerObject> response) {
                    GlobalUtils.dismissLoadingProgress();
                    if (response.isSuccessful()) {
                        mUserObj = response.body();
                        Log.e("Success", mUserObj.getToken());
                        //store the token
                        GlobalUtils.token = mUserObj.getToken();
                        createStore();

                    } else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            parseErrors(requestBody, jObjError);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<SellerObject> call, Throwable t) {
                    GlobalUtils.dismissLoadingProgress();
                    Log.e("Error ", this.getClass().getSimpleName() + t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createStore() {
        GlobalUtils.showLoadingProgress(mContext);
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        try {
            final Map<String, String> requestBody = new HashMap<>();
            requestBody.put("name", "dsfs");
            requestBody.put("address", "sds");
            requestBody.put("city", "sdsad");
            requestBody.put("country", "asdsad");
            requestBody.put("bank_name", "asdsad");
            requestBody.put("account_name", "sadsad");
            requestBody.put("account_number", "asdasd");

            Call<StoreObject> registration = apiService.registerStore(requestBody, "JWT " + GlobalUtils.token);
            registration.enqueue(new Callback<StoreObject>() {
                @Override
                public void onResponse(Call<StoreObject> call, Response<StoreObject> response) {
                    GlobalUtils.dismissLoadingProgress();
                    if (response.isSuccessful()) {
                        StoreObject storeObject = response.body();
                        Log.e("Success", "");
                        //createSeller();
                        mUserObj.setStoreObject(storeObject);
                        GlobalUtils.showInfoDialog(mContext, "Info", "Registration Complete", "OK", null);
                    } else {
                        JSONObject jObjError = null;
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            parseErrors(requestBody, jObjError);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<StoreObject> call, Throwable t) {
                    GlobalUtils.dismissLoadingProgress();
                    Log.e("Error ", this.getClass().getSimpleName() + t.toString());
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void parseErrors(Map<String, String> requestBody, JSONObject jObjError) {

        for (String key : requestBody.keySet()) {
            if (jObjError.has(key)) {
                String error = null;
                try {
                    error = jObjError.getJSONArray(key).get(0).toString();
                    Log.e("Error ", this.getClass().getSimpleName() + error);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void goToMainPage() {
        Intent i = new Intent(mContext, MainActivity.class);
        i.putExtra(Constants.EXTRA_FROM_CHOOSE_PLAN, Constants.EXTRA_FROM_CHOOSE_PLAN);
        startActivity(i);
    }


    private void moveToChoosenActivity(int type) {

        Intent intent = new Intent(this, ChoosenActivity.class);
        switch (type) {
            case Constants.TYPE_COUNTRY:
                intent.putExtra("extra", Constants.TYPE_COUNTRY);
                break;
            case Constants.TYPE_CITY:
                intent.putExtra("extra", Constants.TYPE_COUNTRY);
                break;
        }

        intent.putExtra(Integer.class.toString(), type);
        startActivityForResult(intent, type);
        overridePendingTransition(R.anim.anim_slide_in_bottom,
                R.anim.anim_slide_out_bottom);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == Constants.TYPE_CITY) {
            Bundle bundle = data.getExtras();
            //WHAT TO DO TO GET THE BUNDLE VALUES//
            String city = bundle.getString("city");
            et_city.setText(city);
        }

        if (requestCode == Constants.TYPE_COUNTRY) {
            Bundle bundle = data.getExtras();
            //WHAT TO DO TO GET THE BUNDLE VALUES//
            String country = bundle.getString("country");
            et_country.setText(country);
        }

        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == TYPE_UPLOAD_PHOTO) {
                InputStream stream = null;
                Bitmap bitmap = null;

                try {
                    Uri uri = data.getData();
                    Bundle bundle = data.getExtras();
                    if (uri == null) {
                        // case nexus device
                        Bitmap imageBitmap = (Bitmap) bundle.get("data");
                        // mImgProfile.setImageBitmap(imageBitmap);
                        bitmap = imageBitmap;
                    } else {
                        stream = mContext.getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(stream);

                        String absPath = ImageUtils.getPath(mContext, uri);
                        bitmap = ImageUtils.rotateBitmap(bitmap, Uri.parse(absPath));
                    }
                    // thumb bitmap
                    int bmHeight = bitmap.getHeight();
                    int bmWith = bitmap.getWidth();

                    float ratio = bmWith * 1.0f / bmHeight;

                    Bitmap thumbBitmap = ImageUtils.getBitmapThumb(bitmap, 1080, Math.round(1080 / ratio));
                    btn_image_selection.setImageBitmap(thumbBitmap);
                    pro_img = thumbBitmap;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void afterClickImage() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {

                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        MY_REQUEST_CODE);
            }
        }
        //start your camera
        Intent intentChooseImage = null;
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        Intent pickIntent = new Intent(Intent.ACTION_PICK);
        pickIntent.setType("image/*");

        if (Build.VERSION.SDK_INT < 19) {
            intentChooseImage = new Intent();
            intentChooseImage.setAction(Intent.ACTION_GET_CONTENT);
            intentChooseImage.setType("image/*");

        } else {
            intentChooseImage = new Intent(Intent.ACTION_GET_CONTENT);
            intentChooseImage.addCategory(Intent.CATEGORY_OPENABLE);
            intentChooseImage.setType("image/*");
        }
        Intent chooserIntent = Intent.createChooser(takePictureIntent, getResources().getString(R.string.dialog_choose_image_title));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{pickIntent});
        startActivityForResult(chooserIntent, TYPE_UPLOAD_PHOTO);
    }
}
