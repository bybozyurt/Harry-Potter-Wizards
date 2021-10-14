package com.cevlikalprn.harrypotterwizards.ui.listscreen

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.cevlikalprn.harrypotterwizards.R
import com.cevlikalprn.harrypotterwizards.adapter.AdapterClickListener
import com.cevlikalprn.harrypotterwizards.adapter.WizardListAdapter
import com.cevlikalprn.harrypotterwizards.data.database.WizardEntity
import com.cevlikalprn.harrypotterwizards.data.database.asDatabaseModel
import com.cevlikalprn.harrypotterwizards.databinding.FragmentWizardListBinding
import com.cevlikalprn.harrypotterwizards.di.HarryPotterWizardsApplication
import com.cevlikalprn.harrypotterwizards.util.NetworkResult

class WizardListFragment : Fragment(), AdapterClickListener {

    private var _binding: FragmentWizardListBinding? = null
    private val binding: FragmentWizardListBinding
        get() = _binding!!

    private lateinit var viewModel: WizardListViewModel

    private lateinit var adapter: WizardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWizardListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val appContainer =
            (requireActivity().applicationContext as HarryPotterWizardsApplication).appContainer

        viewModel =
            ViewModelProvider(
                this,
                appContainer.wizardListViewModelFactory
            ).get(WizardListViewModel::class.java)

        //adapter
        adapter = WizardListAdapter(this)
        binding.wizardListRecyclerView.adapter = adapter

        viewModel.wizardsFromDatabase.observe(viewLifecycleOwner) { wizards ->
            if (!wizards.isNullOrEmpty()) {
                adapter.wizards = wizards
            } else {
                ifNoResponseFromDatabase()
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.favorites_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu_item -> {
                findNavController().navigate(WizardListFragmentDirections.actionWizardsFragmentToFavoriteWizardsFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun ifNoResponseFromDatabase() {
        viewModel.wizardsFromInternet.observe(viewLifecycleOwner) { wizardsFromInternet ->
            when (wizardsFromInternet) {
                is NetworkResult.Success -> adapter.wizards =
                    asDatabaseModel(wizardsFromInternet.data!!)
                is NetworkResult.Error -> {
                    binding.apply {
                        networkStateImage.setImageResource(R.drawable.ic_mood_bad)
                        errorMessageTextView.text = wizardsFromInternet.errorMessage
                        networkStateImage.visibility = View.VISIBLE
                        errorMessageTextView.visibility = View.VISIBLE
                    }
                }
                is NetworkResult.Loading -> {
                    //TODO
                }
            }
        }
    }

    private fun navigateToDetailsFragment(wizard: WizardEntity) {
        findNavController().navigate(
            WizardListFragmentDirections.actionWizardsFragmentToWizardDetailsFragment(wizard)
        )
    }

    override fun updateWizard(wizard: WizardEntity) {
        viewModel.updateWizard(wizard)
    }

    override fun onItemClicked(wizard: WizardEntity) {
        navigateToDetailsFragment(wizard)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}