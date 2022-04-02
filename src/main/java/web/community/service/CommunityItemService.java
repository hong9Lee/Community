package web.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import web.community.domain.User;
import web.community.domain.dto.CommunityItemDTO;
import web.community.domain.dto.ResponseDTO;
import web.community.helper.enums.ResponseStatusType;
import web.community.helper.util.Utils;
import web.community.repository.CommunityItemRepository;
import web.community.repository.UserRepository;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityItemService {
    private final CommunityItemRepository itemRepo;
    private final UserRepository userRepo;

    /**
        CommunityItem 저장 메서드
        1. authorization 데이터를 이용하여 user find()
        2. param 데이터를 이용하여 CommunityItem save()
     */
    public ResponseDTO.ResDefault saveCommunityItem(CommunityItemDTO.SaveItemDTO itemDTO,
                                           String authorization) {

        Optional<User> findUser = Utils.getUser(authorization, userRepo);
        if(!findUser.isPresent()) throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");

        CommunityItem item = CommunityItem.builder()
                .user(findUser.get())
                .title(itemDTO.getTitle())
                .contents(itemDTO.getContents())
                .quit("N")
                .build();

        itemRepo.save(item);
        return new ResponseDTO.ResDefault(ResponseStatusType.OK.getCode(), ResponseStatusType.OK.getMessage());
    }

    /**
       CommunityItem 전체를 가져오는 메서드
       1. authorization 데이터를 이용하여 user find()
       2. userId를 이용하여 전체 게시물을 가져온다 (유저가 누른 좋아요 표시를 위해)
       3. DTO에 담아 반환
     */
    public ResponseDTO.ResData getCommunityItemAll(String authorization) {
        Optional<User> findUser = Utils.getUser(authorization, userRepo);
        if(!findUser.isPresent()) throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");

        List<CommunityItem> allItems = itemRepo.findAllItems(findUser.get().getId());

        List<CommunityItemDTO.GetItems> items = new ArrayList<>();
        allItems.forEach(v -> {
            CommunityItemDTO.GetItems setItem = Utils.setItemByDTO(v);
            items.add(setItem);
        });

        return new ResponseDTO.ResData(ResponseStatusType.OK.getCode(), ResponseStatusType.OK.getMessage(), items);
    }

    /**
        CommunityItem update 메서드
        1. authorization 데이터를 이용하여 user find()
        2. CommunityItem update
     */
    public ResponseDTO.ResData updateItem(CommunityItemDTO.UpdateItemDTO itemDTO, Long itemId) {
        Optional<CommunityItem> getItem = itemRepo.findById(itemId);
        if(!getItem.isPresent()) throw new NoSuchElementException("해당 게시물이 존재하지 않습니다.");

        CommunityItem item = getItem.get();
        if(itemDTO.getTitle() != null ) item.setTitle(itemDTO.getTitle());
        if(itemDTO.getContents() != null ) item.setContents(itemDTO.getContents());
        itemRepo.save(item);

        CommunityItemDTO.GetItems tem = Utils.setItemByDTO(item);
        return new ResponseDTO.ResData(ResponseStatusType.OK.getCode(), ResponseStatusType.OK.getMessage(), tem);
    }

    /**
       CommunityItem delete 메서드
        1. authorization 데이터를 이용하여 user find()
        2. 요청보낸 유저가 작성한 글인 경우 삭제
     */
    public ResponseDTO.ResDefault deleteItem(Long id, String authorization) throws EmptyResultDataAccessException {
        Optional<User> findUser = Utils.getUser(authorization, userRepo);
        if(!findUser.isPresent()) throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");

        Long userId = findUser.get().getId();
        Optional<CommunityItem> getItem = itemRepo.findItemByUserIdAndItemId(userId, id);
        if(!getItem.isPresent()) throw new NoSuchElementException("유저가 작성한 게시물이 존재하지 않습니다.");

        itemRepo.deleteById(id);
        return new ResponseDTO.ResDefault(ResponseStatusType.OK.getCode(), ResponseStatusType.OK.getMessage());
    }
}
