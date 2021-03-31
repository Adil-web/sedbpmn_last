package kz.kazgisa.controller;

import kz.kazgisa.data.entity.AdmDocument;
import kz.kazgisa.data.entity.AdmDocumentFile;
import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.entity.user.User;

import kz.kazgisa.service.AdmDocumentService;
import kz.kazgisa.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("adm-documents")
public class AdmDocumentController {
    private final AdmDocumentService service;
    private final UserService userService;

    public AdmDocumentController(AdmDocumentService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getadmDocuments(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getadmDocuments(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deladmDocuments(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("categories")
    public ResponseEntity<?> getadmDocuments() {
        return ResponseEntity.ok(service.getCategories());
    }

    @PostMapping
    ResponseEntity<?> saveadmDocument(@RequestBody AdmDocument admDocument) {
        User user = userService.getCurrentUser();
        admDocument.setAuthor(user);
        return ResponseEntity.ok(service.save(admDocument));
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateadmDocument(@PathVariable Long id,
                                           @RequestBody AdmDocument admDocument) {
        admDocument.setId(id);
        if(admDocument.getAuthor() == null) {
            User user = userService.getCurrentUser();
            admDocument.setAuthor(user);
        }
        return ResponseEntity.ok(service.save(admDocument));
    }

    @PostMapping("/{id}/files")
    public ResponseEntity<?> addFiles(@RequestBody List<AdmDocumentFile> files,
                                      @PathVariable Long id) {

        return ResponseEntity.ok(service.addFile(files, id));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<?> getFiles(@PathVariable Long id) {

        return ResponseEntity.ok(service.getFiles(id));
    }

    @DeleteMapping("{id}/files/{fileId}")
    public ResponseEntity deleteFile(@PathVariable Long id, @PathVariable Long fileId) {
        service.deleteFile(fileId);
        return ResponseEntity.ok(new AppFile());
    }

}
