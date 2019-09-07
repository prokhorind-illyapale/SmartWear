package ua.javaee.springreact.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.javaee.springreact.web.entity.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Device findByName(String name);
}
