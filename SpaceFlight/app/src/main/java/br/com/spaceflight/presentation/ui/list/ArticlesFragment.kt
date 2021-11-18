package br.com.spaceflight.presentation.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.spaceflight.core.util.state.NetworkState
import br.com.spaceflight.databinding.FragmentListBinding
import br.com.spaceflight.presentation.adapter.ArticleAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private val adapterArticles by lazy { ArticleAdapter() }
    private val viewModel: ArticlesViewModel by viewModels()

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycleView()
        initObserver2()
    }

    private fun setupRecycleView() {
        binding.rvArticles.apply {
            adapter = adapterArticles
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        adapterArticles.setOnClickListener { article ->
            article.id?.let {
                val action =
                    ArticlesFragmentDirections.actionArticlesFragmentToDetailsArticleFragment(it)
                findNavController().navigate(action)
            }
        }
    }

    private fun initObserver2() {
        viewModel.articles2.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NetworkState.Success -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    adapterArticles.articles = state.result.toList()
                }
                is NetworkState.Error -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT)
                        .show()
                }
                is NetworkState.Empty -> {
                    binding.progressBar.visibility = View.INVISIBLE
                    Toast.makeText(context, "Caiu aqui", Toast.LENGTH_SHORT).show()
                }
                is NetworkState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}