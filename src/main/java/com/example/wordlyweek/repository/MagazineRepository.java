package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.*;
import java.util.*;

public interface MagazineRepository {
    ArrayList<Magazine> getMagazines();

    Magazine getMagazineById(int id);

    Magazine addMagazine(Magazine magazine);

    Magazine updateMagazine(int id, Magazine magazine);

    void deleteMagazine(int id);

    List<Writer> getMagazineWriter(int id);
}