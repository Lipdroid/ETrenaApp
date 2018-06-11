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

            case Constants.REQUEST_SELLER_PROFILE:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.URL_SELLER_PROFILE;

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

            case Constants.REQUEST_GET_FAVURITE_LIST_BUYER:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.FAVOURITE_ITEM_LIST_URL_BUYER;

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;
            case Constants.REQUEST_GET_FAVURITE_LIST_SELLER:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.FAVOURITE_ITEM_LIST_URL_SELLER
                        + "?" + Constants.PARAM_IS_FAVOURITE + "=" + parameters.get(Constants.PARAM_IS_FAVOURITE);

                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;
            case Constants.REQUEST_GET_FAVURITE_LIST:
                mRestType = Constants.REST_GET;
                REQUEST_DATA_URL = UrlConstants.FAVOURITE_ITEM_LIST_URL
                        + "?" + Constants.PARAM_IS_FAVOURITE + "=" + parameters.get(Constants.PARAM_IS_FAVOURITE);
            case Constants.REQUEST_ADD_IN_FAV_LIST_SELLER:
                mRestType = Constants.REST_PATCH;
                REQUEST_DATA_URL = UrlConstants.ADD_IN_FAV_LIST_SELLER
                        + "" + parameters.get(Constants.PARAM_PRODUCT_ID)+"/";
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_IS_FAVOURITE,
                        (String) parameters.get(Constants.PARAM_IS_FAVOURITE)));
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_ADD_IN_FAV_LIST_BUYER:
                mRestType = Constants.REST_POST;
                REQUEST_DATA_URL = UrlConstants.ADD_IN_FAV_LIST_BUYER;
                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRODUCT,
                        (String) parameters.get(Constants.PARAM_PRODUCT)));
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;

            case Constants.REQUEST_REMOVE_FROM_FAV_LIST_BUYER:
                mRestType = Constants.REST_DELETE;
                REQUEST_DATA_URL = UrlConstants.REMOVE_FROM_FAV_LIST_BUYER
                        + parameters.get(Constants.PARAM_PRODUCT_ID)+"/";
                GlobalUtils.addAditionalHeader = true;
                GlobalUtils.additionalHeaderTag = "Authorization";
                GlobalUtils.additionalHeaderValue = "JWT " + SharedPreferencesUtils.getString(mContex, Constants.PREF_TOKEN, null);
                break;


//
//            case Constants.REQUEST_SUBMIT_POST:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.BASE_URL;
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TAG,
////                        (String) parameters.get(Constants.PARAM_TAG)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_TITLE,
////                        (String) parameters.get(Constants.PARAM_POST_TITLE)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_ADDRESS,
////                        (String) parameters.get(Constants.PARAM_ADDRESS)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_RATE,
////                        (String) parameters.get(Constants.PARAM_RATE)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_ORGANIZER,
////                        (String) parameters.get(Constants.PARAM_POST_ORGANIZER)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_ORGANIZER_ID,
////                        (String) parameters.get(Constants.PARAM_POST_ORGANIZER_ID)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_TIME,
////                        (String) parameters.get(Constants.PARAM_POST_TIME)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_DATE,
////                        (String) parameters.get(Constants.PARAM_POST_DATE)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_CONTACT_INFO,
////                        (String) parameters.get(Constants.PARAM_POST_CONTACT_INFO)));
////                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_POST_DESCRIPTION,
////                        (String) parameters.get(Constants.PARAM_POST_DESCRIPTION)));
////
////                if (parameters.containsKey(Constants.PARAM_POST_IMAGE)) {
////                    // create hash map to save avatar bitmap
////                    Map.Entry<String, Bitmap> hashIcon = new Map.Entry<String, Bitmap>() {
////
////                        @Override
////                        public String getKey() {
////                            // TODO Auto-generated method stub
////                            return Constants.PARAM_POST_IMAGE;
////                        }
////
////                        @Override
////                        public Bitmap getValue() {
////                            // TODO Auto-generated method stub
////                            return (Bitmap) parameters.get(Constants.PARAM_POST_IMAGE);
////                        }
////
////                        @Override
////                        public Bitmap setValue(Bitmap object) {
////                            // TODO Auto-generated method stub
////                            return (Bitmap) parameters.get(Constants.PARAM_POST_IMAGE);
////                        }
////                    };
////
////                    bitmapParams.add(hashIcon);
////                }
//                break;
//

