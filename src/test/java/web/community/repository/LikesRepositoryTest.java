package web.community.repository;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import web.community.domain.Likes;
import web.community.domain.User;
import web.community.helper.enums.AccountType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LikesRepositoryTest {

    @Autowired private LikesRepository likesRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommunityItemRepository communityItemRepository;

    private Likes givenLikes(CommunityItem communityItem, User user) {
        Likes likes = new Likes();
        likes.setCommunityItem(communityItem);
        likes.setUser(user);
        return likes;
    }

    private User givenUser(String nickName, String accountId) {
        User user = new User();
        user.setNickName(nickName);
        user.setAccountType(AccountType.LESSEE);
        user.setAccountId(AccountType.LESSEE + " " + accountId);
        user.setQuit("N");
        return userRepository.save(user);
    }


    private CommunityItem givenCommunityItems(String contents, User user) {
        CommunityItem communityItem = new CommunityItem();
        communityItem.setContents(contents);
        communityItem.setUser(user);
        return communityItemRepository.save(communityItem);
    }

    @Test
    @Description("좋아요 갯수 검증")
    @Transactional
    void likeCountTest() {
        User user = givenUser("의왕 건물주", "11");
        User user1 = givenUser("안산 건물주", "41");
        CommunityItem communityItem = givenCommunityItems("잘 썼어요", user);

        Likes likes = givenLikes(communityItem, user);
        communityItem.addLikes(likes);
        Likes likes1 = givenLikes(communityItem, user1);
        communityItem.addLikes(likes1);
        likesRepository.saveAll(Lists.newArrayList(likes, likes1));

        Long id = communityItem.getId();
        int likeCount = communityItemRepository.findById(id).get().getLikedList().size();
        assertThat(likeCount).isEqualTo(2);
    }

    @Test
    @Description("유저가 누른 좋아요 갯수 검증")
    @Transactional
    void clickedCountByUser(){
        User user = givenUser("의왕 건물주", "11");
        User user1 = givenUser("군포 건물주", "41");

        CommunityItem communityItem = givenCommunityItems("잘 썼어요", user);
        CommunityItem communityItem1 = givenCommunityItems("별로에요", user);

        Likes likes = givenLikes(communityItem, user);
        Likes likes1 = givenLikes(communityItem1, user);
        Likes likes2 = givenLikes(communityItem, user1);

        likesRepository.saveAll(Lists.newArrayList(likes, likes1, likes2));

        Long id = user.getId();
        List<Likes> userByUserId = likesRepository.findUserByUserId(id);
        assertThat(userByUserId.size()).isEqualTo(2);
    }



}
