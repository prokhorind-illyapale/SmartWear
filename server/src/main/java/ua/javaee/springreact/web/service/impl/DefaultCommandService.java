package ua.javaee.springreact.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.javaee.springreact.web.converter.CommandDataToCommandConverter;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.entity.Command;
import ua.javaee.springreact.web.repository.CommandRepository;

import java.util.List;

@Service
public class DefaultCommandService {
    @Autowired
    private CommandRepository commandRepository;
    @Autowired
    private CommandDataToCommandConverter commandDataToCommandConverter;

    public void add(CommandData commandData) {
        Command command = commandDataToCommandConverter.convert(commandData);
        commandRepository.save(command);
    }

    public Command findByName(String name) {
        return commandRepository.findByName(name);
    }

    public void remove(CommandData commandData) {
        Command command = commandRepository.findByName(commandData.getName());
        commandRepository.delete(command);
    }

    public List<Command> findAll() {
        return commandRepository.findAll();
    }
}
