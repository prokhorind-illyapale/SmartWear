package ua.javaee.springreact.web.converter;

/**
 * Created by kleba on 24.02.2019.
 */
public interface AbstractConverter<T, V> {
    T convert(V source);
}
