package web.community.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.Comments;
import web.community.domain.CommunityItem;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityItemRepositoryTest {

    @Autowired
    private CommunityItemRepository communityItemRepository;
    @Autowired private LikesRepository likesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentsRepository commentsRepository;

    @Test
    @DisplayName("")
    @Transactional
    public void N1테스트() {

        /*
        // Lazy (x), Eager (N+1 발생)
        List<CommunityItem> all = communityItemRepository.findAll();

        // Lazy (N+1 발생), Eager (N+1 발생)
        for (CommunityItem item : all) {
            item.getCommentsList();
        }
        */

        /* fetchJoin */
        List<CommunityItem> allJoinFetch = communityItemRepository.findAllJoinFetch();
        for (CommunityItem item : allJoinFetch) {
            System.out.println(item.getCommentsList());
        }
    }


    @Test
    @DisplayName("findAll TEST")
    @Transactional
    void getAllItemsTest() {
        communityItemRepository.findAll().forEach(System.out::println);
    }

    @Test
    @Transactional
    void deleteItemTest() {
        System.out.println("********************************************");
        System.out.println(communityItemRepository.findById(1L));
        System.out.println("USERS =====> " + userRepository.findAll());
        System.out.println("LIKES =====> " + likesRepository.findAll());
        System.out.println("COMMENTS =====> " + communityItemRepository.findAll());


        System.out.println("********************************************");

        communityItemRepository.deleteById(1L);


        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println(communityItemRepository.findAll().size());
        System.out.println("USERS =====> " + userRepository.findAll());
        System.out.println("LIKES =====> " + likesRepository.findAll());
        System.out.println("COMMENTS =====> " + commentsRepository.findAll());
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }

}
