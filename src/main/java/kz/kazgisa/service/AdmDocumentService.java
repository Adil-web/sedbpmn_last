package kz.kazgisa.service;

import kz.kazgisa.data.entity.AdmDocument;
import kz.kazgisa.data.entity.AdmDocumentFile;
import kz.kazgisa.data.entity.CorrespondenceFile;
import kz.kazgisa.data.entity.dict.AdmDocumentCategory;
import kz.kazgisa.data.repositories.AdmDocumentCategoryRepository;
import kz.kazgisa.data.repositories.AdmDocumentFileRepository;
import kz.kazgisa.data.repositories.AdmDocumentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AdmDocumentService {
    private final AdmDocumentRepository admDocumentRepository;
    private final AdmDocumentCategoryRepository admDocumentCategoryRepository;
    private final AdmDocumentFileRepository admDocumentFileRepository;

    public AdmDocumentService(AdmDocumentRepository admDocumentRepository, AdmDocumentCategoryRepository admDocumentCategoryRepository, AdmDocumentFileRepository admDocumentFileRepository) {
        this.admDocumentRepository = admDocumentRepository;
        this.admDocumentCategoryRepository = admDocumentCategoryRepository;
        this.admDocumentFileRepository = admDocumentFileRepository;
    }

    public Page<AdmDocument> getAll(Pageable pageable) {
        return admDocumentRepository.findAll(pageable);
    }

    public AdmDocument getById(Long id) {
        return admDocumentRepository.findById(id).get();
    }

    public AdmDocument deleteById(Long id) {
        admDocumentRepository.deleteById(id);
        return new AdmDocument();
    }

    public List<AdmDocumentCategory> getCategories() {
        return admDocumentCategoryRepository.findAll();
    }

    public AdmDocument save(AdmDocument admDocument) {
        return admDocumentRepository.save(admDocument);
    }

    @Transactional
    public void deleteFile(Long id) {
        admDocumentFileRepository.deleteById(id);
    }


    public List addFile(List<AdmDocumentFile> files, Long appId) {
        for (AdmDocumentFile file : files) {
            file.setAdmDocument(admDocumentRepository.findById(appId).get());
        }

        return admDocumentFileRepository.saveAll(files);
    }

    public List<AdmDocumentFile> getFiles(Long id) {
        return admDocumentFileRepository.findByAdmDocumentId(id);
    }

    public AdmDocumentFile getFileByName(String id) {
            return admDocumentFileRepository.findFirstByName(id);
    }
}
