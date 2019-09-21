package com.youndevice.app.rest.controller;

import com.youndevice.app.rest.dto.ApiResponseDTO;
import com.youndevice.app.rest.dto.ApplianceDTO;
import com.youndevice.app.rest.service.apiServices.ApplianceApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/appliance")
public class ApplianceApiController extends BaseApiController {


    @Autowired
    ApplianceApiService applianceApiService;

    @RequestMapping(value = "/list", produces = "application/json")
//    @ResponseBody
    public ResponseEntity<List<ApplianceDTO>> listAppliancesByUserId(@RequestParam(value = "userId", defaultValue = "5") Long userId,
                                                                     @RequestParam(value = "max", defaultValue = "10") Integer max,
                                                                     @RequestParam(value = "offset", defaultValue = "0") Integer offset) {
        return applianceApiService.findByUserId(userId, max, offset);
    }


    @RequestMapping(value = "/device/register", method = RequestMethod.POST)
    public ApiResponseDTO registerAppliance(@RequestBody ApplianceDTO applianceDTO) {
        //TODO Ask the flow to register any Appliances
        return applianceApiService.registerAppliance(applianceDTO);
    }

    //THis api will be used by device to get current Status
    @RequestMapping(value = "/device/status", method = RequestMethod.GET)
    public ApiResponseDTO<String> getApplianceStatus(@RequestParam(value = "appliance_id", defaultValue = "5") Long applianceId) {
        return applianceApiService.getApplianceStatus(applianceId);
    }


    @RequestMapping(value = "device/status", method = RequestMethod.POST)
    public ApiResponseDTO<String> setApplianceActualDeviceStatus(@RequestParam(value = "appliance_id", defaultValue = "5") Long applianceId,
                                                                 @RequestParam(value = "appliance_status", defaultValue = "0") String applianceStatus) {
        return applianceApiService.setApplianceStatus(applianceId, applianceStatus);
    }

    @RequestMapping(value = "device/changeWebStatus", method = RequestMethod.POST)
    public ApiResponseDTO<String> setApplianceWebStatus(@RequestParam(value = "appliance_id", defaultValue = "5") Long applianceId,
                                                        @RequestParam(value = "web_status", defaultValue = "0") String applianceStatus) {
        return applianceApiService.setApplianceWebStatus(applianceId, applianceStatus);
    }
}