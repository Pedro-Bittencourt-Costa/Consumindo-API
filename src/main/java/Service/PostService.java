package Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

import Interface.IPostService;
import Model.Post;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
public class PostService implements IPostService{
    private final Gson gson = new Gson();

    public HttpURLConnection connection(String methodHttp) throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/posts");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod(methodHttp.toUpperCase());
        return con;
    }

    public StringBuilder responseInStringFormat(HttpURLConnection con, int expectedHttpCode) throws  IOException {
        int responseCode = con.getResponseCode();

        if (!(responseCode == expectedHttpCode)) throw new IOException("Posts não criado");

        BufferedReader response = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder responseString = new StringBuilder();
        String line;

        while ((line = response.readLine()) != null) {
            responseString.append(line);
        }

        return responseString;
    }


    public Post createPost(Post post) throws IOException {

        HttpURLConnection con = connection("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        String postJson = gson.toJson(post);

        DataOutputStream sendPost = new DataOutputStream(con.getOutputStream());
        sendPost.writeBytes(postJson);
        sendPost.flush();
        sendPost.close();

        StringBuilder responseString = responseInStringFormat(con, HttpURLConnection.HTTP_CREATED);

        return gson.fromJson(responseString.toString(), Post.class);

    }

    public List<Post> getPosts() throws IOException {

        HttpURLConnection connection = connection("Get");

        StringBuilder response = responseInStringFormat(connection, HttpURLConnection.HTTP_OK);
        Type listType = new TypeToken<ArrayList<Post>>() {}.getType();

        return gson.fromJson(response.toString(), listType);

    }
}
