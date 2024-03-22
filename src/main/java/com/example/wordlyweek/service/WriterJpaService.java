package com.example.wordlyweek.service;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class WriterJpaService implements WriterRepository {
    @Autowired
    private WriterJpaRepository writerJpaRepository;

    @Autowired
    private MagazineJpaRepository magazineJpaRepository;

    @Override
    public ArrayList<Writer> getWriters() {
        List<Writer> writerList = writerJpaRepository.findAll();
        ArrayList<Writer> writers = new ArrayList<>(writerList);
        return writers;
    }

    @Override
    public Writer getWriter(int id) {
        try {
            Writer writer = writerJpaRepository.findById(id).get();
            return writer;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer addWriter(Writer writer) {
        try {
            List<Integer> magazineIds = new ArrayList<>();
            for (Magazine magazine : writer.getMagazines()) {
                magazineIds.add(magazine.getId());
            }
            List<Magazine> magazines = magazineJpaRepository.findAllById(magazineIds);
            if (magazineIds.size() != magazines.size()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            writer.setMagazines(magazines);

            for (Magazine magazine : magazines) {
                magazine.getWriters().add(writer);
            }

            Writer savedWriter = writerJpaRepository.save(writer);
            magazineJpaRepository.saveAll(magazines);
            return savedWriter;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public Writer updateWriter(int id, Writer writer) {
        try {
            Writer newWriter = writerJpaRepository.findById(id).get();
            if (writer.getName() != null) {
                newWriter.setName(writer.getName());
            }
            if (writer.getBio() != null) {
                newWriter.setBio(writer.getBio());
            }
            if (writer.getMagazines() != null) {
                List<Magazine> magazines = newWriter.getMagazines();
                for (Magazine magazine : magazines) {
                    magazine.getWriters().remove(newWriter);
                }
                magazineJpaRepository.saveAll(magazines);

                List<Integer> newMagazineIds = new ArrayList<>();
                for (Magazine magazine : writer.getMagazines()) {
                    newMagazineIds.add(magazine.getId());
                }
                List<Magazine> newMagazines = magazineJpaRepository.findAllById(newMagazineIds);
                if (newMagazineIds.size() != newMagazines.size()) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                }

                for (Magazine magazine : newMagazines) {
                    magazine.getWriters().add(newWriter);
                }
                magazineJpaRepository.saveAll(newMagazines);
                newWriter.setMagazines(newMagazines);
            }
            return newWriter;
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public void deleteWriter(int id) {
        try {
            Writer writer = writerJpaRepository.findById(id).get();

            List<Magazine> magazines = writer.getMagazines();
            for (Magazine magazine : magazines) {
                magazine.getWriters().remove(writer);
            }
            magazineJpaRepository.saveAll(magazines);

            writerJpaRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        throw new ResponseStatusException(HttpStatus.NO_CONTENT);
    }

    @Override
    public List<Magazine> getWriterMagazine(int id) {
        Writer writer = writerJpaRepository.findById(id).get();
        List<Magazine> magazines = writer.getMagazines();
        return magazines;
    }

}