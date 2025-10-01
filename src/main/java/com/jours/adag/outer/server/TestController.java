package com.jours.adag.outer.server;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TestController {

    @GetMapping("/test")
    public String index(@RequestParam(required = false, name = "id", defaultValue = "asdfasdf") String id) {
        return "index";
    }


    @GetMapping("/member/{id}/{name}")
    public String findByNameAndId(@PathVariable("id") String id, @PathVariable("name") String name) {
        return "ID: " + id + ", name: " + name;
    }

    /**
     * 컨트롤러 메소드 주석 테스트
     *
     * 컨트롤러 메소드 주석 설명 칸입니다~~
     * ~~~~~
     * @param requestTestEntity asdfasdf
     * @return
     */
    @Deprecated
    @PostMapping("/posttest")
    public ResponseEntity<ResponseTestEntity> testEntities(@RequestBody RequestTestEntity requestTestEntity) {
        ResponseTestEntity entity = new ResponseTestEntity();
        entity.setMessage("테스트~");
        entity.setEntities(List.of(new ResponseTestEntity.Entity("김승우", "인천"), new ResponseTestEntity.Entity("박양립", "부천")));
        return ResponseEntity.ok(entity);
    }
}