//
//
//            case Constants.REQUEST_GET_USER_TYPE:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.GET_USER_TYPE_URL;
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//                break;
//
//            case Constants.REQUEST_GET_PROMOCODE_LIST:
//                mRestType = Constants.REST_GET;
//                REQUEST_DATA_URL = ConstantURLS.GET_MERCHANT_PROMOCODE_LIST_URL;
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//                break;
//            case Constants.REQUEST_GET_PROMOCODE_LIST_BY_DATE:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.GET_PROMOCODE_LIST_BY_DATE_URL_USER;
//
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_START_DATE,
//                        (String) parameters.get(Constants.PARAM_START_DATE)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_END_DATE,
//                        (String) parameters.get(Constants.PARAM_END_DATE)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//                break;
//            case Constants.REQUEST_GET_PROMOCODE:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.GET_PROMO_URL;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_MERHCANT_ID,
//                        (String) parameters.get(Constants.PARAM_MERHCANT_ID)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRODUCT_ID,
//                        (String) parameters.get(Constants.PARAM_PRODUCT_ID)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_VALIDITY,
//                        (String) parameters.get(Constants.PARAM_VALIDITY)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_DISCOUNT,
//                        (String) parameters.get(Constants.PARAM_DISCOUNT)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//                break;
//
//            case Constants.REQUEST_CHANGE_STATUS:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.URL_CHANGE_STATUS;
//
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PROMO_ID,
//                        (String) parameters.get(Constants.PARAM_PROMO_ID)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_STATUS,
//                        (String) parameters.get(Constants.PARAM_STATUS)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//                break;
//            case Constants.REQUEST_GET_PROMOCODE_DETAILS:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.GET_PROMOCODE_DETAILS;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_MERHCANT_ID,
//                        (String) parameters.get(Constants.PARAM_MERHCANT_ID)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PRODUCT_ID,
//                        (String) parameters.get(Constants.PARAM_PRODUCT_ID)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_VALIDITY,
//                        (String) parameters.get(Constants.PARAM_VALIDITY)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_DISCOUNT,
//                        (String) parameters.get(Constants.PARAM_DISCOUNT)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//                break;
//            case Constants.REQUEST_FORGOT_PASSWORD:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.FORGOT_PASSWORD;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_EMAIL,
//                        (String) parameters.get(Constants.PARAM_EMAIL)));
//
//                break;
//
//            case Constants.REQUEST_RESET_PASSWORD:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.RESET_PASSWORD;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PASSWORD,
//                        (String) parameters.get(Constants.PARAM_PASSWORD)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CONFIRM_PASSWORD,
//                        (String) parameters.get(Constants.PARAM_CONFIRM_PASSWORD)));
//
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//
//                break;
//
//
//            case Constants.REQUEST_VALIDATE_PROMO:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.URL_VALIDATE_PROMO;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PROMO_ID_V2,
//                        (String) parameters.get(Constants.PARAM_PROMO_ID_V2)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//
//                break;
//            case Constants.REQUEST_REMOVE_PROMO:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.URL_REMOVE_PROMO;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PROMO_ID_V2,
//                        (String) parameters.get(Constants.PARAM_PROMO_ID_V2)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//
//                break;
//
//
//            case Constants.REQUEST_SOCIAL_LOGIN:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.URL_SOCIAL_LOGIN;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_TOKEN,
//                        (String) parameters.get(Constants.PARAM_TOKEN)));
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_PROVIDER,
//                        (String) parameters.get(Constants.PARAM_PROVIDER)));
//
//                break;
//            case Constants.REQUEST_USER_TYPE_UPDATE:
//                mRestType = Constants.REST_POST;
//                REQUEST_DATA_URL = ConstantURLS.URL_UPDATE_USER_TYPE;
//
//                nameValueParams.add(new BasicNameValuePair(Constants.PARAM_CATEGORY,
//                        (String) parameters.get(Constants.PARAM_CATEGORY)));
//
//                GlobalUtils.addAditionalHeader = true;
//                GlobalUtils.additionalHeaderTag = "Authorization";
//                GlobalUtils.additionalHeaderValue = "bearer " + SharedPreferencesUtils.getString(mContex, Constants.TOKEN, null);
//
//                break;

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
