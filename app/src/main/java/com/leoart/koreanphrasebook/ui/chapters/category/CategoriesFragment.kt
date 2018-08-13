package com.leoart.koreanphrasebook.ui.chapters.category

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.analytics.AnalyticsManager
import com.leoart.koreanphrasebook.data.repository.models.ECategory
import com.leoart.koreanphrasebook.ui.*
import com.leoart.koreanphrasebook.ui.chapters.phrase.PhraseListFragment
import com.leoart.koreanphrasebook.ui.models.Chapter
import com.leoart.koreanphrasebook.utils.NetworkChecker
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_categories.*
import java.util.*
import javax.inject.Inject

/**
 * Created by bogdan on 6/18/17.
 */
class CategoriesFragment : BaseFragment(), CategoriesAdapter.CategoryInteractionListener {

    private var chapter: Chapter? = null
    private var adapter: CategoriesAdapter? = null
    private var mainView: MainView? = null
    @Inject
    lateinit var analyticsManager: AnalyticsManager
    private lateinit var model: CategoriesViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_categories, container, false)
        val rvCategories = view.findViewById<RecyclerView>(R.id.rv_categories)
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvCategories.layoutManager = layoutManager
        rvCategories.itemAnimator = DefaultItemAnimator()
        rvCategories.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        adapter = CategoriesAdapter(ArrayList<ECategory>(), this)
        rvCategories.adapter = adapter
        model = ViewModelProviders.of(
                this,
                ViewModelFactory(view.context)
        ).get(CategoriesViewModel::class.java)
        this.chapter?.let {
            model.getCategories(it.key).observe(this, Observer<List<ECategory>> {
                it?.let {
                    checkConnection(it)
                }
            })
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        setTitle()
    }

    private fun checkConnection(list: List<ECategory>) {
        activity?.let {
            if (list.isEmpty() && !NetworkChecker(it.applicationContext).isNetworkAvailable) {
                mainView?.replace(NoNetworkFragment.newInstance(), false)
                Log.d("ASD", "no con fragment")
            } else {
                adapter?.setCategories(list)
                Log.d("ASD", "show cat fragment")
            }
        }
    }

    override fun onCategoryClick(category: ECategory) {
        mainView?.replace(PhraseListFragment.newInstance(category.category ?: "", category.inId))
        category.category.let {
            analyticsManager.openChapterCategory(it)
        }
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

        fun newInstance(title: String, chapter: Chapter, mainView: MainView?): CategoriesFragment {
            val fragment = CategoriesFragment()
            val args = Bundle()
            args.putString(MainActivity.TITLE, title)
            fragment.chapter = chapter
            fragment.mainView = mainView
            fragment.arguments = args
            return fragment
        }
    }
}