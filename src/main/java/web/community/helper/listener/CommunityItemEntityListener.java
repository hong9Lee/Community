package web.community.helper.listener;

import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import web.community.domain.CommunityItemHistory;
import web.community.helper.enums.CrudType;
import web.community.helper.util.BeanUtils;
import web.community.repository.CommunityItemHistoryRepository;
import javax.persistence.*;

public class CommunityItemEntityListener {

    @PostPersist
    public void postPersist(Object o) {
        CommunityItemHistoryRepository historyRepository = BeanUtils.getBean(CommunityItemHistoryRepository.class);
        CommunityItem item = (CommunityItem) o;

        CommunityItemHistory history = objectByBuilder(CrudType.CREATE, item);
        history.setCommunityItem(item);
        historyRepository.save(history);
    }

    @PostUpdate
    public void postUpdate(Object o) {
        CommunityItemHistoryRepository historyRepository = BeanUtils.getBean(CommunityItemHistoryRepository.class);
        CommunityItem item = (CommunityItem) o;

        CommunityItemHistory history = objectByBuilder(CrudType.UPDATE, item);
        history.setCommunityItem(item);
        historyRepository.save(history);
    }

    @PostRemove
    public void postRemove(Object o) {
        CommunityItemHistoryRepository historyRepository = BeanUtils.getBean(CommunityItemHistoryRepository.class);
        CommunityItem item = (CommunityItem) o;

        CommunityItemHistory history = objectByBuilder(CrudType.DELETE, item);
        history.setItemId(item.getId());
        historyRepository.save(history);
    }

    public CommunityItemHistory objectByBuilder(CrudType type, CommunityItem item) {
        return CommunityItemHistory.builder()
                .status(type)
                .title(item.getTitle())
                .contents(item.getContents())
                .build();
    }
}
