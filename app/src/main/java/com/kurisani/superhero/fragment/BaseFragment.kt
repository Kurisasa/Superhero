package com.kurisani.superhero.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kurisani.superhero.activity.BaseActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseFragment : Fragment() {

    protected lateinit var activity: BaseActivity
    protected val compositeDisposable = CompositeDisposable()

    protected fun registerDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.activity = getActivity() as BaseActivity
    }
}