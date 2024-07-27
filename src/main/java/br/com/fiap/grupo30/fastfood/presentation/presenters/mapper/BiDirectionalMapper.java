package br.com.fiap.grupo30.fastfood.presentation.presenters.mapper;

public interface BiDirectionalMapper<S, T> {
    T mapTo(S source);

    S mapFrom(T target);
}
