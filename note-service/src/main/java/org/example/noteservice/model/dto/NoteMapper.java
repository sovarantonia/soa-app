package org.example.noteservice.model.dto;

import org.example.noteservice.model.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {
    NoteResponseDto toDto(Note note);
}
