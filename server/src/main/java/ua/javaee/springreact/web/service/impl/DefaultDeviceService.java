package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.converter.DeviceDataToDeviceConverter;
import ua.javaee.springreact.web.data.DeviceData;
import ua.javaee.springreact.web.entity.Device;
import ua.javaee.springreact.web.repository.DeviceRepository;

import java.util.List;

@Service
public class DefaultDeviceService {
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceDataToDeviceConverter deviceDataToDeviceConverter;

    public void add(DeviceData deviceData) {
        Device device = deviceDataToDeviceConverter.convert(deviceData);
        deviceRepository.save(device);
    }

    public Device findByName(String name) {
        return deviceRepository.findByName(name);
    }

    public void remove(DeviceData deviceData) {
        Device device = deviceRepository.findByName(deviceData.getName());
        deviceRepository.delete(device);
    }

    public List<Device> findAll() {
        return deviceRepository.findAll();
    }
}
