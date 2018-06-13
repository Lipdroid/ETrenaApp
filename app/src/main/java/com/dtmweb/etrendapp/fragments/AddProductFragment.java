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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.devsmart.android.ui.HorizontalListView;
import com.dtmweb.etrendapp.ChoosenActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.AttributeAdapter;
import com.dtmweb.etrendapp.adapters.CartAdapter;
import com.dtmweb.etrendapp.adapters.ImageListAdapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.interfaces.DialogCallback;
import com.dtmweb.etrendapp.models.CategoryObject;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.PlaceObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.models.StoreObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.ImageUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProductFragment extends Fragment implements View.OnClickListener {


    private ImageView btn_add_image = null;
    public static final int TYPE_UPLOAD_PHOTO = 999;
    private int MY_REQUEST_CODE = 111;
    private Context mContext = null;
    private List<ImageObject> mListImages = null;
    private List<String> mListAtributes = null;
    private List<String> mListImageIds = null;

    private ImageListAdapter adapter = null;
    private AttributeAdapter mAdapter = null;

    private HorizontalListView lv_list_attribute = null;
    private HorizontalListView lv_list_img = null;

    private LinearLayout imageList_ln = null;
    private LinearLayout attribute_ln = null;

    private CategoryObject categoryObject = null;

    private EditText et_title = null;
    private EditText et_category = null;
    private EditText et_price = null;
    private EditText et_offer_price = null;
    private EditText et_quantity = null;
    private EditText et_size = null;
    private EditText et_details = null;
    private ImageView add_attribute = null;
    private Button btn_go = null;

    private String title = null;
    private String category = null;
    private String details = null;
    private String attribute = null;
    private String price = null;
    private String discount = null;
    private String quantity = null;
    private String attribute_id = null;


    public AddProductFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_product, container, false);
        mContext = getActivity();
        btn_add_image = (ImageView) root.findViewById(R.id.btn_add_image);
        btn_add_image.setOnClickListener(this);
        lv_list_img = (HorizontalListView) root.findViewById(R.id.lv_list_img);
        imageList_ln = (LinearLayout) root.findViewById(R.id.imageList_ln);
        lv_list_attribute = (HorizontalListView) root.findViewById(R.id.lv_list_attribute);
        attribute_ln = (LinearLayout) root.findViewById(R.id.attribute_ln);
        et_title = (EditText) root.findViewById(R.id.et_title);
        et_category = (EditText) root.findViewById(R.id.et_category);
        et_price = (EditText) root.findViewById(R.id.et_price);
        et_offer_price = (EditText) root.findViewById(R.id.et_offer_price);
        et_quantity = (EditText) root.findViewById(R.id.et_quantity);
        et_size = (EditText) root.findViewById(R.id.et_size);
        et_details = (EditText) root.findViewById(R.id.et_details);
        add_attribute = (ImageView) root.findViewById(R.id.add_attribute);
        btn_go = (Button) root.findViewById(R.id.btn_go);
        et_category.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) afterClickCategory();
                return false;
            }
        });
        add_attribute.setOnClickListener(this);
        btn_go.setOnClickListener(this);
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_add_image:
                afterClickImage();
                break;
            case R.id.add_attribute:
                afterClickAddAttribute();
                break;
            case R.id.btn_go:
                afterClickCreate();
                break;
        }
    }

    private void afterClickCreate() {
        title = et_title.getText().toString();
        if (categoryObject != null) {
            category = categoryObject.getId();
            attribute_id = categoryObject.getAttribute_id();
        }
        details = et_details.getText().toString();
        price = et_price.getText().toString();
        discount = et_offer_price.getText().toString();
        quantity = et_quantity.getText().toString();

        requestToCreateProduct();

    }

    private void afterClickAddAttribute() {
        if (!et_size.getText().toString().equals("")) {
            String attribute = et_size.getText().toString();
            if (mListAtributes != null) {
                mListAtributes.add(attribute);
            } else {
                mListAtributes = new ArrayList<>();
                mListAtributes.add(attribute);
            }
            populateAtrributeList(mListAtributes);
            et_size.setText("");
        } else {
            GlobalUtils.showInfoDialog(mContext, "Error", "Please insert a attribute first", "OK", null);
        }
    }

    private void afterClickCategory() {
        moveToChoosenActivity(Constants.TYPE_CATEGORY);
    }

    private void moveToChoosenActivity(int type) {
        Intent intent = new Intent(getActivity(), ChoosenActivity.class);
        intent.putExtra("extra", Constants.TYPE_CATEGORY);
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

        if (requestCode == Constants.TYPE_CATEGORY) {
            categoryObject = data.getParcelableExtra(CategoryObject.class.toString());
            et_category.setText(categoryObject.getName());
            et_size.setHint(categoryObject.getAttribute_name());

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
                    Bitmap selected_image = thumbBitmap;
                    requestUploadPhoto(selected_image);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void removeImageAtPosition(int position){
        if(mListImageIds != null){
            mListImageIds.remove(position);
            mListImages.remove(position);
            adapter.notifyDataSetChanged();
        }
        if (mListImages != null && mListImages.size() == 0) {
            imageList_ln.setVisibility(View.GONE);
        } else {
            imageList_ln.setVisibility(View.VISIBLE);
        }
    }

    public void removeAttributeAtPosition(int position){
        if(mListAtributes != null){
            mListAtributes.remove(position);
            mAdapter.notifyDataSetChanged();
        }
        if (mListAtributes != null && mListAtributes.size() == 0) {
            attribute_ln.setVisibility(View.GONE);
        } else {
            attribute_ln.setVisibility(View.VISIBLE);
        }
    }

    private void requestUploadPhoto(Bitmap selected_image) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_IMG, selected_image);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_UPLOAD_PRODUCT_IMAGE, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Image Upload", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("id")) {
                            ImageObject imageObject = new ImageObject();
                            imageObject.setId(jsonObject.getString("id"));
                            imageObject.setUrl(jsonObject.getString("image"));
                            if (mListImages != null) {
                                mListImages.add(imageObject);
                            } else {
                                mListImages = new ArrayList<>();
                                mListImages.add(imageObject);
                            }
                            if (mListImageIds != null) {
                                mListImageIds.add(imageObject.getId());
                            } else {
                                mListImageIds = new ArrayList<>();
                                mListImageIds.add(imageObject.getId());
                            }
                            populateImageList(mListImages);

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

    private void populateImageList(List<ImageObject> mListImages) {
        adapter = new ImageListAdapter(mContext, mListImages,this);
        lv_list_img.setAdapter(adapter);
        if (mListImages != null && mListImages.size() == 0) {
            imageList_ln.setVisibility(View.GONE);
        } else {
            imageList_ln.setVisibility(View.VISIBLE);
        }
    }

    private void populateAtrributeList(List<String> mListAtributes) {
        mAdapter = new AttributeAdapter(mContext, mListAtributes,this);
        lv_list_attribute.setAdapter(mAdapter);
        if (mListAtributes != null && mListAtributes.size() == 0) {
            attribute_ln.setVisibility(View.GONE);
        } else {
            attribute_ln.setVisibility(View.VISIBLE);
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

    private void requestToCreateProduct() {

        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_TITLE, title);
        params.put(Constants.PARAM_CATEGORY, category);
        params.put(Constants.PARAM_PRICE, price);
        params.put(Constants.PARAM_DISCOUNT_PRICE, discount);
        params.put(Constants.PARAM_QUANTITY, quantity);
        params.put(Constants.PARAM_DETAILS, details);
        params.put(Constants.PARAM_ATTRIBUTE, attribute_id);
        params.put(Constants.PARAM_WEIGHT, "0");
        params.put(Constants.PARAM_ATTRIBUTE_VALUE, mListAtributes);
        params.put(Constants.PARAM_IMAGES, mListImageIds);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_CREATE_PRODUCT, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Add Product", result);
                if (result != null) {
                    try {
                        JSONObject jsonProduct = new JSONObject(result);
                        if (jsonProduct.has("id")) {
                            ProductObject productObject = new ProductObject();
                            if (jsonProduct.has("id")) {
                                productObject.setId(jsonProduct.getString("id"));
                            }
                            if (jsonProduct.has("title")) {
                                productObject.setTitle(jsonProduct.getString("title"));
                            }
                            if (jsonProduct.has("details")) {
                                productObject.setDetails(jsonProduct.getString("details"));
                            }
                            if (jsonProduct.has("is_fav")) {
                                productObject.setIs_favourite(jsonProduct.getString("is_fav"));
                            }
                            if (jsonProduct.has("discounted_price")) {
                                productObject.setDiscounted_price(jsonProduct.getString("discounted_price"));
                            }
                            if (jsonProduct.has("discount")) {
                                productObject.setDiscount(jsonProduct.getString("discount"));
                            }
                            if (jsonProduct.has("quantity")) {
                                productObject.setQuantity(jsonProduct.getString("quantity"));
                            }
                            if (jsonProduct.has("weight")) {
                                productObject.setWeight(jsonProduct.getString("weight"));
                            }
                            if (jsonProduct.has("images")) {
                                JSONArray imagesArray = jsonProduct.getJSONArray("images");
                                List<ImageObject> images = new ArrayList<>();
                                for (int j = 0; j < imagesArray.length(); j++) {
                                    JSONObject jsonObjectImage = imagesArray.getJSONObject(j);
                                    ImageObject image = new ImageObject();
                                    image.setId(jsonObjectImage.getString("id"));
                                    image.setUrl(jsonObjectImage.getString("image"));
                                    images.add(image);
                                }
                                productObject.setImages(images);

                            }

                            if(jsonProduct.has("store")){
                                JSONObject jsonStore = jsonProduct.getJSONObject("store");
                                productObject.setStore_id(jsonStore.getString("id"));
                                productObject.setStore_name(jsonStore.getString("name"));

                            }


                            if (jsonProduct.has("attribute_value")) {
                                JSONArray attrArray = jsonProduct.getJSONArray("attribute_value");
                                List<String> attr = new ArrayList<>();
                                for (int j = 0; j < attrArray.length(); j++) {
                                    String attr_value = (String) attrArray.get(j);
                                    attr.add(attr_value);
                                }
                                productObject.setAttributeValues(attr);

                            }

                            JSONObject jsonCategory = jsonProduct.getJSONObject("category");
                            CategoryObject categoryObject = new CategoryObject();
                            categoryObject.setId(jsonCategory.getString("id"));
                            categoryObject.setName(jsonCategory.getString("name"));
                            categoryObject.setIcon(jsonCategory.getString("icon"));

                            if (jsonProduct.has("attribute")) {
                                JSONObject jsonAttr = jsonProduct.getJSONObject("attribute");
                                categoryObject.setAttribute_id(jsonAttr.getString("id"));
                                categoryObject.setAttribute_name(jsonAttr.getString("name"));
                            }

                            productObject.setCategoryObject(categoryObject);

                            //success message
                            GlobalUtils.showInfoDialog(mContext, "Success", "Product is successfully added.", "OK", null);
                            //clear the fields
                            clearFields();

                        } else {
                            //parse errors
                            parseErrors(params, jsonProduct);
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

    private void clearFields() {
        et_title.setText("");
        et_details.setText("");
        et_price.setText("");
        et_offer_price.setText("");
        et_quantity.setText("");
        et_category.setText("");
        categoryObject = null;
        mListAtributes.clear();
        mListImageIds.clear();
        mListImages.clear();
        attribute_ln.setVisibility(View.GONE);
        imageList_ln.setVisibility(View.GONE);

    }
}

