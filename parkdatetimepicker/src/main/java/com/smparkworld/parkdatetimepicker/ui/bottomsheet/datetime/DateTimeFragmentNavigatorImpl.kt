package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.smparkworld.parkdatetimepicker.R
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal class DateTimeFragmentNavigatorImpl : DateTimeFragmentNavigator {

    override fun beginTransaction(): PhaseTransaction {
        return PhaseTransactionInternal(::navigateTransaction)
    }

    override fun clearFragments(manager: FragmentManager) {
        val transaction = manager.beginTransaction()
        Phase.values().forEach { phase ->
            val fragment = manager.findFragmentByTag(phase.getFragmentTag())
            if (fragment != null) {
                transaction.remove(fragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun navigateTransaction(transaction: PhaseTransactionInternal, manager: FragmentManager, @IdRes containerId: Int) {
        if (checkDonePhase(transaction, manager)) return

        val oldFragment = manager.findFragmentByTag(transaction.oldPhase.getFragmentTag())
        val newFragment = manager.findFragmentByTag(transaction.newPhase.getFragmentTag())

        navigateHeaderTransaction(
            oldHeaderView = transaction.oldHeaderView,
            newHeaderView = transaction.newHeaderView,
            withAnimation = (oldFragment == null || newFragment == null)
        )

        commitFragmentTransaction(manager) { fragmentTransaction ->
            when {
                (oldFragment != null && newFragment != null) -> {
                    fragmentTransaction
                        .hide(oldFragment)
                        .show(newFragment)
                }
                (oldFragment == null && newFragment != null) -> {
                    fragmentTransaction
                        .show(newFragment)
                }
                (oldFragment != null && newFragment == null) -> {
                    transaction.newPhase.createFragment()?.let { fragment ->
                        fragmentTransaction
                            .hide(oldFragment)
                            .add(containerId, fragment, transaction.newPhase.getFragmentTag())
                            .show(fragment)
                    }
                }
                (oldFragment == null && newFragment == null) -> {
                    transaction.newPhase.createFragment()?.let { fragment ->
                        fragmentTransaction
                            .add(containerId, fragment, transaction.newPhase.getFragmentTag())
                            .show(fragment)
                    }
                }
            }
        }
    }

    private fun commitFragmentTransaction(manager: FragmentManager, perform: (FragmentTransaction) -> Unit) {
        manager.beginTransaction()
            .setCustomAnimations(
                R.anim.alpha_fade_in_200,
                R.anim.alpha_fade_out_200
            )
            .apply(perform)
            .setReorderingAllowed(true)
            .commitAllowingStateLoss()
    }

    private fun navigateHeaderTransaction(oldHeaderView: View?, newHeaderView: View?, withAnimation: Boolean) {
        when {
            (oldHeaderView != null && newHeaderView != null) -> {
                if (withAnimation) {
                    hideHeaderView(oldHeaderView)
                    showHeaderView(newHeaderView)
                } else {
                    setHeaderViewVisibility(oldHeaderView, false)
                    setHeaderViewVisibility(newHeaderView, true)
                }
            }
            (oldHeaderView == null && newHeaderView != null) -> {
                setHeaderViewVisibility(newHeaderView, true)
            }
            (oldHeaderView != null && newHeaderView == null) -> {
                if (withAnimation) {
                    hideHeaderView(oldHeaderView)
                } else {
                    setHeaderViewVisibility(oldHeaderView, false)
                }
            }
        }
    }

    private fun hideHeaderView(headerView: View, callback: (() -> Unit)? = null) {
        headerView.alpha = 1f
        headerView.animate()
            .alphaBy(1f)
            .alpha(0f)
            .withEndAction {
                headerView.visibility = View.GONE
                callback?.invoke()
            }
            .duration = DURATION
    }

    private fun showHeaderView(headerView: View) {
        headerView.alpha = 0f
        headerView.animate()
            .alphaBy(0f)
            .alpha(1f)
            .withStartAction {
                headerView.visibility = View.VISIBLE
            }
            .duration = DURATION
    }

    private fun setHeaderViewVisibility(view: View, isVisible: Boolean) {
        if (isVisible) {
            view.alpha = 1f
            view.visibility = View.VISIBLE
        } else {
            view.alpha = 0f
            view.visibility = View.GONE
        }
    }

    private fun checkDonePhase(transaction: PhaseTransactionInternal, manager: FragmentManager): Boolean {
        if (transaction.newPhase == Phase.DONE) {
            clearFragments(manager)
            transaction.onDone?.invoke()
            return true
        }
        return false
    }

    private class PhaseTransactionInternal(
        private val onCommit: (transaction: PhaseTransactionInternal, manager: FragmentManager, containerId: Int) -> Unit
    ) : PhaseTransaction {

        lateinit var oldPhase: Phase
            private set

        lateinit var newPhase: Phase
            private set

        var oldHeaderView: View? = null
            private set

        var newHeaderView: View? = null
            private set

        var onDone: (() -> Unit)? = null
            private set

        override fun addOldPhase(oldPhase: Phase): PhaseTransaction {
            this.oldPhase = oldPhase
            return this
        }

        override fun addNewPhase(newPhase: Phase): PhaseTransaction {
            this.newPhase = newPhase
            return this
        }

        override fun addOldPhaseHeaderView(oldHeaderView: View?): PhaseTransaction {
            this.oldHeaderView = oldHeaderView
            return this
        }

        override fun addNewPhaseHeaderView(newHeaderView: View?): PhaseTransaction {
            this.newHeaderView = newHeaderView
            return this
        }

        override fun addOnDone(callback: () -> Unit): PhaseTransaction {
            this.onDone = callback
            return this
        }

        override fun commit(@IdRes containerId: Int, manager: FragmentManager) {
            checkValidation()
            onCommit.invoke(this, manager, containerId)
        }

        private fun checkValidation() {
            if (::oldPhase.isInitialized.not() && ::newPhase.isInitialized.not()) {
                throw IllegalArgumentException(ERROR_INVALID_ARGUMENT_STATE)
            }
        }
    }

    companion object {

        private const val DURATION = 200L
        private const val ERROR_INVALID_ARGUMENT_STATE = "New phase and old phase must be initialize."
    }
}