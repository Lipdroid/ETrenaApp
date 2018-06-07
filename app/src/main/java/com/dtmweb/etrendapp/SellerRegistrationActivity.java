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
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.constants.UrlConstants;
import com.dtmweb.etrendapp.customViews.CircleImageView;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.models.UserObject;
import com.dtmweb.etrendapp.utils.CorrectSizeUtil;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.ImageUtils;
import com.dtmweb.etrendapp.utils.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

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
    private StoreObject mStoreObj = null;
    private UserObject mUserObj = null;
    private PlaceObject countryObject = null;
    private PlaceObject cityObject = null;

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
        if (countryObject != null)
            moveToChoosenActivity(Constants.TYPE_CITY);
        else
            GlobalUtils.showInfoDialog(mContext, "Info", "Please Select a country first!", "OK", null);
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

        if (store_name == null || store_name.equals("")) {
            Log.e(TAG, "store name input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "store name input is empty", "OK", null);
            return;
        } else if (password == null || password.equals("")) {
            Log.e(TAG, "password input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "password input is empty", "OK", null);
            return;
        } else if (password.length() < 8 && !GlobalUtils.isValidPassword(password)) {
            Log.e(TAG, "password is not Valid(Minimum 8 digit)");
            GlobalUtils.showInfoDialog(mContext, "Error", "password is not Valid(Minimum 8 digit)", "OK", null);
            return;
        } else if (password_retype == null || password_retype.equals("")) {
            Log.e(TAG, "password retype input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "password retype input is empty", "OK", null);
            return;
        } else if (bank_name == null || bank_name.equals("")) {
            Log.e(TAG, "bank name input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "bank name input is empty", "OK", null);
            return;
        } else if (bank_account_name == null || bank_account_name.equals("")) {
            Log.e(TAG, "bank account name input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "bank account name input is empty", "OK", null);
            return;
        } else if (bank_account_number == null || bank_account_number.equals("")) {
            Log.e(TAG, "bank account number input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "bank account number input is empty", "OK", null);
            return;
        } else if (country == null || country.equals("")) {
            Log.e(TAG, "country input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "country name input is empty", "OK", null);
            return;
        } else if (city == null || store_owner_name.equals("")) {
            Log.e(TAG, "city input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "city name input is empty", "OK", null);
            return;
        } else if (store_owner_name == null || address.equals("")) {
            Log.e(TAG, "store owner name input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "store owner name input is empty", "OK", null);
            return;
        } else if (store_owner_contact == null || store_owner_contact.equals("")) {
            Log.e(TAG, "store owner contact input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "store owner contact input is empty", "OK", null);
            return;
        } else if (email == null || email.equals("")) {
            Log.e(TAG, "email input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "email input is empty", "OK", null);
            return;
        } else if (instagram == null || instagram.equals("")) {
            Log.e(TAG, "instagram input is empty");
            GlobalUtils.showInfoDialog(mContext, "Error", "instagram input is empty", "OK", null);
            return;
        } else if (!password.equals(password_retype)) {
            Log.e(TAG, "confirm password do not match");
            GlobalUtils.showInfoDialog(mContext, "Error", "confirm password do not match", "OK", null);
            return;
        } else if (pro_img == null) {
            Log.e(TAG, "missing profile image");
            GlobalUtils.showInfoDialog(mContext, "Error", "profile picture is missing", "OK", null);
            return;
        }

        requestToSighUp();

    }

    private void requestToSighUp() {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_EMAIL, email);
        params.put(Constants.PARAM_PASSWORD1, password);
        params.put(Constants.PARAM_PASSWORD2, password_retype);
        params.put(Constants.PARAM_PHONE, store_owner_contact);
        params.put(Constants.PARAM_STORE_NAME, store_owner_name);
        params.put(Constants.PARAM_INSTAGRAM, instagram);
        params.put(Constants.PARAM_IMG, pro_img);
        params.put(Constants.PARAM_SELLER, "true");
        params.put(Constants.PARAM_BUYER, "false");
        params.put(Constants.PARAM_ADDRESS, address);
        params.put(Constants.PARAM_CITY, city);
        params.put(Constants.PARAM_COUNTRY, country);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_REGISTER_USER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e(TAG, result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("token")) {
                            String token = jsonObject.getString("token");
                            //save the token in preference for furthur api call
                            SharedPreferencesUtils.putString(mContext, Constants.PREF_TOKEN, token);
                            //Parse the User data
                            if (jsonObject.has("user")) {
                                mUserObj = new UserObject();
                                JSONObject userJson = jsonObject.getJSONObject("user");

                                if (userJson.has("id")) {
                                    mUserObj.setUserId(userJson.getString("id"));
                                }
                                if (jsonObject.has("name")) {
                                    mUserObj.setUsername(jsonObject.getString("name"));
                                }
                                if (userJson.has("email")) {
                                    mUserObj.setEmail(userJson.getString("email"));
                                }
                                if (userJson.has("phone")) {
                                    mUserObj.setContact_no(userJson.getString("phone"));
                                }
                                if (userJson.has("image")) {
                                    mUserObj.setPro_img(UrlConstants.BASE_URL + userJson.getString("image"));
                                }
                                if (userJson.has("instagram")) {
                                    mUserObj.setInstagram(userJson.getString("instagram"));
                                }
                                if (userJson.has("address")) {
                                    mUserObj.setAddress(jsonObject.getString("address"));
                                }
                                if (userJson.has("city")) {
                                    mUserObj.setCity(jsonObject.getString("city"));
                                }
                                if (userJson.has("country")) {
                                    mUserObj.setCountry(jsonObject.getString("country"));
                                }

                                mUserObj.setUser_type(false, true);

                            }
                            //save the current user
                            GlobalUtils.saveCurrentUser(mUserObj);

                            //save the user type
                            GlobalUtils.user_type = mUserObj.getUser_type();

                            //create the store
                            createStoreAPI();
                        } else {
                            //parse errors
                            parseErrors(params, jsonObject);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }


            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();

            }

            @Override
            public void onException(Exception e) {

                GlobalUtils.dismissLoadingProgress();

            }
        });

        mRequestAsync.execute();

    }

    private void createStoreAPI() {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_STORE_NAME, store_name);
        params.put(Constants.PARAM_BANK_NAME, bank_name);
        params.put(Constants.PARAM_ACC_NAME, bank_account_name);
        params.put(Constants.PARAM_ACC_NUMBER, bank_account_number);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_CREATE_SELLER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e(TAG, result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("id")) {
                            mStoreObj = new StoreObject();
                            if (jsonObject.has("name")) {
                                mStoreObj.setStore_name(jsonObject.getString("name"));
                            }
                            if (jsonObject.has("cover_photo")) {
                                mStoreObj.setCover_photo(jsonObject.getString("cover_photo"));
                            }
                            if (jsonObject.has("is_active")) {
                                mStoreObj.setIs_Active(jsonObject.getBoolean("is_active"));
                            }
                            if (jsonObject.has("is_subscribed")) {
                                mStoreObj.setIs_subscribed(jsonObject.getBoolean("is_subscribed"));
                            }
                            if (jsonObject.has("bank_name")) {
                                mStoreObj.setBank_name(jsonObject.getString("bank_name"));
                            }
                            if (jsonObject.has("account_name")) {
                                mStoreObj.setBank_acc_name(jsonObject.getString("account_name"));
                            }
                            if (jsonObject.has("account_number")) {
                                mStoreObj.setBank_acc_number(jsonObject.getString("account_number"));
                            }

                            mUserObj.setStoreObject(mStoreObj);

                            //save the current user
                            GlobalUtils.saveCurrentUser(mUserObj);
                            //save the store
                            GlobalUtils.saveCurrentStore(mStoreObj);
                            //show popup success
                            GlobalUtils.showInfoDialog(mContext, "Registration", "The user has been successfully registered.", "OK", new DialogCallback() {
                                @Override
                                public void onAction1() {
                                    afterClickBack();
                                }

                                @Override
                                public void onAction2() {

                                }

                                @Override
                                public void onAction3() {

                                }

                                @Override
                                public void onAction4() {

                                }
                            });

                        } else {
                            //parse errors
                            parseErrors(params, jsonObject);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    GlobalUtils.showInfoDialog(mContext, "Failed", "Something went wrong please try again", "OK", null);

                }


            }

            @Override
            public void progress() {
                GlobalUtils.showLoadingProgress(mContext);
            }

            @Override
            public void onInterrupted(Exception e) {
                GlobalUtils.dismissLoadingProgress();

            }

            @Override
            public void onException(Exception e) {

                GlobalUtils.dismissLoadingProgress();

            }
        });

        mRequestAsync.execute();

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
                intent.putExtra("countryId", countryObject.getId());
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
            cityObject = data.getParcelableExtra(PlaceObject.class.toString());
            et_city.setText(cityObject.getName());
        }

        if (requestCode == Constants.TYPE_COUNTRY) {
            countryObject = data.getParcelableExtra(PlaceObject.class.toString());
            et_country.setText(countryObject.getName());
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

    private void parseErrors(HashMap<String, Object> requestBody, JSONObject jObjError) {
        try {
            for (String key : requestBody.keySet()) {
                if (jObjError.has(key)) {
                    String error = null;
                    error = jObjError.getJSONArray(key).get(0).toString();
                    Log.e("Error ", this.getClass().getSimpleName() + error);
                    GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                    return;
                }
            }
            if (jObjError.has("non_field_errors")) {
                String error = jObjError.getJSONArray("non_field_errors").get(0).toString();
                if (error != null) {
                    GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                    return;
                }
            }

            if (jObjError.has("detail")) {
                String error = jObjError.getString("detail");
                GlobalUtils.showInfoDialog(mContext, "Failed", error, "OK", null);
                return;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
