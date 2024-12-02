package br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State

interface filterState {
    fun next(): filterState;
}