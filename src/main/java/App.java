import Interface.IPostService;
import Model.Post;
import Service.PostService;

import java.util.List;

public class App {

    public static void main(String[] args) throws Exception {

        IPostService postService = new PostService();
//        List<Post> posts = postService.getPosts();
//
//        posts.forEach(post -> {
//            System.out.println("-----------------------");
//            System.out.println("Title: - " + post.getTitle());
//            System.out.println("Body:" + post.getBody());
//            System.out.println("----------------------");
//        });
////
        Post postById = postService.getPostById(4);
        System.out.println(postById);

//        Post post01 = new Post(9, 4, "deu bom", "Hoje tem gol do gabi golihkbjbjh" );
//        Post createdPost = postService.createPost(post01);
//        System.out.println(createdPost);

    }
}
