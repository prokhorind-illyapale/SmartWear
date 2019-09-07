package ua.javaee.springreact.web.entity;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table
public class Command {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "command_id")
    private long commandId;
    @Column
    private String name;

    public long getCommandId() {
        return commandId;
    }

    public void setCommandId(long commandId) {
        this.commandId = commandId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
