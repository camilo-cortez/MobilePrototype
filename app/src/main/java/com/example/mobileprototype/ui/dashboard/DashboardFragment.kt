package com.example.mobileprototype.ui.dashboard

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import com.example.mobileprototype.R

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.mobileprototype.databinding.FragmentDashboardBinding
import java.util.Calendar

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var textViewDate: TextView
    private lateinit var selectedDateTimeTextView: TextView
    private val calendar = Calendar.getInstance()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root


        val confirmationButton: Button = root.findViewById(R.id.buttonCreateReminder)

        // Configuración del spinner de artistas
        val artistOptions = arrayOf("Arctic Monkeys", "The Strokes", "Interpol")
        val artistAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, artistOptions)
        artistAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerArtist: Spinner = root.findViewById(R.id.spinnerArtist)
        spinnerArtist.adapter = artistAdapter

        // Configuración del spinner de recordatorio
        val reminderOptions = arrayOf("10 minutos antes", "15 minutos antes", "30 minutos antes")
        val reminderAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, reminderOptions)
        reminderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinnerReminder: Spinner = root.findViewById(R.id.spinnerReminder)
        spinnerReminder.adapter = reminderAdapter

         selectedDateTimeTextView = root.findViewById(R.id.selectedDateTimeTextView)


        selectedDateTimeTextView.setOnClickListener {
            showDatePickerDialog()
        }
        confirmationButton.setOnClickListener {
            showCustomDialog()
        }

        return root
    }

    private fun showDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Cuando se selecciona la fecha, mostrar el diálogo del selector de hora
                showTimePickerDialog(year, month, dayOfMonth)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog(year: Int, month: Int, dayOfMonth: Int) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog =
            TimePickerDialog(requireContext(), { _: TimePicker, hourOfDay: Int, minute: Int ->
                // Cuando se selecciona la hora, actualizar el TextView con la fecha y la hora seleccionadas
                selectedDateTimeTextView.text = String.format(
                    "%02d/%02d/%d %02d:%02d",
                    dayOfMonth,
                    month + 1,
                    year,
                    hourOfDay,
                    minute
                )
            }, hour, minute, true)
        timePickerDialog.show()
    }

    private fun showCustomDialog() {
        var dialog = context?.let { Dialog(it) }
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.layout_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val tvMessage: TextView = dialog.findViewById(R.id.custom_dialog_message)
        var okButton: Button = dialog.findViewById(R.id.cd_ok_button)

        okButton.setOnClickListener {
            Toast.makeText(context, "clicked ok", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
