package com.raul.androidapps.lanaapplication.ui.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.MainFragmentBinding
import com.raul.androidapps.lanaapplication.ui.checkout.CheckoutDialogFragment
import com.raul.androidapps.lanaapplication.ui.common.BaseFragment
import com.raul.androidapps.lanaapplication.vo.Result


class MainFragment : BaseFragment(), BasketInteractions {

    private lateinit var binding: MainFragmentBinding

    private lateinit var viewModel: MainViewModel

    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

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
        adapter = ProductAdapter(bindingComponent, resourcesManager, this)
        binding.productList.apply {
            adapter = this@MainFragment.adapter
            val itemDecor = DividerItemDecoration(context, VERTICAL)
            addItemDecoration(itemDecor)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getProductsAsObservable()
            .observe(viewLifecycleOwner, Observer {
                it?.let {
                    when (it.status) {
                        Result.Status.SUCCESS -> {
                            hideLoading()
                            adapter.submitList(it.data)
                        }
                        Result.Status.ERROR -> {
                            hideLoading()
                            showError(it.message)
                            it.data?.let { list -> adapter.submitList(list) }
                        }
                        Result.Status.LOADING -> {
                            showLoading()
                            it.data?.let { list -> adapter.submitList(list) }
                        }
                    }
                }
            })


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.checkout_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.checkout_item -> {
                showCheckoutScreen()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun showError(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    override fun addProductToBasket(code: String?) {
        code?.let { viewModel.addProductToBasket(code) }
    }

    override fun removeProductToBasket(code: String?) {
        code?.let { viewModel.removeProductToBasket(code) }
    }

    override fun clearBasket() {
        viewModel.clearBasket()
    }

    private fun showCheckoutScreen() {
        activity?.supportFragmentManager?.let {
            CheckoutDialogFragment.newInstance(30).show(it, "dialog")
        }
    }
}

//todo implement pull to refresh