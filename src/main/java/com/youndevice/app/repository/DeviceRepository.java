package com.youndevice.app.repository;


import com.youndevice.app.domain.Device;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findAllByUser_Id(Long userId, Pageable pageable);

}