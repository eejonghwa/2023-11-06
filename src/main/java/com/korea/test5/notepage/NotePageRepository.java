package com.korea.test5.notepage;

import com.korea.test5.notebook.Notebook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotePageRepository extends JpaRepository<NotePage, Long> {
    List<NotePage> findByNotebook(Notebook targetNotebook);
}
