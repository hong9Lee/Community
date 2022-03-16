package web.community.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityItemRepositoryTest {

    @Autowired
    private CommunityItemRepository communityItemRepository;
    @Autowired private LikesRepository likesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommentsRepository commentsRepository;


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
