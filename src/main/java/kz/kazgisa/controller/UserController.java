package kz.kazgisa.controller;

import kz.kazgisa.data.entity.dict.Menu;
import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.entity.user.UserMenu;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.enums.OrgType;
import kz.kazgisa.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
@RequestMapping("users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity getUserByUsername(@RequestParam(required = false) String username,
                                            @RequestParam(required = false) String email,
                                            @RequestParam(required = false) Integer page,
                                            @RequestParam(required = false) Integer size,
                                            @RequestParam(required = false, defaultValue = "") String searchText,
                                            @RequestParam(required = false) Long regionId) {
        if(username != null)
            return ResponseEntity.ok(userService.getUserByUserName(username));
        else if(email != null)
            return ResponseEntity.ok(userService.getUserByEmail(email));
        else if(size!=null && page !=null)
            if(regionId==null)
            return ResponseEntity.ok(userService.getUsers(searchText, PageRequest.of(page,size)));
            else
                return ResponseEntity.ok(userService.getUsersByRegion(regionId,PageRequest.of(page,size)));
        else
            return ResponseEntity.ok(userService.getUsers());
    }

    @GetMapping("{id}")
    public ResponseEntity getUserByUsername(@PathVariable Long id) {
            return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody User user) {
        User ex = userService.getUserByEmail(user.getEmail());
        if(ex != null)
            throw new RuntimeException("email-already-exist");
        else
            return ResponseEntity.ok(userService.saveUser(user));
    }

    @PutMapping("{id}")
    public ResponseEntity updateUser(@PathVariable Long id,@RequestBody User user) {
        user.setId(id);
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("roles")
    public ResponseEntity getUserRolesByUsername(@RequestParam(required = false) String username,
                                                 @RequestParam(required = false) Long roleId,
                                                 @RequestParam(required = false) Long subserviceId,
                                                 @RequestParam(required = false) OrgType orgType
                                                 ) {
        if(username != null)
        return ResponseEntity.ok(userService.getUserRolesByUserName(username));
        else if(orgType != null)
            return ResponseEntity.ok(userService.getUsersByOrganizationTypeAndRoleId(orgType,roleId));
        else
            return ResponseEntity.ok(userService.getUsersByRoleId(roleId,subserviceId));
    }

    @PostMapping("roles")
    public ResponseEntity createUserRoles(@RequestBody UserRole userRole) {
        return ResponseEntity.ok(userService.saveUserRoles(userRole));
    }

    @PutMapping("roles/{id}")
    public ResponseEntity updateUserRoles(@PathVariable Long id,@RequestBody UserRole userRole) {
        userRole.setId(id);
        return ResponseEntity.ok(userService.saveUserRoles(userRole));
    }

    @DeleteMapping("roles/{id}")
    public ResponseEntity createUserRoles(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deleteUserRoles(id));
    }

    @GetMapping("subservices")
    public ResponseEntity getSubservices(@RequestParam(required = false) String username) {
        return ResponseEntity.ok(userService.getSubservicesByUserName(username));
    }

    @GetMapping("access")
    public ResponseEntity getAccess() {
        return ResponseEntity.ok(userService.getAccessList());
    }

    @GetMapping("{id}/access")
    public ResponseEntity getUserAccessByUsername(@PathVariable Long id)
    {
        return ResponseEntity.ok(userService.getUserAccess(id));
    }

    @PutMapping("{id}/access")
    public ResponseEntity createUserAccess(@PathVariable Long id,@RequestBody List<Menu> access) {
        return ResponseEntity.ok(userService.saveUserAccess(id,access));
    }


}
