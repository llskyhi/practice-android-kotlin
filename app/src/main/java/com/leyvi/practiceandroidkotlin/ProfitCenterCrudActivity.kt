package com.leyvi.practiceandroidkotlin

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.leyvi.practiceandroidkotlin.database.CrudPracticeDatabase
import com.leyvi.practiceandroidkotlin.databinding.ActivityProfitCenterCrudBinding
import com.leyvi.practiceandroidkotlin.repo.ProfitCenterRepo
import com.leyvi.practiceandroidkotlin.view.ProfitCenterRecyclerViewAdapter
import com.leyvi.practiceandroidkotlin.viewmodel.ProfitCenterCrudViewModel

class ProfitCenterCrudActivity : AppCompatActivity() {

    companion object {
        private val TAG = ProfitCenterCrudActivity::class.simpleName
    }

    private val binding by lazy {
        ActivityProfitCenterCrudBinding.inflate(layoutInflater)
    }

    private val crudPracticeDatabase: CrudPracticeDatabase by lazy {
        CrudPracticeDatabase.getInstance(this)
    }

    private val profitCenterCrudViewModel: ProfitCenterCrudViewModel by lazy {
        ProfitCenterCrudViewModel(ProfitCenterRepo(crudPracticeDatabase.getProfitCenterDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.recyclerProfitCenters.layoutManager = LinearLayoutManager(this)
        profitCenterCrudViewModel.profitCenters.observe(this) { profitCenters ->
            binding.recyclerProfitCenters.adapter = ProfitCenterRecyclerViewAdapter(profitCenters)
        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT,
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int,
            ) {
                Log.d(TAG, "onSwiped: direction: $direction")
            }

        }).attachToRecyclerView(binding.recyclerProfitCenters)
    }
}