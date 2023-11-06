package com.korea.test5.notebook;

import com.korea.test5.notepage.NotePage;
import com.korea.test5.notepage.NotePageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/notebook")
@RequiredArgsConstructor
public class NotebookController {

    private final NotePageService notePageService;
    private final NotebookService notebookService;

    @RequestMapping("/{notebookId}")
    public String select(Model model, @PathVariable Long notebookId){

        Notebook notebook = notebookService.getNotebookById(notebookId);
        List<Notebook> notebookList = notebookService.getParentNotebookList();

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("notePageList", notebook.getNotePageList());
        model.addAttribute("targetNotebook", notebook);
        model.addAttribute("targetNotePage", notebook.getNotePageList().get(0));

        return "main";

    }
    @RequestMapping("/")
    public String main(Model model) {
        List<NotePage> notePageList = notePageService.getNotePageList();
        List<Notebook> notebookList = notebookService.getParentNotebookList();

        if(notebookList.isEmpty()){
            notebookService.saveDefaultNotebook();
            return "redirect:/";
        }

        if(notePageList.isEmpty()){
            notePageService.saveDefaultPage(notebookList.get(0));
            return "redirect:/";
        }

        model.addAttribute("notebookList", notebookList);
        model.addAttribute("notePageList", notePageList);
        model.addAttribute("targetNotebook", notebookList.get(0));
        model.addAttribute("targetNotePage", notePageList.get(0));

        return "main";
    }

    @PostMapping("/write")
    public String write(){
        Notebook notebook = notebookService.saveDefaultNotebook();
        notePageService.saveDefaultPage(notebook);
        return "redirect:/notebook/" + notebook.getId();
    }

    @PostMapping("/add-group")
    public String addGroup(Long notebookId){
        Notebook notebook = notebookService.saveGroupNotebook(notebookId);
        notePageService.saveDefaultPage(notebook);
        return "redirect:/notebook/" + notebook.getId();
    }

    @PostMapping("/delete")
    public String delete(Long notebookId){
        Notebook notebook = notebookService.getNotebookById(notebookId);
        notebookService.deleteById(notebookId);
        return "redirect:/";
    }

    @PostMapping("/update")
    public String update(Long notebookId, String name){
        Notebook notebook = notebookService.getNotebookById(notebookId);
        notebook.setName(name);
        notebookService.save(notebook);
        return "redirect:/notebook/" + notebook.getId();
    }
}
