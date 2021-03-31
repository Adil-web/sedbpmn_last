package kz.kazgisa.service;

import kz.kazgisa.data.dto.UserDto;
import kz.kazgisa.data.entity.dict.Menu;
import kz.kazgisa.data.entity.dict.Subservice;
import kz.kazgisa.data.entity.user.Role;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.entity.user.UserMenu;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.enums.OrgType;
import kz.kazgisa.data.enums.RoleType;
import kz.kazgisa.data.repositories.dict.MenuRepository;
import kz.kazgisa.data.repositories.dict.SubserviceRepository;
import kz.kazgisa.data.repositories.user.UserMenuRepository;
import kz.kazgisa.data.repositories.user.UserRepository;
import kz.kazgisa.data.repositories.user.UserRoleRepository;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static Map<String, UserDto> userMap = new HashMap<>();
    private final UserRoleRepository userRoleRepository;
    private final SubserviceRepository subserviceRepository;
    private final UserRepository userRepository;
    private final UserMenuRepository userMenuRepository;
    private final MenuRepository menuRepository;

    public UserService(UserRoleRepository userRoleRepository, SubserviceRepository subserviceRepository, UserRepository userRepository, UserMenuRepository userMenuRepository, MenuRepository menuRepository) {
        this.userRoleRepository = userRoleRepository;
        this.subserviceRepository = subserviceRepository;
        this.userRepository = userRepository;
        this.userMenuRepository = userMenuRepository;
        this.menuRepository = menuRepository;
    }

    public User fillWithRoles(User user) {
        if (user != null && user.getUsername() != null) {
            user.setRoles(userRoleRepository.findByUsername(user.getUsername()).
                    stream().map(UserRole::getRole).collect(Collectors.toList()));
        }
        return user;
    }

    public User getUserByUserName(String username) {
        return fillWithRoles(userRepository.findFirstByUsername(username));

    }

    public List<UserRole> getUserRolesByUserName(String username) {
        return userRoleRepository.findByUsername(username);
    }

    @Transactional
    public UserRole saveUserRoles(UserRole userRole) {
        if (BooleanUtils.isTrue(userRole.getCurrent())) {
            userRoleRepository.updateCurrent(userRole.getSubservice().getId(), userRole.getRole().getId());
        }
        return userRoleRepository.save(userRole);
    }

    @Transactional
    public UserRole deleteUserRoles(Long id) {
        userRoleRepository.deleteById(id);
        return new UserRole();
    }

    public Page<User> getUsers(String searchText, Pageable pageable) {
        return userRepository.findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrSecondNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchText, searchText, searchText, searchText, searchText, pageable).map(this::fillWithRoles);
    }

    public List<User> getUsers() {
        return userRepository.findAll().stream().map(this::fillWithRoles).collect(Collectors.toList());
    }

    public List<Subservice> getSubservicesByUserName(String username) {
        if (username == null)
            return subserviceRepository.findAll();
        return null;
    }

    public User getUserByEmail(String email) {
        return fillWithRoles(userRepository.findFirstByEmail(email));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User getById(Long id) {
        return userRepository.findById(id).get();
    }

    @Transactional
    public User deleteUser(Long id) {
        userRepository.deleteById(id);
        return new User();
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null)
            return null;
        String name = authentication.getName();
        User user = getUserByUserName(name);
        if (user == null)
            user = getUserByEmail(name);
        return user;
    }

    public List<Subservice> getCurrentUserSubservices() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        List<UserRole> roles = getUserRolesByUserName(name);
        return roles.stream().map(UserRole::getSubservice).collect(Collectors.toList());
    }

    public List<Subservice> getCurrentUserSubservices(String username) {
        String name = username;
        List<UserRole> roles = getUserRolesByUserName(name);
        return roles.stream().map(UserRole::getSubservice).collect(Collectors.toList());
    }

    public List<User> getUsersByRoleId(Long roleId, Long subserviceId) {
        return userRoleRepository.findByRoleIdAndSubserviceId(roleId, subserviceId).stream().map(r -> {
            if (r.getUsername() != null)
                return userRepository.findFirstByUsername(r.getUsername());
            else
                return new User();
        }).collect(Collectors.toList());
    }

    public List<Menu> getAccessList() {
        return menuRepository.findAll();
    }

    public List<Menu> getUserAccess(Long id) {
        return userMenuRepository.findByUserId(id).stream().map(s -> s.getMenu()).collect(Collectors.toList());
    }

    @Transactional
    public List<Menu> saveUserAccess(Long id, List<Menu> access) {
        userMenuRepository.deleteByUserId(id);
        User user = userRepository.findById(id).get();
        for (Menu menu : access) {
            UserMenu userMenu = new UserMenu();
            userMenu.setMenu(menu);
            userMenu.setUser(user);
            userMenuRepository.save(userMenu);
        }
        return access;
    }

    public List<User> getUsersByOrganizationId(Long id) {
        return userRepository.findByOrganizationId(id);
    }

    public List<User> getUsersByOrganizationTypeAndRoleId(OrgType orgType, Long roleId) {
        return userRoleRepository.findByRoleId(roleId).stream()
                .map(userRole -> userRepository.findFirstByUsername(userRole.getUsername()))
                .filter(user -> {
                    if (user == null || user.getOrganization() == null || user.getOrganization().getOrgType() == null)
                        return false;
                    return user.getOrganization().getOrgType().equals(orgType);
                }).collect(Collectors.toList());
    }

    public Page<User> getUsersByRegion(Long regionId, Pageable pageable) {
        return userRepository.findByRegionId(regionId, pageable).map(user -> {
            if (user.getUsername() != null)
                user.setRoles(userRoleRepository.findByUsername(user.getUsername()).stream().map(UserRole::getRole).collect(Collectors.toList()));
            return user;
        });
    }

    public Boolean userHasRole(String username, String roleName) {
        List<UserRole> userRoles = userRoleRepository.findByUsername(username);
        for(UserRole userRole : userRoles) {
            if(userRole.getRole() != null && userRole.getRole().getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
