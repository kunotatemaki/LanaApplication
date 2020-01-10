package com.raul.androidapps.lanaapplication.ui.checkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raul.androidapps.lanaapplication.R
import com.raul.androidapps.lanaapplication.databinding.BindingComponent
import com.raul.androidapps.lanaapplication.di.injectViewModel
import com.raul.androidapps.lanaapplication.resources.ResourcesManager
import com.raul.androidapps.lanaapplication.ui.common.ViewModelFactory
import com.raul.androidapps.lanaapplication.ui.main.ProductAdapter
import com.raul.androidapps.lanaapplication.databinding.FragmentCheckoutDialogBinding
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_checkout_dialog_item.view.*
import kotlinx.android.synthetic.main.fragment_checkout_dialog.*
import javax.inject.Inject

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    CheckoutDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class CheckoutDialogFragment : BottomSheetDialogFragment() , HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var bindingComponent: BindingComponent

    @Inject
    lateinit var resourcesManager: ResourcesManager

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
        adapter = CheckoutAdapter(bindingComponent, resourcesManager, this)
        binding.checkoutList.apply {
            adapter = this@MainFragment.adapter
            val itemDecor = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(itemDecor)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = arguments?.getInt(ARG_ITEM_COUNT)?.let { CheckoutAdapter(it) }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = injectViewModel(viewModelFactory)
        viewModel.getCheckoutAsObservable().observe(viewLifecycleOwner, Observer {
            it?.let {

            }
        })
    }

    private inner class ViewHolder internal constructor(
        inflater: LayoutInflater,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        inflater.inflate(
            R.layout.fragment_checkout_dialog_item,
            parent,
            false
        )
    ) {

        internal val text: TextView = itemView.text
    }

    private inner class CheckoutAdapterq internal constructor(private val mItemCount: Int) :
        RecyclerView.Adapter<ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = position.toString()
        }

        override fun getItemCount(): Int {
            return mItemCount
        }
    }

    companion object {

        fun newInstance(itemCount: Int): CheckoutDialogFragment =
            CheckoutDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ITEM_COUNT, itemCount)
                }
            }

    }

}