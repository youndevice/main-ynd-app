package com.youndevice.app.dto;


import com.youndevice.app.enums.DeviceType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDTO {

    private Long id;

    private DeviceType deviceType;

    private String userFriendlyName;

}
