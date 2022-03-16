package web.community.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import web.community.domain.User;
import web.community.helper.enums.AccountType;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommunityItemRepository communityItemRepository;

    @Autowired
    private EntityManager em;

    @Test
    @Description("User를 생성, 저장 검증")
    void userSaveTest(){
        User user = givenUser();
        User save = userRepository.save(user);
        assertThat(save.getNickName()).isEqualTo(user.getNickName());
    }

    @Test
    @Description("User의 updatedAt 필드를 통해 Auditing 기능 검증.")
    void userAuditingTest() {
        User user = userRepository.findById(1L).get();
        LocalDateTime userUpdatedAt = user.getUpdatedAt();

        user.setNickName("강남 건물주");
        userRepository.saveAndFlush(user);

        User updatedUser = userRepository.findById(1L).get();
        LocalDateTime updateUserUpdateAt = updatedUser.getUpdatedAt();
        assertThat(userUpdatedAt).isNotEqualTo(updateUserUpdateAt);
    }

    @Test
    @Description("User Delete 검증")
    void userDeleteTest() {
        userRepository.deleteById(1L);

        Optional<User> deletedId = userRepository.findById(1L);
        assertThat(false).isEqualTo(deletedId.isPresent());
    }

    @Test
    @Description("User Quit Update 검증")
    void userQuitTest() {
        User user = userRepository.getById(1L);
        String old_Quit = user.getQuit();

        user.setQuit("Y");
        userRepository.saveAndFlush(user);

        assertThat(old_Quit).isNotEqualTo(userRepository.findById(1L).get().getQuit());
    }

    @Test
    @Description("User addCommunityItem 검증")
    void userAddCommunityItem() {
        User user = givenUser();
        Long id = user.getId();

        CommunityItem communityItem = givenCommunityItems(user);
        user.addCommunityItem(communityItem);

        User user1 = userRepository.findById(id).get();
        Long userByInItemList = user1.getCommunityItemList().get(0).getUser().getId();

        assertThat(id).isEqualTo(userByInItemList);
    }





    private User givenUser() {
        User user = new User();
        user.setNickName("판교 건물주");
        user.setAccountType(AccountType.LESSEE);
        user.setAccountId(AccountType.LESSEE + " 41");
        user.setQuit("N");
        return userRepository.save(user);
    }


    private CommunityItem givenCommunityItems(User user) {
        CommunityItem communityItem = new CommunityItem();
        communityItem.setUser(user);

        communityItem.setContents("잘썼어요");
        return communityItemRepository.save(communityItem);
    }


}
