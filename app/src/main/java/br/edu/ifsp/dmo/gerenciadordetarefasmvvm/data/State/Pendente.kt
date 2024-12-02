package br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State

object Pendente: filterState {
    override fun next(): filterState {
        return Feitas;
    }

}