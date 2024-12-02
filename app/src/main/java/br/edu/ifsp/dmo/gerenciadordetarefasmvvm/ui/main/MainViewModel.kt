package br.edu.ifsp.dmo.gerenciadordetarefasmvvm.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.dao.TaskDao
import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.model.Task
import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State.filterState
import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State.Todas;
import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State.Pendente;
import br.edu.ifsp.dmo.gerenciadordetarefasmvvm.data.State.Feitas
import java.util.stream.Collectors

class MainViewModel : ViewModel() {
    private val dao = TaskDao

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>>
        get() {
            return _tasks
        }

    private val _insertTask = MutableLiveData<Boolean>()
    val insertTask: LiveData<Boolean> = _insertTask

    private val _updateTask = MutableLiveData<Boolean>()
    val updateTask: LiveData<Boolean>
        get() = _updateTask

    private val _filterState = MutableLiveData<filterState>()
    val filterState : LiveData<filterState> = _filterState

    init {
        _filterState.value = Todas
        mock()
        load()
    }

    fun insertTask(description: String) {
        val task = Task(description, false)
        dao.add(task)
        _insertTask.value = true
        load()
    }

    fun updateTask(position: Int) {
        val task = if(_filterState.value is Feitas){
            dao.getAll()[position+dao.getAll().stream().filter{ t -> !t.isCompleted}.collect(
                Collectors.toList()).size]
        }else{
            dao.getAll()[position]
        }
        task.isCompleted = !task.isCompleted
        dao.updateList()
        _updateTask.value = true
        load()
    }

    private fun mock() {
        dao.add(Task("Arrumar a Cama", false))
        dao.add(Task("Retirar o lixo", false))
        dao.add(Task("Fazer trabalho de DMO1", true))
    }

    private fun load() {
        _tasks.value = when (_filterState.value) {
            is Todas -> dao.getAll()
            is Pendente -> dao.getAll().stream().filter{ t -> !t.isCompleted}.collect(
                Collectors.toList())
            else -> dao.getAll().stream().filter{ t -> t.isCompleted}.collect(
                Collectors.toList())
        }
    }

    fun aplicarFiltro(){
        _filterState.value = _filterState.value?.next();
        load();
    }
}