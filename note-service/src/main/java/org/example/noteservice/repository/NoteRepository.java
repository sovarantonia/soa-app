package org.example.noteservice.repository;

import org.example.noteservice.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> getNotesByUserIdOrderByDateDesc(Long userId);
}