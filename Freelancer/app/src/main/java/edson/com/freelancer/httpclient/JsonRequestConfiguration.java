package edson.com.freelancer.httpclient;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by joseco on 11/17/2015.
 */
public class JsonRequestConfiguration extends RequestConfiguration {


    public JsonRequestConfiguration(String url, MethodType type, JSONObject object) {
        super(url, type);
        this.params = object.toString();
        this.contentType = "application/json";
    }

    public JsonRequestConfiguration(String url, MethodType type, JSONArray array) {
        super(url, type);
        this.params = array.toString();
        this.contentType = "application/json";
    }

}
