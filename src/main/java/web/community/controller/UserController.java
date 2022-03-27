package web.community.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import web.community.domain.User;
import web.community.service.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/api/bulk/saveTest")
    public void saveUser() {
        long start = System.currentTimeMillis();
        userService.saveUsers();
        log.info("elapsed time : {}", System.currentTimeMillis() - start);
    }

    @GetMapping("/api/cache/test")
    public void cacheTest() {
        long start = System.currentTimeMillis();
        //        List<User> all = userRepository.findAll();
        User user = userService.findUsersByIndex();
        System.out.println(user);
        System.out.println("elapsed time : " + (System.currentTimeMillis() - start));
//        CacheManager cacheManager = ehCacheManager.getCacheManager();
    }


}
