package br.com.spaceflight.ui.artcles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.spaceflight.R
import br.com.spaceflight.databinding.FragmentListBinding
import br.com.spaceflight.ui.adapter.ArticleAdapter
import br.com.spaceflight.util.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ArticlesFragment : Fragment(R.layout.fragment_list) {

    private val adapterArticles by lazy { ArticleAdapter() }
    private val viewModel: ArticlesViewModel by viewModels()

    private val binding: FragmentListBinding by lazy {
        FragmentListBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getArticles()
        setupRecycleView()
        initObserver()
    }


    private fun setupRecycleView() {
        binding.rvArticles.apply {
            adapter = adapterArticles
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
    }

    private fun initObserver() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.articles.collectLatest { state ->
                when (state) {
                    is State.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        adapterArticles.articles = state.result.toList()
                    }
                    is State.Error -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), state.error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                    is State.Empty -> {
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                    is State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}