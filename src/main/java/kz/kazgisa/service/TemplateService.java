package kz.kazgisa.service;

import kz.kazgisa.data.dto.FileDto;
import kz.kazgisa.data.entity.OrgTemplate;
import kz.kazgisa.data.repositories.OrgTemplateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TemplateService {
    private final OrgTemplateRepository orgTemplateRepository;
    Logger logger = LoggerFactory.getLogger(TemplateService.class);


    public TemplateService(OrgTemplateRepository orgTemplateRepository) {
        this.orgTemplateRepository = orgTemplateRepository;
    }

    public List<OrgTemplate> getTemplates(Long orgId, Long subserviceId, Boolean approved) {
        if(subserviceId == 0L) {
            if (approved == null) {
                return orgTemplateRepository.findByOrgId(orgId);
            }
            return orgTemplateRepository.findByOrgIdAndApproved(orgId, approved);
        } else {
            if (approved == null) {
                return orgTemplateRepository.findByOrgIdAndSubserviceId(orgId, subserviceId);
            }
            return orgTemplateRepository.findByOrgIdAndSubserviceIdAndApproved(orgId, subserviceId, approved);
        }
    }

    public OrgTemplate saveTemplate(OrgTemplate orgTemplate) {
        return orgTemplateRepository.save(orgTemplate);
    }

    public void deleteTemplate(Long id) {
        orgTemplateRepository.deleteById(id);
    }

    public List<FileDto> getTemplatesHeaders() throws IOException {
        List<FileDto> fileDtoList = new ArrayList<>();
        ClassLoader cl = this.getClass().getClassLoader();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
        Resource[] resources = resolver.getResources("classpath*:/images/*") ;
        for (Resource resource: resources){
            logger.info(resource.getFilename());
            FileDto dto = new FileDto();
            dto.fileName = resource.getFilename();
            fileDtoList.add(dto);
        }
        return fileDtoList;
    }
}
