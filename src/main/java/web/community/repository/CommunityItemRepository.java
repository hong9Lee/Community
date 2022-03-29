package web.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.community.domain.Comments;
import web.community.domain.CommunityItem;
import java.util.List;
import java.util.Optional;

public interface CommunityItemRepository extends JpaRepository<CommunityItem, Long> {

    /** 게시물의 총 좋아요 갯수, 현재 로그인된 사용자가 해당 게시물의 좋아요를 눌렀는지 여부를 List로 찾아오는 쿼리 */
    @Query(value = "select c.id, c.created_at, c.updated_at, c.title, c.contents, c.quit, c.user_id, " +
            "( select count(*) from likes i where c.id = i.community_item_id) as like_count, " +
            "(select exists (select * from likes s where s.community_item_id = c.id and user_id = :user_id ) ) " +
            "as is_like from community_item c"
            , nativeQuery = true)
    List<CommunityItem> findAllItems(@Param("user_id") Long userId);


    /** userId와 itemId로 communityItem 찾아오는 쿼리 */
    @Query(value = "select i from CommunityItem as i where i.user.id = :user_id AND i.id = :item_id")
    Optional<CommunityItem> findItemByUserIdAndItemId(@Param("user_id") Long userId,
                                                     @Param("item_id") Long itemId);


    /** 가장 최근 데이터 가져오기 */
    @Query(value = "select * from community_item i order by i.id desc limit 1", nativeQuery = true)
    Optional<CommunityItem> findLast();


    /** N+1 회피를 위해 FetchJoin */
    @Query("select DISTINCT a from CommunityItem a join fetch a.commentsList")
    List<CommunityItem> findAllJoinFetch();

}
