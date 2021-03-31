package kz.kazgisa.controller;

import kz.kazgisa.data.entity.dict.AppComment;
import kz.kazgisa.data.repositories.dict.AppCommentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("comments")
@Controller
public class AppCommentController {
    private final AppCommentRepository appCommentRepository;

    public AppCommentController(AppCommentRepository appCommentRepository) {
        this.appCommentRepository = appCommentRepository;
    }

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(appCommentRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable Long id) {
        return ResponseEntity.ok(appCommentRepository.findById(id).get());
    }

    @PostMapping
    public ResponseEntity save(@RequestBody AppComment appComment) {
        return ResponseEntity.ok(appCommentRepository.save(appComment));
    }

    @PutMapping("{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody AppComment appComment) {
        appComment.setId(id);
        return ResponseEntity.ok(appCommentRepository.save(appComment));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteAppComment(@PathVariable Long id) {
        appCommentRepository.deleteById(id);
        return ResponseEntity.ok(new AppComment());
    }
}
