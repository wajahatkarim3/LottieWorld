package com.wajahatkarim3.lottieworld.base

import androidx.recyclerview.widget.RecyclerView

/**
 * A base class to create RecyclerView.Adapter instances easily.
 * Since [ListAdapter] doesn't support the Pagination, so instead of
 * creating RecyclerView ListAdapters, use this class to create.
 * This nicely integrates with the existing Data binding adapters such as
 * adapterDataSet, adapterData etc.
 *
 * This also allows you to either add items in the existing list or
 * replace the entire list with the new items for refresh, or first time fetch use cases.
 *
 * @author Wajahat Karim
 */
abstract class BaseRecyclerViewAdapter<R: RecyclerView.ViewHolder, T> : RecyclerView.Adapter<R>() {

    private val itemsList: MutableList<T> = mutableListOf()

    /**
     * Adds the new [list] in the existing items list of Adapter.
     * @param The new list to add
     */
    open fun submitList(list: List<T>) {
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * Clears the old list and adds the new [list] as fresh.
     * @param The list to replace the old list with.
     */
    open fun replaceList(list: List<T>) {
        itemsList.clear()
        itemsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }

    fun getItem(position: Int): T {
        return itemsList[position]
    }

    fun getItemsList(): List<T> {
        return itemsList
    }

    fun updateItemAt(index: Int, item: T) {
        if (index in 0 until itemCount) {
            itemsList[index] = item
            notifyItemChanged(index)
        }
    }
}