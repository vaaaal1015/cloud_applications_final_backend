package com.example.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import com.example.demo.entity.Paper;
import com.example.demo.entity.Vot;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class VotController {
    private List<Vot> DB = new ArrayList<>();
    private List<Paper> paperDB = new ArrayList<>();

    @CrossOrigin
    @PostConstruct
    private void initDB() {
        List<String> list;

        list = new ArrayList<>();
        list.add("必定有錢剩");
        list.add("鐵柱磨成針");
        list.add("天下無敵人");
        list.add("點石可成金");
        DB.add(new Vot(1, "只要有恆心 的下一句是什麼？", list));

        list = new ArrayList<>();
        list.add("宋朝");
        list.add("元朝");
        list.add("唐朝");
        list.add("清朝");
        DB.add(new Vot(2, "垂簾聽政始於何時？", list));

        list = new ArrayList<>();
        list.add("是");
        list.add("不是");
        DB.add(new Vot(3, "郭老是不是好老師", list));

        list = new ArrayList<>();
        list.add("是");
        list.add("當然是");
        DB.add(new Vot(4, "這是最棒的投票網站", list));

        list = new ArrayList<>();
        list.add("科科科科科科科科科科科");
        list.add("呵呵呵呵呵呵呵呵呵呵呵");
        list.add(":)");
        DB.add(new Vot(5, "哈哈哈哈哈哈哈哈哈哈哈哈哈哈", list));

        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 4);
        map.put(2, 4);
        map.put(3, 2);
        map.put(4, 2);
        map.put(5, 2);
        for (int i = 0; i < 500; i++) {
            int id = (int) (Math.random() * 5) + 1;
            int op = (int) (Math.random() * map.get(id)) + 1;
            paperDB.add(new Paper(id, op));
        }
    }

    @CrossOrigin
    @GetMapping("/vots/{id}")
    public ResponseEntity<Vot> getVot(@PathVariable("id") int id) {
        for (Vot vot : DB) {
            if (vot.getId() == id) {
                return ResponseEntity.ok().body(vot);
            }
        }
        return ResponseEntity.notFound().build();


    }

    @CrossOrigin
    @GetMapping("/result/{id}")
    public ResponseEntity<List<Integer>> getVotResult(@PathVariable("id") int id) {
        List<Integer> count = new ArrayList<>();

        for (Vot vot : DB) {
            if (vot.getId() == id) {
                for (int i = 0; i < vot.getOption().size(); i++) {
                    count.add(0);
                }
                for (Paper paper : paperDB) {
                    if (paper.getId() == vot.getId()) {
                        count.set(paper.getSelect() - 1, count.get(paper.getSelect() - 1) + 1);
                    }
                }
                return ResponseEntity.ok().body(count);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @CrossOrigin
    @GetMapping("/vots")
    public ResponseEntity<List<Vot>> getVots() {

        // 排序id由大到小
        Collections.sort(DB, new Comparator<Vot>() {
            public int compare(Vot o1, Vot o2) {
                return o2.getId() - o1.getId();
            }
        });

        return ResponseEntity.ok().body(DB);
    }

    @CrossOrigin
    @PostMapping("/create")
    public ResponseEntity<Integer> createVot(@RequestBody Vot request) {

        Vot vot = new Vot(DB.size() + 1, request.getTitle(), request.getOption());
        DB.add(vot);

        // URI location = ServletUriComponentsBuilder.fromCurrentServletMapping().path("/vots/{id}")
        //         .buildAndExpand(vot.getId()).toUri();

        return ResponseEntity.ok().body(vot.getId());
    }

    @CrossOrigin
    @PostMapping("/voting")
    public ResponseEntity<String> voting(@RequestBody Paper request) {
        Paper paper = new Paper(request.getId(), request.getSelect());
        paperDB.add(paper);

        return ResponseEntity.ok().body("OK");
    }

}
