package web.community.domain;

import lombok.*;
import web.community.helper.enums.AccountType;
import web.community.helper.listener.BaseTimeEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Table(indexes = @Index(name = "i_user", columnList = "nickname"))
@Cacheable // 엔티티 캐시
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id; // 회원id

    private String publicName; // 화면에 표출될 nickName + accountType
    private String nickName; // 닉네임
    private String accountId; // 계정ID

    @Enumerated(EnumType.STRING)
    private AccountType accountType; // 계정타입 ( LESSOR : 임대인, REALTOR : 공인 중개사, LESSEE : 임차인 )
    private String quit; // 탈퇴여부

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user"
            , cascade = {CascadeType.ALL}
            , orphanRemoval = true
    )
    @ToString.Exclude
    private List<CommunityItem> communityItemList = new ArrayList<>();

    public void addCommunityItem(CommunityItem... communityItems) {
        Collections.addAll(this.communityItemList, communityItems);
    }

    public String setPublicName() {
        if (this.accountType == AccountType.LESSOR) {
            return "(임대인)";
        } else if (this.accountType == AccountType.REALTOR) {
            return "(공인 중개사)";
        } else if (this.accountType == AccountType.LESSEE) {
            return "(임차인)";
        }
        return "";
    }

    @Builder
    public User(String nickName, String accountId, String quit) {
        this.nickName = nickName;
        this.accountId = accountId;
        this.quit = quit;
    }




}
