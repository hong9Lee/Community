package web.community.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.community.domain.CommunityItem;
import web.community.domain.Likes;
import web.community.domain.User;
import web.community.helper.util.Utils;
import web.community.repository.CommunityItemRepository;
import web.community.repository.LikesRepository;
import web.community.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikesService {
    private final LikesRepository likesRepository;
    private final CommunityItemRepository communityItemRepository;
    private final UserRepository userRepository;

    /**
       좋아요 추가/삭제 메서드
       1. get item, user
       2. 좋아요 여부 파악 -> 좋아요 추가/삭제
     */
    public void apply(String authorization, Long itemId) {
        Optional<CommunityItem> getItem = communityItemRepository.findById(itemId);
        if(!getItem.isPresent()) throw new NoSuchElementException("해당 게시물이 존재하지 않습니다.");

        Optional<User> findUser = Utils.getUser(authorization, userRepository);
        if(!findUser.isPresent()) throw new NoSuchElementException("해당 유저가 존재하지 않습니다.");

        // 좋아요 유무 파악
        if (isLiked(itemId, getItem, findUser)) return;
        Utils.createLikes(findUser.get(), getItem.get(), likesRepository);
    }

    private boolean isLiked(Long itemId, Optional<CommunityItem> getItem, Optional<User> findUser) {
        Optional<Likes> likeByUser = likesRepository.findByUserIdAndItemId(findUser.get().getId(), itemId);

        if(likeByUser.isPresent()) { // 좋아요가 존재할 경우
            Long likeId = likeByUser.get().getId();
            likesRepository.deleteById(likeId);

            List<Likes> likedList = getItem.get().getLikedList();
            likedList.removeIf(v -> v.getId() == likeId);
            return true;
        }
        return false;
    }
}
