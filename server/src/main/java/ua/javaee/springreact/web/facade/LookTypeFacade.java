package ua.javaee.springreact.web.facade;

import ua.javaee.springreact.web.data.LookTypeData;

import java.util.List;

public interface LookTypeFacade {
    boolean isLookTypeExists(String name);

    List<LookTypeData> getAllLookTypes();

    LookTypeData getLookTypeByName(String name);

    boolean removeLookType(String name);

    boolean saveLookType(LookTypeData data);
}
