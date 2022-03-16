package web.community.domain;

import lombok.*;
import web.community.helper.enums.CrudType;
import web.community.helper.listener.BaseTimeEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CommunityItemHistory extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private CrudType status; // 저장상태

    private String title; // 글 제목
    private String contents; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private CommunityItem communityItem;

    private Long itemId;

    public void setCommunityItem(CommunityItem communityItem) {
        setItemId(communityItem.getId());
        this.communityItem = communityItem;
    }
}
