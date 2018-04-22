package com.borge.wipro_challenge.util;

import com.borge.wipro_challenge.remote.Client;
import com.borge.wipro_challenge.remote.SOService;

public class ApiUtil {

    public static final String BASE_URL = "http://api.openweathermap.org/";

    public static SOService getSOService() {
        return Client.getClient(BASE_URL).create(SOService.class);
    }
}
