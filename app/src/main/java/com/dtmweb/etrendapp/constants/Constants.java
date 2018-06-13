package com.dtmweb.etrendapp.constants;

/**
 * Created by mdmunirhossain on 3/12/18.
 */

public class Constants {
    public static final int FRAG_HOME = 1;
    public static final int FRAG_MANAGE_PRODUCTS = 2;
    public static final int FRAG_MANAGE_ORDERS = 3;
    public static final int FRAG_MY_STORE = 4;
    public static final int FRAG_ADD_PRODUCT = 5;
    public static final int FRAG_ADD_NEW_CARD = 6;
    public static final int FRAG_CHOOSE_PAYMENT_METHOD = 7;
    public static final int FRAG_EDIT_PROFILE = 8;
    public static final int FRAG_PRODUCT_DETAILS = 9;
    public static final int FRAG_BROWSE_PRODUCT = 10;
    public static final int FRAG_BILLING_ADDRESS = 11;
    public static final int FRAG_MY_PLAN = 12;
    public static final int FRAG_CHOOSE_PLAN = 13;


    public static final String CATEGORY_BUYER = "buyerType" ;
    public static final String CATEGORY_SELLER = "sellerType" ;
    public static final String CATEGORY_NON_LOGGED = "notLoggedIn" ;

    public static final int HOME = 0;
    public static final int CATEGORY = 1;
    public static final int FAVOURITE = 2;
    public static final int CART = 3;
    public static final int PROFILE = 4;
    public static final int DISSELECT_ALL = 5;

    public static final String CATEGORY_ACCESSORIZE = "1";
    public static final String CATEGORY_CLOTHES = "2";
    public static final String CATEGORY_PERFUME = "3";

    public static final String EXTRA_FROM_CHOOSE_PLAN = "from_choose_plan_page";

    public static final String AUTH_USERNAME = null;
    public static final String AUTH_PASSWORD = null;
    public static final int CONNECTION_TIME_OUT = 10000;

    public static final int REST_POST = 0;
    public static final int REST_GET = 1;
    public static final int REST_PUT = 2;
    public static final int REST_DELETE = 3;
    public static int REST_PATCH = 4;


    public static final int TIME_OUT = 30000;

    public static final String PARAM_JSON_DATA = "jsonData";
    public static final int TYPE_COUNTRY = 0;
    public static final int TYPE_CITY = 1;
    public static final int TYPE_CATEGORY = 2;


    //Requests
    public static final int REQUEST_REGISTER_USER = 1;
    public static final int REQUEST_CREATE_SELLER = 2;
    public static final int REQUEST_LOGIN = 3;
    public static final int REQUEST_SELLER_PROFILE = 4;
    public static final int REQUEST_LOGOUT = 5;
    public static final int REQUEST_GET_USER = 6;
    public static final int REQUEST_GET_BANNER = 7;
    public static final int REQUEST_GET_CITY = 8;
    public static final int REQUEST_GET_COUNTRY = 9;
    public static final int REQUEST_GET_PRODUCTS = 10;
    public static final int REQUEST_GET_CATEGORY = 11;
    public static final int REQUEST_GET_PRODUCT_DETAILS = 12;
    public static final int REQUEST_GET_STORE = 13;
    public static final int REQUEST_GET_STORE_PRODUCT_LIST = 14;
    public static final int REQUEST_GET_SUBSCRIPTION_RATE = 15;
    public static final int REQUEST_UPDATE_COVER_PHOTO = 16;
    public static final int REQUEST_GET_FAVURITE_LIST = 17;
    public static final int REQUEST_ADD_IN_FAV_LIST = 18;
    public static final int REQUEST_REMOVE_FROM_FAV_LIST = 19;
    public static final int REQUEST_UPLOAD_PRODUCT_IMAGE = 20;
    public static final int REQUEST_UPDATE_USER = 21;
    public static final int REQUEST_UPDATE_STORE = 22;
    public static final int REQUEST_CREATE_PRODUCT = 23;


    //params
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_PASSWORD = "password";
    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_IMG = "image";
    public static final String PARAM_STORE_NAME = "name";
    public static final String PARAM_BANK_NAME = "bank_name";
    public static final String PARAM_ACC_NAME = "account_name";
    public static final String PARAM_ACC_NUMBER = "account_number";
    public static final String PARAM_COUNTRY = "country";
    public static final String PARAM_CITY = "city";
    public static final String PARAM_ADDRESS = "address";
    public static final String PARAM_STORE_OWNER = "store_owner";
    public static final String PARAM_CONTACT_NO = "contact_no";
    public static final String PARAM_INSTAGRAM = "instagram";
    public static final String PARAM_FULL_NAME = "full_name";
    public static final String PARAM_PASSWORD1 = "password1";
    public static final String PARAM_PASSWORD2 = "password2";
    public static final String PARAM_PHONE = "phone";
    public static final String PARAM_SELLER = "is_seller";
    public static final String PARAM_BUYER = "is_buyer";
    public static final String PARAM_PRODUCT_ID = "product_id";
    public static final String PARAM_CATEGORY = "category";
    public static final String PARAM_COVER_IMAGE = "cover_photo";
    public static final String PARAM_IS_FAVOURITE = "is_favourite";
    public static final String PARAM_PRODUCT = "product";
    public static final String PARAM_TITLE = "title";
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_DISCOUNT_PRICE = "discount";
    public static final String PARAM_QUANTITY = "quantity";
    public static final String PARAM_DETAILS = "details";
    public static final String PARAM_ATTRIBUTE = "attribute";
    public static final String PARAM_WEIGHT = "weight";
    public static final String PARAM_ATTRIBUTE_VALUE = "attribute_value";
    public static final String PARAM_IMAGES = "images";

    //prefs
    public static final String PREF_TOKEN = "prefs_token";

    public static final String DATA = "data";
}
