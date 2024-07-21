package br.com.fiap.grupo30.fastfood.application.mapper;

public interface UniDirectionalMapper<S, T> {
    T map(S source);
}
