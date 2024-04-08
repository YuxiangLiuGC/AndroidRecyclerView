package com.example.productapp

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CountsDialogFragment : DialogFragment() {

    companion object {
        private const val ARG_CLICK_COUNTS = "clickCounts"

        fun newInstance(clickCounts: String): CountsDialogFragment {
            val fragment = CountsDialogFragment()
            val args = Bundle()
            args.putString(ARG_CLICK_COUNTS, clickCounts)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val clickCounts = arguments?.getString(ARG_CLICK_COUNTS) ?: ""

        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Counts")
            .setMessage(clickCounts)
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }

        return builder.create()
    }
}