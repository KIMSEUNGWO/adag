package com.jours.adag.outer.server.scanTestServer;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ScanTestController {

    /**
     * @param id ㅁㄴㅇㄹㅁㄴㅇㄹ
     * @return ㅁㄴㅇㄹㅁㄴㅇㄹ
     */
    @GetMapping("/scan")
    public ResponseEntity<String> asdfaefefe(@RequestParam(required = false, name = "id", defaultValue = "asdfasdf") String id) {
        return ResponseEntity.ok("OK");
    }
}
