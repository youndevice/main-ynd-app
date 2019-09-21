package com.youndevice.app.controller;

import com.youndevice.app.domain.Device;
import com.youndevice.app.domain.User;
import com.youndevice.app.dto.DeviceDTO;
import com.youndevice.app.enums.DeviceType;
import com.youndevice.app.repository.DeviceRepository;
import com.youndevice.app.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.Arrays;
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
        ModelAndView modelAndView = new ModelAndView("device/list", responseMap);
        return modelAndView;
    }

    @GetMapping("/device/create")
    public String createDevice(Model model) {
        List deviceTypeList = Arrays.asList(DeviceType.values());
        System.out.println(deviceTypeList);
        model.addAttribute("deviceTypeList", deviceTypeList);
        return "device/create";
    }

    @PostMapping("/device/save")
    public String saveDevice(@Valid Device device, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "device/create";
        }
        deviceRepository.save(device);
        return "redirect:/device/list";
    }

    @GetMapping("/device/edit/{id}")
    public String editDevice(@PathVariable("id") long id, Model model) {
        System.out.println("Device Id : "+id);
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List deviceTypeList = Arrays.asList(DeviceType.values());
        model.addAttribute("deviceTypeList", deviceTypeList);
        model.addAttribute("device", device);
        return "device/create";
    }

    @PostMapping("/device/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Device device,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            device.setId(id);
            return "device/create";
        }
//        Device deviceInstance = deviceRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        deviceRepository.save(device);
        return "redirect:/device/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        deviceRepository.delete(device);
        return "redirect:/device/list";
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