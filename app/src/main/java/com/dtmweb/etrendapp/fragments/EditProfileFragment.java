package com.dtmweb.etrendapp.fragments;


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
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dtmweb.etrendapp.ChoosenActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.constants.UrlConstants;
import com.dtmweb.etrendapp.customViews.CircleImageView;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.models.UserObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.ImageUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.dtmweb.etrendapp.utils.SharedPreferencesUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfileFragment extends Fragment {
    public static final int TYPE_UPLOAD_PHOTO = 999;
    private int MY_REQUEST_CODE = 111;
    private PlaceObject countryObject = null;
    private PlaceObject cityObject = null;
    private UserObject mUserObj = null;
    private Bitmap pro_img = null;
    private ImageView btn_image_selection = null;
    private Context mContext = null;

    private EditText et_store_name = null;
    private EditText et_bank_name = null;
    private EditText et_bank_account_name = null;
    private EditText et_bank_account_number = null;
    private EditText et_country = null;
    private EditText et_city = null;
    private EditText et_address = null;
    private EditText et_name = null;
    private EditText et_contact = null;
    private EditText et_instagram = null;

    String store_name = null;
    String bank_name = null;
    String bank_account_name = null;
    String bank_account_number = null;
    String country = null;
    String city = null;
    String address = null;
    String name = null;
    String contact = null;
    String instagram = null;
    private StoreObject mStoreObj = null;
    private EditText et_full_name = null;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = null;
        mContext = getActivity();
        switch (GlobalUtils.user_type) {
            case Constants.CATEGORY_BUYER:
                root = inflater.inflate(R.layout.activity_buyer_registration, container, false);
                ImageView btn_cross = (ImageView) root.findViewById(R.id.btn_cross);
                btn_image_selection = (ImageView) root.findViewById(R.id.btn_image_selection);
                Button btn_go = (Button) root.findViewById(R.id.btn_go);
                LinearLayout pass_layout = (LinearLayout) root.findViewById(R.id.pass_layout);
                RelativeLayout header = (RelativeLayout) root.findViewById(R.id.header);
                et_name = (EditText) root.findViewById(R.id.et_full_name);
                EditText et_password = (EditText) root.findViewById(R.id.et_password);
                EditText et_password_retype = (EditText) root.findViewById(R.id.et_password_retype);
                et_country = (EditText) root.findViewById(R.id.et_country);
                et_city = (EditText) root.findViewById(R.id.et_city);
                et_address = (EditText) root.findViewById(R.id.et_address);
                et_contact = (EditText) root.findViewById(R.id.et_contact);
                EditText et_user_name = (EditText) root.findViewById(R.id.et_user_name);
                EditText et_mail = (EditText) root.findViewById(R.id.et_mail);

                //set up the views
                mUserObj = GlobalUtils.getCurrentUser();
                if (mUserObj != null) {
                    //hide the password field
                    et_password_retype.setVisibility(View.GONE);
                    et_password.setVisibility(View.GONE);
                    btn_cross.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    pass_layout.setVisibility(View.GONE);
                    //mail can not be editable
                    et_mail.setEnabled(false);
                    //set the valuse to the view
                    et_name.setText(mUserObj.getUsername());
                    et_address.setText(mUserObj.getAddress());
                    et_city.setText(mUserObj.getCity());
                    et_country.setText(mUserObj.getCountry());
                    et_mail.setText(mUserObj.getEmail());
                    et_contact.setText(mUserObj.getContact_no());
                    et_user_name.setText(mUserObj.getUsername());
                    btn_go.setText("UPDATE");
                    btn_go.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //update the buyer
                            updateProfile();
                        }
                    });
                    et_city.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            afterClickCity();
                        }
                    });
                    et_country.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            afterClickCountry();
                        }
                    });
                }
                break;
            case Constants.CATEGORY_SELLER:
                root = inflater.inflate(R.layout.activity_seller_registration, container, false);
                btn_cross = (ImageView) root.findViewById(R.id.btn_cross);
                header = (RelativeLayout) root.findViewById(R.id.header);
                pass_layout = (LinearLayout) root.findViewById(R.id.pass_layout);
                btn_image_selection = (CircleImageView) root.findViewById(R.id.btn_image_selection);
                btn_go = (Button) root.findViewById(R.id.btn_go);
                et_store_name = (EditText) root.findViewById(R.id.et_store_name);
                et_password = (EditText) root.findViewById(R.id.et_password);
                et_password_retype = (EditText) root.findViewById(R.id.et_password_retype);
                et_bank_name = (EditText) root.findViewById(R.id.et_bank_name);
                et_bank_account_name = (EditText) root.findViewById(R.id.et_bank_account_name);
                et_bank_account_number = (EditText) root.findViewById(R.id.et_bank_account_number);
                et_country = (EditText) root.findViewById(R.id.et_country);
                et_city = (EditText) root.findViewById(R.id.et_city);
                et_address = (EditText) root.findViewById(R.id.et_address);
                et_name = (EditText) root.findViewById(R.id.et_store_owner_name);
                et_contact = (EditText) root.findViewById(R.id.et_store_owner_contact);
                et_mail = (EditText) root.findViewById(R.id.et_mail);
                et_instagram = (EditText) root.findViewById(R.id.et_instagram);

                //set up the views
                mUserObj = GlobalUtils.getCurrentUser();
                if (mUserObj != null) {
                    //hide the password field
                    et_password_retype.setVisibility(View.GONE);
                    et_password.setVisibility(View.GONE);
                    btn_cross.setVisibility(View.GONE);
                    header.setVisibility(View.GONE);
                    pass_layout.setVisibility(View.GONE);
                    //mail can not be editable
                    et_mail.setEnabled(false);
                    //set the valuse to the view
                    et_address.setText(mUserObj.getAddress());
                    et_city.setText(mUserObj.getCity());
                    et_country.setText(mUserObj.getCountry());
                    et_mail.setText(mUserObj.getEmail());
                    et_contact.setText(mUserObj.getContact_no());
                    et_name.setText(mUserObj.getUsername());
                    et_instagram.setText(mUserObj.getInstagram());
                    mStoreObj = mUserObj.getStoreObject();
                    if (mStoreObj != null) {
                        setUpStoreView();

                    }else{
                        //get the store
                        requestToGetStoreInfo();
                    }

                    Picasso.get()
                            .load(mUserObj.getPro_img())
                            .placeholder(R.color.common_gray)
                            .error(R.color.common_gray)
                            .into(btn_image_selection);
                }
                btn_go.setText("UPDATE");
                btn_image_selection.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        afterClickImage();
                    }
                });
                et_city.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        afterClickCity();
                    }
                });
                et_country.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        afterClickCountry();
                    }
                });
                btn_go.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //update the seller
                        updateProfile();
                    }
                });
                break;
            case Constants.CATEGORY_NON_LOGGED:
                root = inflater.inflate(R.layout.fragment_edit_profile, container, false);
                break;
        }

        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void updateProfile() {
        if (et_store_name != null) {
            store_name = et_store_name.getText().toString();
        }
        if (et_bank_name != null) {
            bank_name = et_bank_name.getText().toString();
        }
        if (et_bank_account_name != null) {
            bank_account_name = et_bank_account_name.getText().toString();
        }
        if (et_bank_account_number != null) {
            bank_account_number = et_bank_account_number.getText().toString();
        }
        if (et_country != null) {
            country = et_country.getText().toString();
        }
        if (cityObject != null) {
            city = cityObject.getId();
        }
        if (et_address != null) {
            address = et_address.getText().toString();
        }
        if (et_name != null) {
            name = et_name.getText().toString();
        }
        if (et_contact != null) {
            contact = et_contact.getText().toString();
        }
        if (et_instagram != null) {
            instagram = et_instagram.getText().toString();
        }

        requestToUpdate();
    }

    private void afterClickCity() {
        if (countryObject != null)
            moveToChoosenActivity(Constants.TYPE_CITY);
        else
            GlobalUtils.showInfoDialog(getActivity(), "Info", "Please Select a country first!", "OK", null);
    }

    private void afterClickCountry() {
        moveToChoosenActivity(Constants.TYPE_COUNTRY);
    }

    private void moveToChoosenActivity(int type) {

        Intent intent = new Intent(getActivity(), ChoosenActivity.class);
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
        getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom,
                R.anim.anim_slide_out_bottom);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                        stream = getActivity().getContentResolver().openInputStream(data.getData());
                        bitmap = BitmapFactory.decodeStream(stream);

                        String absPath = ImageUtils.getPath(getActivity(), uri);
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
            if (getActivity().checkSelfPermission(Manifest.permission.CAMERA)
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


    private void requestToUpdate() {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PHONE, contact);
        params.put(Constants.PARAM_STORE_NAME, name);
        params.put(Constants.PARAM_INSTAGRAM, instagram);
        params.put(Constants.PARAM_IMG, pro_img);
        params.put(Constants.PARAM_ADDRESS, address);
        if (cityObject != null) {
            params.put(Constants.PARAM_CITY, city);
        }
        params.put(Constants.PARAM_COUNTRY, country);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_UPDATE_USER, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Update profile", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("id")) {
                            //Parse the User data
                            mUserObj = new UserObject();
                            JSONObject userJson = jsonObject;

                            if (userJson.has("id")) {
                                mUserObj.setUserId(userJson.getString("id"));
                            }
                            if (userJson.has("name")) {
                                mUserObj.setUsername(userJson.getString("name"));
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
                                mUserObj.setAddress(userJson.getString("address"));
                            }
                            if (userJson.has("city")) {
                                JSONObject jsonCity = userJson.getJSONObject("city");
                                if (jsonCity.has("name")) {
                                    mUserObj.setCity(jsonCity.getString("name"));
                                }
                                if (jsonCity.has("country")) {
                                    JSONObject jsonCountry = jsonCity.getJSONObject("country");
                                    if (jsonCountry.has("name")) {
                                        mUserObj.setCountry(jsonCountry.getString("name"));
                                    }

                                }
                            }

                            if (userJson.has("is_subscribed")) {
                                if (!userJson.isNull("is_subscribed"))
                                    mUserObj.setIs_subscribed(userJson.getBoolean("is_subscribed"));
                                else {
                                    //store is not created yet
                                }
                            }

                            //set the type
                            Boolean is_seller = false;
                            Boolean is_buyer = false;
                            if (userJson.has("is_buyer")) {
                                is_buyer = userJson.getBoolean("is_buyer");
                            }
                            if (userJson.has("is_seller")) {
                                is_seller = userJson.getBoolean("is_seller");
                            }


                            mUserObj.setUser_type(is_buyer, is_seller);


                            //save the current user
                            GlobalUtils.saveCurrentUser(mUserObj);

                            //save the user type
                            GlobalUtils.user_type = mUserObj.getUser_type();

                            if (mUserObj.getUser_type().equals(Constants.CATEGORY_SELLER)) {
                                //create the store
                                updateStoreAPI();
                            } else {
                                GlobalUtils.showInfoDialog(mContext, "Update", "The user has been successfully updated.", "OK", new DialogCallback() {
                                    @Override
                                    public void onAction1() {

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

                            }
                        } else {
                            //parse errors
                            parseErrors(params, jsonObject);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else

                {
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

    private void updateStoreAPI() {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_STORE_NAME, store_name);
        params.put(Constants.PARAM_BANK_NAME, bank_name);
        params.put(Constants.PARAM_ACC_NAME, bank_account_name);
        params.put(Constants.PARAM_ACC_NUMBER, bank_account_number);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_UPDATE_STORE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Update Store", result);
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
                            GlobalUtils.showInfoDialog(mContext, "Update", "The user has been successfully updated.", "OK", new DialogCallback() {
                                @Override
                                public void onAction1() {

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
    private void requestToGetStoreInfo() {
        final HashMap<String, Object> params = new HashMap<String, Object>();

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_STORE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Store API", result);
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

                            setUpStoreView();
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

    private void setUpStoreView() {
        et_bank_account_name.setText(mStoreObj.getBank_acc_name());
        et_bank_account_number.setText(mStoreObj.getBank_acc_number());
        et_bank_name.setText(mStoreObj.getBank_name());
        et_store_name.setText(mStoreObj.getStore_name());
    }


}

