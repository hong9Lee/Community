package web.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import web.community.domain.Comments;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments, Long> {

    /** 해당 유저가 작성한 댓글들을 List로 가져오는 쿼리 */
    @Query(value = "select i from Comments as i where i.user.nickName = :name")
    List<Comments> findByNickName(@Param("name") String name);
}
