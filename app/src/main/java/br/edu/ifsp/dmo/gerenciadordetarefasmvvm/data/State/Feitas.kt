package br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State

object Feitas: filterState {
    override fun next(): filterState {
        return Todas;
    }
}