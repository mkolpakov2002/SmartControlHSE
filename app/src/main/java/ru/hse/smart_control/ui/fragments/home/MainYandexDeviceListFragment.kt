package ru.hse.smart_control.ui.fragments.home

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.hse.smart_control.R
import ru.hse.smart_control.domain.connection.ConnectionFactory
import ru.hse.smart_control.model.db.AppDatabase
import ru.hse.smart_control.model.entities.UniversalSchemeEntity
import ru.hse.smart_control.databinding.FragmentMainBinding
import ru.hse.smart_control.ui.MainActivity

class MainYandexDeviceListFragment : Fragment(), OnRefreshListener, MultipleTypesAdapterKt.OnItemClickListener,
    MultipleTypesAdapterKt.OnItemLongClickListener {

    private lateinit  var multipleTypesAdapter: MultipleTypesAdapterKt
    private lateinit var bottomSheetDialogToAdd: BottomSheetDialog
    private var isMultiSelectVisible = false

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var universalSchemeEntityItemTypeList: List<UniversalSchemeEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireContext().registerReceiver(
            bluetoothStateChanged,
            IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        )

        val viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        // Наблюдать за списком всех устройств
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.devices?.collect { devices ->
                universalSchemeEntityItemTypeList = devices
                onRefresh()
            }
        }

        val orientation = this.resources.configuration.orientation
        val gridLayoutManager: GridLayoutManager = if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            // code for portrait mode
            GridLayoutManager(requireContext(), 3, LinearLayoutManager.VERTICAL, false)
        } else {
            // code for landscape mode
            GridLayoutManager(requireContext(), 6, LinearLayoutManager.VERTICAL, false)
        }

        binding.swipeRefreshLayout.setOnRefreshListener(this)
        binding.floatingActionButtonStartSendingData.setOnClickListener {
            if (multipleTypesAdapter.areDevicesConnectable()){
                val list = ArrayList<Int>()
                for (item in multipleTypesAdapter.getSelectedItems()) {
                    list.add(item.id)
                }
                val b = Bundle()
                b.putIntegerArrayList("deviceIdList", list)
                findNavController(binding.root).navigate(R.id.action_mainMenuFragment_to_connectionTypeFragment, b)
            } else {
                (Snackbar.make(
                    binding.root,
                    getString(R.string.selection_class_device_error),
                    Snackbar.LENGTH_LONG
                ).setAction(getString(R.string.ok)) {}).show()
            }
        }
        binding.floatingActionButtonDeleteSelected.hide()
        binding.floatingActionButtonDeleteSelected.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                for(item in multipleTypesAdapter.getSelectedItems())
                    AppDatabase.getInstance(requireContext()).deviceOldItemTypeDao()?.delete(item.id)
                onRefresh()
            }
        }
        binding.recyclerMain.layoutManager = gridLayoutManager
        binding.recyclerMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0 && binding.floatingActionButtonStartSendingData.isExtended) {
                    binding.floatingActionButtonStartSendingData.shrink()
                } else if (dy < 0 && !binding.floatingActionButtonStartSendingData.isExtended) {
                    binding.floatingActionButtonStartSendingData.extend()
                }
            }
        })
        binding.recyclerMain.clipToPadding = false
        (activity as MainActivity).bottomAppBarSize?.let { binding.recyclerMain.setPadding(0, 0, 0, it) }
        bottomSheetDialogToAdd = BottomSheetDialog(requireContext())
        bottomSheetDialogToAdd.setContentView(R.layout.bottom_sheet_dialog_add_device)
        bottomSheetDialogToAdd.setCancelable(true)
        bottomSheetDialogToAdd.dismiss()
        hideBottomSheetToAdd()
        val buttonToAddDeviceViaMAC =
            bottomSheetDialogToAdd.findViewById<Button>(R.id.button_manual_mac)
        val buttonToAddDevice =
            bottomSheetDialogToAdd.findViewById<Button>(R.id.button_add_device)

        buttonToAddDevice?.setOnClickListener {
            bottomSheetDialogToAdd.dismiss()
            if (!ConnectionFactory.isBtSupported) {
                val snackbar = Snackbar
                    .make(
                        binding.swipeRefreshLayout, getString(R.string.suggestionNoBtAdapter),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction(getString(R.string.ok)) { }
                snackbar.show()
            } else if (
                ConnectionFactory.isBtEnabled &&
                ConnectionFactory.isNotEmptyBluetoothBounded) {
                findNavController(requireParentFragment().requireView())
                    .navigate(R.id.addDeviceFragment)
            } else if (!ConnectionFactory.isBtEnabled) {
                val snackbar = Snackbar
                    .make(
                        binding.swipeRefreshLayout, getString(R.string.en_bt_for_list),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction(getString(R.string.ok)) {
                        val intentBtEnabled =
                            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        requireContext().startActivity(intentBtEnabled)
                    }
                snackbar.show()
            } else {
                val snackbar = Snackbar
                    .make(
                        binding.swipeRefreshLayout, getString(R.string.no_devices_added),
                        Snackbar.LENGTH_LONG
                    )
                    .setAction(getString(R.string.ok)) {
                        val intentBtEnabled =
                            Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                        requireContext().startActivity(intentBtEnabled)
                    }
                snackbar.show()
            }
        }

        buttonToAddDeviceViaMAC?.setOnClickListener {
            bottomSheetDialogToAdd.dismiss()
            findNavController(requireParentFragment().requireView()).navigate(R.id.action_mainMenuFragment_to_deviceMenuFragment)
        }
    }

    override fun onRefresh() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                hideAllButtons()
                if (!this@MainYandexDeviceListFragment::multipleTypesAdapter.isInitialized) {
                    initAdapter()
                } else {
                    multipleTypesAdapter.updateItems(universalSchemeEntityItemTypeList)
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    private fun initAdapter(){
        multipleTypesAdapter = MultipleTypesAdapterKt(requireContext(), universalSchemeEntityItemTypeList)
        binding.recyclerMain.adapter = multipleTypesAdapter
        multipleTypesAdapter.onItemLongClickListener = this
        multipleTypesAdapter.onItemClickListener = this
    }

    //выполняемый код при изменении состояния bluetooth
    private val bluetoothStateChanged: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            onRefresh()
        }
    }

    private fun hideAllButtons() {
        lifecycleScope.launch {
            withContext(Dispatchers.Main) {
                binding.floatingActionButtonDeleteSelected.hide()
                binding.floatingActionButtonStartSendingData.hide()
                hideBottomSheetToAdd()
                isMultiSelectVisible = false
            }
        }
    }

    private fun showItemSelectionMenu() {
        hideBottomSheetToAdd()
        binding.floatingActionButtonDeleteSelected.show()
        binding.floatingActionButtonStartSendingData.show()
        isMultiSelectVisible = true
    }

    private fun showBottomSheetToAdd() {
        bottomSheetDialogToAdd.show()
    }

    private fun hideBottomSheetToAdd() {
        bottomSheetDialogToAdd.cancel()
    }

    override fun onItemClick(item: MultipleTypesAdapterKt.Item) {
        if(item.isButton){
            showBottomSheetToAdd()
        } else if(!multipleTypesAdapter.isMultiSelect && isMultiSelectVisible){
            hideAllButtons()
        } else if(!multipleTypesAdapter.isMultiSelect){
            val args = Bundle()
            args.putBoolean("isNew", false)
            args.putSerializable("deviceOld", item.universalSchemeEntity)
            findNavController(binding.root).navigate(R.id.deviceMenuFragment, args)
        }

    }

    override fun onItemLongClick() {
        if(multipleTypesAdapter.isMultiSelect && !isMultiSelectVisible){
            showItemSelectionMenu()
        }
    }

}