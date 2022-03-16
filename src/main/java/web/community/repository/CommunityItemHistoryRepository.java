package web.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.community.domain.CommunityItemHistory;

public interface CommunityItemHistoryRepository extends JpaRepository<CommunityItemHistory, Long> {
}
