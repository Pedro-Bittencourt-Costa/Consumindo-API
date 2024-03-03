import Interface.IPostService;
import Model.Post;
import Service.PostService;

import java.io.IOException;

public class App {

    public static void main(String[] args) {

        IPostService postService = new PostService();
        try {
//            List<Post> posts = postService.getPosts();
//
//            posts.forEach(post -> {
//                System.out.println("-----------------------");
//                System.out.println("Title: - " + post.getTitle());
//                System.out.println("Body:" + post.getBody());
//                System.out.println("----------------------");
//            });
///
            Post postById = postService.getPostById(3898);
            System.out.println(postById);

//            Post post01 = new Post(7, "deu bom", "testando ultimas atualizações" );
//            Post createdPost = postService.createPost(post01);
//            System.out.println(createdPost);
//
//            Post updatedPost = postService.updatePost(post01, 3);
//            System.out.println(updatedPost);
//
//            postService.deletePost(3);

        }catch(IOException ex){
            System.err.println(ex.getMessage());
        }
    }
}
