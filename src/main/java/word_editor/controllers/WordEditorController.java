package word_editor.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import word_editor.service.IWordEditor;

import static word_editor.api.WordEditorConstants.*;

@Controller
@RequestMapping(WORD_EDITOR_ENDPOINT)
public class WordEditorController {

    @Autowired
    IWordEditor wordEditor;

    @GetMapping
    ResponseEntity<?> getWordEditorArea() {
        String getText = wordEditor.print();
        return ResponseEntity.ok(getText);
    }

    @PostMapping
    ResponseEntity<?> addWord(@RequestParam String text, @RequestParam(required = false) Integer position) {
        if(position == null)
            wordEditor.add(text);
        else
            wordEditor.add(text, position);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = REMOVE_PATH)
    ResponseEntity<?> remove(@RequestParam int from, @RequestParam int to) {
        wordEditor.remove(from, to);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = MODIFY_PATH)
    ResponseEntity<?> modify(@RequestParam String style, @RequestParam int from, @RequestParam int to) {
        switch (style) {
            case "i":
                wordEditor.italic(from, to);
                break;
            case "b":
                wordEditor.bold(from, to);
                break;
            case "u":
                wordEditor.underline(from, to);
                break;
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = UNDO_PATH)
    ResponseEntity<?> undo() {
        wordEditor.undo();
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = REDO_PATH)
    ResponseEntity<?> redo() {
        wordEditor.redo();
        return ResponseEntity.ok().build();
    }

}
