package com.raul.androidapps.lanaapplication.ui.checkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.BindingComponent
import com.raul.androidapps.lanaapplication.databinding.FragmentCheckoutDialogBinding
import com.raul.androidapps.lanaapplication.di.injectViewModel
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.ui.common.ViewModelFactory
import com.raul.androidapps.lanaapplication.utils.ViewUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import java.lang.ref.WeakReference
import javax.inject.Inject

class CheckoutDialogFragment : BottomSheetDialogFragment(), HasAndroidInjector,
    CheckoutBasketInteractions {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var bindingComponent: BindingComponent

    @Inject
    lateinit var resourcesManager: ResourcesManager

    @Inject
    lateinit var viewUtils: ViewUtils

    private lateinit var viewModel: CheckoutViewModel
    private lateinit var binding: FragmentCheckoutDialogBinding

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_checkout_dialog,
            container,
            false,
            bindingComponent
        )
        binding.checkoutList.addItemDecoration(
            DividerItemDecoration(
                context,
                DividerItemDecoration.VERTICAL
            )
        )
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        viewModel.getCheckoutAsObservable().observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it.products.isNotEmpty()) {
                    binding.checkoutList.adapter = CheckoutAdapter(
                        checkout = it,
                        bindingComponent = bindingComponent,
                        resourcesManager = resourcesManager,
                        checkoutBasketInteractions = this
                    )
                }
            }
        })
    }


    override fun clearBasket() {
        viewModel.clearBasket()
    }

    override fun checkout() {
        clearBasket()
        dismiss()
        activity?.let {
            viewUtils.showAlertDialog(
                activity = WeakReference(it),
                allowCancelWhenTouchingOutside = false,
                message = resourcesManager.getString(R.string.thanks),
                positiveButton = resourcesManager.getString(R.string.accept)
            )
        }
    }

    override fun cancelCheckout() {
        dismiss()
    }

    companion object {

        fun newInstance(): CheckoutDialogFragment =
            CheckoutDialogFragment()
    }

}