package web.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.User;
import web.community.repository.UserRepository;


import javax.persistence.EntityManager;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final EntityManager em;
    private final CacheManager cacheManager;




    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;


    public void saveUsers() {
        ArrayList<User> list = new ArrayList<>();

        for (int i = 0; i < 5000; i++) {
            list.add(
                    User.builder()
                            .nickName("test " + i)
                            .accountId("test " + i)
                            .quit("N")
                            .build()
            );
        }

        userRepository.saveAll(list);
    }

    @Cacheable(cacheNames = "findUserCache")
    public User findUsersByIndex() {
//        System.out.println("\n\n" + "=========================================================\n"
//                + "Using cache manager: " + this.cacheManager.getClass().getName() + "\n"
//                + "=========================================================\n\n");
//        Cache findUserCache = cacheManager.getCache("findUserCache");
//        System.out.println(findUserCache);
        return userRepository.findByNickName("test 4997");
    }

}
