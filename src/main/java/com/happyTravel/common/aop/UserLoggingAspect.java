//package com.happyTravel.common.aop;
//
//import com.happyTravel.user.dto.UserLoginDtoReq;
//import com.happyTravel.user.dto.UserSignUpDtoReq;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.AfterThrowing;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.stereotype.Component;
//
//import java.util.UUID;
//
//@Aspect
//@Component
//public class UserLoggingAspect {
//
//    //  로거 설정: UserLoggingAspect 클래스에 대한 로깅 객체 생성
//    private static final Logger logger = LoggerFactory.getLogger(UserLoggingAspect.class);
//
//    // MDC 키 상수
//    private static final String USER_ID_KEY = "입력 아이디";
//    private static final String REQUEST_ID_KEY = "요청 아이디";
//
//    //  모든 메서드 호출 전 요청 ID 설정
//    @Before("execution(* com.happyTravel.user.service.UserService.*(..))")
//    public void setRequestId() {
//        // 요청 ID를 생성하여 MDC에 추가
//        String requestId = UUID.randomUUID().toString();
//        MDC.put(REQUEST_ID_KEY, requestId);
//    }
//
//    //  회원 가입 메서드 호출 전 로깅
//    //  Pointcut: UserService의 userSignUp 메서드가 실행되기 전에 동작
//    @Before("execution(* com.happyTravel.user.service.UserService.userSignUp(..))")
//    public void logBeforeRegistration(JoinPoint joinPoint) {
//        //  메서드에 전달된 인자들을 가져옴
//        Object[] args = joinPoint.getArgs();
//        //  첫 번째 인자가 UserSignUpDtoReq 타입인지 확인하고 로깅 시작
//        //  instanceof로 객체 타입 확인
//        if (args.length > 0 && args[0] instanceof UserSignUpDtoReq userSignUpData) {
//            //  사용자 ID를 MDC(Mapped Diagnostic Context)에 추가하여 로깅 시 사용자 정보를 포함
//            MDC.put(USER_ID_KEY, userSignUpData.getUserId());
//            //  회원 가입 시작 로깅
//            logger.info("회원가입 시작    요청 아이디: {}   입력아이디: {}", MDC.get(REQUEST_ID_KEY), userSignUpData.getUserId());
//        }
//    }
//
//    //  회원 가입 성공 후 로깅
//    //  Pointcut: userSignUp 메서드가 성공적으로 완료된 후 동작
//    @AfterReturning(pointcut = "execution(* com.happyTravel.user.service.UserService.userSignUp(..))", returning = "result")
//    public void logAfterRegistration(JoinPoint joinPoint, Object result) {
//        Object[] args = joinPoint.getArgs();
//        //  첫 번째 인자가 UserSignUpDtoReq 타입인지 확인하고 로깅
//        if (args.length > 0 && args[0] instanceof UserSignUpDtoReq userSignUpData) {
//            //  사용자 ID, 요청 ID 추가
//            logger.info("회원가입 성공    요청 아이디: {}    입력 아이디: {}", MDC.get(REQUEST_ID_KEY), userSignUpData.getUserId());
//        }
//        //  MDC를 클리어하여 불필요한 데이터가 남지 않도록 처리
//        MDC.clear();
//    }
//
//    //  회원 가입 중 예외 발생 시 로깅
//    //  Pointcut: userSignUp 메서드에서 예외가 발생한 경우 동작
//    @AfterThrowing(pointcut = "execution(* com.happyTravel.user.service.UserService.userSignUp(..))", throwing = "ex")
//    public void logRegistrationError(JoinPoint joinPoint, Throwable ex) {
//        Object[] args = joinPoint.getArgs();
//        //  첫 번째 인자가 UserSignUpDtoReq 타입인지 확인하고 예외 발생 로깅
//        if (args.length > 0 && args[0] instanceof UserSignUpDtoReq userSignUpData) {
//            //  사용자 ID, 요청 ID 추가
//            logger.error("회원가입 실패   요청 아이디: {}  입력 아이디: {}  오류 메시지: {} ", MDC.get(REQUEST_ID_KEY), userSignUpData.getUserId(), ex.getMessage());
//        }
//        //  MDC 클리어
//        MDC.clear();
//    }
//
//    //  로그인 메서드 호출 전 로깅
//    //  Pointcut: UserService의 userLogin 메서드가 실행되기 전에 동작
//    @Before("execution(* com.happyTravel.user.service.UserService.userLogin(..))")
//    public void logBeforeLogin(JoinPoint joinPoint) {
//        Object[] args = joinPoint.getArgs();
//        //  첫 번째 인자가 UserLoginDtoReq 타입인지 확인하고 로그인 시도 로깅
//        if (args.length > 0 && args[0] instanceof UserLoginDtoReq userLoginData) {
//            //  사용자 ID를 MDC에 추가
//            MDC.put("입력 아이디", userLoginData.getUserId());
//            logger.info("로그인 시도 요청 아이디: {}  입력 아이디: {}", MDC.get(REQUEST_ID_KEY), userLoginData.getUserId());
//        }
//    }
//
//    //  로그인 성공 후 로깅
//    //  Pointcut: userLogin 메서드가 성공적으로 완료된 후 동작
//    @AfterReturning(pointcut = "execution(* com.happyTravel.user.service.UserService.userLogin(..))", returning = "result")
//    public void logAfterLogin(JoinPoint joinPoint, Object result) {
//        Object[] args = joinPoint.getArgs();
//        //  첫 번째 인자가 UserLoginDtoReq 타입인지 확인하고 로그인 성공 로깅
//        if (args.length > 0 && args[0] instanceof UserLoginDtoReq userLoginData) {
//            //  사용자 ID, 요청 ID 추가
//            logger.info("로그인 성공 요청 아이디: {}  입력 아이디: {}", MDC.get(REQUEST_ID_KEY), userLoginData.getUserId());
//        }
//        //  MDC 클리어
//        MDC.clear();
//    }
//
//    //  로그인 중 예외 발생 시 로깅
//    //  Pointcut: userLogin 메서드에서 예외가 발생한 경우 동작
//    @AfterThrowing(pointcut = "execution(* com.happyTravel.user.service.UserService.userLogin(..))", throwing = "ex")
//    public void logLoginError(JoinPoint joinPoint, Throwable ex) {
//        Object[] args = joinPoint.getArgs();
//        //  첫 번째 인자가 UserLoginDtoReq 타입인지 확인하고 로그인 실패 로깅
//        if (args.length > 0 && args[0] instanceof UserLoginDtoReq userLoginData) {
//            //  사용자 ID, 요청 ID 추가
//            logger.error("로그인 실패    요청 아이디: {}  입력 아이디: {}  오류 메시지={}", MDC.get(REQUEST_ID_KEY), userLoginData.getUserId(), ex.getMessage());
//        }
//        //  MDC 클리어
//        MDC.clear();
//    }
//
//}
