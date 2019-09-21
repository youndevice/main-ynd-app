package com.youndevice.app.controller;

import com.youndevice.app.domain.Appliance;
import com.youndevice.app.dto.ApplianceDTO;
import com.youndevice.app.repository.ApplianceRepository;
import com.youndevice.app.repository.DeviceRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/appliance")
public class ApplianceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ApplianceRepository applianceRepository;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(value = {"/list"}, method = RequestMethod.GET)
    public ModelAndView listAppliance() {
        List<Appliance> applianceList = applianceRepository.findAll();
        System.out.println("size of appliance=" + applianceList.size());
        List<ApplianceDTO> applianceDTOList = applianceList.stream()
                .map(appliance -> convertToDto(appliance))
                .collect(Collectors.toList());
        Map responseMap = new HashMap();
        responseMap.put("applianceList", applianceDTOList);
        ModelAndView modelAndView = new ModelAndView("appliance/list", responseMap);
        return modelAndView;
    }

    @GetMapping("/create")
    public String createAppliance(Model model) {
        return "appliance/create";
    }

    @PostMapping("/save")
    public String saveAppliance(@Valid Appliance appliance, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "appliance/create";
        }
        applianceRepository.save(appliance);
        return "redirect:/appliance/list";
    }

    @GetMapping("/edit/{id}")
    public String editAppliance(@PathVariable("id") long id, Model model) {
        System.out.println("Appliance Id : " + id);
        Appliance appliance = applianceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Appliance Id:" + id));
        model.addAttribute("appliance", appliance);
        return "appliance/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAppliance(@PathVariable("id") long id, @Valid Appliance appliance,
                                  BindingResult result, Model model) {
        if (result.hasErrors()) {
            appliance.setId(id);
            return "appliance/create";
        }
        Appliance applianceInstance = applianceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appliance Id:" + id));
        applianceInstance.setUserFriendlyName(appliance.getUserFriendlyName());
        applianceRepository.save(applianceInstance);
        return "redirect:/appliance/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAppliance(@PathVariable("id") long id, Model model) {
        Appliance appliance = applianceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid appliance Id:" + id));
        applianceRepository.delete(appliance);
        return "redirect:/appliance/list";
    }

    private ApplianceDTO convertToDto(Appliance appliance) {
        ApplianceDTO applianceDTO = modelMapper.map(appliance, ApplianceDTO.class);
        return applianceDTO;
    }

}