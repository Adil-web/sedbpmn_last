package kz.kazgisa.service;

import kz.kazgisa.data.entity.SubserviceRole;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.repositories.SubserviceRoleRepository;
import kz.kazgisa.data.repositories.dict.SubserviceRepository;
import kz.kazgisa.data.repositories.user.UserRepository;
import kz.kazgisa.data.repositories.user.UserRoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubserviceService {
    private final SubserviceRepository subserviceRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;
    private final SubserviceRoleRepository subserviceRoleRepository;

    public SubserviceService(SubserviceRepository subserviceRepository, UserRoleRepository userRoleRepository, UserRepository userRepository, SubserviceRoleRepository subserviceRoleRepository) {
        this.subserviceRepository = subserviceRepository;
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
        this.subserviceRoleRepository = subserviceRoleRepository;
    }


    public Page<Subservice> getSubservices(String name, Pageable pageable) {
        if(name == null)
            return subserviceRepository.findAll(pageable);
        else
            return subserviceRepository.findByNameRuContainingIgnoreCase(name,pageable);
    }

    public Subservice getSubserviceById(Long id) {
        return subserviceRepository.findById(id).get();
    }

    public Subservice saveSubservice(Subservice subservice) {
        return subserviceRepository.save(subservice);
    }

    public List<UserRole> getSubserviceMembersById(Long id, Long regionId) {

        List<UserRole> list = userRoleRepository.findBySubserviceIdOrderByRoleNumAsc(id).stream().peek(ur-> {
            if(ur.getUsername() != null)
                ur.setUser(userRepository.findFirstByUsername(ur.getUsername()));
        }).collect(Collectors.toList());
        if(regionId != null) {
            list = list.stream().filter(userRole -> userRole.getUser()!=null &&
                    userRole.getUser().getRegionId() != null &&
                    userRole.getUser().getRegionId().equals(regionId))
                    .collect(Collectors.toList());
        }
        return list;
    }

    @Transactional
    public SubserviceRole deleteSubserviceRolesBySubserviceIdAndRoleId(Long id, Long roleId) {
        subserviceRoleRepository.deleteBySubserviceIdAndRoleId(id,roleId);
                return new SubserviceRole();
    }
}
