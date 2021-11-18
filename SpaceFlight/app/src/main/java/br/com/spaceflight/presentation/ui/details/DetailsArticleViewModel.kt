package br.com.spaceflight.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.spaceflight.data.model.Articles
import br.com.spaceflight.domain.use_case.details.DetailsArticleUseCase
import br.com.spaceflight.core.util.state.NetworkState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsArticleViewModel @Inject constructor(
    private val detailsArticleUseCase: DetailsArticleUseCase
) : ViewModel() {

    private val _article = MutableStateFlow<NetworkState<Articles>>(NetworkState.Loading)
    val article = _article.asStateFlow()

    fun getArticleById(id: Int) = viewModelScope.launch {
        detailsArticleUseCase(id)
            .catch {
                _article.value = NetworkState.Error(it.message.toString())
            }
            .collect { article ->
                _article.value = NetworkState.Success(article)
            }

    }
}