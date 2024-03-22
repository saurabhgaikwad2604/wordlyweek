package com.example.wordlyweek.controller;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.service.MagazineJpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
public class MagazineController {
    @Autowired
    public MagazineJpaService magazineService;

    @GetMapping("/magazines")
    public ArrayList<Magazine> getMagazines() {
        return magazineService.getMagazines();
    }

    @GetMapping("/magazines/{magazineId}")
    public Magazine getMagazineById(@PathVariable("magazineId") int id) {
        return magazineService.getMagazineById(id);
    }

    @GetMapping("/magazines/{magazineId}/writers")
    public List<Writer> getMagazineWriter(@PathVariable("magazineId") int id) {
        return magazineService.getMagazineWriter(id);
    }

    @PostMapping("/magazines")
    public Magazine addMagazine(@RequestBody Magazine magazine) {
        return magazineService.addMagazine(magazine);
    }

    @PutMapping("/magazines/{magazineId}")
    public Magazine updateMagazine(@PathVariable("magazineId") int id, @RequestBody Magazine magazine) {
        return magazineService.updateMagazine(id, magazine);
    }

    @DeleteMapping("/magazines/{magazineId}")
    public void deleteMagazine(@PathVariable("magazineId") int id) {
        magazineService.deleteMagazine(id);
    }
}