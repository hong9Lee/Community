package web.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.community.domain.dto.CommunityItemDTO;
import web.community.domain.dto.ResponseDTO;
import web.community.service.CommunityItemService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CommunityItemController {

    private final CommunityItemService commService;

    /**
     1. 전체 Community 게시글 가져오기

     authorization ex)
        Request Header -> Authorization -> LESSEO 1
     */
    @GetMapping("/community/items")
    public ResponseEntity getItemList(@RequestHeader String authorization) {
        ResponseDTO.ResData res = commService.getCommunityItemAll(authorization);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     2. Community 게시글 저장

     param ex)
         {
         "title" : "또 이용하고 싶어요",
         "contents" : "써보니까 도움이 됐어요~"
         }

     authorization ex)
        Request Header -> Authorization -> LESSEO 1

    */
    @PostMapping("/community/items")
    public ResponseEntity saveItem(@Valid @RequestBody CommunityItemDTO.SaveItemDTO itemDTO,
                                   @RequestHeader String authorization) {
        ResponseDTO.ResDefault res = commService.saveCommunityItem(itemDTO, authorization);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     3. Community 게시글 업데이트
     */
    @PostMapping("/community/items/{id}")
    public ResponseEntity updateItem(@Valid @RequestBody CommunityItemDTO.UpdateItemDTO itemDTO,
                                     @PathVariable("id") Long itemId) {

        Object res = commService.updateItem(itemDTO, itemId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     4. Community 게시글 삭제
     */
    @DeleteMapping("/community/items/{id}")
    public ResponseEntity deleteItem(@PathVariable("id") Long id,
                                     @RequestHeader String authorization) {
        ResponseDTO.ResDefault res = commService.deleteItem(id, authorization);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
