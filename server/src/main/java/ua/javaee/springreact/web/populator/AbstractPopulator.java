package ua.javaee.springreact.web.populator;



/**
 * Created by kleba on 09.02.2019.
 */
public interface AbstractPopulator  <T,V > {
    void populate (T source , V target);
}
