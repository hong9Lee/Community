package web.community.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommunityItemHistoryRepositoryTest {

    @Autowired private CommunityItemRepository communityItemRepository;
    @Autowired private EntityManager em;

    @Test
    @Transactional
    void itemHistoryTest() {
        CommunityItem item = new CommunityItem();
        item.setTitle("History Listener Title Test 1");
        item.setContents("History Listener Contents Test 1");
        communityItemRepository.save(item);

        em.flush();
        em.clear();

        item.setTitle("History Listener Title Test 2");
        item.setContents("History Listener Contents Test 2");
        communityItemRepository.save(item);

        em.flush();
        em.clear();

        communityItemRepository.deleteById(item.getId());
    }
}
