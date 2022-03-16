package web.community.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import web.community.domain.dto.CommunityItemDTO;
import web.community.repository.CommunityItemRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CommunityItemControllerTest {
    @Autowired private MockMvc mockMvc;
    @Autowired private CommunityItemRepository communityItemRepository;

    /** 전체 게시글 가져오는 테스트 */

    @Test
    @Description("CommunityItem 전체 데이터 GET 검증")
    void getAllDataTest() throws Exception {

        List<CommunityItem> allData = communityItemRepository.findAll();
        int allDataSize = allData.size();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/getItemsAll")
                        .header(HttpHeaders.AUTHORIZATION, "LESSOR 1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(allDataSize)));
    }

    @Test
    @Description("Request Header에 Authorization 값이 Null인 경우 테스트")
    void getAllDataByAuthNullTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/getItemsAll")
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Request Header에 Authorization 값이 잘못된 경우 테스트")
    void getAllDataByWrongAuthTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/community/getItemsAll")
                        .header(HttpHeaders.AUTHORIZATION, "LES")
                )
                .andExpect(status().isInternalServerError());
    }


    /** 게시글 저장 테스트 */

    @Test
    @Description("게시글 저장 테스트")
    void saveItemTest() throws Exception {

        CommunityItemDTO.SaveItemDTO saveItemDTO = new CommunityItemDTO.SaveItemDTO();
        saveItemDTO.setTitle("좋아요,,,");
        saveItemDTO.setContents("사용하기 편리해요.");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/community/saveItem")
                        .header(HttpHeaders.AUTHORIZATION, "REALTOR 4")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(saveItemDTO))
                )
                .andExpect(status().isOk());

        Optional<CommunityItem> getData = communityItemRepository.findLast();
        CommunityItem lastData = getData.get();

        assertThat(saveItemDTO.getTitle()).isEqualTo(lastData.getTitle());
        assertThat(saveItemDTO.getContents()).isEqualTo(lastData.getContents());
    }

    @Test
    @Description("param이 빠진 경우 에러 테스트")
    void saveItemParamErrorTest() throws Exception {

        CommunityItemDTO.SaveItemDTO saveItemDTO = new CommunityItemDTO.SaveItemDTO();
        saveItemDTO.setContents("사용하기 편리해요.");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/community/saveItem")
                        .header(HttpHeaders.AUTHORIZATION, "REALTOR 4")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(saveItemDTO))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    @Description("Header AUTHORIZATION values가 빠진 경우 에러 테스트")
    void saveItemHeaderErrorTest() throws Exception {

        CommunityItemDTO.SaveItemDTO saveItemDTO = new CommunityItemDTO.SaveItemDTO();
        saveItemDTO.setTitle("좋아요,,,");
        saveItemDTO.setContents("사용하기 편리해요.");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/community/saveItem")
                        .header(HttpHeaders.AUTHORIZATION, "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(saveItemDTO))
                )
                .andExpect(status().isInternalServerError());
    }

    /** 게시글 업데이트 테스트 */

    @Test
    @Description("게시글 업데이트 테스트")
    @Transactional
    void updateItemTest() throws Exception {

        CommunityItemDTO.UpdateItemDTO updateItemDTO = new CommunityItemDTO.UpdateItemDTO();
        updateItemDTO.setTitle("업데이트 게시물 테스트");

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/community/updateItem/5")
                        .header(HttpHeaders.AUTHORIZATION, "LESSEE 5")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updateItemDTO))
                )
                .andExpect(status().isOk());

        Optional<CommunityItem> getItem = communityItemRepository.findById(5L);
        assertThat(updateItemDTO.getTitle()).isEqualTo(getItem.get().getTitle());
    }


    /** 게시글 삭제 테스트 */

    @Test
    @Description("게시글 삭제 테스트")
    void deleteItemTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/community/deleteItem/1")
                        .header(HttpHeaders.AUTHORIZATION, "LESSOR 1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());

        Optional<CommunityItem> getItem = communityItemRepository.findById(1L);
        assertThat(getItem.isPresent()).isEqualTo(false);
    }


}
