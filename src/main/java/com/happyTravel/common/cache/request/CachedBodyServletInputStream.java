package com.happyTravel.common.cache.request;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;

// 요청 본문을 캐시된 바이트 배열로부터 읽기 위한 ServletInputStream 클래스
public class CachedBodyServletInputStream extends ServletInputStream {

    // 캐시된 본문을 읽기 위한 ByteArrayInputStream
    private final ByteArrayInputStream inputStream;

    // 생성자: 캐시된 바이트 배열을 인자로 받아 ByteArrayInputStream을 초기화
    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.inputStream = new ByteArrayInputStream(cachedBody); // 주어진 바이트 배열로 inputStream 초기화
    }

    // 스트림의 데이터가 더 이상 없으면 true를 반환
    @Override
    public boolean isFinished() {
        return inputStream.available() == 0; // 남아있는 바이트 수가 0이면 true 반환
    }

    // 이 스트림이 언제든지 읽을 준비가 되어 있음을 나타내기 위해 true 반환
    @Override
    public boolean isReady() {
        return true; // 항상 준비 상태로 설정
    }

    // 비동기 읽기를 위한 리스너를 설정하는 메서드
    @Override
    public void setReadListener(ReadListener listener) {
        // 이 메서드는 사용되지 않지만 필수로 구현해야 함
    }

    // 캐시된 본문에서 한 바이트를 읽어 반환
    @Override
    public int read() {
        return inputStream.read(); // ByteArrayInputStream에서 바이트를 읽음
    }
}
