package web.community.domain.dto;

import lombok.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class CommunityItemDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class GetItems {
        private Long id; // 게시글 아이디
        private String title; // 게시글 제목
        private String contents;
        private String isLike; // 사용자가 좋아요를 눌렀는지 여부 Y/N
        private int likeCount;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class SaveItemDTO {
        @NotNull
        private String title;
        @NotNull
        private String contents;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @ToString
    public static class UpdateItemDTO {
        private String title;
        private String contents;
    }



}
