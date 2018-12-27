package com.weddingcar.user.common.config;


import com.weddingcar.user.BuildConfig;
import com.weddingcar.user.common.utils.StringUtils;

/**
 * Global config
 */

public class Config {
    private static boolean isDebug = BuildConfig.DEBUG;

    private static final String APP_BASE_URL_DEV = "http://139.196.254.89:8080/";
    private static final String APP_BASE_URL_TEST = "http://139.196.254.89:8080/";
    private static final String APP_BASE_URL_PRO = "http://139.196.254.89:8080/";
    private static final String APP_BASE_URL_USER_Model = "http://139.196.254.89:8080/LJTP/Model/";
    private static final String APP_BASE_URL_USER_AVATOR = "http://139.196.254.89:8080/LJTP/CATP/";
    private static final String APP_BASE_URL_CAR_BRANDS = "http://139.196.254.89:8080/LJTP/Logo/";
    public static final String HX_USER_PASSWORD = "12345678";
    public static final String PAY_APP_ID_WX = "wx8d51fb8191c9742b";
    public static final String PAY_APP_KEY_WX = "89ac92c09d9b99154fe32bfbe963c456";

    /**
     * Determine whether is test environment currently
     */
    public boolean isTestEnvironment() {
        return isDebug || StringUtils.equals(BuildConfig.FLAVOR, "staging");
    }

    /**
     * Get app html url by flavor
     * @return result   app html url
     */
    public static String getAppHtmlUrl() {
        if (isDebug) {
            return APP_BASE_URL_DEV;
        }

        return APP_BASE_URL_PRO;
    }

    /**
     * Get the app base url of development environment
     */
    public static String getAppBaseUrlDev() {
        return APP_BASE_URL_DEV;
    }

    /**
     * Get the app base url of staging(test) environment
     */
    public static String getAppBaseUrlTest() {
        return APP_BASE_URL_TEST;
    }

    /**
     * Get the app base url of production environment
     */
    public static String getAppBaseUrlPro() {
        return APP_BASE_URL_PRO;
    }

    public static String getCarBrandsBaseUrl() {
        return APP_BASE_URL_CAR_BRANDS;
    }

    public static String getCarModelUrl() {
        return APP_BASE_URL_USER_Model;
    }

    public static String getUserAvatorBaseUrl() {
        return APP_BASE_URL_USER_AVATOR;
    }
}
