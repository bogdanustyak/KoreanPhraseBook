package com.leoart.koreanphrasebook.ui.search

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.leoart.koreanphrasebook.R
import com.leoart.koreanphrasebook.data.network.firebase.FBSearch
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        FBSearch("де").search()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ items ->
                    for (item in items){
                        print(item)
                    }
                })
    }
}
