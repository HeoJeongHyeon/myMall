# 개인공부용 프로젝트 
>### 개발환경
- Project: `Gradle - Groovy` 
- Language: `Java 17` 
- Spring Boot: `3.3.5`
  - `Spring Web`
  - `Spring Data JPA`
  - `Spring Security`
  - `Spring Validation`
- Packaging: `Jar`
- template engine : `thymeleaf`
- DataBase : `MySQL Driver`

>### 프로젝트 공부 기간
- `24.11.21 - 24.11.28`
- `1주안에 기능이 돌아가게끔 하는 것에 집중`
- `적용 방법에 집중`

<details>
  <summary><h2> 에러 </h2></summary>

### 에러 
Spring Security에서 로그인 성공 후, 인증된 사용자의 정보를 관리하기 위해 UserDetails 객체를 세션에 저장하는 이 과정에서 Serializable 문제가 발생.
Spring Security에서 로그인 성공 시, UserDetails 객체가 세션에 저장되고,
이 과정에서 객체를 직렬화 가능한 형태로 변환해야 하는데, 직렬화가 불가능한 필드가 있거나 Serializable 인터페이스를 구현하지 않은 경우 문제가 발생하게 된다는 것.
직렬화가 필요한 이유는 세션 데이터를 여러 서버 간 공유하거나 외부 저장소에 저장할 때 필수적이기 때문.
UserDeatils에 직렬화 인터페이스를 추가해줬더니 해결되었음. 

### 직렬화와 역직렬화 
- 객체 → 직렬화 → 바이트 스트림 (01010101)
객체를 파일, 네트워크 등 외부 저장소에 저장하거나 전송할 수 있도록 바이트 형태로 변환.
- 바이트 스트림 (01010101) → 역직렬화 → 객체
바이트 데이터를 다시 원래의 Java 객체로 복원.



  
</details>


<details>
  <summary><h2> 느낀점 </h2></summary>

  스파게티 코드.
졸작이후 2번째 단순한 개인 공부 프젝이지만, 그래도 첫 개인 공부 프젝보다는 비즈니스로직을 작성하는 것이 매끄러워진듯.
rest api 활용한 내가 만들고 싶은 것을 구현해볼 생각.
처음부터 기능에 욕심내지말고, 틀을 구축한 후에, 필요한 것을 찾고 분리하면서 체계적으로 개발하는게 중요하다는 것을 느낌.
화면 구현에 있어서 조금 힘들어서, 도움을 받았습니다.

</details>


<details>
  <summary><h2>메인기능</h2></summary>
  
- 관리자와 유저 분리
- 로그인 상태 관리,유지 JSESSIONID + remember-me
  - 브라우저 종료 → JSESSIONID 삭제 → 세션 종료
    브라우저 종료 후 재접속 → remember-me 쿠키로 자동 로그인
    tokenValiditySeconds 시간 경과 → 쿠키 만료 → 재로그인 필요
- 장바구니에서 주문


</details>

<details>
  <summary><h2>ERD</h2></summary>
  
![image](https://github.com/user-attachments/assets/4ce38c13-6ae1-433d-95c5-c74e2a870dc6)

</details>


  
<details>
  <summary><h2> 이미지 </h2></summary>

> ### 화면
- **로그인**
![image](https://github.com/user-attachments/assets/8d2e05d2-1b0c-4658-a387-ea0520df1fce)

- 회원가입
![image](https://github.com/user-attachments/assets/3d2fb633-9d88-41f7-9785-020a0bedff13)

- 회원화면
![image](https://github.com/user-attachments/assets/e2f5220a-e4c8-41c9-91a3-10a83fb73227)

- 상품 목록
![image](https://github.com/user-attachments/assets/3b66702a-b319-44b9-abd7-30cdd6c7f0ee)

- 상품 상세
![image](https://github.com/user-attachments/assets/be2ae826-2b65-4b87-be2c-faa182c074c1)

- 장바구니
![image](https://github.com/user-attachments/assets/23804918-6343-459f-acd8-98861e1b304a)

- 주문서작성
![image](https://github.com/user-attachments/assets/27cd9f55-6152-42a4-836d-1e7ea0b60302)

- 주소 추가
![image](https://github.com/user-attachments/assets/e5df6741-489d-4439-b9d8-743bcc675a63)

- 주문
![image](https://github.com/user-attachments/assets/ce3a841e-0f71-4712-bfb4-acd856ebddd3)

- 관리자 페이지
![image](https://github.com/user-attachments/assets/9353f273-554c-4617-ad3f-1aa723d62bc9)

- 상품 등록
![image](https://github.com/user-attachments/assets/45d1fbc1-8263-45f9-b8b2-76d84e7ea62c)

  
</details>


