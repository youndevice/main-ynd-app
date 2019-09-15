package com.youndevice.app.controller;

import com.youndevice.app.domain.Device;
import com.youndevice.app.dto.DeviceDTO;
import com.youndevice.app.repository.DeviceRepository;
import com.youndevice.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class DeviceController {

    @Autowired
    private UserService userService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = {"/device/list"}, method = RequestMethod.GET)
    public ModelAndView listDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        System.out.println("size of devices=" + deviceList.size());
        List<DeviceDTO> deviceDTOList = deviceList.stream()
                .map(device -> convertToDto(device))
                .collect(Collectors.toList());
        Map responseMap = new HashMap();
        responseMap.put("deviceList", deviceDTOList);
        ModelAndView modelAndView = new ModelAndView("deviceListing", responseMap);
        return modelAndView;
    }

    private DeviceDTO convertToDto(Device device) {
        DeviceDTO deviceDTO = modelMapper.map(device, DeviceDTO.class);
        return deviceDTO;
    }

    private Device convertToEntity(DeviceDTO deviceDTO) throws ParseException {
        Device device = modelMapper.map(deviceDTO, Device.class);
        if (deviceDTO.getId() != null) {
//            Device oldDevice = postService.getPostById(deviceDTO.getId());
//            device.setRedditID(oldDevice.getRedditID());
//            device.setSent(oldDevice.isSent());
        }
        return device;
    }

}