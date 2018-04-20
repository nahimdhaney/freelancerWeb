package edson.com.freelancer.httpclient;

/**
 * Created by joseco on 11/17/2015.
 */
public abstract class RequestConfiguration {

    private MethodType type;
    protected String params;
    private String requestUrl;
    protected String contentType;

    public RequestConfiguration(String url, MethodType type){
        this.requestUrl = url;
        this.type = type;
        this.contentType = "application/x-www-form-urlencoded";
    }

    public MethodType getType() {
        return type;
    }

    public String getParams() {
        return params;
    }

    public String getRequestUrl() {
        return type == MethodType.POST ? requestUrl :
                (requestUrl.endsWith("?") ? requestUrl : requestUrl + "?") + params;
    }
}
