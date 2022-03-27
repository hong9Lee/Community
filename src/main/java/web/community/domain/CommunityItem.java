package web.community.domain;

import lombok.*;
import web.community.helper.listener.BaseTimeEntity;
import web.community.helper.listener.CommunityItemEntityListener;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(value = {CommunityItemEntityListener.class})
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@Cacheable
public class CommunityItem extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title; // 글 제목
    private String contents; // 내용

    private String quit; // 논리 삭제 여부
    private int likeCount;
    private String isLike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // 작성자

    @OneToMany(
            mappedBy = "communityItem"
            , fetch = FetchType.LAZY
            , cascade = {CascadeType.ALL}
    )
    @ToString.Exclude
    private List<Likes> likedList = new ArrayList<>();

    @OneToMany(mappedBy = "communityItem"
            , fetch = FetchType.LAZY
            , cascade = {CascadeType.ALL}
    )
    @ToString.Exclude
    private List<Comments> commentsList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_item_id", insertable = false, updatable = false)
    @ToString.Exclude
    private List<CommunityItemHistory> itemHistoryList = new ArrayList<>();

    public void addCommunityItem(Comments... commentsList) {
        Collections.addAll(this.commentsList, commentsList);
    }
    public void addLikes(Likes like) {
        this.likedList.add(like);
    }
}
