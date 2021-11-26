package id.elfastudio.rijksmuseum.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.elfastudio.rijksmuseum.R
import id.elfastudio.rijksmuseum.adapter.CollectionsAdapter
import id.elfastudio.rijksmuseum.data.entity.ArtObjectsItem
import id.elfastudio.rijksmuseum.databinding.FragmentHomeBinding
import id.elfastudio.rijksmuseum.others.Status
import id.elfastudio.rijksmuseum.ui.DetailActivity

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by hiltNavGraphViewModels(R.id.mobile_navigation)
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewmodel = homeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        binding.refresh.setOnRefreshListener {
            homeViewModel.refresh()
        }
    }

    private fun setObserver() {
        homeViewModel.collections.observe(viewLifecycleOwner) {
            when (it.status) {
                Status.LOADING -> {
                    homeViewModel.loading = true
                    homeViewModel.message.value = getString(R.string.msg_loading)
                }
                Status.SUCCESS -> {
                    homeViewModel.loading = false
                    binding.refresh.isRefreshing = false
                    it.data?.let { collection ->
                        homeViewModel.empty.value = collection.artObjects.isEmpty()
                        if (collection.artObjects.isEmpty())
                            homeViewModel.message.value = getString(R.string.msg_empty)
                        else {
                            val collectionAdapter = CollectionsAdapter(collection.artObjects)
                            collectionAdapter.onItemClickListener =
                                object : CollectionsAdapter.OnItemClickListener {
                                    override fun onItemClicked(item: ArtObjectsItem) {
                                        startActivity(
                                            Intent(context, DetailActivity::class.java)
                                                .putExtra(DetailActivity.EXTRA_DATA, item)
                                        )
                                    }
                                }
                            binding.recyclerArts.apply {
                                layoutManager = LinearLayoutManager(context)
                                adapter = collectionAdapter
                                addItemDecoration(
                                    DividerItemDecoration(
                                        context,
                                        DividerItemDecoration.VERTICAL
                                    )
                                )
                            }
                        }
                    }
                }
                Status.ERROR -> {
                    homeViewModel.loading = false
                    homeViewModel.error.value = true
                    homeViewModel.message.value = it.message
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}