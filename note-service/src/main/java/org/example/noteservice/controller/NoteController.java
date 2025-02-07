package org.example.noteservice.controller;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.noteservice.model.Note;
import org.example.noteservice.model.dto.NoteMapper;
import org.example.noteservice.model.dto.NoteRequestDto;
import org.example.noteservice.model.dto.NoteResponseDto;
import org.example.noteservice.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
public class NoteController {
    private final NoteService noteService;
    private final NoteMapper noteMapper;

    @GetMapping("/user/{userId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NoteResponseDto>> findUserNotes(@PathVariable Long userId) {
        List<Note> notes = noteService.getNotesByUserId(userId);

        return ResponseEntity.ok(notes.stream().map(noteMapper::toDto).toList());
    }

    @GetMapping("/{noteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NoteResponseDto> findNoteById(@PathVariable Long noteId) {
        Note note = noteService.getNoteById(noteId).orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(noteMapper.toDto(note));
    }

    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> createNote(@RequestBody NoteRequestDto note) {
        return new ResponseEntity<>(noteService.saveNote(note), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<NoteResponseDto> updateNote(@PathVariable Long id, @RequestBody NoteRequestDto note) {
        Note updatedNote = noteService.updateNote(id, note);
        return ResponseEntity.ok(noteMapper.toDto(updatedNote));
    }

    @DeleteMapping("/{noteId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteNote(@PathVariable Long noteId) {
        noteService.deleteNote(noteId);
        return ResponseEntity.ok("Note deleted");
    }

}
