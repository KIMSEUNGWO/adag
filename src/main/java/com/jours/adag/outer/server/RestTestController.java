package com.jours.adag.outer.server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTestController {

    /**
     *
     * @return
     */
    @GetMapping("/api")
    public ResponseEntity<String> apiTest(@RequestParam int code) {
        System.out.println("code = " + code);
        if (code == 403) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (code == 404) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok("OK");
    }
}
