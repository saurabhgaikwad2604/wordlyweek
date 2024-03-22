package com.example.wordlyweek.service;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.example.wordlyweek.model.*;
import com.example.wordlyweek.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MagazineJpaService implements MagazineRepository {
	@Autowired
	private MagazineJpaRepository magazineJpaRepository;
	@Autowired
	private WriterJpaRepository writerJpaRepository;

	@Override
	public ArrayList<Magazine> getMagazines() {
		List<Magazine> magazinesList = magazineJpaRepository.findAll();
		ArrayList<Magazine> magazines = new ArrayList<>(magazinesList);
		return magazines;
	}

	@Override
	public Magazine getMagazineById(int id) {
		try {
			Magazine magazine = magazineJpaRepository.findById(id).get();
			return magazine;
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Magazine addMagazine(Magazine magazine) {
		try {
			List<Integer> writerIds = new ArrayList<>();
			for (Writer writer : magazine.getWriters()) {
				writerIds.add(writer.getId());
			}
			List<Writer> writers = writerJpaRepository.findAllById(writerIds);
			if (writerIds.size() != writers.size()) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
			}
			magazine.setWriters(writers);

			for (Writer writer : writers) {
				writer.getMagazines().add(magazine);
			}
			Magazine savedMagazine = magazineJpaRepository.save(magazine);
			writerJpaRepository.saveAll(writers);
			return savedMagazine;
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public Magazine updateMagazine(int id, Magazine magazine) {
		try {
			Magazine newMagazine = magazineJpaRepository.findById(id).get();
			if (magazine.getTitle() != null) {
				newMagazine.setTitle(magazine.getTitle());
			}
			if (magazine.getPublicationDate() != null) {
				newMagazine.setPublicationDate(magazine.getPublicationDate());
			}
			if (magazine.getWriters() != null) {
				List<Writer> writers = newMagazine.getWriters();
				for (Writer writer : writers) {
					writer.getMagazines().remove(newMagazine);
				}
				writerJpaRepository.saveAll(writers);

				List<Integer> newWriterIds = new ArrayList<>();
				for (Writer writer : magazine.getWriters()) {
					newWriterIds.add(writer.getId());
				}
				List<Writer> newWriters = writerJpaRepository.findAllById(newWriterIds);
				if (newWriterIds.size() != newWriters.size()) {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
				}

				for (Writer writer : newWriters) {
					writer.getMagazines().add(newMagazine);
				}
				writerJpaRepository.saveAll(newWriters);
				newMagazine.setWriters(newWriters);
			}
			return magazineJpaRepository.save(newMagazine);
		} catch (NoSuchElementException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public void deleteMagazine(int id) {
		try {
			Magazine magazine = magazineJpaRepository.findById(id).get();

			List<Writer> writers = magazine.getWriters();
			for (Writer writer : writers) {
				writer.getMagazines().remove(magazine);
			}
			writerJpaRepository.saveAll(writers);

			magazineJpaRepository.deleteById(id);
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		}
		throw new ResponseStatusException(HttpStatus.NO_CONTENT);
	}

	@Override
	public List<Writer> getMagazineWriter(int id) {
		Magazine magazine = magazineJpaRepository.findById(id).get();
		List<Writer> writers = magazine.getWriters();
		return writers;
	}

}