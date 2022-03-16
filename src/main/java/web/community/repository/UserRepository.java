package web.community.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import web.community.domain.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    /** NickName으로 유저 찾아오는 쿼리 */
    User findByNickName(String name);
}
