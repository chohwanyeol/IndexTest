package com.indexTest.IndexTest.Controller;


import com.indexTest.IndexTest.repository.UserNoIndexRepository;
import com.indexTest.IndexTest.repository.UserWithIndexRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {


    private final UserNoIndexRepository userNoIndexRepository;
    private final UserWithIndexRepository userWithIndexRepository;

    @GetMapping("/no-index")
    @Operation(summary = "인덱스 없는 유저 테이블 조회 테스트", description = "1000번 email로 조회 (인덱스 없음)")
    public ResponseEntity<String> noIndexBenchmark() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            userNoIndexRepository.findByEmail("test99999@example.com");
        }
        long end = System.currentTimeMillis();
        return ResponseEntity.ok("No Index 소요 시간: " + (end - start) + "ms");
    }

    @GetMapping("/with-index")
    @Operation(summary = "인덱스 있는 유저 테이블 조회 테스트", description = "1000번 email로 조회 (인덱스 있음)")
    public ResponseEntity<String> withIndexBenchmark() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            userWithIndexRepository.findByEmail("test99999@example.com");
        }
        long end = System.currentTimeMillis();
        return ResponseEntity.ok("With Index 소요 시간: " + (end - start) + "ms");
    }
}
