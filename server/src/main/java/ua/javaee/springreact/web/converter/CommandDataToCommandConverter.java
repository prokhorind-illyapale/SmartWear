package ua.javaee.springreact.web.converter;

import org.springframework.stereotype.Component;
import ua.javaee.springreact.web.data.CommandData;
import ua.javaee.springreact.web.entity.Command;

@Component
public class CommandDataToCommandConverter implements AbstractConverter<Command, CommandData> {
    @Override
    public Command convert(CommandData source) {
        Command target = new Command();
        target.setName(source.getName());
        return target;
    }
}
