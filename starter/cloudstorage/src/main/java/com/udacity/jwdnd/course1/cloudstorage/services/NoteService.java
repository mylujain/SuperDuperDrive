package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    @Autowired
    private NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public List<Note> getAllNotes(Integer userid) {
        return noteMapper.getAllNotes(userid);
    }

    public void createNote(Note note) {
        boolean exist=note.getNoteid() != null;
        if (exist) {
            noteMapper.updateNote(note);
        } else {
            noteMapper.insertNote(note);

        }
    }

    public void deleteNote(Integer noteid, Integer userid) {
        noteMapper.deleteNote(noteid, userid);
    }
}
