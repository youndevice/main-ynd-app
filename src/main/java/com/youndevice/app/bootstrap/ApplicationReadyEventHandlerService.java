package com.youndevice.app.bootstrap;

import com.youndevice.app.domain.Appliance;
import com.youndevice.app.domain.Device;
import com.youndevice.app.domain.Role;
import com.youndevice.app.domain.User;
import com.youndevice.app.enums.DeviceType;
import com.youndevice.app.enums.RoleType;
import com.youndevice.app.service.repo.RoleRepoService;
import com.youndevice.app.service.repo.UserRepoService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ApplicationReadyEventHandlerService implements ApplicationListener<ApplicationReadyEvent> {

    Log log = LogFactory.getLog(ApplicationReadyEventHandlerService.class);

    @Value("${skip.bootstrap:false}")
    Boolean skipBootstrap;

    @Autowired
    UserRepoService userRepoService;

    @Autowired
    RoleRepoService roleRepoService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
//        skipBootstrap = true;
        if (skipBootstrap) {
            log.info("Bootstrap skipped as config property 'skip.bootstrap' is set to true");
        } else {
            createTestRole();
            createUserTestData();
        }
    }

    private void createUserTestData() {
        List<User> userList = new ArrayList<>();
        String testEmailId = null;
        createAdminUser();
        for (int i = 1; i <= 5; i++) {
            testEmailId = "email" + i + "@testmail.com";
            if (userRepoService.countUserByEmailId(testEmailId) < 1) {
                User user = new User("firstName", "lastName", testEmailId, new BCryptPasswordEncoder().encode("igdefault"));
                user.setDevices(createTestDevices(i, user));
                HashSet<Role> roleSet = new HashSet<Role>();
                roleSet.add(roleRepoService.findRoleByAuthority(RoleType.ROLE_USER.name()));
                user.setRoles(roleSet);
                userList.add(user);
            }
        }
        userRepoService.save(userList);
    }

    private void createAdminUser() {
        log.info("Admin user created");
        String emailId = "admin@ynd.com";
        if (userRepoService.countUserByEmailId(emailId) < 1) {
            User adminUser = new User("firstName", "lastName", emailId, new BCryptPasswordEncoder().encode("admin"));
            HashSet<Role> roleSet = new HashSet<Role>();
            roleSet.add(roleRepoService.findRoleByAuthority(RoleType.ROLE_ADMIN.name()));
            adminUser.setRoles(roleSet);
            userRepoService.save(adminUser);
        }
    }

    private Set<Device> createTestDevices(Integer noOfDevices, User user) {
        Set<Device> devices = new HashSet<>(noOfDevices);
        for (Integer count = 0; count < noOfDevices; count++) {
            Device device = new Device();
            device.setDeviceType(DeviceType.TYPE_1);
            device.setUserFriendlyName("User Friendly name for Device");
            device.setAppliances(createTestAppliances(count, device));
            device.setUser(user);
            devices.add(device);
        }
        return devices;
    }

    private Set<Appliance> createTestAppliances(Integer noOfAppliances, Device device) {
        Set<Appliance> appliances = new HashSet<>(noOfAppliances);
        for (Integer count = 0; count < noOfAppliances; count++) {
            Appliance appliance = new Appliance();
            appliance.setWebStatus("1");
            appliance.setUserFriendlyName("User Friendly name for Appliance- ");
            appliances.add(appliance);
            appliance.setDevice(device);
        }
        return appliances;
    }

    private void createTestRole() {
        if (roleRepoService.countRoleByAuthority(RoleType.ROLE_ADMIN.name()) < 1) {
            Role role1 = new Role();
            role1.setAuthority(RoleType.ROLE_ADMIN.name());
            roleRepoService.save(role1);
        }
        if (roleRepoService.countRoleByAuthority(RoleType.ROLE_USER.name()) < 1) {
            Role role2 = new Role();
            role2.setAuthority(RoleType.ROLE_USER.name());
            roleRepoService.save(role2);
        }
    }
}
