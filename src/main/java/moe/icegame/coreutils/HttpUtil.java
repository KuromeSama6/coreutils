package moe.icegame.coreutils;

import moe.icegame.coreutils.classes.StandardHttpResponse;
import okhttp3.*;
import org.bukkit.Bukkit;
import sun.net.www.http.HttpClient;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class HttpUtil {
    public static StandardHttpResponse HttpPostSync(String url, String content) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        //System.out.println(String.format("requesting to %s", url));

        RequestBody body = RequestBody.create(content, mediaType);
        Request req = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response res = client.newCall(req).execute()) {
            int code = res.code();
            if (res.body() != null) return new StandardHttpResponse(code, res.body().string());
            else return new StandardHttpResponse(code, "{}");

        } catch (Exception e) {
            Bukkit.getLogger().warning(String.format("Error on HTTP Request, url = %s", url));
            e.printStackTrace();
            return StandardHttpResponse.failedResponse();
        }

    }

    public static void HttpPost(String url, String body, Consumer<StandardHttpResponse> callback) {
        CompletableFuture.runAsync(() -> {
           callback.accept(HttpPostSync(url, body));
        });
    }

}
