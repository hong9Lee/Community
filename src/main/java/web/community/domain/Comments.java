package web.community.domain;

import lombok.*;
import web.community.helper.listener.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
/** 댓글 Entity */
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 아이디

    private String contents; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_item_id")
    private CommunityItem communityItem; // 게시글

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(name = "user_id")
    private User user;

}
