package com.example.wordlyweek.repository;

import com.example.wordlyweek.model.*;
import java.util.*;

public interface WriterRepository {
    ArrayList<Writer> getWriters();

    Writer getWriter(int id);

    Writer addWriter(Writer writer);

    Writer updateWriter(int id, Writer writer);

    void deleteWriter(int id);

    List<Magazine> getWriterMagazine(int id);
}