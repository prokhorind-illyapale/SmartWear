package ua.javaee.springreact.web.service;

import ua.javaee.springreact.web.entity.LookType;

import java.util.List;

public interface LookTypeService {
    List<LookType> getAllLookTypes();

    LookType getLookTypeByName(String name);

    boolean removeLookType(LookType lookType);

    boolean saveLookType(LookType lookType);
}
