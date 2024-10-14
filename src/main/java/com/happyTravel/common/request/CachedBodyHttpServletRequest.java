package com.happyTravel.common.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

//  요청 본문을 캐시하는 HttpServletRequest 래퍼 클래스
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    //  캐시된 요청 본문을 저장할 바이트 배열
    private final byte[] cachedBody;

    //  생성자: HttpServletRequest를 인자로 받아 요청 본문을 읽어와 캐시
    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream inputStream = request.getInputStream();
        this.cachedBody = inputStream.readAllBytes(); // 요청 본문을 읽어와 캐시
    }

    //  요청 본문을 읽기 위한 InputStream을 반환하는 메서드
    @Override
    public ServletInputStream getInputStream() {
        return new CachedBodyServletInputStream(cachedBody);
    }

    //  요청 본문을 읽기 위한 BufferedReader를 반환하는 메서드
    @Override
    public BufferedReader getReader() {
        //  캐시된 본문으로부터 입력 스트림을 생성하여 BufferedReader로 감싸서 반환
        return new BufferedReader(new InputStreamReader(getInputStream(), StandardCharsets.UTF_8));
    }

    //  요청 본문을 문자열로 반환하는 메서드
    public String getBody() {
        //  캐시된 바이트 배열을 UTF-8 문자열로 변환하여 반환
        return new String(cachedBody, StandardCharsets.UTF_8);
    }

    //  요청 본문을 바이트 배열로 반환하는 메서드
    public byte[] getContentAsByteArray() {
        return cachedBody;
    }

}
