package com.youndevice.app.rest.controller;

import com.youndevice.app.domain.User;
import com.youndevice.app.rest.dto.ApiResponseDTO;
import com.youndevice.app.rest.dto.UserDTO;
import com.youndevice.app.rest.service.UserApiService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserApiController extends BaseApiController {

    Log log = LogFactory.getLog(UserApiController.class);

    @Autowired
    UserApiService userApiService;

    @RequestMapping("/index")
    public User getDetails(@RequestParam(value = "name", defaultValue = "Usser") String name,
                           @RequestParam(value = "id", defaultValue = "1") String id) {
        return new User(id, name);
    }


    @RequestMapping("/admin")
    public User getAdminDetails(@RequestParam(value = "name", defaultValue = "admin") String name,
                                @RequestParam(value = "id", defaultValue = "1") String id) {
        return new User(id, name);
    }

    @RequestMapping(value = "/register")
    public ApiResponseDTO register(@Validated @RequestBody UserDTO userDTO) {
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Registration");
        log.info("firstname = " + userDTO.getFirstName());
        return userApiService.saveUser(userDTO);

    }
}