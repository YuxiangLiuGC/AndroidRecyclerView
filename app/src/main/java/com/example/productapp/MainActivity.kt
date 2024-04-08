package com.example.productapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.productapp.model.Product
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.viewmodel.ProductViewModel

class MainActivity : AppCompatActivity() {

    val productList = getProduct()
    private lateinit var productViewModel: ProductViewModel
    private lateinit var clickCountsObserver: Observer<MutableMap<String, Int>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        productViewModel = ViewModelProvider(this).get(ProductViewModel::class.java)

        //Bind recyclerview to view and configure layout manager and adapter
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ProductAdapter(productList, productViewModel)


        val textViewClickCounts: TextView = findViewById(R.id.textViewClickCounts)

        // Observer to update the clickCounts map
        clickCountsObserver = Observer { clickCounts ->
            // Format clickCounts map into a string
            val clickCountsString = formatClickCounts(clickCounts)
            textViewClickCounts.text = clickCountsString
        }
        // Observe changes in clickCounts map
        productViewModel.clickCounts.observe(this, clickCountsObserver)
        //new
        val buttonClickCounts: Button = findViewById(R.id.buttonClickCounts)
        buttonClickCounts.setOnClickListener {
            val clickCountsString = formatClickCounts(productViewModel.clickCounts.value ?: mutableMapOf())
            val dialogFragment = CountsDialogFragment.newInstance(clickCountsString)
            dialogFragment.show(supportFragmentManager, "ClickCountsDialogFragment")
        }
        val buttonViewCounts: Button = findViewById(R.id.buttonViewCounts)
        buttonViewCounts.setOnClickListener {
            val clickCountsString = formatViewCounts(productViewModel.viewedProducts.value ?: mutableSetOf())
            val dialogFragment = CountsDialogFragment.newInstance(clickCountsString)
            dialogFragment.show(supportFragmentManager, "ClickCountsDialogFragment")
        }

    }
    fun getProduct(): List<Product> {
        val productList = mutableListOf<Product>()
        for(i in 1..20){
            productList.add(Product("Product $i"))
        }
        return productList
    }
    private fun formatClickCounts(clickCountsMap: MutableMap<String, Int>): String {
        val stringBuilder = StringBuilder()
        for ((product, count) in clickCountsMap) {
            stringBuilder.append("$product: $count\n")
        }
        return stringBuilder.toString()
    }
    private fun formatViewCounts(clickViewSet: MutableSet<String>): String {
        val stringBuilder = StringBuilder()
        for (id in clickViewSet) {
            stringBuilder.append("$id\n")
        }
        return stringBuilder.toString()
    }
}