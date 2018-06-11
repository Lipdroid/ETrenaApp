package com.dtmweb.etrendapp.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
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
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.customViews.CircleImageView;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.models.UserObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.ImageUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
    private EditText et_city = null;
    private EditText et_country = null;
    private Bitmap pro_img = null;
    private ImageView btn_image_selection = null;

    public EditProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = null;

        switch (GlobalUtils.user_type) {
            case Constants.CATEGORY_BUYER:
                root = inflater.inflate(R.layout.activity_buyer_registration, container, false);
                ImageView btn_cross = (ImageView) root.findViewById(R.id.btn_cross);
                btn_image_selection = (ImageView) root.findViewById(R.id.btn_image_selection);
                Button btn_go = (Button) root.findViewById(R.id.btn_go);
                LinearLayout pass_layout = (LinearLayout) root.findViewById(R.id.pass_layout);
                RelativeLayout header = (RelativeLayout) root.findViewById(R.id.header);
                EditText et_full_name = (EditText) root.findViewById(R.id.et_full_name);
                EditText et_password = (EditText) root.findViewById(R.id.et_password);
                EditText et_password_retype = (EditText) root.findViewById(R.id.et_password_retype);
                et_country = (EditText) root.findViewById(R.id.et_country);
                et_city = (EditText) root.findViewById(R.id.et_city);
                EditText et_address = (EditText) root.findViewById(R.id.et_address);
                EditText et_contact = (EditText) root.findViewById(R.id.et_contact);
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
                    et_full_name.setText(mUserObj.getUsername());
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
                EditText et_store_name = (EditText) root.findViewById(R.id.et_store_name);
                et_password = (EditText) root.findViewById(R.id.et_password);
                et_password_retype = (EditText) root.findViewById(R.id.et_password_retype);
                EditText et_bank_name = (EditText) root.findViewById(R.id.et_bank_name);
                EditText et_bank_account_name = (EditText) root.findViewById(R.id.et_bank_account_name);
                EditText et_bank_account_number = (EditText) root.findViewById(R.id.et_bank_account_number);
                et_country = (EditText) root.findViewById(R.id.et_country);
                et_city = (EditText) root.findViewById(R.id.et_city);
                et_address = (EditText) root.findViewById(R.id.et_address);
                EditText et_store_owner_name = (EditText) root.findViewById(R.id.et_store_owner_name);
                EditText et_store_owner_contact = (EditText) root.findViewById(R.id.et_store_owner_contact);
                et_mail = (EditText) root.findViewById(R.id.et_mail);
                EditText et_instagram = (EditText) root.findViewById(R.id.et_instagram);

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
                    et_store_owner_contact.setText(mUserObj.getContact_no());
                    et_store_owner_name.setText(mUserObj.getUsername());

                    StoreObject mStoreObj = mUserObj.getStoreObject();
                    if (mStoreObj != null) {
                        et_bank_account_name.setText(mStoreObj.getBank_acc_name());
                        et_bank_account_number.setText(mStoreObj.getBank_acc_number());
                        et_bank_name.setText(mStoreObj.getBank_name());
                        et_store_name.setText(mStoreObj.getStore_name());

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

}

