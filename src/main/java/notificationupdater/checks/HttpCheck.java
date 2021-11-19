package notificationupdater.checks;


import notificationupdater.HttpGetter;

public abstract class HttpCheck implements Check {

    private HttpGetter httpGet;

    public HttpCheck(HttpGetter httpGet) {
        this.httpGet = httpGet;
    }

    public HttpGetter getHttpGet() {
        return httpGet;
    }
}
