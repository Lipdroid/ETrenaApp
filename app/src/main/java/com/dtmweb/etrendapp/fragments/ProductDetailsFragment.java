package com.dtmweb.etrendapp.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.dtmweb.etrendapp.ChoosenActivity;
import com.dtmweb.etrendapp.LoginActivity;
import com.dtmweb.etrendapp.R;
import com.dtmweb.etrendapp.adapters.ImageListAdapter;
import com.dtmweb.etrendapp.adapters.SlidingImage_Adapter;
import com.dtmweb.etrendapp.apis.RequestAsyncTask;
import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.customViews.ValueSelector;
import com.dtmweb.etrendapp.interfaces.AsyncCallback;
import com.dtmweb.etrendapp.models.CategoryObject;
import com.dtmweb.etrendapp.models.ImageObject;
import com.dtmweb.etrendapp.models.ProductObject;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.MultipleScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.app.Activity.RESULT_CANCELED;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProductDetailsFragment extends Fragment {

    private ViewPager product_pager = null;
    private static final Integer[] IMAGES = {R.drawable.demo_image_clothing, R.drawable.demo_image_clothing_two, R.drawable.demo_image_clothing, R.drawable.demo_image_clothing_two};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private Button btn_add = null;
    private Context mContext = null;
    private String product_id = null;


    private TextView tv_title = null;
    private TextView tv_details = null;
    private TextView tv_price = null;
    private TextView tv_store_name = null;
    private ImageView fav_icon = null;
    private EditText et_attribute = null;

    private ValueSelector valueSelector_quantity = null;
    private ProductObject productObject = null;

    private ListView list_images = null;
    private ImageListAdapter adapter = null;

    public ProductDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_product_details, container, false);
        product_pager = (ViewPager) root.findViewById(R.id.product_pager);
        btn_add = (Button) root.findViewById(R.id.btn_add);
        tv_title = (TextView) root.findViewById(R.id.tv_title);
        tv_details = (TextView) root.findViewById(R.id.tv_details);
        tv_price = (TextView) root.findViewById(R.id.tv_price);
        tv_store_name = (TextView) root.findViewById(R.id.tv_store_name);
        fav_icon = (ImageView) root.findViewById(R.id.fav_icon);
        valueSelector_quantity = (ValueSelector) root.findViewById(R.id.valueSelector_quantity);
        et_attribute = (EditText) root.findViewById(R.id.et_attribute);
        et_attribute.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) afterClickAttribute();
                return false;
            }
        });
        list_images = (ListView) root.findViewById(R.id.list_images);
        mContext = getActivity();
        if (getArguments().getString(String.class.toString()) != null) {
            product_id = getArguments().getString(String.class.toString());
            requestToGetProductDetails(product_id);
        }
        if (GlobalUtils.user_type.equals(Constants.CATEGORY_SELLER)) {
            fav_icon.setVisibility(View.VISIBLE);
        } else if (GlobalUtils.user_type.equals(Constants.CATEGORY_BUYER)) {
            fav_icon.setVisibility(View.VISIBLE);
        } else {
            fav_icon.setVisibility(View.GONE);
        }

        fav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("Fav:", "toggle favourite");
                if (productObject != null) {
                    if (productObject.getIs_favourite().equals("false")) {
                        requetToAddFavListBuyer(productObject.getId());
                    } else {
                        removeProductFromFavListBuyer(productObject.getId());
                    }
                }
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if not logged in open the login pages
                if (GlobalUtils.user_type.equals(Constants.CATEGORY_NON_LOGGED)) {
                    //Show Login Screens
                    startActivity(new Intent(mContext, LoginActivity.class));
                    getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_scale_to_center);

                } else {
                    //add to cart
                }
            }
        });
        new MultipleScreen(getActivity());
        MultipleScreen.resizeAllView((ViewGroup) root);
        return root;
    }

    private void afterClickAttribute() {
        if (productObject != null) {
            moveToChoosenActivity(Constants.TYPE_ATTRIBUTE);
        }
    }

    private void moveToChoosenActivity(int type) {
        Intent intent = new Intent(getActivity(), ChoosenActivity.class);
        intent.putExtra("extra", Constants.TYPE_ATTRIBUTE);
        intent.putStringArrayListExtra("list", (ArrayList<String>) productObject.getAttributeValues());
        startActivityForResult(intent, type);
        getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom,
                R.anim.anim_slide_out_bottom);
    }

    private void populatePager(List<ImageObject> mListImage) {
        product_pager.setAdapter(new SlidingImage_Adapter(getActivity(), mListImage));
    }

    private void requestToGetProductDetails(String product_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT_ID, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_GET_PRODUCT, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("GET Product", result);
                if (result != null) {
                    try {
                        JSONObject jsonProduct = new JSONObject(result);
                        if (jsonProduct.has("id")) {
                            productObject = new ProductObject();
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


                            if (jsonProduct.has("attribute_value")) {
                                JSONArray attrArray = jsonProduct.getJSONArray("attribute_value");
                                List<String> attr = new ArrayList<>();
                                for (int j = 0; j < attrArray.length(); j++) {
                                    String attr_value = (String) attrArray.get(j);
                                    attr.add(attr_value);
                                }
                                productObject.setAttributeValues(attr);

                            }
                            if (jsonProduct.has("store")) {
                                JSONObject jsonStore = jsonProduct.getJSONObject("store");
                                productObject.setStore_id(jsonStore.getString("id"));
                                productObject.setStore_name(jsonStore.getString("name"));

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

                            //setUp the view
                            setUpDetails(productObject);


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

    private void setUpDetails(ProductObject productObject) {
        tv_title.setText(productObject.getTitle());
        tv_details.setText(productObject.getDetails());
        tv_price.setText("SAR " + productObject.getDiscounted_price());
        tv_store_name.setText(productObject.getStore_name());
        if (productObject.getIs_favourite() != null && productObject.getIs_favourite().equals("true")) {
            fav_icon.setImageResource(R.drawable.fav_selected);
        } else {
            fav_icon.setImageResource(R.drawable.fav_unselected);
        }
        valueSelector_quantity.setMaxValue(Integer.parseInt(productObject.getQuantity()));
        et_attribute.setText(productObject.getCategoryObject().getAttribute_name());
        populatePager(productObject.getImages());
        populateSideImageList(productObject.getImages());

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }

        if (requestCode == Constants.TYPE_ATTRIBUTE) {
            String attribute = data.getExtras().getString("attribute");
            et_attribute.setText(attribute);

        }
    }

    private void removeProductFromFavListBuyer(final String product_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT_ID, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_REMOVE_FROM_FAV_LIST, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("remove favourite", result);
                try {
                    fav_icon.setImageResource(R.drawable.fav_unselected);

                } catch (Exception e) {
                    e.printStackTrace();
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


    private void requetToAddFavListBuyer(final String product_id) {
        final HashMap<String, Object> params = new HashMap<String, Object>();
        params.put(Constants.PARAM_PRODUCT, product_id);

        RequestAsyncTask mRequestAsync = new RequestAsyncTask(mContext, Constants.REQUEST_ADD_IN_FAV_LIST, params, new AsyncCallback() {
            @SuppressLint("LongLogTag")
            @Override
            public void done(String result) {
                GlobalUtils.dismissLoadingProgress();
                Log.e("Buyer favourite", result);
                if (result != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject.has("product")) {
                            JSONObject jsonProduct = jsonObject.getJSONObject("product");
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

                            //if not error then update
                            if (productObject.getIs_favourite().equals("true"))
                                fav_icon.setImageResource(R.drawable.fav_selected);
                            else
                                fav_icon.setImageResource(R.drawable.fav_unselected);


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

    private void populateSideImageList(List<ImageObject> images){
        adapter = new ImageListAdapter(mContext,images,null);
        list_images.setAdapter(adapter);
        list_images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                product_pager.setCurrentItem(position);
            }
        });
    }

}
