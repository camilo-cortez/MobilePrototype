package com.example.mobileprototype.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileprototype.ReminderAdapter
import com.example.mobileprototype.ReminderData
import com.example.mobileprototype.databinding.FragmentHomeBinding
import java.util.Locale

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var mList = ArrayList<ReminderData>()
    private lateinit var adapter: ReminderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

       // val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            //textView.text = it
        }
        recyclerView = binding.cardRecyclerView
        searchView = binding.searchView

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        addDataToList()
        adapter = ReminderAdapter(mList)
        recyclerView.adapter = adapter

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }

        })

        return root
    }

    private fun addDataToList(){
        mList.add(ReminderData("Recordatorio 1","Artist 1", "28/02/2024", "20 min"))
        mList.add(ReminderData("Recordatorio 2","Artist 2", "28/02/2024", "20 min"))
        mList.add(ReminderData("Recordatorio 3","Artist 3", "28/02/2024", "20 min"))
        mList.add(ReminderData("Recordatorio 4","Artist 4", "28/02/2024", "20 min"))
    }

    private fun filterList(query: String?){
        if(query != null){
            val filteredList = ArrayList<ReminderData>()
            for (i in mList){
                if (i.title.lowercase(Locale.ROOT).contains(query) || i.artist.lowercase(Locale.ROOT).contains(query)){
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()){
                Toast.makeText(context, "No se encontraron recordatorios", Toast.LENGTH_SHORT).show()
                adapter.setFilteredList(filteredList)
            }
            else{
                adapter.setFilteredList(filteredList)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}