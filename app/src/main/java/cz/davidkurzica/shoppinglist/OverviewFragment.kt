package cz.davidkurzica.shoppinglist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import cz.davidkurzica.shoppinglist.databinding.FragmentOverviewBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

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

        val db =
            Room.databaseBuilder(
                requireContext(),
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

        val itemDao = db.itemDao()
        val items = itemDao.getAll()

        binding.itemOverview.adapter = ShoppingAdapter(items)
        binding.itemOverview.layoutManager = LinearLayoutManager(context)
        binding.itemOverview.setHasFixedSize(true)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_OverviewFragment_to_NewFragment)
        }
    }
}