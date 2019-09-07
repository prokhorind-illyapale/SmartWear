package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.entity.Command;

@Component
public class CommandToCommandDataConverter implements AbstractConverter<CommandData, Command> {
    @Override
    public CommandData convert(Command source) {
        CommandData target = new CommandData();
        target.setName(source.getName());
        return target;
    }
}
