package methods;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HTTPMethods {
    public String get() {
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpGet("http://httpbin.org/get"))
        ) {
            HttpEntity entity = response.getEntity();

            return IOUtils.toString(entity.getContent(), "cp1251");
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return null;
    }

    public String post() {
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpPost("http://httpbin.org/post"))
        ) {
            HttpEntity entity = response.getEntity();

            return IOUtils.toString(entity.getContent(), "cp1251");
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return null;
    }

    public String put() {
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpPut("http://httpbin.org/put"))
        ) {
            HttpEntity entity = response.getEntity();

            return IOUtils.toString(entity.getContent(), "cp1251");
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return null;
    }

    public String delete(){
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpDelete("http://httpbin.org/delete"))
        ) {
            HttpEntity entity = response.getEntity();

            return IOUtils.toString(entity.getContent(), "cp1251");
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return null;
    }

    public String patch() {
        try (
                CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpPatch("http://httpbin.org/patch"))
        ) {
            HttpEntity entity = response.getEntity();

            return IOUtils.toString(entity.getContent(), "cp1251");
        } catch (Throwable cause) {
            cause.printStackTrace();
        }
        return null;
    }
}
