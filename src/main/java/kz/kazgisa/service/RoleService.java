package kz.kazgisa.service;

import kz.kazgisa.data.entity.SubserviceRole;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.Role;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.repositories.SubserviceRoleRepository;
import kz.kazgisa.data.repositories.user.RoleRepository;
import kz.kazgisa.data.repositories.user.UserRoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {
    private final RoleRepository roleRepository;
    private final SubserviceRoleRepository subserviceRoleRepository;
    private final UserRoleRepository userRoleRepository;

    public RoleService(RoleRepository roleRepository,
                       SubserviceRoleRepository subserviceRoleRepository,
                       UserRoleRepository userRoleRepository) {
        this.roleRepository = roleRepository;
        this.subserviceRoleRepository = subserviceRoleRepository;
        this.userRoleRepository = userRoleRepository;
    }

    public Page<Role> getRoles(String name,Long subserviceId, Pageable pageable) {
        if(subserviceId !=null) {
            List<Long> map = subserviceRoleRepository.findBySubserviceId(subserviceId).stream().map(sr->sr.getRole().getId()).collect(Collectors.toList());
            return roleRepository.findByIdInOrderByNumAsc(map,pageable);
        }
        if(name == null)
            return roleRepository.findAll(pageable);
        return roleRepository.findByNameRuContainingIgnoreCase(name,pageable);
    }

    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public Role deleteRole(Long id) {
        roleRepository.deleteById(id);
        return new Role();
    }

    public Role getRoleById(Long id) {
        return roleRepository.findById(id).get();
    }

    public List<SubserviceRole> getRoleSubservicesById(Long id) {
        return subserviceRoleRepository.findByRoleId(id);
    }

    public SubserviceRole saveRoleSubservicesById(Long id, SubserviceRole subserviceRole) {
        subserviceRole.setRole(roleRepository.findById(id).get());
        return subserviceRoleRepository.save(subserviceRole);
    }

    public Role getCurrentRoleForSubservice(String username, Long subserviceId) {
        List<UserRole> userRoles = userRoleRepository.findByUsername(username);
        for (UserRole userRole : userRoles) {
            if(userRole.getCurrent() && userRole.getSubservice() != null && userRole.getSubservice().getId() == subserviceId) {
                return userRole.getRole();
            }
        }
        return null;
    }

    @Transactional
    public SubserviceRole deleteRoleSubservicesById(Long subRoleId) {
        subserviceRoleRepository.deleteById(subRoleId);
        return new SubserviceRole();
    }
}
