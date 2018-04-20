package edson.com.freelancer.httpclient;


/**
 * Created by edson on 11/17/2017.
 */
public enum MethodType {

    GET("GET"),
    POST("POST");

    private final String value;

    private MethodType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

}
