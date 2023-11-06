package com.korea.test5.notepage;

import com.korea.test5.notebook.Notebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NotePageService {
    private final NotePageRepository notePageRepository;

    public List<NotePage> getNotePageList() {
        return notePageRepository.findAll();
    }

    public NotePage getNotePageById(Long id) {
        Optional<NotePage> notePageOptional = notePageRepository.findById(id);
        if(notePageOptional.isPresent()){
            return notePageOptional.get();
        }
        throw new IllegalArgumentException("해당 게시물은 존재하지 않습니다.");
    }

    public void save(NotePage notePage) {
        notePageRepository.save(notePage);
    }

    public void deleteById(Long id) {
        notePageRepository.deleteById(id);
    }

    public void saveDefaultPage(Notebook notebook) {
        NotePage notePage = new NotePage();
        notePage.setTitle("title");
        notePage.setContent("");
        notePage.setNotebook(notebook);
        notePage.setCreateDate(LocalDateTime.now());

        notePageRepository.save(notePage);
    }

    public List<NotePage> getNotePageListByNotebook(Notebook targetNotebook) {
        return notePageRepository.findByNotebook(targetNotebook);
    }
}
