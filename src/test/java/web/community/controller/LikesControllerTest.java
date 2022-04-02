package web.community.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import web.community.repository.LikesRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LikesControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private LikesRepository likesRepository;

    @Test
    @Description("like 갯수 증가 테스트")
    @Transactional
    void likeTest() throws Exception{
        int beforeLikeCount = likesRepository.findLikeCount(5L);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/likes/5")
                        .header(HttpHeaders.AUTHORIZATION, "LESSEE 3")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("success"));


        int afterLikeCount = likesRepository.findLikeCount(5L);
        assertThat(afterLikeCount).isEqualTo(beforeLikeCount+1);
    }

    @Test
    @Description("like 취소 테스트")
    @Transactional
    void likeCancelTest() throws Exception{
        int beforeLikeCount = likesRepository.findLikeCount(5L);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/likes/5")
                        .header(HttpHeaders.AUTHORIZATION, "LESSEE 3")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        int afterLikeCount = likesRepository.findLikeCount(5L);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/likes/5")
                        .header(HttpHeaders.AUTHORIZATION, "LESSEE 3")
                )
                .andExpect(status().isOk())
                .andExpect(content().string("success"));

        int cancelLikeCount = likesRepository.findLikeCount(5L);

        assertThat(afterLikeCount).isEqualTo(beforeLikeCount+1);
        assertThat(cancelLikeCount).isEqualTo(beforeLikeCount);
    }

    @Test
    @Description("존재하지 않는 게시물에 like 요청 테스트")
    void NoSuchCommunityItemLikeTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/likes/100")
                        .header(HttpHeaders.AUTHORIZATION, "LESSOR 1")
                )
                .andExpect(status().isInternalServerError());
    }

    @Test
    @Description("존재하지 않는 게시물에 like 요청 테스트")
    void emLikeTest() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/likes/1")
                        .header(HttpHeaders.AUTHORIZATION, "LESS")
                )
                .andExpect(status().isInternalServerError());
    }
}
