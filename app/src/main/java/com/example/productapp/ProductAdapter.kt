package com.example.productapp

import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.model.Product
import com.example.productapp.viewmodel.ProductViewModel

class ProductAdapter(private val products: List<Product>,
                     private val productViewModel: ProductViewModel
                    ) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private val countdownMap = mutableMapOf<String, CountDownTimer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product,productViewModel)
    }

    override fun getItemCount(): Int {
        return products.size
    }
    // Use this method to start count down
    override fun onViewAttachedToWindow(holder: ProductViewHolder) {
        super.onViewAttachedToWindow(holder)
        val product = products[holder.bindingAdapterPosition]
        startCountDownTimer(product.id)
    }
    //Use this method to cancel count down
    override fun onViewDetachedFromWindow(holder: ProductViewHolder) {
        super.onViewDetachedFromWindow(holder)
        val product = products[holder.bindingAdapterPosition]
        countdownMap[product.id]?.cancel()
    }
    //use product id as key and CountDownTimer as value
    private fun startCountDownTimer(productId: String) {
        val timer = countdownMap[productId]
        if(timer!=null) return

        countdownMap[productId] = object : CountDownTimer(1000, 1000) { // 1 second countdown
            override fun onTick(millisUntilFinished: Long) {
                // Not used
            }
            override fun onFinish() {
                // If timer finishes, consider item as viewed
                productViewModel.recordViewed(productId)
            }
        }.start()
    }
    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textView)

        fun bind(product: Product, productViewModel: ProductViewModel) {
            textView.text = product.id
            //In view holder, use setOnClickListenser to record click
            itemView.setOnClickListener {
                productViewModel.recordClick(product.id)
                val message = "Item ${product.id} clicked!"
                Toast.makeText(itemView.context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
