package com.leoart.koreanphrasebook.chapters.category

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.leoart.koreanphrasebook.DemoDataProvider
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.chapters.models.Category
import com.leoart.koreanphrasebook.chapters.models.Chapter
import com.leoart.koreanphrasebook.chapters.phrase.PhraseListActivity

/**
 * Created by bogdan on 11/5/16.
 */
class CategoryScreenActivity : AppCompatActivity(), CategoriesView, CategoriesAdapter.CategoryInteractionListener {
    private var adapter: CategoriesAdapter? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        val rvCategories = findViewById(R.id.rv_categories) as RecyclerView
        val layoutManager = LinearLayoutManager(baseContext, LinearLayoutManager.VERTICAL, false)
        rvCategories.layoutManager = layoutManager
        rvCategories.itemAnimator = DefaultItemAnimator()

        adapter = CategoriesAdapter(DemoDataProvider().chapterCategories, this)
        rvCategories.adapter = adapter

        val chapter = intent.getParcelableExtra<Chapter>(CHAPTER_NAME)
        CategoriesPresenter(this).requestCategories(chapter)
    }

    override fun onCategoryClick(category: Category) {
        val intent = Intent(this, PhraseListActivity::class.java)
        startActivity(intent)
    }

    override fun showCategories(categories: List<Category>) {
        adapter?.setCategories(categories)
    }

    companion object {

        val CHAPTER_NAME = "chapter_NAME"
    }
}
