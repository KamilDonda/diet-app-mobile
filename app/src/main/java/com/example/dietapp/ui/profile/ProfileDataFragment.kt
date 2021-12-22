package com.example.dietapp.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.utils.FloatConverter
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.dialog_textfield.view.*
import kotlinx.android.synthetic.main.fragment_profile_data.*


class ProfileDataFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDropdownMenu(listOf("Mężczyzna", "Kobieta"), "Mężczyzna", gender.editText)
        setupDropdownMenu(
            listOf("Schudnięcie", "Utrzymanie wagi", "Nabranie wagi"),
            "Utrzymanie wagi",
            goal.editText
        )
        setupDropdownMenu(
            listOf("Bardzo niska", "Niska", "Umiarkowana", "Wysoka", "Bardzo wysoka"),
            "Umiarkowana",
            activity_level.editText
        )

        setupDatePicker()
        showAlertWithTextInputLayout("Podaj wagę", weight, "kg", weight)
        showAlertWithTextInputLayout("Podaj wzrost", height, "cm", height)
    }

    private fun setupDropdownMenu(items: List<String>, default: String, view: EditText?) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
        view?.setText(default)
        (view as? AutoCompleteTextView)?.setAdapter(adapter)
    }

    private fun setupDatePicker() {
        val constraintsBuilder = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointBackward.now())

        val datePicker =
            MaterialDatePicker.Builder
                .datePicker()
                .setTitleText("Wybierz datę urodzenia")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(constraintsBuilder.build())
                .build()

        age.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }
    }

    private fun showAlertWithTextInputLayout(
        title: String,
        button: MaterialButton,
        suffix: String,
        displayedView: MaterialButton
    ) {
        button.setOnClickListener {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_textfield, null)
            view.dialog_title.text = title
            view.dialog_textLayout.suffixText = suffix
            val builder = AlertDialog.Builder(activity).setView(view)
            val alert = builder.show()
            view.dialog_cancel.setOnClickListener {
                alert.dismiss()
            }
            view.dialog_ok.setOnClickListener {
                displayedView.text = FloatConverter.floatToString(
                    view.dialog_input.text.toString().toFloat(),
                    suffix
                )
                alert.dismiss()
            }
        }
    }
}