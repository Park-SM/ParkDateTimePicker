package com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime

import android.view.View
import androidx.fragment.app.FragmentManager
import com.smparkworld.parkdatetimepicker.ui.bottomsheet.datetime.model.Phase

internal class DateTimeFragmentNavigatorImpl : DateTimeFragmentNavigator {

    override fun beginTransaction(): PhaseTransaction {
        return PhaseTransactionInternal(::navigateTransaction)
    }

    // Implement to start header visibility animation and start fragment visibility animation based on transaction.
    private fun navigateTransaction(transaction: PhaseTransactionInternal, manager: FragmentManager) {

    }

    private class PhaseTransactionInternal(
        private val onCommit: (transaction: PhaseTransactionInternal, manager: FragmentManager) -> Unit
    ) : PhaseTransaction {

        var oldPhase: Phase? = null
        var newPhase: Phase? = null
        var oldHeaderView: View? = null
        var newHeaderView: View? = null

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

        override fun commit(manager: FragmentManager) {
            onCommit.invoke(this, manager)
        }
    }
}