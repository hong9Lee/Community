package web.community.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.community.service.LikesService;

@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesService;

    @GetMapping(value = "/community/likes/{itemId}")
    public ResponseEntity addLlikes(@PathVariable("itemId") Long itemId
                            ,@RequestHeader String authorization) {
        likesService.apply(authorization, itemId);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
