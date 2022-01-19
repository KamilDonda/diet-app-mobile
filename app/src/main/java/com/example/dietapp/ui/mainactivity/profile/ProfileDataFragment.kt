package com.example.dietapp.ui.mainactivity.profile

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.children
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.dietapp.R
import com.example.dietapp.database.models.User
import com.example.dietapp.utils.AgeConverter
import com.example.dietapp.utils.ArrayUtil.Companion.getArrayList
import com.example.dietapp.utils.FloatConverter
import com.example.dietapp.utils.ProfileDataConverter.Companion.activityIntToString
import com.example.dietapp.utils.ProfileDataConverter.Companion.activityStringToInt
import com.example.dietapp.utils.ProfileDataConverter.Companion.genderBoolToString
import com.example.dietapp.utils.ProfileDataConverter.Companion.genderStringToBool
import com.example.dietapp.utils.ProfileDataConverter.Companion.goalIntToString
import com.example.dietapp.utils.ProfileDataConverter.Companion.goalStringToInt
import com.example.dietapp.utils.setupDropdownMenu
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.dialog_textfield.view.*
import kotlinx.android.synthetic.main.fragment_profile_data.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class ProfileDataFragment : Fragment() {

    private val viewModel: ProfileViewModel by sharedViewModel()

    private lateinit var genders: ArrayList<String>
    private lateinit var goals: ArrayList<String>
    private lateinit var activities: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        genders = getArrayList(R.array.genders, requireContext())
        goals = getArrayList(R.array.goals, requireContext())
        activities = getArrayList(R.array.activities, requireContext())

        viewModel.bmi.observe(viewLifecycleOwner, {
            bmi.text = it.toString()
        })

        initData()

        setupDropdownMenu(genders, gender.editText)
        setupDropdownMenu(goals, goal.editText)
        setupDropdownMenu(activities, activity_level.editText)

        setupDatePicker()
        showAlertWithTextInputLayout("Podaj wagę", weight, "kg")
        showAlertWithTextInputLayout("Podaj wzrost", height, "cm")

        gender.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setGender(genderStringToBool(text.toString(), requireContext()))
        }
        activity_level.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setActivity(activityStringToInt(text.toString(), requireContext()))
        }
        goal.editText?.doOnTextChanged { text, _, _, _ ->
            viewModel.setGoal(goalStringToInt(text.toString(), requireContext()))
        }

        setupButtons()
        setupChips()
    }

    private fun initData() {
        viewModel.reset()
        gender.editText?.setText(genderBoolToString(viewModel.gender, requireContext()))
        goal.editText?.setText(goalIntToString(viewModel.goal, requireContext()))
        activity_level.editText?.setText(activityIntToString(viewModel.activity, requireContext()))

        age.text = AgeConverter.intToString(viewModel.age)
        weight.text = FloatConverter.floatToString(viewModel.weight, "kg")
        height.text = FloatConverter.floatToString(viewModel.height, "cm")
        initChips()

        viewModel.setBMI()
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

            var data = when (suffix) {
                "cm" -> viewModel.height.toString()
                "kg" -> viewModel.weight.toString()
                else -> ""
            }

            if (data == null.toString()) {
                data = ""
            }

            view.dialog_input.setText(data)

            view.dialog_input.doOnTextChanged { text, _, _, _ ->
                view.dialog_textLayout.error = null
            }

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

    private fun setupButtons() {
        profile_reset.setOnClickListener {
            initData()
        }

        profile_save.setOnClickListener {
            val user = User(
                viewModel.getUserId(),
                "",
                viewModel.gender,
                viewModel.age,
                viewModel.height,
                viewModel.weight,
                viewModel.activity,
                viewModel.goal,
                viewModel.preferences,
            )
            viewModel.save(user)
        }
    }

    private fun setupChips() {
        val chips = chip_group?.children?.toList()
        chips?.forEach {
            (it as Chip).setOnClickListener {
                val tags = chips.filter {
                    (it as Chip).isChecked
                }.map {
                    (it as Chip).text.toString()
                }
                viewModel.setPreferences(tags)
            }
        }
    }

    private fun initChips() {
        val chips = chip_group?.children?.toList()
        chips?.forEach {
            if ((it as Chip).text in viewModel.preferences) {
                it.isChecked = true
            }
        }
    }
}