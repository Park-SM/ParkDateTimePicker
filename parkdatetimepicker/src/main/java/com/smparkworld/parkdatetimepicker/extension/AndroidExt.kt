package com.smparkworld.parkdatetimepicker.extension

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

internal fun <T> MutableCollection<T>.addIfNonNull(element: T?): Boolean {
    return if (element != null) {
        add(element)
        true
    } else {
        false
    }
}

internal inline fun <reified VM : ViewModel> Fragment.viewModels(
    noinline getFactory: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val getNonNullFactory = getFactory ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class.java, { this }, getNonNullFactory)
}

internal inline fun <reified VM : ViewModel> Fragment.parentViewModels(
    noinline getFactory: (() -> ViewModelProvider.Factory)? = null
): Lazy<VM> {
    val getNonNullFactory = getFactory ?: {
        defaultViewModelProviderFactory
    }
    return ViewModelLazy(VM::class.java, ::requireParentFragment, getNonNullFactory)
}

internal class ViewModelLazy<VM : ViewModel>(
    private val viewModelClass: Class<VM>,
    private val getOwner: () -> ViewModelStoreOwner,
    private val getFactory: () -> ViewModelProvider.Factory
) : Lazy<VM> {

    private var cached: VM? = null

    override val value: VM
        get() {
            val vm = cached
            return if (vm == null) {
                val owner = getOwner.invoke()
                val viewModelFactory = getFactory.invoke()

                ViewModelProvider(owner, viewModelFactory)[viewModelClass].also {
                    cached = it
                }
            } else {
                vm
            }
        }

    override fun isInitialized(): Boolean = (cached != null)
}

internal fun <T> MutableLiveData<T>.updateAssign(perform: (T) -> Unit) {
    val oldValue = this.value
    if (oldValue != null) {
        this.value = oldValue.apply(perform)
    }
}
