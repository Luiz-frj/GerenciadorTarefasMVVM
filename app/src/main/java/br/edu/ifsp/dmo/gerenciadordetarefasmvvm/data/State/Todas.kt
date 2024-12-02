package br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State

object Todas: filterState {
    override fun next(): filterState {
        return Pendente;
    }
}