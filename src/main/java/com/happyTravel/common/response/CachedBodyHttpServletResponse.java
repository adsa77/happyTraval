package com.happyTravel.common.response;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

//  HttpServletResponse의 응답 본문을 캐시하는 클래스
public class CachedBodyHttpServletResponse extends HttpServletResponseWrapper {

    //  응답 본문을 저장할 ByteArrayOutputStream
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    //  PrintWriter 객체를 선언
    private PrintWriter writer;

    //  생성자: 원본 HttpServletResponse 객체를 받아서 초기화
    public CachedBodyHttpServletResponse(HttpServletResponse response) {
        super(response);
    }

    //  getWriter() 메서드 재정의: PrintWriter 객체를 반환
    @Override
    public PrintWriter getWriter() throws IOException {
        //  writer가 초기화되지 않았다면
        if (writer == null) {
            //  ByteArrayOutputStream에 연결된 PrintWriter 생성
            writer = new PrintWriter(outputStream);
        }
        return writer;
    }

    //  캐시된 응답 본문을 바이트 배열로 반환하는 메서드
    public byte[] getContentAsByteArray() {
        //  ByteArrayOutputStream의 내용을 바이트 배열로 반환
        return outputStream.toByteArray();
    }

    //  flushBuffer() 메서드 재정의: 버퍼를 비우고 원본 응답으로 플러시
    @Override
    public void flushBuffer() throws IOException {
        // writer가 null이 아닌 경우에만 flush를 수행
        if (writer != null) {
            writer.flush();
        }
        // 원본 응답의 flushBuffer() 메서드 호출
        super.flushBuffer();
    }
}
