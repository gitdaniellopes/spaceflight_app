package br.com.spaceflight.presentation.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.spaceflight.core.util.state.NetworkState
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.domain.use_case.list.ListArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticlesViewModel @Inject constructor(
    private val listArticlesUseCase: ListArticlesUseCase
) : ViewModel() {

    private val _articles= MutableLiveData<NetworkState<List<Articles>>>()
    val articles get() = _articles

    init {
        getAll()
    }

    private fun getAll() = viewModelScope.launch {
        listArticlesUseCase()
            .catch {
                _articles.postValue(NetworkState.Error(it.message.toString()))
            }
            .collect {
                _articles.postValue(it)
            }
    }
}