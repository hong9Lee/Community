# community
spring data jpa를 활용하여 community 백엔드 페이지 구축

1. 실행방법
community>src>main>java>web>community>CommunityApplication.java 실행

2. 구현방식
SpringBoot v2.6.4, SpringDataJpa, h2를 사용함.
Domain, Controller, Service, Repository로 나누어 작업함.
게시물 등록, 삭제, 업데이트는 EntityListener를 활용하여 CommunityItemHistory 엔티티에 저장함.

 * erd
![erdImage](https://user-images.githubusercontent.com/94272140/158561416-6bb602d7-8dec-4eb4-8c1e-e1abc26dfd71.png)

3. 검증방식
src>test 폴더에 JUnit을 사용하여 테스트 케이스를 만들어 검증함.

Controller의 경우 PostMan을 검증에 활용함.
1) 공통
 Headers에 Authorization: account_type + account_id를 넣음.
 authorization ex)
        Request Header -> Authorization -> LESSEO 1
        
2) url, param
 * 전체 게시글 검색
  -> URL : GET /community/getItemsAll 
 
 * 게시글 추가
  -> URL : POST /community/saveItem
  -> PARAM ex) :
         {
         "title" : "또 이용하고 싶어요.",
         "contents" : "써보니까 도움이 됐어요."
         }
 
 * 게시글 업데이트
  -> URL : POST /community/updateItem/{id}
  -> PARAM ex) :
         {
         "title" : "또 이용하고 싶어요.",
         "contents" : "써보니까 도움이 됐어요."
         }
         
 * 게시글 삭제
  -> URL : DELETE /community/deleteItem/{id}
