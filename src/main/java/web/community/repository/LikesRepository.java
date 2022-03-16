package web.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.community.domain.Likes;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes, Long> {

    /** 유저가 누른 좋아요를 List로 찾아오는 쿼리 */
    @Query(value = "select i from Likes as i where i.user.id = :id")
    List<Likes> findUserByUserId(@Param("id") Long id);

    /** 유저가 좋아요를 누른 게시물들을 Optional로 찾아오는 쿼리 */
    @Query(value = "select i from Likes as i where i.user.id = :user_id AND i.communityItem.id = :item_id")
    Optional<Likes> findByUserIdAndItemId(@Param("user_id") Long userId,
                                @Param("item_id") Long itemId);

    /** communityItem의 좋아요 갯수를 찾아오는 쿼리 */
    @Query(value = "select count(*) from likes i where i.community_item_id = :item_id", nativeQuery = true)
    Integer findLikeCount(@Param("item_id") Long itemId);

}
