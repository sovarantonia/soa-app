package org.example.noteservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.noteservice.kafka.KafkaLogEvent;
import org.example.noteservice.model.Note;
import org.example.noteservice.model.dto.EmailInfoDto;
import org.example.noteservice.model.dto.NoteRequestDto;
import org.example.noteservice.rabbit.RabbitClient;
import org.example.noteservice.repository.NoteRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final RabbitClient rabbitClient;

    public Note saveNote(NoteRequestDto noteRequestDto) {

        if (noteRequestDto.getGrade() == null) {
            throw new IllegalArgumentException("Grade must be an integer between 1 and 10");
        }

        Note createdNote = Note.builder()
                .userId(noteRequestDto.getUserId())
                .title(noteRequestDto.getTitle())
                .text(noteRequestDto.getText())
                .date(noteRequestDto.getDate())
                .grade(noteRequestDto.getGrade())
                .build();

        eventPublisher.publishEvent(new KafkaLogEvent("Note from user with id " + noteRequestDto.getUserId() + " was created"));

        EmailInfoDto emailInfoDto = EmailInfoDto
                .builder()
                .title(noteRequestDto.getTitle())
                .text(noteRequestDto.getText())
                .date(noteRequestDto.getDate().toString())
                .grade(noteRequestDto.getGrade())
                .email("recipient@test.com")
                .build();

        rabbitClient.sendMessage(emailInfoDto);

        return noteRepository.save(createdNote);
    }

    public void deleteNote(Long id) {
        if (noteRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Note does not exist");
        }
        eventPublisher.publishEvent(new KafkaLogEvent("Note with id " + id + " was deleted"));
        noteRepository.deleteById(id);
    }

    public Note updateNote(Long id, NoteRequestDto noteRequestDto) {
        Note updatedNote = noteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Note does not exist"));

        if (!noteRequestDto.getTitle().isBlank() && !noteRequestDto.getTitle().isEmpty()) {
            updatedNote.setTitle(noteRequestDto.getTitle());
        }

        if (!noteRequestDto.getText().isBlank() && !noteRequestDto.getText().isEmpty()) {
            updatedNote.setText(noteRequestDto.getText());
        }

        if (noteRequestDto.getGrade() != 0) {
            updatedNote.setGrade(noteRequestDto.getGrade());
        }

        eventPublisher.publishEvent(new KafkaLogEvent("Note with id " + id + " was updated"));

        return noteRepository.save(updatedNote);
    }


    public Optional<Note> getNoteById(Long id) {
        if (noteRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(String.format("Note with id %s does not exist", id));
        }
        eventPublisher.publishEvent(new KafkaLogEvent("Note with id " + id));
        return noteRepository.findById(id);
    }


    public List<Note> getNotesByUserId(Long userId) {
        eventPublisher.publishEvent(new KafkaLogEvent("Get notes by user with id " + userId));
        return noteRepository.getNotesByUserIdOrderByDateDesc(userId);
    }


    public List<Note> getAllNotes() {
        eventPublisher.publishEvent(new KafkaLogEvent("Get all notes"));
        return noteRepository.findAll();
    }
}
