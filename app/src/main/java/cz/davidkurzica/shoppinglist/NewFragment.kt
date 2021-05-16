package cz.davidkurzica.shoppinglist

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import cz.davidkurzica.shoppinglist.data.AppDatabase
import cz.davidkurzica.shoppinglist.data.Item
import cz.davidkurzica.shoppinglist.databinding.FragmentNewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class NewFragment : Fragment() {

    private var _binding: FragmentNewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val options = listOf("ovoce", "zelenina", "oblečení", "spotřební zboží", "ostatní")
        val adapter = ArrayAdapter(requireContext(), R.layout.item_category, options)
        (binding.categoryTextField.editText as? AutoCompleteTextView)?.setAdapter(adapter)

        binding.cancelButton.setOnClickListener {
            hideKeyboard()
            findNavController().navigate(R.id.action_NewFragment_to_OverviewFragment)
        }

        binding.saveButton.setOnClickListener {

            val db = AppDatabase(requireContext())
            val itemDao = db.itemDao()

            val itemName = binding.itemNameTextField.editText?.text.toString()
            val amount = binding.quantityTextField.editText?.text.toString().toInt()
            val category = binding.categoryTextField.editText?.text.toString()
            val item = Item(0, itemName, amount, category)

            GlobalScope.launch {
                itemDao.insertAll(item)

                hideKeyboard()
                findNavController().navigate(R.id.action_NewFragment_to_OverviewFragment)
            }
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if no view has focus
        val currentFocusedView = activity?.currentFocus
        currentFocusedView?.let {
            inputMethodManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}