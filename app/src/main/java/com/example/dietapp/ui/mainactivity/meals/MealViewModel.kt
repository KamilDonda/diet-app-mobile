package com.example.dietapp.ui.mainactivity.meals

import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.dietapp.models.Meal
import com.example.dietapp.ui.filter.FilterViewModel
import kotlinx.coroutines.launch
import java.text.Collator
import java.util.*
import kotlin.collections.ArrayList

class MealViewModel : FilterViewModel() {

    private val _meals = arrayListOf<Meal>()

    val meals = MutableLiveData(ArrayList<Meal>())

    var currentMeal: Meal? = null
        private set

    fun setCurrentMeal(meal: Meal) {
        currentMeal = meal
    }

    fun setCurrentMeal(position: Int) {
        currentMeal = meals.value!![position]
    }

    // todo
    fun temp(): ArrayList<Meal> {
        val meals = ArrayList<Meal>()
        meals.add(
            Meal(
                1,
                "Name1",
                "Do zrobienia zapiekanki potrzebujesz 600 gramów mielonego mięsa. Użyłam szynki wieprzowej, którą zmieliłam w domu" +
                        " na średnich oczkach. Z wieprzowiny polecam też łopatkę, która będzie bardziej tłusta niż szynka.  Dużą cebulę obierz" +
                        " i posiekaj drobno. Posiekaj też obrane pieczarki oraz czosnek. Nagrzej dobrze większą patelnię i dodaj trzy łyżki" +
                        " ulubionego oleju roślinnego do smażenia. Podsmażaj tak warzywa przez 10 minut (bez przykrywki). Mieszaj je co jakiś" +
                        " czas przy pomocy drewnianej łyżki. Na patelnię z podsmażonymi warzywami wyłóż też zmielone mięso oraz przyprawy:" +
                        " łyżeczka słodkiej papryki, pół łyżeczki soli, po 1/4 łyżeczki chili i pieprzu. Zawartość patelni mieszaj przez kilka" +
                        " minut, by dokładnie wymieszać mięso z warzywami. Całość podsmażaj tak przez 10 minut i wyłącz patelnię. Do garnka" +
                        " wlej wodę i zagotuj. Na ugotowanie 400 gramów makaronu potrzebujesz do czterech litrów wody. Wodę posól dopiero," +
                        " gdy zacznie się gotować. Do wrzątku wsyp płaską łyżkę soli. W garnku umieść makaron i gotuj al dente. Ugotowany" +
                        " powinien być sprężysty, ale miękki. Ugotowany makaron przełóż na durszlak. Po ugotowaniu makaron waży u mnie 960" +
                        " gramów. Makaron przełóż do naczynia żaroodpornego, w którym planujesz piec zapiekankę. Naczynie możesz wcześniej" +
                        " wysmarować od środka odrobiną oleju. Na ugotowany makaron wyłóż całą zawartość patelni (mięso mielone z warzywami)." +
                        " Pięć średniej wielkości pomidorów malinowych obierz ze skórki (nie jest to konieczne) i pokrój na mniejsze kawałki" +
                        " i rozłóż na całej powierzchni naczynia. Posiekaj drobno natkę pietruszki i również rozłóż. Większy kawałek ulubionego" +
                        " żółtego sera zetrzyj na tarce, na grubych oczkach i wyłóż na pozostałe składniki. Naczynie z gotową do pieczenia" +
                        " zapiekanką makaronową z mięsem mielonym i warzywami umieść w piekarniku nagrzanym do 185 stopni. Wybierz środkową" +
                        " półkę z opcją pieczenia góra/dół. Zapiekankę piecz bez przykrycia przez 30 minut.",
                "",
                3200f,
                30f,
                64.5f,
                42.536f,
                arrayListOf(
                    "600 g mielonej łopatki wieprzowej",
                    "5 pieczarek",
                    "3 ząbki czosnku",
                    "łyżeczka słodkiej papryki"
                )
            )
        )
        meals.add(Meal(2, "Name2", "", "", 150.21f, 1f, 1f, 1f))
        meals.add(Meal(3, "Name3", "", "", 200.567f, 1f, 1f, 1f))
        meals.add(Meal(4, "Name4", "", "", 32.5f, 1f, 1f, 1f))
        meals.add(Meal(5, "Name5", "", "", 10f, 1f, 1f, 1f))
        meals.add(Meal(6, "Name6", "", "", 15f, 1f, 1f, 1f))
        return meals
    }

    fun prepareMeals() {
        if (_meals.isEmpty()) {
            viewModelScope.launch {
//                _meals.addAll(dbService.db.ingredientDao().selectAll())
                _meals.addAll(temp())
                meals.postValue(_meals)
            }
        }
    }

    var searchText: String = ""
        private set

    fun setSearchText(searchText: String) {
        this.searchText = searchText
    }

    fun search() {
        var data = _meals.filter {
            it.name.contains(searchText, true) &&
                    it.kcal.toFloat() > filter.caloriesMin &&
                    it.proteins.toFloat() > filter.proteinsMin &&
                    it.fats.toFloat() > filter.fatsMin &&
                    it.carbs.toFloat() > filter.carbsMin
        } as ArrayList

        if (filter.caloriesMax != null && filter.caloriesMax != 0) {
            data = data.filter { it.kcal.toFloat() < filter.caloriesMax!! } as ArrayList
        }
        if (filter.proteinsMax != null && filter.proteinsMax != 0) {
            data = data.filter { it.proteins.toFloat() < filter.proteinsMax!! } as ArrayList
        }
        if (filter.fatsMax != null && filter.fatsMax != 0) {
            data = data.filter { it.fats.toFloat() < filter.fatsMax!! } as ArrayList
        }
        if (filter.carbsMax != null && filter.carbsMax != 0) {
            data = data.filter { it.carbs.toFloat() < filter.carbsMax!! } as ArrayList
        }

        when (filter.order) {
            0 -> data.sortWith(Comparator.comparing(Meal::name, Collator.getInstance()))
            1 -> data.sortWith(Comparator.comparing(Meal::name, Collator.getInstance().reversed()))
            2 -> data.sortBy { it.kcal }
            3 -> data.sortByDescending { it.kcal }
            4 -> data.sortBy { it.proteins }
            5 -> data.sortByDescending { it.proteins }
            6 -> data.sortBy { it.carbs }
            7 -> data.sortByDescending { it.carbs }
            8 -> data.sortBy { it.fats }
            9 -> data.sortByDescending { it.fats }
        }

        meals.postValue(data)
    }

    private lateinit var state: Parcelable
    fun saveRecyclerViewState(parcelable: Parcelable) {
        state = parcelable
    }

    fun restoreRecyclerViewState(): Parcelable = state
    fun stateInitialized(): Boolean = ::state.isInitialized
}