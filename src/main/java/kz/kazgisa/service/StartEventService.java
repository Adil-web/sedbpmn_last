package kz.kazgisa.service;

import kz.kazgisa.data.entity.user.User;
import kz.kazgisa.data.entity.user.UserRole;
import kz.kazgisa.data.repositories.user.UserRepository;
import kz.kazgisa.data.repositories.user.UserRoleRepository;
import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class StartEventService  implements TaskListener {

    private final UserRoleRepository userRoleRepository;
    private final UserRepository userRepository;

    public StartEventService(UserRoleRepository userRoleRepository, UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void notify(DelegateTask delegateTask) {
        String mio = (String) delegateTask.getVariable("mio");
        Long regionId = ((Number) delegateTask.getVariable("regionId")).longValue();
        String group = delegateTask.getCandidates().stream().findFirst().get().getGroupId();
        UserRole userRole = null;
        if(mio != null && mio.equals("true")) {
            userRole = userRoleRepository.findByRoleNameAndCurrentTrue(group)
                    .stream()
                    .filter(uR -> {
                        User user = userRepository.findFirstByUsername(uR.getUsername());
                        return Objects.equals(user.getRegionId(), regionId);
                    }).findFirst().orElse(null);
            if(userRole == null) {
                userRole = userRoleRepository.findByRoleName(group)
                        .stream()
                        .filter(uR-> {
                            User user = userRepository.findFirstByUsername(uR.getUsername());
                            return Objects.equals(user.getRegionId(),regionId);
                        }).findFirst().orElse(null);
            }
        } else {
            userRole = userRoleRepository.findFirstByRoleNameAndCurrentTrue(group);
        }
        if(userRole != null) {
            delegateTask.setAssignee(userRole.getUsername());
            System.out.println("assignee to " + userRole.getUsername());
        }
    }
}

