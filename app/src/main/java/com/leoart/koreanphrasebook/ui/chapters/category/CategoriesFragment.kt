package com.leoart.koreanphrasebook.ui.chapters.category

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.ui.BaseFragment
import com.leoart.koreanphrasebook.ui.chapters.models.Category
import com.leoart.koreanphrasebook.ui.chapters.models.Chapter
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhraseListActivity
import java.util.*

/**
 * Created by bogdan on 6/18/17.
 */
class CategoriesFragment(title: String) : BaseFragment(title), CategoriesView, CategoriesAdapter.CategoryInteractionListener {

    private var chapter: Chapter? = null
    private var adapter: CategoriesAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.activity_categories, container, false)
        val rvCategories = view.findViewById(R.id.rv_categories) as RecyclerView
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCategories.layoutManager = layoutManager
        rvCategories.itemAnimator = DefaultItemAnimator()
        rvCategories.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = CategoriesAdapter(ArrayList<Category>(), this)
        rvCategories.adapter = adapter
        this.chapter?.let {
            CategoriesPresenter(this).requestCategories(it)
        }
        return view
    }

    override fun onCategoryClick(category: Category) {
        val intent = Intent(context, PhraseListActivity::class.java)
        intent.putExtra(PhraseListActivity.CATEGORY, category.id)
        intent.putExtra(PhraseListActivity.NAME, category.name["word"])
        startActivity(intent)
    }

    override fun showCategories(categories: List<Category>) {
        adapter?.setCategories(categories)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                // todo
                // onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    companion object {

        fun newInstance(title: String, chapter: Chapter): CategoriesFragment {
            val fragment = CategoriesFragment(title)
            val args = Bundle()
            fragment.chapter = chapter
            fragment.arguments = args
            return fragment
        }
    }
}