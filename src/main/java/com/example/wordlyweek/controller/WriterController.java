package com.example.wordlyweek.controller;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.service.WriterJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class WriterController {
    @Autowired
    public WriterJpaService writerService;

    @GetMapping("/magazines/writers")
    public ArrayList<Writer> getWriters() {
        return writerService.getWriters();
    }

    @GetMapping("/magazines/writers/{writerId}")
    public Writer getWriter(@PathVariable("writerId") int id) {
        return writerService.getWriter(id);
    }

    @GetMapping("/writers/{writerId}/magazines")
    public List<Magazine> getWriterMagazine(@PathVariable("writerId") int id) {
        return writerService.getWriterMagazine(id);
    }

    @PostMapping("/magazines/writers")
    public Writer addWriter(@RequestBody Writer writer) {
        return writerService.addWriter(writer);
    }

    @PutMapping("/magazines/writers/{writerId}")
    public Writer updatWriter(@PathVariable("writerId") int id, @RequestBody Writer writer) {
        return writerService.updateWriter(id, writer);
    }

    @DeleteMapping("/magazines/writers/{writerId}")
    public void deleteWriter(@PathVariable("writerId") int id) {
        writerService.deleteWriter(id);
    }
}