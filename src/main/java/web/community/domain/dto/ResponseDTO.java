package web.community.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import web.community.helper.enums.ResponseStatusType;


public class ResponseDTO {

    @Data
    @AllArgsConstructor
    @Builder
    public static class ResDefault {
        private String status;
        private String message;

        public ResDefault() {
            this.status = ResponseStatusType.BAD_REQUEST.getCode();
            this.message = ResponseStatusType.BAD_REQUEST.getMessage();
        }
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ResData {
        private String status;
        private String message;
        private Object data;

        public ResData() {
            this.status = ResponseStatusType.BAD_REQUEST.getCode();
            this.message = ResponseStatusType.BAD_REQUEST.getMessage();
            this.data = null;
        }
    }




}
