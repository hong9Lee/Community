package web.community.controller;

import net.sf.ehcache.CacheManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.User;
import web.community.repository.UserRepository;
import web.community.service.UserService;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;
    @Autowired private MockMvc mockMvc;



    @Test
//    @Transactional
    public void jpaBulkInsertTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/bulk/saveTest")
                ).andExpect(status().isOk());
    }

    @Test
    public void indexingTest() {
        long start = System.currentTimeMillis();
        User user = userService.findUsersByIndex();
        System.out.println(user);
        System.out.println("elapsed time : " + (System.currentTimeMillis() - start));
    }

    @Test
    @Transactional
    public void cachingTest() {


        long start = System.currentTimeMillis();
//        List<User> all = userRepository.findAll();
        User user = userService.findUsersByIndex();
        System.out.println("elapsed time : " + (System.currentTimeMillis() - start));
//        CacheManager cacheManager = ehCacheManager.getCacheManager();

    }

}
