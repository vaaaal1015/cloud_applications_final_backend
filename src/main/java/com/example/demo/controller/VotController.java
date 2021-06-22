package com.example.demo.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import com.example.demo.entity.Paper;
import com.example.demo.entity.Vot;
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

    @PostConstruct
    private void initDB() {
        List<String> list = new ArrayList<>();
        list.add("option 1");
        list.add("option 2");
        list.add("option 3");

        DB.add(new Vot(1, "title", list));
        DB.add(new Vot(2, "title", list));
        DB.add(new Vot(3, "title", list));
        DB.add(new Vot(4, "title", list));
        DB.add(new Vot(5, "title", list));

        paperDB.add(new Paper(1, 3));
        paperDB.add(new Paper(1, 3));
        paperDB.add(new Paper(1, 2));
        paperDB.add(new Paper(1, 1));
        paperDB.add(new Paper(1, 3));
    }

    @GetMapping("/vots/{id}")
    public ResponseEntity<Vot> getVot(@PathVariable("id") int id) {
        for (Vot vot : DB) {
            if (vot.getId() == id) {
                return ResponseEntity.ok().body(vot);
            }
        }
        return ResponseEntity.notFound().build();


    }

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

    @PostMapping("/create")
    public ResponseEntity<Vot> createVot(@RequestBody Vot request) {

        Vot vot = new Vot(DB.size() + 1, request.getTitle(), request.getOption());
        DB.add(vot);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(vot.getId()).toUri();

        return ResponseEntity.created(location).body(vot);
    }

}
