package ru.roslyackov.springboot.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.roslyackov.springboot.auth.entity.Activity;
import ru.roslyackov.springboot.auth.entity.Role;
import ru.roslyackov.springboot.auth.entity.User;
import ru.roslyackov.springboot.auth.repository.ActivityRepository;
import ru.roslyackov.springboot.auth.repository.RoleRepository;
import ru.roslyackov.springboot.auth.repository.UserRepository;

import java.util.Optional;

@Service
@Transactional
public class UserService {

    public static final String DEFAULT_ROLE = "USER";


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private ActivityRepository activityRepository;


    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, ActivityRepository activityRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.activityRepository = activityRepository;
    }


    public void register(User user, Activity activity) {

        userRepository.save(user);
        activityRepository.save(activity);

    }


    public boolean userExists(String username, String email) {

        if (userRepository.existsByUsername(username)) {
            return true;
        }

        if (userRepository.existsByEmail(email)) {
            return true; // если запись в БД существует
        }

        return false;
    }

    public Optional<Role> findByName(String role) {
        return roleRepository.findByName(role);
    }

    public Activity saveActivity(Activity activity) {
        return activityRepository.save(activity);
    }


    public Optional<Activity> findActivityByUserId(long id) {
        return activityRepository.findByUserId(id);
    }

    public Optional<Activity> findActivityByUuid(String uuid) {
        return activityRepository.findByUuid(uuid);
    }

    public int activate(String uuid) {
        return activityRepository.changeActivated(uuid, true);
    }

    public int deactivate(String uuid) {
        return activityRepository.changeActivated(uuid, false);
    }


    public int updatePassword(String password, String email) {
        return userRepository.updatePassword(password, email);
    }
}
