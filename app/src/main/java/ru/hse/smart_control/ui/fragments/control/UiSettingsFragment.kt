package ru.hse.smart_control.ui.fragments.control

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.smart_control.databinding.FragmentUiSettingsBinding
import ru.hse.smart_control.ui.enum.CapabilitySupType
import ru.hse.smart_control.ui.enum.MethodsType
import ru.hse.smart_control.ui.enum.TypeAction
import ru.hse.smart_control.ui.enum.WidgetType

class UiSettingsFragment : Fragment() {

    private var _binding: FragmentUiSettingsBinding? = null

    private val binding get() = _binding!!

    private val viewModel: UiSettingsViewModel by activityViewModels()

    private var devName = ""
    private var deviceMethodType = ""
    private var capabilityType = ""
    private var capabilitySubType = ""
    private var widgetType = ""
    private var posLeft = 0
    var widgetId = ""
    var isFormVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUiSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments?.getStringArrayList("key")

        val rvAdapter = WidgetListAdapter(
            object : WidgetActionListener {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun deleteWidgetById(id: String) {
                    viewModel.getWidgetId(id)
                    viewModel.removeItemById(id.toInt())
                }

                override fun getWidgetId(id: String) {
                    widgetId = id
                    binding.containerCreateWidget.visibility = View.VISIBLE
                    binding.containerWidgetId.visibility = View.VISIBLE


                    viewModel.itemList.observe(viewLifecycleOwner, Observer { items ->
                        val foundItem = items.find { it.id.toString() == id }
                        with(binding) {
                            tvWidgetId.text = foundItem?.id.toString()
                            selectSpinnerItemByText(spinnerMethodType,
                                foundItem?.methodsType
                            )
                            selectSpinnerItemByText(spinnerCapabilityType,
                                foundItem?.capabilityType
                            )
                            selectSpinnerItemByText(spinnerCapabilitySubType,
                                foundItem?.capabilitySubType
                            )
                            selectSpinnerItemByText(spinnerWidgetType,
                                foundItem?.widgetType
                            )
                            btnCreateWidget.text = "Сохранить"
                        }
                    })
                }
            }
        )
        binding.rvWidgetList.adapter = rvAdapter

        viewModel.itemList.observe(viewLifecycleOwner, Observer { items ->
            rvAdapter.submitList(items.toList())
        })

        val deviceType = ArrayList<String>()
        MethodsType.values().forEach { deviceType.add(it.toString()) }
        val adapterDeviceType =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                deviceType
            )
        adapterDeviceType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerMethodType.adapter = adapterDeviceType
        binding.spinnerMethodType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    deviceMethodType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        val deviceCapabilityType = ArrayList<String>()
        TypeAction.values().forEach { deviceCapabilityType.add(it.toString()) }
        val adapterDeviceCapabilityType =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                deviceCapabilityType
            )
        adapterDeviceCapabilityType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCapabilityType.adapter = adapterDeviceCapabilityType
        binding.spinnerCapabilityType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    capabilityType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        val deviceCapabilitySubType = ArrayList<String>()
        CapabilitySupType.values().forEach { deviceCapabilitySubType.add(it.toString()) }
        val adapterDeviceCapabilitySubType =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                deviceCapabilitySubType
            )
        adapterDeviceCapabilitySubType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerCapabilitySubType.adapter = adapterDeviceCapabilitySubType
        binding.spinnerCapabilitySubType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    capabilitySubType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        val widgetTypeList = ArrayList<String>()
        WidgetType.values().forEach { widgetTypeList.add(it.toString()) }
        val widgetTypeAdapter =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_list_item_1,
                widgetTypeList
            )
        widgetTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerWidgetType.adapter = widgetTypeAdapter
        binding.spinnerWidgetType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    widgetType = parent.getItemAtPosition(position).toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }

        binding.etPosX.setText("10")
        binding.etPosY.setText("10")
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            viewModel.posLeft.collect {
                withContext(Dispatchers.Main) {
                    binding.etPosX.setText(it.toString())
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            viewModel.posTop.collect {
                withContext(Dispatchers.Main) {
                    binding.etPosY.setText(it.toString())
                }
            }
        }

        binding.etWidgetHeight.setText("100")
        binding.etWidgetWidth.setText("100")

        binding.btnCreateWidget.setOnClickListener {
            val newElement = WidgetModel(
                title = "TUYA",
                methodsType = deviceMethodType,
                capabilityType = capabilityType,
                capabilitySubType = capabilitySubType,
                widgetType = widgetType,
                posX = binding.etPosX.text.toString().toInt(),
                posY = binding.etPosY.text.toString().toInt(),
//                width = binding.etWidgetWidth.text.toString().toInt(),
//                height = binding.etWidgetHeight.text.toString().toInt()
            )
            viewModel.setElement(newElement)
        }
    }


    fun selectSpinnerItemByText(spinner: Spinner, text: String?) {
        val adapter = spinner.adapter
        if (adapter != null) {
            for (position in 0 until adapter.count) {
                if (adapter.getItem(position) == text) {
                    spinner.setSelection(position)
                    return
                }
            }
        }
    }
}