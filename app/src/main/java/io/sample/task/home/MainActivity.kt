package io.sample.task.home

import android.content.*
import android.os.*
import android.view.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.lifecycle.*
import androidx.recyclerview.widget.*
import io.sample.task.R
import io.sample.task.downloader.*
import org.koin.androidx.viewmodel.ext.android.*


class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {

    private val viewModel: MainActivityViewModel by viewModel()
    private val data = arrayListOf("Select Sort type","Unsort","Insertion","Bubble","Heap")
    private lateinit var recyclerView: RecyclerView
    private lateinit var sortTypes: Spinner
    private lateinit var adapter: SortListAdapter

    private lateinit var workManager: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        workManager = findViewById(R.id.workManager)
        workManager.setOnClickListener {
            val intent = Intent(this@MainActivity, FileDownloaderActivity::class.java)
            startActivity(intent)
        }

        sortTypes = findViewById(R.id.sortTypes)
        sortTypes.adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,data)
        sortTypes.onItemSelectedListener = this

        recyclerView = findViewById(R.id.sortedList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = SortListAdapter(null)
        recyclerView.adapter = adapter

        initObservables()
        viewModel.doSorting("Unsort")


    }


    private fun initObservables(){
        viewModel.getData().observe(this, Observer {
            adapter.setData(it)
        })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        viewModel.doSorting(data[position])
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
}