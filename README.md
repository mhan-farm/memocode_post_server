
# 진행사항
- cloudfront url을 pre-signed url로 변경하는 api 구현
- post contoller와 service를 command(create,update,delete)와 query(read)를 기준으로 나눔
- post create, update(patch), delete 구현
- opaque token을 사용하여 access token을 받아 매 요청마다 인증서버에 사용자의 정보를 요청함, 이득적인 부분은 화이트리스트를 통해 리프레시 토큰없이 액세스토큰을 제어할 수 있음
- post 전체조회, 단건조회, 내가 쓴 게시글 전체조회 구현
- post 이미지 처리 구현(aws s3와 cdn을 통해 이미지 업로드)
- 시리즈 구현

# 미구현
- 카테고리 구현
- 좋아요 구현
- 댓글 구현

# 추후 구현 예정
- 이미지, 파일 보안(pre-signed url을 통해 접근 제한)