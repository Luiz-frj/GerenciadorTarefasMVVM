package br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.dao

import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.model.Task
import java.util.stream.Collectors

object TaskDao {

    private var tasks: MutableList<Task> = mutableListOf();

    fun add(task: Task){
        tasks.add(task);
    }

    fun getAll() = tasks;

    fun get(id: Long): Task?{
        return tasks.stream()
            .filter { t -> t.id == id }
            .findFirst()
            .orElse(null)
    }

    fun updateList(){
        tasks = tasks.stream().sorted().collect(Collectors.toList())
    }

}