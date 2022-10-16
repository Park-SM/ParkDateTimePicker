package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.FragmentManager
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

        val oldFragment = manager.findFragmentByTag(transaction.oldPhase?.getFragmentTag())
        val newFragment = manager.findFragmentByTag(transaction.newPhase.getFragmentTag())

        navigateHeaderTransaction(
            oldHeaderView = transaction.oldHeaderView,
            newHeaderView = transaction.newHeaderView,
            withAnimation = (oldFragment == null || newFragment == null)
        )

        val fragmentTransaction = manager.beginTransaction()
            .setCustomAnimations(
                R.anim.alpha_fade_in_200,
                R.anim.alpha_fade_out_200
            )
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
                val fragment = transaction.newPhase.createFragment() ?: return
                fragmentTransaction
                    .add(containerId, fragment, transaction.newPhase.getFragmentTag())
                    .hide(oldFragment)
                    .show(fragment)
            }
            (oldFragment == null && newFragment == null) -> {
                val fragment = transaction.newPhase.createFragment() ?: return
                fragmentTransaction
                    .add(containerId, fragment, transaction.newPhase.getFragmentTag())
                    .show(fragment)
            }
        }
        fragmentTransaction
            .setReorderingAllowed(true)
            .commitAllowingStateLoss()
    }

    private fun navigateHeaderTransaction(oldHeaderView: View?, newHeaderView: View?, withAnimation: Boolean) {
        if (oldHeaderView != null) {
            if (withAnimation) {
                oldHeaderView.animate()
                    .alpha(0f)
                    .withEndAction {
                        oldHeaderView.visibility = View.INVISIBLE
                    }
                    .duration = 200
            } else {
                oldHeaderView.visibility = View.INVISIBLE
            }
        }
        if (newHeaderView != null) {
            if (withAnimation) {
                newHeaderView.animate()
                    .alpha(1f)
                    .withStartAction {
                        newHeaderView.visibility = View.VISIBLE
                    }
                    .duration = 200
            } else {
                newHeaderView.visibility = View.VISIBLE
            }
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

        var oldPhase: Phase? = null
            private set

        lateinit var newPhase: Phase
            private set

        var oldHeaderView: View? = null
            private set

        var newHeaderView: View? = null
            private set

        var onDone: (() -> Unit)? = null
            private set

        override fun addOldPhase(oldPhase: Phase?): PhaseTransaction {
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
            if (::newPhase.isInitialized.not()) {
                throw IllegalArgumentException(ERROR_INVALID_ARGUMENT_STATE)
            }
        }
    }

    companion object {
        private const val ERROR_INVALID_ARGUMENT_STATE = "New phase must be initialize."
    }
}