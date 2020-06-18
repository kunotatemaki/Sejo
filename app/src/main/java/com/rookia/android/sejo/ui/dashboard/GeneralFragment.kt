package com.rookia.android.sejo.ui.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.rookia.android.androidutils.domain.vo.Result
import com.rookia.android.sejo.R
import com.rookia.android.sejo.databinding.FragmentGeneralBinding
import com.rookia.android.sejo.ui.common.BaseFragment
import com.rookia.android.sejo.ui.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_general.*
import timber.log.Timber

@AndroidEntryPoint
class GeneralFragment : BaseFragment(R.layout.fragment_general), GeneralAdapter.GroupItemClickable {


    private val viewModel: GeneralViewModel by viewModels()
    private lateinit var adapter: GeneralAdapter

    private lateinit var binding: FragmentGeneralBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as? MainActivity)?.hideKeyboard()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGeneralBinding.bind(view)

        adapter = GeneralAdapter(this)
//        test.setText("623")
        button1.setOnClickListener {
            navigateToGroupCreation()
//            test.showPassword()
        }
//        hide.setOnClickListener {
//            test.hidePassword()
//        }
//        error.setOnClickListener {
//            test.showErrorFeedback()
//        }
        binding.list.adapter = adapter

        viewModel.groups.observe(viewLifecycleOwner, Observer {
            Timber.d("")
            when(it.status){
                Result.Status.SUCCESS -> {
                    hideLoading()
                    adapter.submitList(it.data)
                }
                Result.Status.LOADING -> {
                    showLoading()
                    adapter.submitList(it.data)
                }
                Result.Status.ERROR -> {
                    hideLoading()
                }
            }


        })

    }

    override fun onResume() {
        super.onResume()
        viewModel.loadGroups()
    }

    private fun navigateToGroupCreation() {
        val direction =
            GeneralFragmentDirections.actionBlankFragmentToGroupCreationActivity()
        findNavController().navigate(direction)
    }

    override fun onGroupClicked(groupId: Long) {
        viewModel.setSelectedGroup(groupId)
    }
}