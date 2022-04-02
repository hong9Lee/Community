package web.community.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.Comments;
import web.community.domain.CommunityItem;
import web.community.domain.User;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommentsRepositoryTest {

    @Autowired private CommentsRepository commentsRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private CommunityItemRepository communityItemRepository;

    public User givenUser(String nickName) { // 유저생성
        User user = new User();
        user.setNickName(nickName);
        return userRepository.save(user);
    }

    public Comments givenComments(String contents, User user) { // 댓글 생성
        Comments comments = new Comments();
        comments.setContents(contents);
        comments.setUser(user);
        return commentsRepository.save(comments);
    }

    @Test
    @Description("댓글 save 검증")
    @Transactional
    void save() {
//        User user = givenUser("TEST_USER1");
//        User user1 = givenUser("게시글을 작성한 TEST USER");
//
//        Comments comments = givenComments("테스트 댓글", user);
//        Comments comments1 = givenComments("테스트 댓글1", user);
//
//        CommunityItem communityItem = new CommunityItem();
//        communityItem.setUser(user1);
//        communityItem.setContents("테스트를 위한 게시물 입니다.");
//        communityItem.addCommunityItem(comments, comments1);
//
//        comments.setCommunityItem(communityItem);
//        comments1.setCommunityItem(communityItem);
//        communityItemRepository.save(communityItem);
//
//        User getUser = userRepository.findByNickName("TEST_USER1");
//        Comments getComments = commentsRepository.findByNickName("TEST_USER1").get(0);
//        assertThat(getUser.getId()).isEqualTo(getComments.getUser().getId());
    }
}
