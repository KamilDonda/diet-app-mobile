package com.example.dietapp.ui.profile

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.utils.AgeConverter
import com.example.dietapp.utils.FloatConverter
import com.google.android.material.button.MaterialButton
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.dialog_textfield.view.*
import kotlinx.android.synthetic.main.fragment_profile_data.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class ProfileDataFragment : Fragment() {

    private val viewModel: ProfileViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.bmi.observe(viewLifecycleOwner, {
            bmi.text = it.toString()
        })

        initData()

        setupDropdownMenu(listOf("Mężczyzna", "Kobieta"), gender.editText)
        setupDropdownMenu(
            listOf("Schudnięcie", "Utrzymanie wagi", "Nabranie wagi"),
            goal.editText
        )
        setupDropdownMenu(
            listOf("Bardzo niska", "Niska", "Umiarkowana", "Wysoka", "Bardzo wysoka"),
            activity_level.editText
        )

        setupDatePicker()
        showAlertWithTextInputLayout("Podaj wagę", weight, "kg")
        showAlertWithTextInputLayout("Podaj wzrost", height, "cm")

        gender.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setGender(text.toString())
        }
        activity_level.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setActivity(text.toString())
        }
        goal.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setGoal(text.toString())
        }
    }

    private fun initData() {
        gender.editText?.setText(viewModel.gender)
        goal.editText?.setText(viewModel.goal)
        activity_level.editText?.setText(viewModel.activity)

        age.text = AgeConverter.intToString(viewModel.age)
        weight.text = FloatConverter.floatToString(viewModel.weight, "kg")
        height.text = FloatConverter.floatToString(viewModel.height, "cm")

        viewModel.setBMI()
    }

    private fun setupDropdownMenu(items: List<String>, view: EditText?) {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, items)
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

        datePicker.addOnPositiveButtonClickListener {
            val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
            calendar.time = Date(it)
            viewModel.setAge(getAge(calendar))
            age.text = AgeConverter.intToString(viewModel.age)
        }

        age.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }
    }

    private fun showAlertWithTextInputLayout(
        title: String,
        button: MaterialButton,
        suffix: String,
    ) {
        button.setOnClickListener {
            val view = LayoutInflater.from(activity).inflate(R.layout.dialog_textfield, null)
            view.dialog_title.text = title
            view.dialog_textLayout.suffixText = suffix
            val builder = AlertDialog.Builder(activity).setView(view)
            val alert = builder.show()

            val data = when (suffix) {
                "cm" -> viewModel.height.toString()
                "kg" -> viewModel.weight.toString()
                else -> ""
            }
            view.dialog_input.setText(data)

            view.dialog_input.addTextChangedListener(object : TextWatcher {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    view.dialog_textLayout.error = null
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable?) {}
            })

            view.dialog_cancel.setOnClickListener {
                alert.dismiss()
            }

            view.dialog_ok.setOnClickListener {
                val input = view.dialog_input.text.toString()
                if (input.isEmpty()) {
                    view.dialog_textLayout.error = getString(R.string.error_empty_field)
                } else {
                    try {
                        if (suffix == "kg") {
                            when {
                                input.toFloat() < 40 -> {
                                    view.dialog_textLayout.error =
                                        getString(R.string.error_light_weight)
                                }
                                input.toFloat() > 150 -> {
                                    view.dialog_textLayout.error =
                                        getString(R.string.error_heavy_weight)
                                }
                                else -> {
                                    viewModel.setWeight(input.toFloat())
                                    button.text = FloatConverter.floatToString(
                                        viewModel.weight,
                                        suffix
                                    )
                                    alert.dismiss()
                                }
                            }
                        }
                        if (suffix == "cm") {
                            when {
                                input.toFloat() < 140 -> {
                                    view.dialog_textLayout.error =
                                        getString(R.string.error_small_height)
                                }
                                input.toFloat() > 220 -> {
                                    view.dialog_textLayout.error =
                                        getString(R.string.error_high_height)
                                }
                                else -> {
                                    viewModel.setHeight(input.toFloat())
                                    button.text = FloatConverter.floatToString(
                                        viewModel.height,
                                        suffix
                                    )
                                    alert.dismiss()
                                }
                            }
                        }
                        viewModel.setBMI()
                    } catch (e: NumberFormatException) {
                        view.dialog_textLayout.error = getString(R.string.error_format)
                    }
                }
            }
        }
    }

    private fun getAge(calendar: Calendar): Int {
        val today = Calendar.getInstance()
        var age = today.get(Calendar.YEAR) - calendar.get(Calendar.YEAR)
        if (today.get(Calendar.DAY_OF_YEAR) < calendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }
}