package com.dtmweb.etrendapp.apis;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;


import com.dtmweb.etrendapp.constants.Constants;
import com.dtmweb.etrendapp.constants.UrlConstants;
import com.dtmweb.etrendapp.utils.GlobalUtils;
import com.dtmweb.etrendapp.utils.SharedPreferencesUtils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Request and get data from API
 *
 * @author PhanTri
 */
public class RequestData {
    private JsonParser mJsonParser = null;
    private String REQUEST_DATA_URL = null;
    private int mRestType = 0;
    private Context mContex = null;

    public RequestData(Context context) {
        mJsonParser = new JsonParser();
        mContex = context;
    }

    /**
     * TODO <br>
     * Function to get data
     *
     * @return json in string
     * @author Phan Tri
     * @date Oct 15, 2014
     */
    @SuppressWarnings("unchecked")
    public String getData(int typeOfRequest, final HashMap<String, Object> parameters) {
        ArrayList<Object> listParams = new ArrayList<Object>();
        ArrayList<NameValuePair> nameValueParams = new ArrayList<NameValuePair>();
        ArrayList<Map.Entry<String, Bitmap>> bitmapParams = new ArrayList<Map.Entry<String, Bitmap>>();
        JSONObject returnJson = null;

        switch (typeOfRequest) {


            case Constants.REQUEST_REGISTER_USER:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.REGISTRATION_URL;

                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_EMAIL,
                        (String) parameters.get(Constants.PARAM_EMAIL)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD1,
                        (String) parameters.get(Constants.PARAM_PASSWORD1)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD2,
                        (String) parameters.get(Constants.PARAM_PASSWORD2)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PHONE,
                        (String) parameters.get(Constants.PARAM_PHONE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STORE_NAME,
                        (String) parameters.get(Constants.PARAM_STORE_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_INSTAGRAM,
                        (String) parameters.get(Constants.PARAM_INSTAGRAM)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_SELLER,
                        (String) parameters.get(Constants.PARAM_SELLER)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_BUYER,
                        (String) parameters.get(Constants.PARAM_BUYER)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ADDRESS,
                        (String) parameters.get(Constants.PARAM_ADDRESS)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CITY,
                        (String) parameters.get(Constants.PARAM_CITY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_COUNTRY,
                        (String) parameters.get(Constants.PARAM_COUNTRY)));

                if (parameters.containsKey(Constants.PARAM_IMG)) {
                    // create hash map to save avatar bitmap
                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {

                        @Override
                        public String getKey() {
                            // TODO Auto-generated method stub
                            return Constants.PARAM_IMG;
                        }

                        @Override
                        public Bitmap getValue() {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }

                        @Override
                        public Bitmap setValue(Bitmap object) {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }
                    };

                    bitmapParams.add(hashIcon);
                }
                break;

            case Constants.REQUEST_CREATE_SELLER:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.SELLER_CREATE_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STORE_NAME,
                        (String) parameters.get(Constants.PARAM_STORE_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NAME,
                        (String) parameters.get(Constants.PARAM_ACC_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_BANK_NAME,
                        (String) parameters.get(Constants.PARAM_BANK_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NAME,
                        (String) parameters.get(Constants.PARAM_ACC_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NUMBER,
                        (String) parameters.get(Constants.PARAM_ACC_NUMBER)));

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;

            case Constants.REQUEST_GET_USER:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_GET_USER;

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;
            case Constants.REQUEST_GET_STORE:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.STORE_DETAILS_URL;

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;
            case Constants.REQUEST_GET_STORE_PRODUCT_LIST:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.STORE_PRODUCT_LIST_URL;

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;
            case Constants.REQUEST_GET_SUBSCRIPTION_RATE:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.GET_SUBSCRIPTION_RATE_URL;
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;
            case Constants.REQUEST_GET_BANNER:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.BANNER_URL;
                break;
            case Constants.REQUEST_GET_COUNTRY:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.COUNTRY_URL;
                break;
            case Constants.REQUEST_GET_CITY:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.CITY_URL
                        + "?" + Constants.PARAM_COUNTRY + "=" + parameters.get(Constants.PARAM_COUNTRY);
                break;
            case Constants.REQUEST_GET_CATEGORY:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.CATEGORY_LIST_URL;
                break;
            case Constants.REQUEST_GET_PRODUCTS:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.PRODUCTS_URL
                        + "?" + Constants.PARAM_CATEGORY + "=" + parameters.get(Constants.PARAM_CATEGORY);
                String token = SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                if (token != null) {
                    GlobalUtils.addAditionalHeader = true;
                    GlobalUtils.additionalHeaderTag = "Authorization";
                    GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                }
                break;
            case Constants.REQUEST_GET_PRODUCT_DETAILS:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.PRODUCT_DETAILS
                        + "/" + parameters.get(Constants.PARAM_PRODUCT_ID);
                break;
            case Constants.REQUEST_LOGIN:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.LOGIN_URL;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_EMAIL,
                        (String) parameters.get(Constants.PARAM_EMAIL)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD,
                        (String) parameters.get(Constants.PARAM_PASSWORD)));
                break;

            case Constants.REQUEST_UPDATE_COVER_PHOTO:
                mRestType = Constants.REST_PATCH;
                REQUEST_DATA_URL = UrlConstants.UPDATE_COVER_URL;
                if (parameters.containsKey(Constants.PARAM_COVER_IMAGE)) {
                    // create hash map to save avatar bitmap
                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {

                        @Override
                        public String getKey() {
                            // TODO Auto-generated method stub
                            return Constants.PARAM_COVER_IMAGE;
                        }

                        @Override
                        public Bitmap getValue() {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_COVER_IMAGE);
                        }

                        @Override
                        public Bitmap setValue(Bitmap object) {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_COVER_IMAGE);
                        }
                    };

                    bitmapParams.add(hashIcon);
                }
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;


            case Constants.REQUEST_LOGOUT:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.LOGOUT_URL;

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_GET_FAVURITE_LIST:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.FAVOURITE_ITEM_LIST_URL;
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;
            case Constants.REQUEST_ADD_IN_FAV_LIST:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.ADD_IN_FAVOURITE_ITEM_LIST;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRODUCT,
                        (String) parameters.get(Constants.PARAM_PRODUCT)));
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;


            case Constants.REQUEST_REMOVE_FROM_FAV_LIST:
                mRestType = Constants.REST_DELETE;
                REQUEST_DATA_URL = UrlConstants.REMOVE_FROM_FAV_LIST
                        + parameters.get(Constants.PARAM_PRODUCT_ID) + "/";
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_UPLOAD_PRODUCT_IMAGE:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.UPLOAD_PRODUCT_IMAGE_URL;
                if (parameters.containsKey(Constants.PARAM_IMG)) {
                    // create hash map to save avatar bitmap
                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {

                        @Override
                        public String getKey() {
                            // TODO Auto-generated method stub
                            return Constants.PARAM_IMG;
                        }

                        @Override
                        public Bitmap getValue() {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }

                        @Override
                        public Bitmap setValue(Bitmap object) {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }
                    };

                    bitmapParams.add(hashIcon);
                }
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_UPDATE_USER:
                mRestType = Constants.REST_PATCH;
                REQUEST_DATA_URL = UrlConstants.URL_UPDATE_USER;

                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PHONE,
                        (String) parameters.get(Constants.PARAM_PHONE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STORE_NAME,
                        (String) parameters.get(Constants.PARAM_STORE_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_INSTAGRAM,
                        (String) parameters.get(Constants.PARAM_INSTAGRAM)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ADDRESS,
                        (String) parameters.get(Constants.PARAM_ADDRESS)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CITY,
                        (String) parameters.get(Constants.PARAM_CITY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_COUNTRY,
                        (String) parameters.get(Constants.PARAM_COUNTRY)));

                if (parameters.containsKey(Constants.PARAM_IMG)) {
                    // create hash map to save avatar bitmap
                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {

                        @Override
                        public String getKey() {
                            // TODO Auto-generated method stub
                            return Constants.PARAM_IMG;
                        }

                        @Override
                        public Bitmap getValue() {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }

                        @Override
                        public Bitmap setValue(Bitmap object) {
                            // TODO Auto-generated method stub
                            return (Bitmap) parameters.get(Constants.PARAM_IMG);
                        }
                    };

                    bitmapParams.add(hashIcon);
                }
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_UPDATE_STORE:
                mRestType = Constants.REST_PATCH;
                REQUEST_DATA_URL = UrlConstants.URL_UPDATE_STORE;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STORE_NAME,
                        (String) parameters.get(Constants.PARAM_STORE_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NAME,
                        (String) parameters.get(Constants.PARAM_ACC_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_BANK_NAME,
                        (String) parameters.get(Constants.PARAM_BANK_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NAME,
                        (String) parameters.get(Constants.PARAM_ACC_NAME)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ACC_NUMBER,
                        (String) parameters.get(Constants.PARAM_ACC_NUMBER)));

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;

            case Constants.REQUEST_CREATE_PRODUCT:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.URL_CREATE_PRODUCT;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TITLE,
                        (String) parameters.get(Constants.PARAM_TITLE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_DETAILS,
                        (String) parameters.get(Constants.PARAM_DETAILS)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRICE,
                        (String) parameters.get(Constants.PARAM_PRICE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_DISCOUNT_PRICE,
                        (String) parameters.get(Constants.PARAM_DISCOUNT_PRICE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_QUANTITY,
                        (String) parameters.get(Constants.PARAM_QUANTITY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ATTRIBUTE,
                        (String) parameters.get(Constants.PARAM_ATTRIBUTE)));

                List<String> attribute = (List<String>) parameters.get(Constants.PARAM_ATTRIBUTE_VALUE);
                for (int i = 0; i < attribute.size(); i++) {
                    nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ATTRIBUTE_VALUE + "[" + i + "]", attribute.get(i)));
                }

                List<String> images = (List<String>) parameters.get(Constants.PARAM_IMAGES);
                for (int i = 0; i < images.size(); i++) {
                    nameValueParams.add(new BasicNameValuePair(Constants.PARAM_IMAGES + "[" + i + "]", images.get(i)));
                }
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CATEGORY,
                        (String) parameters.get(Constants.PARAM_CATEGORY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_WEIGHT,
                        (String) parameters.get(Constants.PARAM_WEIGHT)));

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;

            case Constants.REQUEST_GET_PRODUCT:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_GET_PRODUCT
                        + parameters.get(Constants.PARAM_PRODUCT_ID) + "/";
                token = SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                if (token != null) {
                    GlobalUtils.addAditionalHeader = true;
                    GlobalUtils.additionalHeaderTag = "Authorization";
                    GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                }
                break;

            case Constants.REQUEST_ADD_CART:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.URL_ADD_CART;

                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRODUCT,
                        (String) parameters.get(Constants.PARAM_PRODUCT)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRODUCT_IMAGE,
                        (String) parameters.get(Constants.PARAM_PRODUCT_IMAGE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_QUANTITY,
                        (String) parameters.get(Constants.PARAM_QUANTITY)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ATTRIBUTE,
                        (String) parameters.get(Constants.PARAM_ATTRIBUTE)));
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ATTRIBUTE_VALUE,
                        (String) parameters.get(Constants.PARAM_ATTRIBUTE_VALUE)));

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);

                break;

            case Constants.REQUEST_GET_CART:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_GET_CART
                        + "?" + Constants.PARAM_IS_ORDERED + "=" + parameters.get(Constants.PARAM_IS_ORDERED);
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_GET_SELLER_ORDER:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_GET_SELLER_ORDER;
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_GET_STORE_DETAILS:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_GET_STORE_DETAILS
                        + parameters.get(Constants.PARAM_STORE_ID) + "/";
                token = SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                if (token != null) {
                    GlobalUtils.addAditionalHeader = true;
                    GlobalUtils.additionalHeaderTag = "Authorization";
                    GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                }
                break;

            case Constants.REQUEST_GET_STORE_PRODUCT_LIST_BY_STORE:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_GET_STORE_PRODUCT_LIST_BY_STORE
                        + "?" + Constants.PARAM_STORE_ID + "=" + parameters.get(Constants.PARAM_STORE_ID);
                token = SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                if (token != null) {
                    GlobalUtils.addAditionalHeader = true;
                    GlobalUtils.additionalHeaderTag = "Authorization";
                    GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                }
                break;


            default:
                break;
        }

        listParams.add(nameValueParams);
        listParams.add(bitmapParams);
        // Building Parameters
        Log.e("Request URL:", REQUEST_DATA_URL);
        returnJson = mJsonParser.getJSONFromUrl(REQUEST_DATA_URL, mRestType, listParams);

        return (returnJson != null) ? returnValues(returnJson) : null;
    }

    /**
     * TODO <br>
     * Function to return values from server after check <br>
     *
     * @param returnObj
     * @return
     * @author Phan Tri
     * @date Oct 15, 2014
     */
    public String returnValues(JSONObject returnObj) {
        return returnObj.toString();
    }
}
