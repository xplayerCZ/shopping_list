package cz.davidkurzica.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import cz.davidkurzica.shoppinglist.adapters.ShoppingAdapter
import cz.davidkurzica.shoppinglist.data.AppDatabase
import cz.davidkurzica.shoppinglist.data.Item
import cz.davidkurzica.shoppinglist.databinding.FragmentOverviewBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment(), ShoppingAdapter.OnItemClickListener {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var items: MutableList<Item>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        GlobalScope.launch {
            val db = AppDatabase(requireContext())
            val itemDao = db.itemDao()
            items = itemDao.getAll().toMutableList()

            with(binding.itemOverview) {
                adapter = ShoppingAdapter(items, this@OverviewFragment)
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
            }
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_OverviewFragment_to_NewFragment)
        }
    }

    override fun onItemClick(position: Int) {
        GlobalScope.launch {
            val db = AppDatabase(requireContext())
            val itemDao = db.itemDao()

            itemDao.delete(items[position])
            items = itemDao.getAll().toMutableList()
        }
        binding.itemOverview.adapter?.notifyDataSetChanged()
    }
}