package Service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(methodHttp.toUpperCase());
        return connection;
    }

    public HttpURLConnection connection(String methodHttp, int id) throws IOException {

        URL url = new URL("https://jsonplaceholder.typicode.com/posts" + "/" +id);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(methodHttp.toUpperCase());
        return connection;
    }

    public StringBuilder responseInStringFormat(HttpURLConnection connection, int expectedHttpCode) throws  IOException {

        int responseCode = connection.getResponseCode();

        if (!(responseCode == expectedHttpCode))
            throw new IOException("Post ot found");

        BufferedReader response = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder responseString = new StringBuilder();
        String line;

        while ((line = response.readLine()) != null) {
            responseString.append(line);
        }

        return responseString;
    }

    public void sendBody(HttpURLConnection connection, Post post) throws IOException{

        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        String postJson = gson.toJson(post);

        DataOutputStream sendPost = new DataOutputStream(connection.getOutputStream());
        sendPost.writeBytes(postJson);
        sendPost.flush();
        sendPost.close();
    }


    public Post createPost(Post post) throws IOException {

        HttpURLConnection connection = connection("POST");

        sendBody(connection, post);

        StringBuilder responseString = responseInStringFormat(connection, HttpURLConnection.HTTP_CREATED);

        return gson.fromJson(responseString.toString(), Post.class);

    }

    public Post getPostById(int id) throws IOException{

        HttpURLConnection connection = connection("GET", id);

        StringBuilder response = responseInStringFormat(connection, HttpURLConnection.HTTP_OK);

        return gson.fromJson(response.toString(), Post.class);
    }

    public List<Post> getPosts() throws IOException {

        HttpURLConnection connection = connection("Get");

        StringBuilder response = responseInStringFormat(connection, HttpURLConnection.HTTP_OK);
        Type listType = new TypeToken<ArrayList<Post>>() {}.getType();

        return gson.fromJson(response.toString(), listType);

    }

    public Post updatePost(Post post, int id) throws IOException{

        HttpURLConnection connection = connection("PUT", id);

        sendBody(connection, post);

        StringBuilder response = responseInStringFormat(connection, HttpURLConnection.HTTP_OK);

        return gson.fromJson(response.toString(), Post.class);
    }

    public void deletePost(int id) throws IOException{

        HttpURLConnection connection = connection("DELETE", id);

        if(!(connection.getResponseCode() == HttpURLConnection.HTTP_OK))
            throw new IOException("Post not found");
    }
}
