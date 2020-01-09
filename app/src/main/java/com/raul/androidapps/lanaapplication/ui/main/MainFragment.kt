package com.raul.androidapps.lanaapplication.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.MainFragmentBinding
import com.raul.androidapps.lanaapplication.extensions.nonNull
import com.raul.androidapps.lanaapplication.ui.common.BaseFragment
import okhttp3.internal.notify
import timber.log.Timber

class MainFragment : BaseFragment() {

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.main_fragment,
            container,
            false,
            bindingComponent
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProductsAsObservable()
            .observe(viewLifecycleOwner, Observer {
                it?.let{
                    Timber.d("")
                }
                // use the response from server
            })
        binding.message.setOnClickListener {
            viewModel.test()
        }
//        viewModel.needToShowLoading()
//            .nonNull()
//            .observe(this, Observer {
//                if (it == true) {
//                    showLoading()
//                } else {
//                    hideLoading()
//                }
//            })
//        viewModel.needToShowError()
//            .observe(this, Observer { message ->
//                if (message != null) {
//                    showError(message)
//                } else {
//                    viewModel.resetError()
//                }
//            })
//
//        viewModel.getFoo()

    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun showError(message: String?) {
        //todo implement error function
    }

}
