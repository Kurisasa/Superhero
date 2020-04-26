package com.kurisani.superhero.module.home

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.kurisani.superhero.R
import com.kurisani.superhero.SuperheroApplication
import com.kurisani.superhero.fragment.BaseFragment
import com.kurisani.superhero.model.Results
import com.kurisani.superhero.model.SuperHeroResponse
import com.kurisani.superhero.module.home.adapter.HomePagerAdapter
import com.kurisani.superhero.room.entity.Superhero
import com.kurisani.superhero.util.ImageUtil
import com.kurisani.superhero.util.Util
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.toolbar_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener, HomeContract.HomeView {

    private lateinit var homeActivity: HomeActivity
    private lateinit var root: ViewGroup
    private var homePagerAdapter: HomePagerAdapter? = null
    private lateinit var hero: Superhero

    @Inject
    lateinit var homePresenter: HomeContract.HomePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeActivity = activity as HomeActivity
        root = homeActivity.findViewById(android.R.id.content)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    private val viewPagerOnChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            val view = viewPagerHeroes.getChildAt(position)
            view?.let {
            }
        }
    }

    override fun onRefresh() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFetchHeroSuccess(response: Results) {
        if (Util.isOnline()) {
            viewPagerHeroes?.visibility = View.VISIBLE
            textViewNoHeros?.visibility = View.GONE

            homePagerAdapter = HomePagerAdapter(homeActivity, this)
            viewPagerHeroes?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPagerHeroes?.adapter = homePagerAdapter
            homePagerAdapter?.setOnlineItem(response.results.get(0))
            homePagerAdapter?.notifyDataSetChanged()
        }

    }

    override fun onFetchOfflineHeroSuccess(response: List<Superhero>?) {
        viewPagerHeroes?.visibility = View.VISIBLE
        textViewNoHeros?.visibility = View.GONE

        if (response != null) {
            this.hero = response[0]

            viewPagerHeroes?.visibility = View.VISIBLE
            homePageIndicatorView?.visibility = View.VISIBLE
            textViewNoHeros?.visibility = View.GONE

            homePagerAdapter = HomePagerAdapter(homeActivity, this)
            viewPagerHeroes?.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            viewPagerHeroes?.adapter = homePagerAdapter
            homePagerAdapter?.setOfflineItem(response)
            homePagerAdapter?.notifyDataSetChanged()
            viewPagerHeroes?.registerOnPageChangeCallback(viewPagerOnChangeCallback)
            homePageIndicatorView?.count = viewPagerHeroes.adapter?.itemCount!!
            viewPagerHeroes?.currentItem = response.indexOf(hero)
            homePageIndicatorView?.selection = viewPagerHeroes.currentItem

            imageButtonNext?.setOnClickListener {
                if (viewPagerHeroes.currentItem < viewPagerHeroes.adapter?.itemCount!! - 1) {
                    viewPagerHeroes?.setCurrentItem(
                        viewPagerHeroes.currentItem + 1,
                        true
                    )
                } else {
                    imageButtonNext?.visibility = View.GONE
                }
            }

            when (viewPagerHeroes?.currentItem) {
                0 -> {
                    imageButtonPrevious?.visibility = View.GONE
                }
                viewPagerHeroes?.adapter?.itemCount?.let { it - 1 } -> {
                    imageButtonNext?.visibility = View.GONE
                }
                else -> {
                    imageButtonPrevious?.visibility = View.VISIBLE
                }
            }

            imageButtonPrevious?.setOnClickListener {
                if (viewPagerHeroes.currentItem > 0) {
                    viewPagerHeroes?.setCurrentItem(
                        viewPagerHeroes.currentItem - 1,
                        true
                    )
                } else {
                    imageButtonPrevious?.visibility = View.GONE
                }
            }

            viewPagerHeroes?.registerOnPageChangeCallback(object :
                ViewPager2.OnPageChangeCallback() {
                override fun onPageScrollStateChanged(state: Int) {

                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
                    homePageIndicatorView?.selection = position
                    if (viewPagerHeroes?.currentItem == 0) {
                        imageButtonPrevious?.visibility = View.GONE
                    } else {
                        imageButtonPrevious?.visibility = View.VISIBLE
                    }

                    if (viewPagerHeroes?.currentItem == viewPagerHeroes.adapter?.itemCount!! - 1) {
                        imageButtonNext?.visibility = View.GONE
                    } else {
                        imageButtonNext?.visibility = View.VISIBLE
                    }
                }
            })
        } else {
            textViewNoHeros?.visibility = View.VISIBLE
            viewPagerHeroes?.visibility = View.GONE
            homePageIndicatorView?.visibility = View.GONE
            imageButtonPrevious?.visibility = View.GONE
            imageButtonNext?.visibility = View.GONE
        }

        hideRefreshing()
    }

    override fun init() {
        SuperheroApplication.instance!!.getDependencyComponent().inject(this)
        homePresenter.attachView(this)

        if (Util.isOnline()) {
            simpleSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    // do something on text submit
                    homePresenter.fetchHero(query)
                    return false
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    // do something when text changes
                    return false
                }
            })
        } else {
                homePresenter.getLocalSuperHeroes()
        }

    }

    private fun hideRefreshing() {
        homeSwipeRefresh?.isRefreshing = false
    }

    @SuppressLint("SetTextI18n")
    override fun showDetails(hero: SuperHeroResponse) {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_more_info, null)
        val builder = AlertDialog.Builder(activity).setView(dialogView)
        val dialog = builder.show()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val imageViewDialogClose = dialog.findViewById<ImageView>(R.id.imageViewDialogClose)
        val imageViewCover = dialog.findViewById<ImageView>(R.id.imageViewCover)
        val biographyOption = dialog.findViewById<LinearLayout>(R.id.biographyOption)
        val appearanceOption = dialog.findViewById<LinearLayout>(R.id.appearanceOption)
        val powersOption = dialog.findViewById<LinearLayout>(R.id.powersOption)
        val connectionsOption = dialog.findViewById<LinearLayout>(R.id.connectionsOption)
        val workOption = dialog.findViewById<LinearLayout>(R.id.workOption)
        val heroName = dialog.findViewById<TextView>(R.id.heroName)

        val layoutExpand = dialog.findViewById<LinearLayout>(R.id.layoutExpand)
        val fullName = dialog.findViewById<TextView>(R.id.fullName)
        val alterEgos = dialog.findViewById<TextView>(R.id.alterEgos)
        val placeOfBirth = dialog.findViewById<TextView>(R.id.placeOfBirth)
        val firstAppearance = dialog.findViewById<TextView>(R.id.firstAppearance)
        val publisher = dialog.findViewById<TextView>(R.id.publisher)
        val alignment = dialog.findViewById<TextView>(R.id.alignment)

        val layoutExpandApperance = dialog.findViewById<LinearLayout>(R.id.layoutExpandApperance)
        val gender = dialog.findViewById<TextView>(R.id.gender)
        val race = dialog.findViewById<TextView>(R.id.race)
        val height = dialog.findViewById<TextView>(R.id.height)
        val weight = dialog.findViewById<TextView>(R.id.weight)
        val eyeColor = dialog.findViewById<TextView>(R.id.eyeColor)
        val hairColor = dialog.findViewById<TextView>(R.id.hairColor)

        val layoutExpandPowers = dialog.findViewById<LinearLayout>(R.id.layoutExpandPowers)
        val intelligence = dialog.findViewById<TextView>(R.id.intelligence)
        val strength = dialog.findViewById<TextView>(R.id.strength)
        val speed = dialog.findViewById<TextView>(R.id.speed)
        val durability = dialog.findViewById<TextView>(R.id.durability)
        val power = dialog.findViewById<TextView>(R.id.power)
        val combat = dialog.findViewById<TextView>(R.id.combat)

        val layoutExpandConnect = dialog.findViewById<LinearLayout>(R.id.layoutExpandConnect)
        val groupAffiliation = dialog.findViewById<TextView>(R.id.groupAffiliation)
        val relatives = dialog.findViewById<TextView>(R.id.relatives)

        val layoutExpandWork = dialog.findViewById<LinearLayout>(R.id.layoutExpandWork)
        val occupation = dialog.findViewById<TextView>(R.id.occupation)
        val base = dialog.findViewById<TextView>(R.id.base)

        ImageUtil.loadImage(this, imageViewCover, hero.image!!.url)
        heroName.text = hero.name

        biographyOption.setOnClickListener {
            layoutExpand.visibility = if (layoutExpand.isShown) View.GONE else View.VISIBLE
            fullName.text = "Full name: " + hero.name
            alterEgos.text =  "Alter Egos: " + hero.biography?.alterEgos
            placeOfBirth.text =  "Place Of Birth: " + hero.biography?.placeOfBirth
            firstAppearance.text =  "First Appearance: " + hero.biography?.firstAppearance
            publisher.text =  "Publisher: " + hero.biography?.publisher
            alignment.text =  "Alignment: " + hero.biography?.alignment
        }

        appearanceOption.setOnClickListener {
            layoutExpandApperance.visibility = if (layoutExpandApperance.isShown) View.GONE else View.VISIBLE
            gender.text = "Gender: " + hero.appearance?.gender
            race.text =  "Race: " + hero.appearance?.race
            height.text =  "Height: " + hero.appearance?.height
            weight.text =  "Weight: " + hero.appearance?.weight
            eyeColor.text =  "Eye Color: " + hero.appearance?.eyeColor
            hairColor.text =  "Hair Color: " + hero.appearance?.hairColor
        }

        powersOption.setOnClickListener {
            layoutExpandPowers.visibility = if (layoutExpandPowers.isShown) View.GONE else View.VISIBLE
            intelligence.text = "Intelligence: " + hero.powerstats?.intelligence
            strength.text =  "Strength: " + hero.powerstats?.strength
            speed.text =  "Speed: " + hero.powerstats?.speed
            durability.text =  "Durability: " + hero.powerstats?.durability
            power.text =  "Power: " + hero.powerstats?.power
            combat.text =  "Combat: " + hero.powerstats?.combat
        }

        connectionsOption.setOnClickListener {
            layoutExpandConnect.visibility = if (layoutExpandConnect.isShown) View.GONE else View.VISIBLE
            groupAffiliation.text = "Intelligence: " + hero.connections?.groupAffiliation
            relatives.text =  "Strength: " + hero.connections?.relatives
        }

        workOption.setOnClickListener {
            layoutExpandWork.visibility = if (layoutExpandWork.isShown) View.GONE else View.VISIBLE
            occupation.text = "Occupation: " + hero.work?.occupation
            base.text =  "Base: " + hero.work?.base
        }

        imageViewDialogClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun showOfflineDetails(hero: Superhero) {
        val dialogView = LayoutInflater.from(activity).inflate(R.layout.dialog_more_info, null)
        val builder = AlertDialog.Builder(activity).setView(dialogView)
        val dialog = builder.show()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val imageViewDialogClose = dialog.findViewById<ImageView>(R.id.imageViewDialogClose)
        val imageViewCover = dialog.findViewById<ImageView>(R.id.imageViewCover)
        val biographyOption = dialog.findViewById<LinearLayout>(R.id.biographyOption)
        val appearanceOption = dialog.findViewById<LinearLayout>(R.id.appearanceOption)
        val powersOption = dialog.findViewById<LinearLayout>(R.id.powersOption)
        val connectionsOption = dialog.findViewById<LinearLayout>(R.id.connectionsOption)
        val workOption = dialog.findViewById<LinearLayout>(R.id.workOption)
        val heroName = dialog.findViewById<TextView>(R.id.heroName)

        val layoutExpand = dialog.findViewById<LinearLayout>(R.id.layoutExpand)
        val fullName = dialog.findViewById<TextView>(R.id.fullName)
        val alterEgos = dialog.findViewById<TextView>(R.id.alterEgos)
        val placeOfBirth = dialog.findViewById<TextView>(R.id.placeOfBirth)
        val firstAppearance = dialog.findViewById<TextView>(R.id.firstAppearance)
        val publisher = dialog.findViewById<TextView>(R.id.publisher)
        val alignment = dialog.findViewById<TextView>(R.id.alignment)

        val layoutExpandApperance = dialog.findViewById<LinearLayout>(R.id.layoutExpandApperance)
        val gender = dialog.findViewById<TextView>(R.id.gender)
        val race = dialog.findViewById<TextView>(R.id.race)
        val height = dialog.findViewById<TextView>(R.id.height)
        val weight = dialog.findViewById<TextView>(R.id.weight)
        val eyeColor = dialog.findViewById<TextView>(R.id.eyeColor)
        val hairColor = dialog.findViewById<TextView>(R.id.hairColor)

        val layoutExpandPowers = dialog.findViewById<LinearLayout>(R.id.layoutExpandPowers)
        val intelligence = dialog.findViewById<TextView>(R.id.intelligence)
        val strength = dialog.findViewById<TextView>(R.id.strength)
        val speed = dialog.findViewById<TextView>(R.id.speed)
        val durability = dialog.findViewById<TextView>(R.id.durability)
        val power = dialog.findViewById<TextView>(R.id.power)
        val combat = dialog.findViewById<TextView>(R.id.combat)

        val layoutExpandConnect = dialog.findViewById<LinearLayout>(R.id.layoutExpandConnect)
        val groupAffiliation = dialog.findViewById<TextView>(R.id.groupAffiliation)
        val relatives = dialog.findViewById<TextView>(R.id.relatives)

        val layoutExpandWork = dialog.findViewById<LinearLayout>(R.id.layoutExpandWork)
        val occupation = dialog.findViewById<TextView>(R.id.occupation)
        val base = dialog.findViewById<TextView>(R.id.base)

        ImageUtil.loadImage(this, imageViewCover, hero.imageUrl!!)
        heroName.text = hero.name

        biographyOption.setOnClickListener {
            layoutExpand.visibility = if (layoutExpand.isShown) View.GONE else View.VISIBLE
            fullName.text = "Full name: " + hero.name
            alterEgos.text =  "Alter Egos: " + hero.alterEgos
            placeOfBirth.text =  "Place Of Birth: " + hero.placeOfBirth
            firstAppearance.text =  "First Appearance: " + hero.firstAppearance
            publisher.text =  "Publisher: " + hero.publisher
            alignment.text =  "Alignment: " + hero.alignment
        }

        appearanceOption.setOnClickListener {
            layoutExpandApperance.visibility = if (layoutExpandApperance.isShown) View.GONE else View.VISIBLE
            gender.text = "Gender: " + hero.gender
            race.text =  "Race: " + hero.race
            height.text =  "Height: " + hero.height
            weight.text =  "Weight: " + hero.weight
            eyeColor.text =  "Eye Color: " + hero.eyeColor
            hairColor.text =  "Hair Color: " + hero.hairColor
        }

        powersOption.setOnClickListener {
            layoutExpandPowers.visibility = if (layoutExpandPowers.isShown) View.GONE else View.VISIBLE
            intelligence.text = "Intelligence: " + hero.intelligence
            strength.text =  "Strength: " + hero.strength
            speed.text =  "Speed: " + hero.speed
            durability.text =  "Durability: " + hero.durability
            power.text =  "Power: " + hero.power
            combat.text =  "Combat: " + hero.combat
        }

        connectionsOption.setOnClickListener {
            layoutExpandConnect.visibility = if (layoutExpandConnect.isShown) View.GONE else View.VISIBLE
            groupAffiliation.text = "Intelligence: " + hero.groupAffiliation
            relatives.text =  "Strength: " + hero.relatives
        }

        workOption.setOnClickListener {
            layoutExpandWork.visibility = if (layoutExpandWork.isShown) View.GONE else View.VISIBLE
            occupation.text = "Occupation: " + hero.occupation
            base.text =  "Base: " + hero.base
        }

        imageViewDialogClose.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    override fun showProgress(show: Boolean) {
        progressBarLoaderHome?.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun showErrors() {
        Snackbar.make(
            root, // Parent view
            "Searched Super Hero does not exist, try another one!",
            Snackbar.LENGTH_SHORT // How long to display the message.
        ).show()
    }

    override fun onResume() {
        super.onResume()

        if (!Util.isOnline()) {
            homePresenter.getLocalSuperHeroes()
        }
    }

}
