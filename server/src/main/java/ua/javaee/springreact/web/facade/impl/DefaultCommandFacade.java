package ua.javaee.springreact.web.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.converter.CommandToCommandDataConverter;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.entity.Command;
import ua.javaee.springreact.web.service.impl.DefaultCommandService;

import java.util.List;

import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@Component
public class DefaultCommandFacade {
    @Autowired
    private DefaultCommandService defaultCommandService;
    @Autowired
    private CommandToCommandDataConverter commandToCommandDataConverter;

    public void add(CommandData commandData) {
        defaultCommandService.add(commandData);
    }

    public void remove(CommandData commandData) {
        defaultCommandService.remove(commandData);
    }

    public CommandData findByName(String name) {
        Command command = defaultCommandService.findByName(name);
        return isNull(command) ? null : commandToCommandDataConverter.convert(command);
    }

    public List<CommandData> findAll() {
        return defaultCommandService.findAll().stream().map(c -> commandToCommandDataConverter.convert(c)).collect(toList());
    }
}
