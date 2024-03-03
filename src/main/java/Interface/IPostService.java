package Interface;

import Model.Post;

import java.io.IOException;
import java.util.List;

public interface IPostService {
    List<Post> getPosts() throws IOException;

    Post getPostById(Integer id) throws IOException;

    Post createPost(Post post) throws IOException;

    Post updatePost(Post post, int id) throws IOException;

//    void deletePost(Integer id);
}
