package web.community.helper.util;

import web.community.domain.CommunityItem;
import web.community.domain.Likes;
import web.community.domain.User;
import web.community.domain.dto.CommunityItemDTO;
import web.community.helper.enums.ResponseStatusType;
import web.community.helper.exception.Error;
import web.community.helper.exception.ErrorResponse;
import web.community.repository.LikesRepository;
import web.community.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static List<String> authList = new ArrayList<>(Arrays.asList("LESSOR", "REALTOR", "LESSEE"));

    /** Exception의 printStackTrace.toString()을 생성 */
    public static String stackTractToString(Exception e) {
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        return errors.toString();
    }

    /** authorization의 userId를 이용해 유저 찾아오는 메서드 */
    public static Optional<User> getUser(String authorization, UserRepository userRepository) {
        Long authorUserId = getUserId(authorization);
        Optional<User> findUser = userRepository.findById(authorUserId);
        return findUser;
    }

    /** authorization로 부터 userId 추출  */
    public static Long getUserId(String authorization) {
        String[] s = authorization.split(" ");
        if(s.length != 2 || !authList.contains(s[0])) throw new IndexOutOfBoundsException("authorization format 에러");
        return Long.parseLong(s[1]);
    }

    /** Likes 객체 생성 (user, communityItem) */
    public static void createLikes(User user, CommunityItem item, LikesRepository likesRepository) {
        Likes likes = new Likes();
        likes.setUser(user);
        likes.setCommunityItem(item);

        item.addLikes(likes);
        likesRepository.save(likes);
    }

    /** CommunityItem -> GetItems DTO 형태로 build */
    public static CommunityItemDTO.GetItems setItemByDTO(CommunityItem item) {
        return CommunityItemDTO.GetItems.builder()
                .title(item.getTitle())
                .id(item.getId())
                .contents(item.getContents())
                .isLike(item.getIsLike())
                .likeCount(item.getLikeCount())
                .updatedAt(item.getUpdatedAt())
                .createdAt(item.getCreatedAt())
                .build();
    }

    /** 에러 상태값 ErrorResponse DTO 형태로 SET */
    public static ErrorResponse getErrResponse(List<Error> errorList,
                                        HttpServletRequest httpServletRequest,
                                        ResponseStatusType resType) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorList(errorList);
        errorResponse.setRequestUrl(httpServletRequest.getRequestURI());
        errorResponse.setMessage(resType.getMessage());
        errorResponse.setStatus(resType.getCode());
        return errorResponse;
    }

    /** errorList에 들어갈 에러 메시지 SET */
    public static Error getErrMsg(String field, String msg) {
        Error errorMsg = new Error();
        errorMsg.setField(field);
        errorMsg.setMessage(msg);

        return errorMsg;
    }
}
