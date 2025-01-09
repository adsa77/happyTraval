package com.happyTravel.common.validation.user;

////  유효성 검사 로직을 수행할 클래스 지정
//@Constraint(validatedBy = UserLoginDataValidator.class)
////  UserSignUpDtoReq 클래스 전체에 적용
//@Target({ ElementType.TYPE })
////  애너테이션의 수명 주기를 정의하기 위한 Retention 임포트. 실행 시에도 애너테이션이 유지됨
//@Retention(RetentionPolicy.RUNTIME)
//public @interface ValidUserLoginData {
//
//    //  검증 그룹 배열 (기본값: 빈 배열)
//    Class<?>[] groups() default {};
//
//    //  추가 메타데이터를 위한 페이로드 배열 (기본값: 빈 배열)
//    Class<? extends Payload>[] payload() default {};
//
//    //  검증 실패 시 사용할 사용자 정의 에러 코드
//    ErrorCode errorCode(); // 에러 코드 추가
//
//}
