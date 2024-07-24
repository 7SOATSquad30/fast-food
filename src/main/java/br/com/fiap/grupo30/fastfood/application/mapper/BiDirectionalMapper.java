package br.com.fiap.grupo30.fastfood.application.mapper;

public interface BiDirectionalMapper<S, T> {
    T mapTo(S source);

    S mapFrom(T target);
}
