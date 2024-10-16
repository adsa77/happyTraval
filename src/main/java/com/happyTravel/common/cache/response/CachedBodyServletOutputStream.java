package com.happyTravel.common.cache.response;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CachedBodyServletOutputStream extends ServletOutputStream {

    // 응답 본문을 저장할 ByteArrayOutputStream
    private final ByteArrayOutputStream outputStream;
    // WriteListener를 저장할 필드
    private WriteListener writeListener;

    // 생성자: ByteArrayOutputStream을 받아서 초기화
    public CachedBodyServletOutputStream(ByteArrayOutputStream outputStream) {
        this.outputStream = outputStream;
    }

    // 응답 본문을 작성하는 메서드
    @Override
    public void write(int b) throws IOException {
        // 바이트를 ByteArrayOutputStream에 기록
        outputStream.write(b);
        // WriteListener가 설정되어 있으면, write가 완료됨을 알리기
        if (writeListener != null) {
            writeListener.onWritePossible();
        }
    }

    // 스트림이 준비 상태인지 확인하는 메서드
    @Override
    public boolean isReady() {
        return true; // 항상 준비 상태로 반환
    }

    // 쓰기 리스너 설정 메서드
    @Override
    public void setWriteListener(WriteListener listener) {
        this.writeListener = listener; // WriteListener 설정
    }

}
