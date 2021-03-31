package kz.kazgisa.controller;

import kz.kazgisa.data.entity.AppFile;
import kz.kazgisa.data.entity.Correspondence;
import kz.kazgisa.data.entity.CorrespondenceFile;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.enums.CorrespondenceType;
import kz.kazgisa.service.CorrespondenceService;
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

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("correspondences")
public class CorrespondenceController {

    private final CorrespondenceService service;
    private final UserService userService;

    public CorrespondenceController(CorrespondenceService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getCorrespondences(@RequestParam CorrespondenceType type, Pageable pageable) {
        return ResponseEntity.ok(service.getAll(type, pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCorrespondences(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delCorrespondences(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteById(id));
    }

    @GetMapping("categories")
    public ResponseEntity<?> getCorrespondences() {
        return ResponseEntity.ok(service.getCategories());
    }

    @PostMapping ResponseEntity<?> saveCorrespondence(@RequestBody Correspondence correspondence) {
        User user = userService.getCurrentUser();
        correspondence.setAuthor(user);
        return ResponseEntity.ok(service.save(correspondence));
    }

    @PutMapping("{id}")
    ResponseEntity<?> updateCorrespondence(@PathVariable Long id,
                                           @RequestBody Correspondence correspondence) {
        correspondence.setId(id);
        if(correspondence.getAuthor() == null) {
            User user = userService.getCurrentUser();
            correspondence.setAuthor(user);
        }
        return ResponseEntity.ok(service.save(correspondence));
    }

    @PostMapping("/{id}/files")
    public ResponseEntity<?> addFiles(@RequestBody List<CorrespondenceFile> files,
                                      @PathVariable Long id) {

        return ResponseEntity.ok(service.addFile(files, id));
    }

    @GetMapping("/{id}/files")
    public ResponseEntity<?> getFiles(@PathVariable Long id) {

        return ResponseEntity.ok(service.getFiles(id));
    }

    @DeleteMapping("{id}/files/{fileId}")
    public ResponseEntity deleteFile(@PathVariable Long id,@PathVariable Long fileId) {
        service.deleteFile(fileId);
        return ResponseEntity.ok(new AppFile());
    }

}
